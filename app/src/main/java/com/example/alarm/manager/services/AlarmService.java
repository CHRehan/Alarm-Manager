package com.example.alarm.manager.services;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;

import com.example.alarm.manager.R;
import com.example.alarm.manager.views.MainActivity;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class AlarmService extends IntentService {
    private static final String CHANNEL_ID = "ALARM_MANAGER";

    int notificationID = -1;

    public AlarmService(String name) {
        super(name);

    }

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {


        if (intent != null && intent.getExtras() != null)
            notificationID = intent.getExtras().getInt("id", -1);
        return Service.START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        Intent launchIntent = new Intent(this, MainActivity.class);
        launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, launchIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("WakeUp Alarm")
                .setContentText("Alarm is Ringing......")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        startForeground(notificationID, builder.build());


    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
