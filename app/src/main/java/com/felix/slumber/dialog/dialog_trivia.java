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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.felix.slumber.R;

/**
 * Created by Adrian on 5/30/2018.
 */

public class dialog_trivia extends AppCompatDialogFragment {

    private dialogListener_worker listener;
    private TextView tx;
    private Button btn1;
    private Button btn2;

    private String idtrivia;
    private String idtrivia_detail;
    private String question;
    private Integer answer;

    public dialog_trivia() {

    }


    public dialog_trivia(String idtrivia, String idtrivia_detail, String question, Integer answer) {
        this.idtrivia = idtrivia;
        this.idtrivia_detail = idtrivia_detail;
        this.question = question;
        this.answer = answer;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_trivia, null);
        tx = view.findViewById(R.id.question);
        btn1 = view.findViewById(R.id.yes);
        btn2 = view.findViewById(R.id.no);
        tx.setText(question);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer == 1) {
                    listener.apply_listener(true, idtrivia, 1);
                } else {
                    listener.apply_listener(false, idtrivia, 1);
                }
                dismiss();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (answer == 0) {
                    listener.apply_listener(true, idtrivia, 0);
                } else {
                    listener.apply_listener(false, idtrivia, 0);
                }
                dismiss();
            }
        });


        setCancelable(false);
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (dialogListener_worker) getActivity();
    }

    public interface dialogListener_worker {
        void apply_listener(Boolean result, String idtrivia, Integer answer);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);
    }


}
