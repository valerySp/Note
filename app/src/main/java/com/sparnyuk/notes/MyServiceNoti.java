package com.sparnyuk.notes;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.sparnyuk.notes.DBHelper.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyServiceNoti extends Service {

    DBHelperNoti notiBD;
    private PendingIntent pendingIntent;
    long count;
    private String departNoti,titleNoti,timeNoti;
    Intent intent;

    public MyServiceNoti() {
    }

    @Override
    public void onCreate() {

        intent = new Intent(MyServiceNoti.this, AlertReceiver.class);
        //pendingIntent = PendingIntent.getBroadcast(MyServiceNoti.this, 0, intent , PendingIntent.FLAG_MUTABLE);
        notiBD=new DBHelperNoti(this);
        count=0;
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timer myTime=new Timer();
        myTime.schedule(new TimerTask() {
            @Override
            public void run() {
                setAlarm();
            }
        },0,1000);

        return super.onStartCommand(intent, flags, startId);
    }

    @SuppressLint({"Range", "UnspecifiedImmutableFlag"})
    public void setAlarm(){
        // Проверить логику count в операторе if!!!!!!!!!!
        count=notiBD.getRecNoti();
        notiBD.deleteNoti();
        /*if (notiBD.getCount()==0){
            stopSelf();
        }
        else*/
        if(notiBD.getRecNoti()!=0&&count!=notiBD.getRecNoti())
        {
            int id= (int)System.currentTimeMillis();
            Log.d("MyLog", "countServ "+id);
            String selectQuery="SELECT*FROM "+ Constant.TABLE_NAME_NOTI+
                    " ORDER BY "+Constant.KEY_TIME_NOTI+" ASC LIMIT 1";

            SQLiteDatabase base=notiBD.getWritableDatabase();
            Cursor cursor=base.rawQuery(selectQuery,null);

            if (cursor.moveToFirst()){
                do {
                    departNoti=""+cursor.getString(cursor.getColumnIndex(Constant.KEY_DEPARTMENT));
                    titleNoti=""+cursor.getString(cursor.getColumnIndex(Constant.KEY_TITLE));
                    timeNoti=""+cursor.getString(cursor.getColumnIndex(Constant.KEY_TIME_NOTI));
                } while (cursor.moveToNext());
            }
            base.close();

            intent = new Intent(MyServiceNoti.this, AlertReceiver.class);
            intent.putExtra("da",titleNoti);

            pendingIntent = PendingIntent.getBroadcast(MyServiceNoti.this, 0, intent , PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarm.set(AlarmManager.RTC_WAKEUP, Long.parseLong(timeNoti), pendingIntent);
            //Toast.makeText(this, "Будильник установлен на " + (notiBD.getRecNoti()), Toast.LENGTH_SHORT).show();
        }
        else onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    //Попробывать остановить фатал эррор!! делэй-передается через сколько выполнять
    //Попробывать передать позднюю дату и закрыть приложение.
    @Override
    public void onDestroy() {
        /*Timer myTime=new Timer();
        myTime.schedule(new TimerTask() {

            @Override
            public void run() {
                setAlarm();
            }
        },1000,5000);*/

        super.onDestroy();
    }
}