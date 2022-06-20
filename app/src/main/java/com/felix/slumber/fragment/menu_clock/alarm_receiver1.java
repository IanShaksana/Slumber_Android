package com.felix.slumber.fragment.menu_clock;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.felix.slumber.R;

public class alarm_receiver1 extends BroadcastReceiver {

    @Override
    public void onReceive(Context k1, Intent k2) {
        // basic_broadcast(k1, k2);
        startAlarmService(k1,k2);

    }

    private void basic_broadcast(Context k1, Intent k2) {
        // TODO Auto-generated method stub
        Toast.makeText(k1, "SLEEP TIME", Toast.LENGTH_LONG).show();

        createNotificationChannel(k1);
        CharSequence name = "slumber sleepy2";
        String description = "slumber sleepy2";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(k1, "slumber")
                .setSmallIcon(R.drawable.logo_slumber)
                .setContentTitle(name)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(k1);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());


    }

    private void createNotificationChannel(Context k1) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "slumber wakey2";
            String description = "slumber wakey2";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("slumber", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = k1.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void startAlarmService(Context context, Intent intent) {
        Intent intentService = new Intent(context, alarm_service1.class);
        intentService.putExtra("TITLE", intent.getStringExtra("TITLE"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // Toast.makeText(context, "masuk sini", Toast.LENGTH_LONG).show();
            context.startForegroundService(intentService);
        } else {

            Toast.makeText(context, "masuk sini2", Toast.LENGTH_LONG).show();
            context.startService(intentService);
        }
    }

}