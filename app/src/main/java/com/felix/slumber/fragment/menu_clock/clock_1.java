package com.felix.slumber.fragment.menu_clock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.felix.slumber.dialog.dialog_alarm;
import com.felix.slumber.model_body.model_activity;
import com.felix.slumber.model_body.model_profile;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;


import org.json.JSONObject;

import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by Adrian on 5/18/2018.
 */


public class clock_1 extends Fragment implements dialog_alarm.dialogListener_worker {
    private MaterialButton btn_right;
    private MaterialButton btn_left;

    private TextView textView1;
    private TextView textView2;
    private TimePickerDialog timePickerDialog;
    private SharedPreferences sh;

    private String string_access_1 = "SLEEP_TIME_HOUR";
    private String string_access_2 = "SLEEP_TIME_MINUTE";

    private String string_access_3 = "WAKE_TIME_HOUR";
    private String string_access_4 = "WAKE_TIME_MINUTE";

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.menu_fragment_clock_1, container, false);
        sh = getActivity().getSharedPreferences("State", MODE_PRIVATE);
        btn_right = view.findViewById(R.id.right);
        btn_left = view.findViewById(R.id.left);
        btn_right.setVisibility(View.INVISIBLE);
        btn_left.setVisibility(View.INVISIBLE);

        textView1 = view.findViewById(R.id.sleep_time);
        textView2 = view.findViewById(R.id.wake_time);

        if (sh.getInt(string_access_1, 99) != 99 && sh.getInt(string_access_2, 99) != 99) {
            String text = String.format("%02d", sh.getInt(string_access_1, 99)) + ":" + String.format("%02d", sh.getInt(string_access_2, 99));
            textView1.setText(text);
        }

        if (sh.getInt(string_access_3, 99) != 99 && sh.getInt(string_access_4, 99) != 99) {
            String text = String.format("%02d", sh.getInt(string_access_3, 99)) + ":" + String.format("%02d", sh.getInt(string_access_4, 99));
            textView2.setText(text);
        }

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendialog();
                /*
                final Calendar c = Calendar.getInstance();
                final Integer currentHour = c.get(Calendar.HOUR_OF_DAY);
                final Integer currentMinute = c.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String text = String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute);
                        textView.setText(text);
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        c.set(Calendar.MILLISECOND, 0);
                        cancelAlarm(getContext());
                        setAlarm(c);

                        SharedPreferences.Editor editor = sh.edit();
                        editor.putInt(string_access_1, hourOfDay);
                        editor.putInt(string_access_2, minute);
                        editor.commit();
                    }
                }, currentHour, currentMinute, false);
                timePickerDialog.show();
                 */

                /*
                PopupMenu menu = new PopupMenu(getContext(), v);
                menu.getMenu().add("Sleep Time = 21:00, Wake Time = 05:00");
                menu.getMenu().add("Sleep Time = 22:00, Wake Time = 06:00");
                menu.getMenu().add("Sleep Time = 23:00, Wake Time = 07:00");

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        String idalarm = "";
                        Calendar now = Calendar.getInstance();
                        now.setTimeInMillis(System.currentTimeMillis());
                        Calendar c_sleep = Calendar.getInstance();
                        Calendar c_wake = Calendar.getInstance();
                        c_sleep.setTimeInMillis(System.currentTimeMillis());
                        c_wake.setTimeInMillis(System.currentTimeMillis());


                        cancelAlarm_sleep(getContext());
                        cancelAlarm_wake(getContext());

                        if (item.getTitle().toString().equalsIgnoreCase("Sleep Time = 21:00, Wake Time = 05:00")) {
                            idalarm = "alarm1";
                            c_sleep.set(Calendar.HOUR_OF_DAY, 21);
                            c_sleep.set(Calendar.MINUTE, 0);
                            c_sleep.set(Calendar.SECOND, 0);
                            c_sleep.set(Calendar.MILLISECOND, 0);
                            if (c_sleep.before(now)) {
                                c_sleep.add(Calendar.DATE, 1);
                            }
                            setAlarm_sleep(c_sleep);

                            c_wake.set(Calendar.HOUR_OF_DAY, 11);
                            c_wake.set(Calendar.MINUTE, 10);
                            c_wake.set(Calendar.SECOND, 0);
                            c_wake.set(Calendar.MILLISECOND, 0);
                            if (c_wake.before(now)) {
                                c_wake.add(Calendar.DATE, 1);
                            }
                            setAlarm_wake(c_wake);

                            SharedPreferences.Editor editor = sh.edit();
                            editor.putInt(string_access_1, c_sleep.get(Calendar.HOUR_OF_DAY));
                            editor.putInt(string_access_2, c_sleep.get(Calendar.MINUTE));
                            editor.putInt(string_access_3, c_wake.get(Calendar.HOUR_OF_DAY));
                            editor.putInt(string_access_4, c_wake.get(Calendar.MINUTE));
                            editor.commit();
                        }
                        if (item.getTitle().toString().equalsIgnoreCase("Sleep Time = 22:00, Wake Time = 06:00")) {
                            idalarm = "alarm2";
                            c_sleep.set(Calendar.HOUR_OF_DAY, 22);
                            c_sleep.set(Calendar.MINUTE, 0);
                            c_sleep.set(Calendar.SECOND, 0);
                            c_sleep.set(Calendar.MILLISECOND, 0);
                            if (c_sleep.before(now)) {
                                c_sleep.add(Calendar.DATE, 1);
                            }
                            setAlarm_sleep(c_sleep);

                            c_wake.set(Calendar.HOUR_OF_DAY, 6);
                            c_wake.set(Calendar.MINUTE, 0);
                            c_wake.set(Calendar.SECOND, 0);
                            c_wake.set(Calendar.MILLISECOND, 0);
                            if (c_wake.before(now)) {
                                c_wake.add(Calendar.DATE, 1);
                            }
                            setAlarm_wake(c_wake);

                            SharedPreferences.Editor editor = sh.edit();
                            editor.putInt(string_access_1, c_sleep.get(Calendar.HOUR_OF_DAY));
                            editor.putInt(string_access_2, c_sleep.get(Calendar.MINUTE));
                            editor.putInt(string_access_3, c_wake.get(Calendar.HOUR_OF_DAY));
                            editor.putInt(string_access_4, c_wake.get(Calendar.MINUTE));
                            editor.commit();
                        }
                        if (item.getTitle().toString().equalsIgnoreCase("Sleep Time = 23:00, Wake Time = 07:00")) {
                            idalarm = "alarm3";
                            c_sleep.set(Calendar.HOUR_OF_DAY, 23);
                            c_sleep.set(Calendar.MINUTE, 0);
                            c_sleep.set(Calendar.SECOND, 0);
                            c_sleep.set(Calendar.MILLISECOND, 0);
                            if (c_sleep.before(now)) {
                                c_sleep.add(Calendar.DATE, 1);
                            }
                            setAlarm_sleep(c_sleep);

                            c_wake.set(Calendar.HOUR_OF_DAY, 7);
                            c_wake.set(Calendar.MINUTE, 0);
                            c_wake.set(Calendar.SECOND, 0);
                            c_wake.set(Calendar.MILLISECOND, 0);
                            if (c_wake.before(now)) {
                                c_wake.add(Calendar.DATE, 1);
                            }
                            setAlarm_wake(c_wake);

                            SharedPreferences.Editor editor = sh.edit();
                            editor.putInt(string_access_1, c_sleep.get(Calendar.HOUR_OF_DAY));
                            editor.putInt(string_access_2, c_sleep.get(Calendar.MINUTE));
                            editor.putInt(string_access_3, c_wake.get(Calendar.HOUR_OF_DAY));
                            editor.putInt(string_access_4, c_wake.get(Calendar.MINUTE));
                            editor.commit();
                        }
                        model_activity model = new model_activity(new model_profile(getContext()), "ALARM", "", idalarm, "");
                        finish_task(getContext(), model);
                        update();
                        return true;
                    }
                });
                menu.show();
                */
            }
        });


        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new clock_2()).commit();
            }
        });

        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new clock_2()).commit();
            }
        });

        return view;
    }

    private void setAlarm_sleep(Calendar targetCal) {
        Intent intent = new Intent(getActivity(), alarm_receiver1.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(getContext().ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
    }

    public void cancelAlarm_sleep(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, alarm_receiver1.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);
        alarmManager.cancel(alarmPendingIntent);
    }

    private void setAlarm_wake(Calendar targetCal) {
        Intent intent = new Intent(getActivity(), alarm_receiver2.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 2, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(getContext().ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
    }

    public void cancelAlarm_wake(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, alarm_receiver2.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, 2, intent, 0);
        alarmManager.cancel(alarmPendingIntent);
    }

    private void finish_task(final Context context, model_activity model) {
        try {

            http_post post = new http_post(context);
            Gson gson = new Gson();
            JSONObject jo = new JSONObject(gson.toJson(model));
            jo.put("api", getString(R.string.finish_task));
            post.execute(jo);
            post.getListener(new http_post.OnUpdateListener() {
                @Override
                public void onUpdate(JSONObject JObject) {

                    try {

                        if (!JObject.getString("statuscode").equals("OK")) {
                            Toast.makeText(context, "Gagal : " + JObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } else if (JObject.getString("statuscode").equals("no connection")) {
                            Toast.makeText(context, "Tidak ada koneksi ke server", Toast.LENGTH_SHORT).show();
                        } else {
                            // Toast.makeText(context, "Sukses", Toast.LENGTH_SHORT).show();
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
        if (current instanceof clock_1) {
            getFragmentManager().beginTransaction().detach(current).attach(current).commit();
        }
    }

    private void opendialog() {
        dialog_alarm dialogfragment = new dialog_alarm();
        dialogfragment.setTargetFragment(this, 0);
        dialogfragment.show(getParentFragmentManager(), "exa");
    }


    @Override
    public void apply_listener(String idalarm) {

        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());
        Calendar c_sleep = Calendar.getInstance();
        Calendar c_wake = Calendar.getInstance();
        c_sleep.setTimeInMillis(System.currentTimeMillis());
        c_wake.setTimeInMillis(System.currentTimeMillis());
                        /*
                        c_sleep.add(Calendar.DATE,1);
                        c_wake.add(Calendar.DATE,1);

                         */

        cancelAlarm_sleep(getContext());
        cancelAlarm_wake(getContext());

        if (idalarm.equalsIgnoreCase("alarm1")) {
            // idalarm = "alarm1";
            c_sleep.set(Calendar.HOUR_OF_DAY, 20);
            c_sleep.set(Calendar.MINUTE, 0);
            c_sleep.set(Calendar.SECOND, 0);
            c_sleep.set(Calendar.MILLISECOND, 0);
            if (c_sleep.before(now)) {
                c_sleep.add(Calendar.DATE, 1);
            }
            setAlarm_sleep(c_sleep);

            c_wake.set(Calendar.HOUR_OF_DAY, 20);
            c_wake.set(Calendar.MINUTE, 2);
            c_wake.set(Calendar.SECOND, 0);
            c_wake.set(Calendar.MILLISECOND, 0);
            if (c_wake.before(now)) {
                c_wake.add(Calendar.DATE, 1);
            }
            setAlarm_wake(c_wake);

            SharedPreferences.Editor editor = sh.edit();
            editor.putInt(string_access_1, c_sleep.get(Calendar.HOUR_OF_DAY));
            editor.putInt(string_access_2, c_sleep.get(Calendar.MINUTE));
            editor.putInt(string_access_3, c_wake.get(Calendar.HOUR_OF_DAY));
            editor.putInt(string_access_4, c_wake.get(Calendar.MINUTE));
            editor.commit();
        }
        if (idalarm.equalsIgnoreCase("alarm2")) {
            // idalarm = "alarm2";
            c_sleep.set(Calendar.HOUR_OF_DAY, 22);
            c_sleep.set(Calendar.MINUTE, 0);
            c_sleep.set(Calendar.SECOND, 0);
            c_sleep.set(Calendar.MILLISECOND, 0);
            if (c_sleep.before(now)) {
                c_sleep.add(Calendar.DATE, 1);
            }
            setAlarm_sleep(c_sleep);

            c_wake.set(Calendar.HOUR_OF_DAY, 6);
            c_wake.set(Calendar.MINUTE, 0);
            c_wake.set(Calendar.SECOND, 0);
            c_wake.set(Calendar.MILLISECOND, 0);
            if (c_wake.before(now)) {
                c_wake.add(Calendar.DATE, 1);
            }
            setAlarm_wake(c_wake);

            SharedPreferences.Editor editor = sh.edit();
            editor.putInt(string_access_1, c_sleep.get(Calendar.HOUR_OF_DAY));
            editor.putInt(string_access_2, c_sleep.get(Calendar.MINUTE));
            editor.putInt(string_access_3, c_wake.get(Calendar.HOUR_OF_DAY));
            editor.putInt(string_access_4, c_wake.get(Calendar.MINUTE));
            editor.commit();
        }
        if (idalarm.equalsIgnoreCase("alarm3")) {
            // idalarm = "alarm3";
            c_sleep.set(Calendar.HOUR_OF_DAY, 23);
            c_sleep.set(Calendar.MINUTE, 0);
            c_sleep.set(Calendar.SECOND, 0);
            c_sleep.set(Calendar.MILLISECOND, 0);
            if (c_sleep.before(now)) {
                c_sleep.add(Calendar.DATE, 1);
            }
            setAlarm_sleep(c_sleep);

            c_wake.set(Calendar.HOUR_OF_DAY, 7);
            c_wake.set(Calendar.MINUTE, 0);
            c_wake.set(Calendar.SECOND, 0);
            c_wake.set(Calendar.MILLISECOND, 0);
            if (c_wake.before(now)) {
                c_wake.add(Calendar.DATE, 1);
            }
            setAlarm_wake(c_wake);

            SharedPreferences.Editor editor = sh.edit();
            editor.putInt(string_access_1, c_sleep.get(Calendar.HOUR_OF_DAY));
            editor.putInt(string_access_2, c_sleep.get(Calendar.MINUTE));
            editor.putInt(string_access_3, c_wake.get(Calendar.HOUR_OF_DAY));
            editor.putInt(string_access_4, c_wake.get(Calendar.MINUTE));
            editor.commit();
        }
        model_activity model = new model_activity(new model_profile(getContext()), "ALARM", "", idalarm, "");
        finish_task(getContext(), model);
        update();

    }
}