package com.sparnyuk.notes;

import static android.app.Notification.DEFAULT_ALL;
import static android.app.Notification.PRIORITY_HIGH;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.sparnyuk.notes.DBHelper.Constant;

public class AlertReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "CHANNEL_ID";
    private static final int NOTI_ID = 1;
    private NotificationManager manager;
    //private PendingIntent pendingIntent;
    String depart,title;

    @SuppressLint("Range")
    @Override
    public void onReceive(Context context, Intent intent) {
        depart=intent.getStringExtra("da");

        manager=(NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        Log.d("MyLog", "depart "+depart);
        NotificationCompat.Builder notiBuilder=
                new NotificationCompat.Builder(context,CHANNEL_ID)
                        .setAutoCancel(false)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setWhen(System.currentTimeMillis())
                        //.setContentIntent(pendingIntent)
                        //.setContentTitle(title)
                        .setContentText(depart)
                        .setDefaults(DEFAULT_ALL)
                        .setPriority(PRIORITY_HIGH);
        createChannel(manager);


        manager.notify(NOTI_ID,notiBuilder.build());
    }


    private void createChannel(NotificationManager manager) {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

            NotificationChannel notiChannel=
                    new NotificationChannel(CHANNEL_ID,CHANNEL_ID,NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notiChannel);
        }
    }
}
