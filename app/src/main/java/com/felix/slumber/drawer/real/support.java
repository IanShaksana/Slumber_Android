package com.felix.slumber.drawer.real;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.felix.slumber.R;
import com.felix.slumber.background.http_post;
import com.felix.slumber.general.utility;
import com.felix.slumber.model_body.model_dmmessage;
import com.felix.slumber.model_body.model_login;
import com.felix.slumber.model_body.model_profile;
import com.google.gson.Gson;

import org.json.JSONObject;

public class support extends AppCompatActivity {

    private Button btn;
    private EditText edx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_support);
        btn = findViewById(R.id.done);
        edx = findViewById(R.id.edtInput);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                model_profile model = new model_profile(getApplicationContext());
                model_dmmessage msg = new model_dmmessage(model, edx.getText().toString());
                finish_task(getApplicationContext(), msg);
            }
        });
    }


    private void finish_task(final Context context, model_dmmessage model) {
        try {

            http_post post = new http_post(context);
            Gson gson = new Gson();
            JSONObject jo = new JSONObject(gson.toJson(model));
            jo.put("api", getString(R.string.message));
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
                            Toast.makeText(context, "Message Sent", Toast.LENGTH_SHORT).show();
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