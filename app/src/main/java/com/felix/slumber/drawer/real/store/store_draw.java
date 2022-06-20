package com.felix.slumber.drawer.real.store;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.felix.slumber.R;
import com.felix.slumber.background.http_post;
import com.felix.slumber.dialog.dialog_email;
import com.felix.slumber.dialog.dialog_hp;
import com.felix.slumber.general.utility;
import com.felix.slumber.model.model_store_item;
import com.felix.slumber.model_body.model_buy_item;
import com.felix.slumber.model_body.model_login;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class store_draw extends AppCompatActivity implements dialog_email.dialogListener_worker, dialog_hp.dialogListener_worker {

    private Button btn;
    private MaterialTextView text;
    private SharedPreferences sh;
    private List<String> id;
    private ImageView cross;

    GridView simpleList;
    ArrayList<model_store_item> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_store);
        text = findViewById(R.id.text);
        cross = findViewById(R.id.cross);


        model_login model = new model_login(getApplicationContext());
        new utility().update_profile(model, getApplicationContext());
        sh = getSharedPreferences("State", MODE_PRIVATE);

        text.setText("You Have " + sh.getInt("point", 0) + " Point. Exchange your points and enjoy the reward !");

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /*
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(store_draw.this, v);
                menu.getMenu().add("One");
                menu.getMenu().add("Two");
                menu.getMenu().add("Three");

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(store_draw.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                menu.show();
            }
        });

         */

        simpleList = findViewById(R.id.grid_store_view);
        itemList.add(new model_store_item("spotify", R.mipmap.ic_spotify_round, "1 Month", "Spotify Premium", 25));
        itemList.add(new model_store_item("disney", R.mipmap.ic_disney_round, "1 Month", "Disney+ Hotstar", 40));
        itemList.add(new model_store_item("netflix", R.mipmap.ic_netflix_round, "1 Month", "Netflix", 50));
        itemList.add(new model_store_item("youtube", R.mipmap.ic_youtube_round, "1 Month", "Youtube Premium", 25));
        itemList.add(new model_store_item("canva", R.mipmap.ic_canva_round, "1 Month", "Canva Premium", 10));
        itemList.add(new model_store_item("telkom", R.mipmap.ic_telkomsel_round, "Telkomsel Credit", "25.000", 25));
        itemList.add(new model_store_item("xl", R.mipmap.ic_xl_round, "XL Credit", "25.000", 25));
        itemList.add(new model_store_item("byu", R.mipmap.ic_byu_round, "By.U Credit", "20.000", 20));
        itemList.add(new model_store_item("indosat", R.mipmap.ic_indosat_round, "Indosat Credit", "25.000", 25));


        id = new ArrayList<>();
        id.add("telkom");
        id.add("xl");
        id.add("byu");
        id.add("indosat");
        store_item2 myAdapter = new store_item2(this, R.layout.drawer_store_item, itemList);
        simpleList.setAdapter(myAdapter);

    }


    public class store_item2 extends ArrayAdapter {

        ArrayList<model_store_item> itemList = new ArrayList<>();

        Context context;

        public store_item2(Context context, int textViewResourceId, ArrayList<model_store_item> objects) {
            super(context, textViewResourceId, objects);
            itemList = objects;
            this.context = context;
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
            TextView textView3 = (TextView) v.findViewById(R.id.textView3);
            ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
            Button btn = v.findViewById(R.id.claim);
            textView1.setText(itemList.get(position).getTitle());
            textView2.setText(itemList.get(position).getSubtitle());
            textView3.setText(String.valueOf(itemList.get(position).getPoint()) + " Points");
            imageView.setImageResource(itemList.get(position).getIc_src());
            if (sh.getInt("point", 0) < itemList.get(position).getPoint()) {
                btn.setEnabled(false);
                btn.setAlpha((float) 0.25);
            }
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer point = sh.getInt("point", 0);
                    Integer item_point = itemList.get(position).getPoint();
                    if (point >= item_point) {
                        /*
                        Integer new_point = point - item_point;
                        sh = getSharedPreferences("State", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sh.edit();
                        editor.putInt("point", new_point);
                        editor.commit();
                        text.setText("You Have " + sh.getInt("point", 0) + " Point. Exchange your points and enjoy the reward !");
                        */

                        if (id.contains(itemList.get(position).getId())) {
                            opendialog_hp(itemList.get(position).getTitle(), itemList.get(position).getId());
                        } else {
                            opendialog_email(itemList.get(position).getSubtitle(), itemList.get(position).getId());
                        }

                    } else {
                        Snackbar.make(v, "Insufficient Points", Snackbar.LENGTH_SHORT).show();
                    }

                }
            });
            return v;

        }


    }

    private void claim_reward(model_buy_item model) {

        try {
            http_post post = new http_post(getApplicationContext());
            Gson gson = new Gson();
            JSONObject jo = new JSONObject(gson.toJson(model));
            jo.put("api", getString(R.string.shop_buy));
            post.execute(jo);
            post.getListener(new http_post.OnUpdateListener() {
                @Override
                public void onUpdate(JSONObject JObject) {

                    try {
                        if (!JObject.getString("statuscode").equals("OK")) {
                            Toast.makeText(getApplicationContext(), "Gagal : " + JObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } else if (JObject.getString("statuscode").equals("no connection")) {
                            Toast.makeText(getApplicationContext(), "Tidak ada koneksi ke server", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

                            model_login model = new model_login(getApplicationContext());
                            new utility().update_profile(model, getApplicationContext());
                            sh = getSharedPreferences("State", MODE_PRIVATE);
                            finish();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void apply_email(String email, String idtitem) {
        model_buy_item model = new model_buy_item(getApplicationContext(), idtitem, "", email);
        claim_reward(model);
    }

    @Override
    public void apply_hp(String hp, String iditem) {
        model_buy_item model = new model_buy_item(getApplicationContext(), iditem, hp, "");
        claim_reward(model);
    }

    private void opendialog_hp(String title, String iditem) {
        dialog_hp dialogfragment = new dialog_hp(title, iditem);
        dialogfragment.show(getSupportFragmentManager(), "exa");
    }

    private void opendialog_email(String title, String iditem) {
        dialog_email dialogfragment = new dialog_email(title, iditem);
        dialogfragment.show(getSupportFragmentManager(), "exa");
    }


}