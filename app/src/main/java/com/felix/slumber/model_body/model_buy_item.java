package com.felix.slumber.model_body;
import android.content.Context;
import android.content.SharedPreferences;

import lombok.*;

import static android.content.Context.MODE_PRIVATE;

@Getter
@Setter

public class model_buy_item {

    public model_buy_item(){

    }
    public model_buy_item(Context context, String iditem, String hp, String email) {
        SharedPreferences preferences = context.getSharedPreferences("State", MODE_PRIVATE);
        this.idprofile = preferences.getString("id",null);
        this.iditem = iditem;
        this.hp = hp;
        this.email = email;
    }

    private String iditem;
    private String idprofile;
    private String hp;
    private String email;


}