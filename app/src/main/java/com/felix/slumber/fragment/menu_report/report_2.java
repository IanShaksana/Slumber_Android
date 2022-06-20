package com.felix.slumber.fragment.menu_report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.felix.slumber.R;
import com.felix.slumber.background.http_post;
import com.felix.slumber.model.model_sleepdata;
import com.felix.slumber.model_body.model_profile;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Calendar;


/**
 * Created by Adrian on 5/18/2018.
 */

public class report_2 extends Fragment {

    private MaterialButton btn_left;
    private MaterialButton btn_right;
    private TextView av1;
    private TextView av2;
    private TextView av3;
    private TextView av4;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.menu_fragment_report_2, container, false);

        av1 = view.findViewById(R.id.av1);
        av2 = view.findViewById(R.id.av2);
        av3 = view.findViewById(R.id.av3);
        av4 = view.findViewById(R.id.av4);
        btn_right = view.findViewById(R.id.right);
        btn_left = view.findViewById(R.id.left);
        btn_right.setVisibility(View.INVISIBLE);
        btn_left.setVisibility(View.INVISIBLE);

        get_progress(new model_profile(getContext()));
        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new report_1()).commit();
            }
        });

        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new report_1()).commit();
            }
        });


        return view;
    }

    private void get_progress(model_profile model) {
        try {
            http_post post = new http_post(getContext());
            Gson gson = new Gson();
            JSONObject jo = new JSONObject(gson.toJson(model));
            jo.put("api", getString(R.string.report_get));
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
                            model_sleepdata model = new Gson().fromJson(data.toString(), model_sleepdata.class);
                            av1.setText("Avg. Insomnia: " + model.getAvg_insomina());
                            av2.setText("Avg. Sleep Time: " + model.getAvg_sleep_time() + " hours");

                            if (model.getSleep_time() != null) {
                                Calendar c_sleep = Calendar.getInstance();
                                c_sleep.setTime(model.getSleep_time());
                                String sleep_hour;
                                String sleep_minute;
                                if (c_sleep.get(Calendar.MINUTE) < 10) {
                                    sleep_minute = "0" + c_sleep.get(Calendar.MINUTE);
                                } else {
                                    sleep_minute = "" + c_sleep.get(Calendar.MINUTE);
                                }
                                if (c_sleep.get(Calendar.HOUR_OF_DAY) < 10) {
                                    sleep_hour = "0" + c_sleep.get(Calendar.HOUR_OF_DAY);
                                } else {
                                    sleep_hour = "" + c_sleep.get(Calendar.HOUR_OF_DAY);
                                }
                                String parse_sleep_time = sleep_hour + ":" + sleep_minute;
                                av3.setText("Avg. Sleeping Time: " + parse_sleep_time);
                            } else {
                                av3.setText("Avg. Sleeping Time: --");
                            }


                            if (model.getWake_time() != null) {
                                Calendar c_wake = Calendar.getInstance();
                                c_wake.setTime(model.getWake_time());
                                String wake_hour;
                                String wake_minute;
                                if (c_wake.get(Calendar.MINUTE) < 10) {
                                    wake_minute = "0" + c_wake.get(Calendar.MINUTE);
                                } else {
                                    wake_minute = "" + c_wake.get(Calendar.MINUTE);
                                }
                                if (c_wake.get(Calendar.HOUR_OF_DAY) < 10) {
                                    wake_hour = "0" + c_wake.get(Calendar.HOUR_OF_DAY);
                                } else {
                                    wake_hour = "" + c_wake.get(Calendar.HOUR_OF_DAY);
                                }
                                String parse_wake_time = wake_hour + ":" + wake_minute;
                                av4.setText("Avg. Wake Time: " + parse_wake_time);
                            } else {
                                av4.setText("Avg. Wake Time: --");
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


}