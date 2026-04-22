package com.example.kolokvijum1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TaskReceiver extends BroadcastReceiver {

    public static final String ACTION = "com.example.kolokvijum1.TASK_ADDED";

    @Override
    public void onReceive(Context context, Intent intent) {

        int broj = intent.getIntExtra("broj", 0);

        Intent updateIntent = new Intent("UPDATE_FAB_COLOR");
        updateIntent.putExtra("broj", broj);

        context.sendBroadcast(updateIntent);
    }
}