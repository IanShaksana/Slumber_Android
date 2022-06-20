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
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.felix.slumber.R;
import com.google.android.material.button.MaterialButton;

import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by Adrian on 5/18/2018.
 */


public class clock_2 extends Fragment {
    private MaterialButton btn_right;
    private MaterialButton btn_left;

    private TextView textView;
    private TimePickerDialog timePickerDialog;
    private SharedPreferences sh;

    private String string_access_1 = "WAKE_TIME_HOUR";
    private String string_access_2 = "WAKE_TIME_MINUTE";

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.menu_fragment_clock_2, container, false);
        sh = getActivity().getSharedPreferences("State", MODE_PRIVATE);
        btn_right = view.findViewById(R.id.right);
        btn_left = view.findViewById(R.id.left);
        textView = view.findViewById(R.id.wake_time);

        if (sh.getInt(string_access_1, 99) != 99 && sh.getInt(string_access_2, 99) != 99) {
            String text = String.format("%02d", sh.getInt(string_access_1, 99)) + ":" + String.format("%02d", sh.getInt(string_access_2, 99));
            textView.setText(text);
        }

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });

        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new clock_1()).commit();
            }
        });

        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new clock_1()).commit();
            }
        });

        return view;
    }

    private void setAlarm(Calendar targetCal) {
        Intent intent = new Intent(getActivity(), alarm_receiver2.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 2, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(getContext().ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, alarm_receiver2.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, 2, intent, 0);
        alarmManager.cancel(alarmPendingIntent);
    }


}