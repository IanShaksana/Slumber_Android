package com.felix.slumber.fragment.menu_achievement;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.felix.slumber.R;
import com.felix.slumber.general.utility;
import com.felix.slumber.model_body.model_login;
import com.google.android.material.button.MaterialButton;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by Adrian on 5/18/2018.
 */

public class achievement_3 extends Fragment {

    private MaterialButton btn_right;
    private MaterialButton btn_left;
    private TextView point;
    private String idtask;

    private SharedPreferences sh;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.menu_fragment_achievement_2, container, false);

        btn_right = view.findViewById(R.id.right);
        btn_left = view.findViewById(R.id.left);
        point = view.findViewById(R.id.point);
        sh = getContext().getSharedPreferences("State", MODE_PRIVATE);
        model_login model = new model_login(getContext());
        new utility().update_profile(model, getContext());

        point.setText(sh.getInt("point",0)+"");


        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new achievement_1()).commit();

            }
        });

        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new achievement_2()).commit();
            }
        });



        return view;
    }



    private void update() {
        Fragment current = getFragmentManager().findFragmentById(R.id.main_fragment);
        if (current instanceof achievement_1) {
            getFragmentManager().beginTransaction().detach(current).attach(current).commit();
        }
    }



}