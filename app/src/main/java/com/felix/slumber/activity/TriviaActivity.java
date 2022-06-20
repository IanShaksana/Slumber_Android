package com.felix.slumber.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.felix.slumber.R;
import com.felix.slumber.background.http_post;
import com.felix.slumber.general.utility;
import com.felix.slumber.model_body.model_activity;
import com.felix.slumber.model_body.model_login;
import com.felix.slumber.model_body.model_profile;
import com.google.gson.Gson;

import org.json.JSONObject;

public class TriviaActivity extends AppCompatActivity {

    private TextView question_text;

    private Button yes;
    private Button no;
    private Button close;

    String idtrivia = "";
    String idtrivia_detail = "";
    String question = "";
    Integer answer = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        Intent intent = getIntent();
        if (null != intent) {
            idtrivia = intent.getStringExtra("idtrivia");
            idtrivia_detail = intent.getStringExtra("idtrivia_detail");
            question = intent.getStringExtra("question");
            answer = intent.getExtras().getInt("answer");
        }

        question_text = findViewById(R.id.question);
        question_text.setText(question);

        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer == 1) {
                    yes.setBackgroundColor(Color.rgb(0, 255, 0));
                    no.setBackgroundColor(Color.rgb(255, 0, 0));
                    Toast.makeText(TriviaActivity.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                } else {
                    no.setBackgroundColor(Color.rgb(0, 255, 0));
                    yes.setBackgroundColor(Color.rgb(255, 0, 0));
                    Toast.makeText(TriviaActivity.this, "Wrong Answer", Toast.LENGTH_SHORT).show();
                }
                yes.setEnabled(false);
                no.setEnabled(false);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer == 0) {
                    no.setBackgroundColor(Color.rgb(0, 255, 0));
                    yes.setBackgroundColor(Color.rgb(255, 0, 0));
                    Toast.makeText(TriviaActivity.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                } else {
                    yes.setBackgroundColor(Color.rgb(0, 255, 0));
                    no.setBackgroundColor(Color.rgb(255, 0, 0));
                    Toast.makeText(TriviaActivity.this, "Wrong Answer", Toast.LENGTH_SHORT).show();
                }
                yes.setEnabled(false);
                no.setEnabled(false);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model_activity model = new model_activity(new model_profile(TriviaActivity.this), "TRIVIA", "", "", idtrivia);
                finish_task(TriviaActivity.this, model);
            }
        });
    }

    private void finish_task(final Context context, model_activity model) {
        try {


            http_post post = new http_post(context);
            Gson gson = new Gson();
            JSONObject jo = new JSONObject(gson.toJson(model));
            jo.put("api", getString(R.string.finish_task));
            post.execute(jo);
            post.getListener(new http_post.OnUpdateListener() {
                @Override
                public void onUpdate(JSONObject JObject) {

                    try {

                        model_login model = new model_login(getApplicationContext());
                        new utility().update_profile(model, getApplicationContext());

                        if (!JObject.getString("statuscode").equals("OK")) {
                            Toast.makeText(context, "Gagal : " + JObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } else if (JObject.getString("statuscode").equals("no connection")) {
                            Toast.makeText(context, "Tidak ada koneksi ke server", Toast.LENGTH_SHORT).show();
                        } else {
                            // Toast.makeText(context, "Sukses", Toast.LENGTH_SHORT).show();
                            finish();
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

}