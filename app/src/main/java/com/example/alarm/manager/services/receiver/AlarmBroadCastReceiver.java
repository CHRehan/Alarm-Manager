package com.example.alarm.manager.services.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.alarm.manager.services.AlarmService;

public class AlarmBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, AlarmService.class);
        if (intent.getExtras() != null)
            intent.putExtra("id", intent.getIntExtra("id", -1));
        context.startService(i);
    }




}