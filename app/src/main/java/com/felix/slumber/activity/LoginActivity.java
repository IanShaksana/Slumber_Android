package com.felix.slumber.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.felix.slumber.R;
import com.felix.slumber.activity.forget_pass.forget_pass1;
import com.felix.slumber.background.http_post;
import com.felix.slumber.model_body.model_login;
import com.google.gson.Gson;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {


    private Button btn;
    private TextView txt1;
    private TextView txt2;
    private TextView userInput;
    private TextView passInput;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        btn = (Button) findViewById(R.id.signIn);
        txt1 = (TextView) findViewById(R.id.signUp);
        txt2 = (TextView) findViewById(R.id.forgetPass);

        userInput = (TextView) findViewById(R.id.userInput);
        passInput = (TextView) findViewById(R.id.passInput);
        preferences = getSharedPreferences("State", MODE_PRIVATE);
        if (preferences.getBoolean("Login_State", false)) {
            LoginActivity.this.startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
                */
                model_login model = new model_login(userInput.getText().toString(), passInput.getText().toString());
                login(model);
                btn.setEnabled(false);

            }
        });

        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, SignUp.class));
            }
        });

        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, forget_pass1.class));
            }
        });
    }


    private void login(model_login model) {
        try {

            http_post post = new http_post(LoginActivity.this);
            Gson gson = new Gson();
            JSONObject jo = new JSONObject(gson.toJson(model));
            jo.put("api", getString(R.string.login_api));
            post.execute(jo);
            post.getListener(new http_post.OnUpdateListener() {
                @Override
                public void onUpdate(JSONObject JObject) {

                    try {


                        if (!JObject.getString("statuscode").equals("OK")) {
                            Toast.makeText(LoginActivity.this, "Gagal : " + JObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } else if (JObject.getString("statuscode").equals("no connection")) {
                            Toast.makeText(LoginActivity.this, "Tidak ada koneksi ke server", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            login_success(JObject);
                        }

                        btn.setEnabled(true);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void login_success(JSONObject JObject) {
        try {
            JSONObject data = JObject.getJSONArray("data").getJSONObject(0);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("Login_State", true);

            editor.putString("id", data.getString("id"));
            editor.putString("email", data.getString("email"));
            editor.putString("password", data.getString("password"));
            editor.putString("name", data.getString("name"));
            editor.putString("dob", data.getString("dob"));
            editor.putString("address", data.getString("address"));
            editor.putInt("point", data.getInt("point"));
            editor.putString("gender", data.getString("gender"));
            /*
            editor.putInt("Point", 0);
            editor.putBoolean("isPlaying", false);
            editor.putBoolean("isPause", false);
            editor.putBoolean("isShuffle", false);
            editor.putInt("isRepeat", 0);
            */
            editor.commit();
            LoginActivity.this.startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}