package com.felix.slumber.fragment.menu_clock;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;

import androidx.core.app.NotificationCompat;

import com.felix.slumber.R;

public class alarm_service1 extends Service {
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.sleep);
        mediaPlayer.setLooping(true);
        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build());
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, RingActivity1.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        createNotificationChannel(this);

        Notification notification = new NotificationCompat.Builder(this, "slumber_sleep")
                .setSmallIcon(R.drawable.logo_slumber)
                .setContentTitle("Slumber Sleepy Sleepy")
                .setContentIntent(pendingIntent)
                .setContentText("It is better to take a rest today")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        mediaPlayer.start();

        long[] pattern = {0, 100, 1000};
        vibrator.vibrate(pattern, 0);

        // notificationId is a unique int for each notification that you must define
        //notificationManager.notify(1, builder.build());
        startForeground(1, notification);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mediaPlayer.stop();
        vibrator.cancel();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel(Context k1) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "slumber sleepy2";
            String description = "slumber sleepy2";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("slumber_sleep", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = k1.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
