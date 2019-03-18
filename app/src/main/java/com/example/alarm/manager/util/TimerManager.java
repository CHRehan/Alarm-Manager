package com.example.alarm.manager.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.alarm.manager.services.receiver.AlarmBroadCastReceiver;

import java.util.Calendar;

/**
 * Created by Rehan on 03/16/2019.
 */

public class TimerManager {
    private Context context;
    private AlarmManager alarmManager;

    public TimerManager(Context context) {
        this.context = context;
        this.alarmManager = (AlarmManager) this.context.getSystemService(Context.ALARM_SERVICE);
    }

    public void startTimer(Calendar calendar, int requestID) {

        Log.d("TAG", "AlarmTime is set to: " + calendar.getTime());

        Intent intent = new Intent(context, AlarmBroadCastReceiver.class);
        intent.putExtra("id", requestID);
        PendingIntent pi = PendingIntent.getBroadcast(context, requestID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);

    }


    public void cancelTimer(int requestCode) {
        Intent intent = new Intent(context, AlarmBroadCastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.cancel(sender);
    }


    public void resetTimer(Calendar calendar, int requestID) {
        cancelTimer(requestID);
        startTimer(calendar, requestID);
    }


}
