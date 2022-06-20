package com.felix.slumber.general;

import android.content.Context;
import android.content.SharedPreferences;

import com.felix.slumber.R;
import com.felix.slumber.background.http_post;
import com.felix.slumber.model_body.model_login;
import com.google.gson.Gson;

import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

public class utility {

    public void update_profile(model_login model, final Context context) {
        try {

            SharedPreferences preferences = context.getSharedPreferences("State", MODE_PRIVATE);
            final SharedPreferences.Editor editor = preferences.edit();

            http_post post = new http_post(context);
            Gson gson = new Gson();
            JSONObject jo = new JSONObject(gson.toJson(model));
            jo.put("api", context.getString(R.string.profile_api));
            post.execute(jo);
            post.getListener(new http_post.OnUpdateListener() {
                @Override
                public void onUpdate(JSONObject JObject) {

                    try {

                        JSONObject data = JObject.getJSONArray("data").getJSONObject(0);
                        editor.putString("id", data.getString("id"));
                        editor.putString("email", data.getString("email"));
                        editor.putString("password", data.getString("password"));
                        editor.putString("name", data.getString("name"));
                        editor.putString("dob", data.getString("dob"));
                        editor.putString("address", data.getString("address"));
                        editor.putInt("point",data.getInt("point"));
                        editor.putString("gender", data.getString("gender"));
                        editor.commit();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void update_saldo(model_login model, final Context context) {
        try {

            SharedPreferences preferences = context.getSharedPreferences("State", MODE_PRIVATE);
            final SharedPreferences.Editor editor = preferences.edit();

            http_post post = new http_post(context);
            Gson gson = new Gson();
            JSONObject jo = new JSONObject(gson.toJson(model));
            jo.put("api", context.getString(R.string.login_api));
            post.execute(jo);
            post.getListener(new http_post.OnUpdateListener() {
                @Override
                public void onUpdate(JSONObject JObject) {

                    try {

                        editor.putString("email", JObject.getString("email"));
                        editor.putString("password", JObject.getString("password"));
                        editor.putString("name", JObject.getString("name"));
                        editor.putString("dob", JObject.getString("dob"));
                        editor.putString("address", JObject.getString("address"));

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
