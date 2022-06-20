package com.felix.slumber.drawer.real.store;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.felix.slumber.R;
import com.felix.slumber.model.model_store_item;

import java.util.ArrayList;

public class store_item extends ArrayAdapter {

    ArrayList<model_store_item> itemList = new ArrayList<>();

    public store_item(Context context, int textViewResourceId, ArrayList<model_store_item> objects) {
        super(context, textViewResourceId, objects);
        itemList = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.drawer_store_item, null);
        TextView textView1 = (TextView) v.findViewById(R.id.textView1);
        TextView textView2 = (TextView) v.findViewById(R.id.textView2);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
        Button btn = v.findViewById(R.id.claim);
        textView1.setText(itemList.get(position).getTitle());
        textView2.setText(itemList.get(position).getSubtitle());
        imageView.setImageResource(itemList.get(position).getIc_src());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "You Clicked : " + itemList.get(position).getId(), Toast.LENGTH_SHORT).show();
            }
        });
        return v;

    }

}