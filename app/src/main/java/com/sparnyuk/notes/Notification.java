package com.sparnyuk.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;

import android.os.Bundle;

import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.google.android.material.timepicker.MaterialTimePicker;
import com.sparnyuk.notes.DBHelper.Constant;
import com.sparnyuk.notes.DBHelper.DBHelperWater;

import java.text.SimpleDateFormat;

import java.util.Locale;

public class Notification extends AppCompatActivity {

    Button bt;
    TextView text;
    String title, depart,idNoti;
    SimpleDateFormat sdf;
    Calendar calendar;
    PendingIntent pendingIntent;
    //int year, month, day;
    MaterialTimePicker materialTimePicker;
    private DatePickerDialog datePickerDialog;

    private DBHelperNoti bd;
    private DBHelperWater dbHelperWater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_notification);
        bt=findViewById(R.id.noti);
        text=findViewById(R.id.textTime);
        sdf = new SimpleDateFormat("dd-MMM-YYYY:HH-mm", Locale.getDefault());
        calendar = Calendar.getInstance();
        Intent intent=getIntent();
        title=intent.getStringExtra("title");
        depart=intent.getStringExtra("depart");

        bd=new DBHelperNoti(this);

        dbHelperWater=new DBHelperWater(this);//добавлено для заметок
        idNoti=intent.getStringExtra("id");//добавлено для заметок

        initDatePicker();
        openDatePicker();
        openTime();

        /*bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

    }

    private void showPicker(int year, int month, int day) throws PendingIntent.CanceledException {
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);


        long id= bd.insertNoti(""+depart,""+title,""+calendar.getTimeInMillis());
        dbHelperWater.updateTitleNoti(idNoti,""+calendar.getTimeInMillis());//добавлено для заметок


        final int id_pend = (int) calendar.getTimeInMillis();

        Intent intent = new Intent(Notification.this, MyServiceNoti.class).putExtra("da",title).putExtra("id_pend",id_pend);

        //!!!!!!!!!!!!!!
        //startService(new Intent(this, MyServiceNoti.class));- временно останавливаем!! (дублирование в MainActivity);

        /*Checkin SDK
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            pendingIntent = PendingIntent.getBroadcast(Notification.this, 0, intent, PendingIntent.FLAG_MUTABLE);
        } else {
            pendingIntent = PendingIntent.getBroadcast(Notification.this, 0, intent, PendingIntent.FLAG_MUTABLE);
        }*/

        pendingIntent = PendingIntent.getBroadcast(Notification.this, id_pend, intent, PendingIntent.FLAG_MUTABLE);
        pendingIntent.send(this,id_pend,intent);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.set(AlarmManager.RTC_WAKEUP,readTimeNoti(), pendingIntent);
        Toast.makeText(this, "Будильник установлен на " + sdf.format(calendar.getTime()), Toast.LENGTH_SHORT).show();

        onBackPressed();

        /*materialTimePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(22)
                .setMinute(15)
                .setTitleText("Выберите время для будильника")
                .build();
        materialTimePicker.show(getSupportFragmentManager(), "tag_picker");

        materialTimePicker.addOnPositiveButtonClickListener(view -> {
            calendar.set(Calendar.DAY_OF_MONTH, day);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.MINUTE, materialTimePicker.getMinute());
            calendar.set(Calendar.HOUR_OF_DAY, materialTimePicker.getHour());

            long id= bd.insertNoti(""+depart,""+title,""+calendar.getTimeInMillis());

            Intent intent = new Intent(Notification.this, AlertReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(Notification.this, 0, intent , 0);
            AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarm.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);
            Toast.makeText(this, "Будильник установлен на " + sdf.format(calendar.getTime()), Toast.LENGTH_SHORT).show();
            title= String.valueOf(calendar.getTimeInMillis());
            text.setText(title);
        });*/

    }
    public void openTime(){

        TimePickerDialog dialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
            }
        },00,00,true);
        dialog.show();
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                try {
                    showPicker(year,month,day);
                } catch (PendingIntent.CanceledException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        //Для установки вверху календаря текущей даты
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_DEVICE_DEFAULT_DARK;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    public void openDatePicker()
    {
        datePickerDialog.show();
    }

    @SuppressLint("Range")
    public long readTimeNoti(){
        long time=0;
        bd.deleteNoti();
        String selectQuery="SELECT * FROM "+Constant.TABLE_NAME_NOTI+" ORDER BY "+Constant.KEY_TIME_NOTI+" ASC limit 1" ;
        SQLiteDatabase db=bd.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            time= cursor.getLong(cursor.getColumnIndex(Constant.KEY_TIME_NOTI));
        }
        db.close();
        return time+5000;
    }

}

    /*private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }*/
    /*private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }*/
    //НЕ РАБОТАЕТ КОД!!!!
    /*private void readNoti(){
        String selectQuery="SELECT*FROM "+ Constant.TABLE_NAME_NOTI+
                " WHERE "+Constant.KEY_TIME_NOTI+" is NOT NULL"+" ORDER BY "+Constant.KEY_TIME_NOTI+" ASC";

        SQLiteDatabase base=bd.getWritableDatabase();
        Cursor cursor=base.rawQuery(selectQuery,null);


        if (cursor.moveToFirst()){
            @SuppressLint("Range") long time_noti= Long.parseLong(cursor.getString(cursor.getColumnIndex(Constant.KEY_TIME_NOTI)));
            long current_time=System.currentTimeMillis();
            long dif=time_noti-current_time;
            if (dif>0){
                Intent intent = new Intent(Notification.this, AlertReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(Notification.this, 0, intent , 0);
                AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarm.setRepeating(AlarmManager.RTC_WAKEUP,time_noti,AlarmManager.INTERVAL_DAY, pendingIntent);
                Toast.makeText(this, "ad", Toast.LENGTH_SHORT).show();
            }
        }
        base.close();
    }*/



/*
*

    bd=new DBHelperNoti(this);*/