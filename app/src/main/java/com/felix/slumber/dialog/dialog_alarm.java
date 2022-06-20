package com.felix.slumber.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.felix.slumber.R;

/**
 * Created by Adrian on 5/30/2018.
 */

public class dialog_alarm extends AppCompatDialogFragment {

    private dialogListener_worker listener;
    private Button btn1;
    private Button btn2;
    private Button btn3;

    public dialog_alarm() {

    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_alarm, null);
        btn1 = view.findViewById(R.id.alarm1);
        btn2 = view.findViewById(R.id.alarm2);
        btn3 = view.findViewById(R.id.alarm3);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.apply_listener("alarm1");
                dismiss();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.apply_listener("alarm2");
                dismiss();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.apply_listener("alarm3");
                dismiss();
            }
        });


        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (dialogListener_worker) getTargetFragment();
    }

    public interface dialogListener_worker {
        void apply_listener(String idalarm);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);
    }


}
