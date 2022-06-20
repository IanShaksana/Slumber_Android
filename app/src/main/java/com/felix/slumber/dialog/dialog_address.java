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
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.felix.slumber.R;
import com.felix.slumber.adapter.custom_adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrian on 5/30/2018.
 */

public class dialog_address extends AppCompatDialogFragment {

    private dialogListener_worker listener;
    private ListView listView;

    public dialog_address() {

    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_address, null);
        listView = view.findViewById(R.id.list_kota);

        List<String> province = new ArrayList<>();
        province.add("Aceh");
        province.add("Bali");
        province.add("Banten");
        province.add("Bengkulu");
        province.add("Daerah Istimewa Yogyakarta");
        province.add("DKI Jakarta");
        province.add("Gorontalo");
        province.add("Jambi");
        province.add("Jawa Barat");
        province.add("Jawa Tengah");
        province.add("Jawa Timur");
        province.add("Kalimantan Barat");
        province.add("Kalimantan Selatan");
        province.add("Kalimantan Tengah");
        province.add("Kalimantan Timur");
        province.add("Kalimantan Utara");
        province.add("Kepulauan Bangka Belitung");
        province.add("Kepulauan Riau");
        province.add("Lampung");
        province.add("Maluku Utara");
        province.add("Nusa Tenggara Barat");
        province.add("Nusa Tenggara Timur");
        province.add("Papua");
        province.add("Papua Barat");
        province.add("Riau");
        province.add("Sulawesi Barat");
        province.add("Sulawesi Selatan");
        province.add("Sulawesi Tengah");
        province.add("Sulawesi Timur");
        province.add("Sumatra Barat");
        province.add("Sumatra Selatan");
        province.add("Sumatra Utara");


        ListAdapter adapter = new custom_adapter(getContext(), province);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.apply_address(parent.getItemAtPosition(position).toString());
                dismiss();
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
        void apply_address(String add);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);
    }


}
