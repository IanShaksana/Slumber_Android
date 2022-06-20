package com.felix.slumber.drawer.real;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.felix.slumber.R;
import com.felix.slumber.background.http_post;
import com.felix.slumber.model_body.model_login;
import com.google.gson.Gson;

import org.json.JSONObject;

public class setting extends AppCompatActivity {

    private ImageView cross;
    private TextView txt1;
    private TextView txt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_setting);
        cross = findViewById(R.id.cross);
        txt1 = findViewById(R.id.about_us);
        txt2 = findViewById(R.id.logout);

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting.this.startActivity(new Intent(setting.this, about_us.class));
            }
        });


        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model_login model = new model_login(setting.this);
                logout(model);
            }
        });
    }

    private void logout(model_login model) {
        try {

            http_post post = new http_post(setting.this);
            Gson gson = new Gson();
            JSONObject jo = new JSONObject(gson.toJson(model));
            jo.put("api", getString(R.string.logout_api));
            post.execute(jo);
            post.getListener(new http_post.OnUpdateListener() {
                @Override
                public void onUpdate(JSONObject JObject) {

                    try {

                        if (!JObject.getString("statuscode").equals("OK")) {
                            Toast.makeText(setting.this, "Gagal : " + JObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } else if (JObject.getString("statuscode").equals("no connection")) {
                            Toast.makeText(setting.this, "Tidak ada koneksi ke server", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(setting.this, "Logout", Toast.LENGTH_SHORT).show();
                            logout_success();
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

    private void logout_success() {
        try {
            SharedPreferences sh = getSharedPreferences("State", MODE_PRIVATE);
            SharedPreferences.Editor editor = sh.edit();
            editor.putBoolean("Login_State", false);
            editor.commit();
            finishAffinity();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}