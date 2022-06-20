package com.felix.slumber.fragment.menu_achievement;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.felix.slumber.R;
import com.felix.slumber.background.http_post;
import com.felix.slumber.general.utility;
import com.felix.slumber.model_body.model_activity;
import com.felix.slumber.model_body.model_login;
import com.felix.slumber.model_body.model_profile;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import org.json.JSONObject;


/**
 * Created by Adrian on 5/18/2018.
 */

public class achievement_1 extends Fragment {

    private MaterialButton btn_right;
    private MaterialButton btn_left;
    private ProgressBar Pbar;
    private Button finish;
    private TextView title;
    private String idtask;
    private TextView progress_text;
    private TextView nilai;

    private SharedPreferences sh;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        get_progress(new model_profile(getContext()));
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.menu_fragment_achievement_1, container, false);

        btn_right = view.findViewById(R.id.right);
        btn_left = view.findViewById(R.id.left);
        finish = view.findViewById(R.id.finish);
        title = view.findViewById(R.id.title);
        Pbar = view.findViewById(R.id.progress);
        progress_text = view.findViewById(R.id.progress_text);
        nilai = view.findViewById(R.id.nilai);
        title.setSelected(true);

        model_login model = new model_login(getContext());
        new utility().update_profile(model, getContext());


        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new achievement_2()).commit();

            }
        });

        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new achievement_3()).commit();
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model_activity model = new model_activity(new model_profile(getContext()), idtask, "", "", "", "");
                claim_reward(model);
            }
        });


        return view;
    }

    private void get_progress(model_profile model) {
        try {
            http_post post = new http_post(getContext());
            Gson gson = new Gson();
            JSONObject jo = new JSONObject(gson.toJson(model));
            jo.put("api", getString(R.string.get_daily_api));
            post.execute(jo);
            post.getListener(new http_post.OnUpdateListener() {
                @Override
                public void onUpdate(JSONObject JObject) {

                    try {

                        if (!JObject.getString("statuscode").equals("OK")) {
                            Toast.makeText(getContext(), "Gagal : " + JObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } else if (JObject.getString("statuscode").equals("no connection")) {
                            Toast.makeText(getContext(), "Tidak ada koneksi ke server", Toast.LENGTH_SHORT).show();
                        } else {

                            JSONObject data = JObject.getJSONArray("data").getJSONObject(0);
                            if (!data.isNull("daily")) {
                                JSONObject daily = data.getJSONObject("daily");
                                idtask = daily.getString("id");
                                get_mission_daily(data.getInt("progress_daily"), 1, data.getString("title_daily"), daily.getInt("status"), data.getInt("point_daily"));
                            } else {
                                // Toast.makeText(getContext(), "Sukses", Toast.LENGTH_SHORT).show();

                                title.setText("No daily mission");
                                finish.setEnabled(false);
                                finish.setAlpha((float) 0.25);
                                Pbar.setProgress(0);
                            }

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

    private void get_mission_daily(Integer progress_value, Integer rep_value, String title_text, Integer status, Integer point) {
        Integer progress = progress_value;
        Integer rep = rep_value;
        String desc = title_text + " (Daily)";


        nilai.setText(point + " pts");
        title.setText(desc);
        Pbar.setMax(rep);
        Pbar.setProgress(progress);

        progress_text.setText(progress_value + "/" + rep_value);

        if (progress == rep && status == 1) {
            finish.setEnabled(true);
        } else {
            finish.setAlpha((float) 0.25);
            finish.setEnabled(false);
        }

    }

    private void claim_reward(model_activity model) {

        try {
            http_post post = new http_post(getContext());
            Gson gson = new Gson();
            JSONObject jo = new JSONObject(gson.toJson(model));
            jo.put("api", getString(R.string.claim_reward));
            post.execute(jo);
            post.getListener(new http_post.OnUpdateListener() {
                @Override
                public void onUpdate(JSONObject JObject) {

                    try {

                        if (!JObject.getString("statuscode").equals("OK")) {
                            Toast.makeText(getContext(), "Gagal : " + JObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } else if (JObject.getString("statuscode").equals("no connection")) {
                            Toast.makeText(getContext(), "Tidak ada koneksi ke server", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Sukses", Toast.LENGTH_SHORT).show();
                            update();
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

    private void update() {
        Fragment current = getFragmentManager().findFragmentById(R.id.main_fragment);
        if (current instanceof achievement_1) {
            getFragmentManager().beginTransaction().detach(current).attach(current).commit();
        }
    }


}