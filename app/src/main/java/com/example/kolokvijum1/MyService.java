package com.example.kolokvijum1;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class MyService extends Service {

    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (runnable != null) return START_STICKY;

        runnable = new Runnable() {
            @Override
            public void run() {

                if (AppState.isFabRed) {
                    showNotification();

                    Intent i = new Intent("CHANGE_BG");
                    i.setPackage(getPackageName());
                    sendBroadcast(i);
                }

                handler.postDelayed(this, 5000);
            }
        };

        handler.post(runnable);

        return START_STICKY;
    }

    private void showNotification() {

        String channelId = "kanal1";

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "MyChannel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setContentTitle("Upozorenje")
                .setContentText("Crveno je!")
                .setSmallIcon(android.R.drawable.ic_dialog_alert);

        manager.notify(1, builder.build());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
