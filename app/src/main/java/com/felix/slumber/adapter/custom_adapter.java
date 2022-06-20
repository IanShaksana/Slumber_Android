package com.felix.slumber.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.felix.slumber.R;

import java.util.List;


public class custom_adapter extends ArrayAdapter<String> {

    public custom_adapter(@NonNull Context context, List<String> resource) {
        super(context, R.layout.dialog_address, resource);
    }


    private TextView commitment_name;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View cusView = inflater.inflate(R.layout.dialog_address_detail, parent, false);
        commitment_name = cusView.findViewById(R.id.nm_province);
        commitment_name.setText(getItem(position).toString());

        return cusView;

    }

}