package com.felix.slumber.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.felix.slumber.R;
import com.felix.slumber.model.model_article_source;

/**
 * Created by Adrian on 5/30/2018.
 */

public class dialog_article extends AppCompatDialogFragment {

    private dialogListener_worker listener;
    private model_article_source model;
    private ImageView img;
    private TextView tx;
    private TextView title;
    private Button btn;

    public dialog_article() {

    }


    public dialog_article(model_article_source model) {
        this.model = model;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.article_view, null);

        title = view.findViewById(R.id.title);
        img = view.findViewById(R.id.imageView);
        tx = view.findViewById(R.id.textView1);
        btn = view.findViewById(R.id.btn);
        img.setImageURI(model.getImage_res());
        title.setText(getString(model.getResource_title()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tx.setText(Html.fromHtml(getString(model.getResource_content()), Html.FROM_HTML_MODE_LEGACY));
        } else {
            tx.setText(Html.fromHtml(getString(model.getResource_content())));
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.apply_listener(model);
                dismiss();
            }
        });

        //editText.setRawInputType(Configuration.KEYBOARD_12KEY);
        setCancelable(false);
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (dialogListener_worker) getTargetFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public interface dialogListener_worker {
        void apply_listener(model_article_source model);
    }


}
