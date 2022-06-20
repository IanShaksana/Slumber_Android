package com.felix.slumber.fragment.menu_clock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class alarm_receiver2 extends BroadcastReceiver {

    @Override
    public void onReceive(Context k1, Intent k2) {
        startAlarmService(k1,k2);

    }

    private void startAlarmService(Context context, Intent intent) {
        Intent intentService = new Intent(context, alarm_service2.class);
        intentService.putExtra("TITLE", intent.getStringExtra("TITLE"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService);
        } else {
            context.startService(intentService);
        }
    }

}