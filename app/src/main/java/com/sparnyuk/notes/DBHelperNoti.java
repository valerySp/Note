package com.sparnyuk.notes;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.sparnyuk.notes.DBHelper.Constant;

import java.util.ArrayList;

public class DBHelperNoti extends SQLiteOpenHelper {


    public DBHelperNoti(@Nullable Context context) {
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constant.CREATE_TABLE_ETK);
        db.execSQL(Constant.CREATE_TABLE_WATER);
        db.execSQL(Constant.CREATE_TABLE_GAS);
        db.execSQL(Constant.CREATE_TABLE_NOTI);
        db.execSQL(Constant.CREATE_TABLE_CLIMATE);
        db.execSQL(Constant.CREATE_TABLE_DEV);
        db.execSQL(Constant.CREATE_TABLE_STR);
        db.execSQL(Constant.CREATE_TABLE_OTD);
        db.execSQL(Constant.CREATE_TABLE_OTHER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //db.execSQL("drop table if exists "+Constant.TABLE_NAME_ETK);
        //db.execSQL("drop table if exists "+Constant.TABLE_NAME_WATER);
        //db.execSQL("drop table if exists "+Constant.TABLE_NAME_GAS);
        //db.execSQL("drop table if exists "+Constant.TABLE_NAME_CLIMATE);
        //db.execSQL("drop table if exists "+Constant.TABLE_NAME_DEV);
        db.execSQL("drop table if exists "+Constant.TABLE_NAME_NOTI);
        //db.execSQL("drop table if exists "+Constant.TABLE_NAME_STR);
        //db.execSQL("drop table if exists "+Constant.TABLE_NAME_OTD);
        //db.execSQL("drop table if exists "+Constant.TABLE_NAME_OTHER);
        onCreate(db);
    }
    public long insertNoti(String depart, String title, String timeNoti){
        //,String addtime, String update
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(Constant.KEY_DEPARTMENT,depart);
        values.put(Constant.KEY_TITLE,title);
        values.put(Constant.KEY_TIME_NOTI,timeNoti);

        long id=db.insert(Constant.TABLE_NAME_NOTI,null,values);
        db.close();
        return id;
    }

    @SuppressLint("Range")
    public long getRecNoti(){
        long time = 0;
        String selectQuery="SELECT * FROM "+Constant.TABLE_NAME_NOTI+" ORDER BY "+Constant.KEY_TIME_NOTI+" ASC LIMIT 1" ;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            time= Long.parseLong(cursor.getString(cursor.getColumnIndex(Constant.KEY_TIME_NOTI)));
        }
        db.close();
        return time;
    }

    @SuppressLint("Range")
    public int getCount(){
        int count=0;
        String selectQuery="SELECT*FROM "+Constant.TABLE_NAME_NOTI+" ORDER BY "+Constant.KEY_TIME_NOTI ;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            count++;
        }
        db.close();
        return count;
    }

    public void deleteNoti(){

        String s = "DELETE FROM "+Constant.TABLE_NAME_NOTI+
                " WHERE "+Constant.KEY_TIME_NOTI+" <\""+ System.currentTimeMillis() +"\"";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(s,null);
        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") ModelRecord modelRecord=new ModelRecord(
                        ""+cursor.getInt(cursor.getColumnIndex(Constant.KEY_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_DEPARTMENT)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_TITLE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_TIME_NOTI))
                );
                //recordsList.add(modelRecord);
            } while (cursor.moveToNext());
        }
        db.close();
    }

}
/*
    НЕПОНЯТНО КАК ЕЩЕ ОБНОВИТЬ ДЛЯ КАЖДОЙ ТАБЛИЦЫ!!!

    public ArrayList<ModelRecord> getUpDateNoti (String id, String depart, String image){
        ArrayList<ModelRecord> recordsList=new ArrayList<>();
        String selectQuery="UPDATE "+Constant.TABLE_NAME_WATER+
                " SET "
                +Constant.KEY_DEPARTMENT+" = \""+depart+"\""+
                " , "+
                Constant.KEY_IMAGE+" = \""+image+"\""+
                " WHERE "+Constant.KEY_ID+" =\""+id+"\"";

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") ModelRecord modelRecord=new ModelRecord(
                        ""+cursor.getInt(cursor.getColumnIndex(Constant.KEY_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_DEPARTMENT)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_IMAGE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_TITLE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_DISCRIPTION)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_IMAGE_GALLERY))
                );
                recordsList.add(modelRecord);
            } while (cursor.moveToNext());
        }
        db.close();
        return recordsList;
    }*/