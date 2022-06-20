package com.felix.slumber.drawer.real;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.felix.slumber.R;
import com.felix.slumber.activity.MainActivity;
import com.felix.slumber.background.http_post;
import com.felix.slumber.general.utility;
import com.felix.slumber.model_body.model_login;
import com.felix.slumber.model_body.model_profile;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONObject;

public class profile2 extends AppCompatActivity {

    private Button btn;

    private ImageView cross;
    private TextInputEditText reg_pass;
    private TextInputEditText reg_pass_confirm;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_pass_layout3);
        btn = findViewById(R.id.done);
        reg_pass = findViewById(R.id.reg_pass);
        reg_pass_confirm = findViewById(R.id.reg_pass_confirm);
        cross = findViewById(R.id.cross);

        preferences = getSharedPreferences("State", MODE_PRIVATE);
        model_login model = new model_login(getApplicationContext());
        new utility().update_profile(model, getApplicationContext());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reg_pass.getText().toString().equals(reg_pass_confirm.getText().toString())) {
                    String email = preferences.getString("email", "");
                    String password = reg_pass.getText().toString();
                    String name = preferences.getString("name", "");
                    String dob = preferences.getString("dob", "");
                    String address = preferences.getString("address", "");
                    String gender = preferences.getString("gender", "");
                    // model_profile model = new model_profile(preferences.getString("id", ""), email, password, name, dob, address, gender);
                    model_profile model = new model_profile(preferences.getString("id", ""), email, password, name, dob, address, gender);
                    edit(model);
                }
                finish();
            }
        });


        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    private void edit(model_profile model) {
        try {

            http_post post = new http_post(profile2.this);
            Gson gson = new Gson();
            JSONObject jo = new JSONObject(gson.toJson(model));
            jo.put("api", getString(R.string.edit_profile_api));
            post.execute(jo);
            post.getListener(new http_post.OnUpdateListener() {
                @Override
                public void onUpdate(JSONObject JObject) {

                    try {

                        if (!JObject.getString("statuscode").equals("OK")) {
                            Toast.makeText(profile2.this, "Gagal : " + JObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } else if (JObject.getString("statuscode").equals("no connection")) {
                            Toast.makeText(profile2.this, "Tidak ada koneksi ke server", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(profile2.this, "Success", Toast.LENGTH_SHORT).show();
                            edit_success(JObject);
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

    private void edit_success(JSONObject JObject) {
        try {

            model_login model = new model_login(getApplicationContext());
            new utility().update_profile(model, getApplicationContext());
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }






}