package com.felix.slumber.fragment.menu_achievement;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.felix.slumber.R;
import com.felix.slumber.model.model_achievement_daily;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by Adrian on 5/18/2018.
 */

public class achievement_demo extends Fragment {

    private MaterialButton btn_right;
    private MaterialButton btn_left;
    private ProgressBar Pbar;
    private Button finish;
    private TextView title;

    private List<model_achievement_daily> mission;

    private Integer cursor = 0;
    private Integer counter_daily = 0;
    private Integer counter_weekly = 0;
    private Integer counter_monthly = 0;

    private SharedPreferences sh;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.menu_fragment_achievement_1, container, false);

        btn_right = view.findViewById(R.id.right);
        btn_left = view.findViewById(R.id.left);
        finish = view.findViewById(R.id.finish);
        title = view.findViewById(R.id.title);
        Pbar = view.findViewById(R.id.progress);

        sh = getContext().getSharedPreferences("State", MODE_PRIVATE);

        mission = daily_generator();
        // initial
        title.setText(mission.get(cursor).getTitle());
        Pbar.setMax(mission.get(cursor).getRep());
        // initial

        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cursor == 2) {
                    cursor = 0;
                } else {
                    cursor++;
                }
                title.setText(mission.get(cursor).getTitle());
                Pbar.setMax(mission.get(cursor).getRep());
                if (cursor == 0) {
                    Pbar.setProgress(counter_daily);
                }
                if (cursor == 1) {
                    Pbar.setProgress(counter_weekly);
                }
                if (cursor == 2) {
                    Pbar.setProgress(counter_monthly);
                }


                if (counter_daily.equals(mission.get(cursor).getRep())) {
                    finish.setText("Claim Reward");
                }

                if (counter_weekly.equals(mission.get(cursor).getRep())) {
                    finish.setText("Claim Reward");
                }

                if (counter_monthly.equals(mission.get(cursor).getRep())) {
                    finish.setText("Claim Reward");
                }

            }
        });

        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cursor == 0) {
                    cursor = 2;
                } else {
                    cursor--;
                }
                title.setText(mission.get(cursor).getTitle());
                Pbar.setMax(mission.get(cursor).getRep());
                if (cursor == 0) {
                    Pbar.setProgress(counter_daily);
                }
                if (cursor == 1) {
                    Pbar.setProgress(counter_weekly);
                }
                if (cursor == 2) {
                    Pbar.setProgress(counter_monthly);
                }

                if (counter_daily.equals(mission.get(cursor).getRep())) {
                    finish.setText("Claim Reward");
                }

                if (counter_weekly.equals(mission.get(cursor).getRep())) {
                    finish.setText("Claim Reward");
                }

                if (counter_monthly.equals(mission.get(cursor).getRep())) {
                    finish.setText("Claim Reward");
                }
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cursor == 0) {
                    counter_daily++;
                    if (counter_daily.equals(mission.get(cursor).getRep())) {
                        finish.setText("Claim Reward");
                    }
                    if (counter_daily > mission.get(cursor).getRep()) {
                        Snackbar.make(view, mission.get(cursor).getPoint() + " Points Added", Snackbar.LENGTH_SHORT)
                                .show();
                        finish.setText("Finish");
                        counter_daily = 0;
                        SharedPreferences.Editor editor = sh.edit();
                        editor.putInt("point", sh.getInt("point",0)+mission.get(cursor).getPoint());
                        editor.commit();
                    }
                    Pbar.setProgress(counter_daily);
                }

                if (cursor == 1) {
                    counter_weekly++;
                    if (counter_weekly.equals(mission.get(cursor).getRep())) {
                        finish.setText("Claim Reward");
                    }
                    if (counter_weekly > mission.get(cursor).getRep()) {
                        Snackbar.make(view, mission.get(cursor).getPoint() + " Points Added", Snackbar.LENGTH_SHORT)
                                .show();
                        finish.setText("Finish");
                        counter_weekly = 0;
                        SharedPreferences.Editor editor = sh.edit();
                        editor.putInt("point", sh.getInt("point",0)+mission.get(cursor).getPoint());
                        editor.commit();
                    }
                    Pbar.setProgress(counter_weekly);
                }

                if (cursor == 2) {
                    counter_monthly++;
                    if (counter_monthly.equals(mission.get(cursor).getRep())) {
                        finish.setText("Claim Reward");
                    }
                    if (counter_monthly > mission.get(cursor).getRep()) {
                        Snackbar.make(view, mission.get(cursor).getPoint() + " Points Added", Snackbar.LENGTH_SHORT)
                                .show();
                        finish.setText("Finish");
                        counter_monthly = 0;
                        SharedPreferences.Editor editor = sh.edit();
                        editor.putInt("point", sh.getInt("point",0)+mission.get(cursor).getPoint());
                        editor.commit();
                    }
                    Pbar.setProgress(counter_monthly);

                }

            }
        });


        return view;
    }

    public List<model_achievement_daily> daily_generator() {
        List<model_achievement_daily> achi_1 = new ArrayList<>();
        achi_1.add(new model_achievement_daily("1", "Wake Up", 1, 5));
        achi_1.add(new model_achievement_daily("2", "Read Article", 5, 15));
        achi_1.add(new model_achievement_daily("3", "Sleep", 30, 25));
        return achi_1;
    }


}