package com.felix.slumber.activity.forget_pass;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.felix.slumber.R;
import com.felix.slumber.activity.LoginActivity;
import com.felix.slumber.background.http_post;
import com.felix.slumber.model_body.model_profile;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONObject;

public class forget_pass3 extends AppCompatActivity {

    private Button btn;
    private ImageView cross;
    private TextInputEditText reg_pass;
    private TextInputEditText reg_pass_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_pass_layout3);
        btn = findViewById(R.id.done);
        reg_pass = findViewById(R.id.reg_pass);
        reg_pass_confirm = findViewById(R.id.reg_pass_confirm);
        cross = findViewById(R.id.cross);


        final String value = getIntent().getExtras().getString("email");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (reg_pass.getText().toString().equals(reg_pass_confirm.getText().toString())) {
                    String email = value;
                    String password = reg_pass.getText().toString();
                    String name = "";
                    String dob = "";
                    String address = "";
                    String gender = "";
                    // model_profile model = new model_profile(preferences.getString("id", ""), email, password, name, dob, address, gender);
                    model_profile model = new model_profile("", email, password, name, dob, address, gender);
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

            http_post post = new http_post(forget_pass3.this);
            Gson gson = new Gson();
            JSONObject jo = new JSONObject(gson.toJson(model));
            jo.put("api", getString(R.string.change_pass));
            post.execute(jo);
            post.getListener(new http_post.OnUpdateListener() {
                @Override
                public void onUpdate(JSONObject JObject) {

                    try {

                        if (!JObject.getString("statuscode").equals("OK")) {
                            Toast.makeText(forget_pass3.this, "Gagal : " + JObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } else if (JObject.getString("statuscode").equals("no connection")) {
                            Toast.makeText(forget_pass3.this, "Tidak ada koneksi ke server", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(forget_pass3.this, "Success", Toast.LENGTH_SHORT).show();
                            edit_success();
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

    private void edit_success() {
        try {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}