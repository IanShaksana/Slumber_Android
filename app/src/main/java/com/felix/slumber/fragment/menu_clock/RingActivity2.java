package com.felix.slumber.fragment.menu_clock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.felix.slumber.R;
import com.felix.slumber.activity.MainActivity;
import com.felix.slumber.background.http_post;
import com.felix.slumber.model.model_soal;
import com.felix.slumber.model_body.model_profile;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RingActivity2 extends AppCompatActivity {
    private Button dismiss;
    private Button ans_btn;
    private TextView question;
    private EditText answer;

    Integer angka_1_soal;
    Integer angka_2_soal;
    Integer angka_3_soal;
    Integer angka_4_soal;
    String tanda_1_soal;
    String tanda_2_soal;

    private String string_access_1 = "WAKE_TIME_HOUR";
    private String string_access_2 = "WAKE_TIME_MINUTE";
    List<model_soal> model_soals = new ArrayList<>();

    Integer counter_correct = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring_wake);
        dismiss = findViewById(R.id.dismiss);
        ans_btn = findViewById(R.id.ans_btn);
        question = findViewById(R.id.question);
        answer = findViewById(R.id.answer);
        dismiss.setAlpha((float) 0.25);
        dismiss.setEnabled(false);
        SharedPreferences sh = getSharedPreferences("State", MODE_PRIVATE);
        for (int i = 1; i <= 3; i++) {
            model_soals.add(populate_question());
        }


        angka_1_soal = model_soals.get(counter_correct).getAngka_1();
        angka_2_soal = model_soals.get(counter_correct).getAngka_2();
        angka_3_soal = model_soals.get(counter_correct).getAngka_3();
        angka_4_soal = model_soals.get(counter_correct).getAngka_4();

        tanda_1_soal = model_soals.get(counter_correct).getTanda_1();
        tanda_2_soal = model_soals.get(counter_correct).getTanda_2();
        question.setText(angka_1_soal + " " + tanda_1_soal + " " + angka_2_soal + " " + tanda_2_soal + " " + angka_3_soal);


        if (sh.getInt(string_access_1, 99) == 99 && sh.getInt(string_access_2, 99) == 99) {
            RingActivity2.this.startActivity(new Intent(RingActivity2.this, MainActivity.class));
            finish();
        }

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentService = new Intent(getApplicationContext(), alarm_service2.class);
                getApplicationContext().stopService(intentService);
                SharedPreferences sh = getSharedPreferences("State", MODE_PRIVATE);

                SharedPreferences.Editor editor = sh.edit();
                editor.putInt(string_access_1, 99);
                editor.putInt(string_access_2, 99);
                editor.commit();
                report(new model_profile(getApplicationContext()));
                finish();
            }
        });

        ans_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer answer_soal = Integer.parseInt(answer.getText().toString());
                if (answer_soal == angka_4_soal) {
                    Toast.makeText(getApplicationContext(), "Correct Answer",Toast.LENGTH_SHORT).show();
                    counter_correct++;
                    if (counter_correct == 3) {
                        dismiss.setAlpha((float) 1.0);
                        dismiss.setEnabled(true);
                        ans_btn.setAlpha((float) 0.25);
                        ans_btn.setEnabled(false);
                    } else {
                        angka_1_soal = model_soals.get(counter_correct).getAngka_1();
                        angka_2_soal = model_soals.get(counter_correct).getAngka_2();
                        angka_3_soal = model_soals.get(counter_correct).getAngka_3();
                        angka_4_soal = model_soals.get(counter_correct).getAngka_4();
                        tanda_1_soal = model_soals.get(counter_correct).getTanda_1();
                        tanda_2_soal = model_soals.get(counter_correct).getTanda_2();
                        question.setText(angka_1_soal + " " + tanda_1_soal + " " + angka_2_soal + " " + tanda_2_soal + " " + angka_3_soal);
                        answer.setText(null);
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Wrong Answer",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void report(model_profile model) {
        try {
            http_post post = new http_post(RingActivity2.this);
            Gson gson = new Gson();
            JSONObject jo = new JSONObject(gson.toJson(model));
            jo.put("api", getString(R.string.report_wake));
            post.execute(jo);
            post.getListener(new http_post.OnUpdateListener() {
                @Override
                public void onUpdate(JSONObject JObject) {

                    try {

                        if (!JObject.getString("statuscode").equals("OK")) {
                            Toast.makeText(RingActivity2.this, "Gagal : " + JObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } else if (JObject.getString("statuscode").equals("no connection")) {
                            Toast.makeText(RingActivity2.this, "Tidak ada koneksi ke server", Toast.LENGTH_SHORT).show();
                        } else {


                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private model_soal populate_question() {

        List<String> l_tanda = new ArrayList<>();
        l_tanda.add("+");
        l_tanda.add("-");


        Random rn = new Random();
        String tanda_1 = l_tanda.get(rn.nextInt(1 - 0 + 1) + 0);
        String tanda_2 = l_tanda.get(rn.nextInt(1 - 0 + 1) + 0);
        Integer angka_1 = rn.nextInt(100 - 1 + 1) + 1;
        Integer angka_2 = rn.nextInt(angka_1 - 1 + 1) + 1;
        Integer angka12;
        if (tanda_1.equalsIgnoreCase("+")) {
            angka12 = angka_1 + angka_2;
        } else {
            angka12 = angka_1 - angka_2;
        }
        Integer angka_3 = rn.nextInt(angka12 - 1 + 1) + 1;
        Integer angka123;
        if (tanda_2.equalsIgnoreCase("+")) {
            angka123 = angka12 + angka_3;
        } else {
            angka123 = angka12 - angka_3;
        }

        model_soal model_soal = new model_soal(angka_1, angka_2, angka_3, tanda_1, tanda_2, angka123);

        return model_soal;
    }


}
