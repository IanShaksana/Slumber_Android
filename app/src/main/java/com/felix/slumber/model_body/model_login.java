package com.felix.slumber.model_body;

import android.content.Context;
import android.content.SharedPreferences;

import lombok.Getter;
import lombok.Setter;

import static android.content.Context.MODE_PRIVATE;

@Setter
@Getter

public class model_login {
    private String email;
    private String password;

    public model_login(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public model_login(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("State", MODE_PRIVATE);
        this.email = preferences.getString("email",null);
        this.password = preferences.getString("password",null);
    }
}
