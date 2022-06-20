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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.felix.slumber.R;

/**
 * Created by Adrian on 5/30/2018.
 */

public class dialog_email extends AppCompatDialogFragment {

    private dialogListener_worker listener;
    private String title;
    private String iditem;
    private TextView textView;
    private EditText editText;
    private ImageView cross;
    private Button btn;

    public dialog_email() {

    }

    public dialog_email(String title, String iditem) {
        this.title = title;
        this.iditem = iditem;

    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_text, null);
        editText = view.findViewById(R.id.dialog_input);
        cross = view.findViewById(R.id.cross);
        btn = view.findViewById(R.id.btn);
        textView = view.findViewById(R.id.text);

        textView.setText("Input your " + title + " email account");

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dialog = editText.getText().toString();
                if (dialog != null) {
                    listener.apply_email(dialog, iditem);
                    dismiss();
                }
            }
        });
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (dialogListener_worker) getActivity();
    }

    public interface dialogListener_worker {
        void apply_email(String email, String iditem);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);
    }


}
