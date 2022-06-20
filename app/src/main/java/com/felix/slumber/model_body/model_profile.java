package com.felix.slumber.model_body;

import android.content.Context;
import android.content.SharedPreferences;

import lombok.Getter;
import lombok.Setter;

import static android.content.Context.MODE_PRIVATE;

@Setter
@Getter

public class model_profile {
    private String id;
    private String email;
    private String password;
    private String name;
    // private Date dob;

    private String dob;
    private String address;
    private String gender;

    public model_profile(String id,String email, String password, String name, String dob, String address, String gender) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.gender = gender;
    }


    public model_profile(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("State", MODE_PRIVATE);
        this.id = preferences.getString("id",null);
        this.email = preferences.getString("email",null);
        this.password = preferences.getString("password",null);
    }
}
