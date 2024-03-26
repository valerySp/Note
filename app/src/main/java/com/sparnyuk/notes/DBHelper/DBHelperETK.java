package com.sparnyuk.notes.DBHelper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.sparnyuk.notes.ModelRecord;

import java.util.ArrayList;

public class DBHelperETK extends SQLiteOpenHelper {

    public DBHelperETK(@Nullable Context context) {
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
        db.execSQL("drop table if exists "+Constant.TABLE_NAME_ETK);
        //db.execSQL("drop table if exists "+Constant.TABLE_NAME_WATER);
        //db.execSQL("drop table if exists "+Constant.TABLE_NAME_GAS);
        //db.execSQL("drop table if exists "+Constant.TABLE_NAME_CLIMATE);
        //db.execSQL("drop table if exists "+Constant.TABLE_NAME_DEV);
        //db.execSQL("drop table if exists "+Constant.TABLE_NAME_STR);
        //db.execSQL("drop table if exists "+Constant.TABLE_NAME_OTD);
        //db.execSQL("drop table if exists "+Constant.TABLE_NAME_OTHER);
        onCreate(db);
    }
    public long insertTitleETK(String depart, String image, String title, String discr, String addtime, String galery){
        //,String addtime, String update
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(Constant.KEY_DEPARTMENT,depart);
        values.put(Constant.KEY_IMAGE,image);
        values.put(Constant.KEY_TITLE,title);
        values.put(Constant.KEY_DESCRIPTION,discr);
        values.put(Constant.KEY_TIME_ADD,addtime);
        values.put(Constant.KEY_IMAGE_GALLERY,galery);
        //values.put(Constant.KEY_TIME_UPDATE,update);
        long id=db.insert(Constant.TABLE_NAME_ETK,null,values);
        db.close();
        return id;
    }

    public long insertDepartETK(String depart, String image){
        //,String addtime, String update
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(Constant.KEY_DEPARTMENT,depart);
        values.put(Constant.KEY_IMAGE,image);
        //values.put(Constant.KEY_TITLE,title);
        //values.put(Constant.KEY_DISCRIPTION,discr);
        // values.put(Constant.KEY_TIME_ADD,addtime);
        //values.put(Constant.KEY_TIME_UPDATE,update);
        long id=db.insert(Constant.TABLE_NAME_ETK,null,values);
        db.close();
        return id;
    }

    public ArrayList<ModelRecord> getDepartNameETK(String orderBy){
        ArrayList<ModelRecord> recordsList=new ArrayList<>();
        String selectQuery="SELECT * FROM "+Constant.TABLE_NAME_ETK+" WHERE "+Constant.KEY_TITLE+
                " is Null "+" ORDER BY "+orderBy;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") ModelRecord modelRecord=new ModelRecord(
                        ""+cursor.getInt(cursor.getColumnIndex(Constant.KEY_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_DEPARTMENT)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_IMAGE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_TITLE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_DESCRIPTION)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_IMAGE_GALLERY)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_TIME_ADD))
                );
                recordsList.add(modelRecord);
            } while (cursor.moveToNext());
        }
        db.close();
        return recordsList;
    }

    public ArrayList<ModelRecord> getPictureETK(String depart, String title){
        ArrayList<ModelRecord> recordsList=new ArrayList<>();
        String selectQuery="SELECT "+ Constant.KEY_IMAGE_GALLERY+","+Constant.KEY_TIME_ADD+" FROM "+Constant.TABLE_NAME_ETK+" WHERE "+Constant.KEY_DEPARTMENT+
                " =\'"+depart+"\'"+" and "+Constant.KEY_TITLE+" =\'"+title+"\'"
                +" and "+Constant.KEY_IMAGE_GALLERY+" LIKE \"_____%\" ";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") ModelRecord modelRecord=new ModelRecord(
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_IMAGE_GALLERY)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_TIME_ADD))
                );
                recordsList.add(modelRecord);
            } while (cursor.moveToNext());
        }
        db.close();
        return recordsList;
    }

    public ArrayList<ModelRecord> getRecTitleETK(String depart, String orderBy){
        ArrayList<ModelRecord> recordsList=new ArrayList<>();
        String selectQuery="SELECT * FROM "+Constant.TABLE_NAME_ETK+" WHERE "+Constant.KEY_DEPARTMENT+
                " =\'"+depart+"\'"+" and "+Constant.KEY_TITLE+" is NOT NULL"+" GROUP BY "+Constant.KEY_TITLE+ " ORDER BY "+orderBy;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") ModelRecord modelRecord=new ModelRecord(
                        ""+cursor.getInt(cursor.getColumnIndex(Constant.KEY_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_DEPARTMENT)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_IMAGE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_TITLE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_DESCRIPTION)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_IMAGE_GALLERY)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_TIME_ADD))
                );
                recordsList.add(modelRecord);
            } while (cursor.moveToNext());
        }
        db.close();
        return recordsList;
    }

    public ArrayList<ModelRecord> getUpDateTitleETK(String selection, String tilte, String discr, String addtime){
        ArrayList<ModelRecord> recordsList=new ArrayList<>();
        String selectQuery="UPDATE "+Constant.TABLE_NAME_ETK+
                " SET "
                +Constant.KEY_TITLE+" =\'"+tilte+"\'"+
                " , "+
                Constant.KEY_DESCRIPTION +" =\'"+discr+"\'"+" , "+
                Constant.KEY_TIME_ADD+" =\'"+addtime+"\'"+
                //" , "+Constant.KEY_IMAGE_GALLERY+" =\'"+galery+"\'"+
                " WHERE "+Constant.KEY_TITLE+" =\'"+selection+"\'";

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") ModelRecord modelRecord=new ModelRecord(
                        ""+cursor.getInt(cursor.getColumnIndex(Constant.KEY_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_DEPARTMENT)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_IMAGE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_TITLE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_DESCRIPTION)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_IMAGE_GALLERY)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_TIME_ADD))
                );
                recordsList.add(modelRecord);
            } while (cursor.moveToNext());
        }
        db.close();
        return recordsList;
    }

    public ArrayList<ModelRecord> updateTitleNotiETK (String id, String addNoti){
        ArrayList<ModelRecord> recordsList=new ArrayList<>();
        String selectQuery="UPDATE "+Constant.TABLE_NAME_ETK+
                " SET "+Constant.KEY_TIME_NOTI+" =\'"+addNoti+"\'"+
                " WHERE "+Constant.KEY_ID+" =\'"+id+"\'";


        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") ModelRecord modelRecord=new ModelRecord(
                        ""+cursor.getInt(cursor.getColumnIndex(Constant.KEY_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_DEPARTMENT)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_IMAGE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_TITLE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_DESCRIPTION)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_IMAGE_GALLERY)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_TIME_ADD))
                );
                recordsList.add(modelRecord);
            } while (cursor.moveToNext());
        }
        db.close();
        return recordsList;
    }

    public ArrayList<ModelRecord> deleteGalleryETK(String gallery, String title){
        ArrayList<ModelRecord> recordsList=new ArrayList<>();
        String selectQuery="DELETE FROM "+Constant.TABLE_NAME_ETK+

                " WHERE "+Constant.KEY_IMAGE_GALLERY+" =\'"+gallery+"\'"+ " AND "+Constant.KEY_TITLE+" =\'"+title+"\'"
                + " AND "+Constant.KEY_DESCRIPTION +" is "+"'null'";

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") ModelRecord modelRecord=new ModelRecord(
                        ""+cursor.getInt(cursor.getColumnIndex(Constant.KEY_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_DEPARTMENT)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_IMAGE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_TITLE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_DESCRIPTION)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_IMAGE_GALLERY)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_TIME_ADD))
                );
                recordsList.add(modelRecord);
            } while (cursor.moveToNext());
        }
        db.close();
        return recordsList;
    }

    public ArrayList<ModelRecord> getUpDateDepartETK(String selection, String depart, String image){
        ArrayList<ModelRecord> recordsList=new ArrayList<>();
        String selectQuery="UPDATE "+Constant.TABLE_NAME_ETK+
                " SET "
                +Constant.KEY_DEPARTMENT+" =\'"+depart+"\'"+
                " , "+
                Constant.KEY_IMAGE+" =\'"+image+"\'"+
                " WHERE "+Constant.KEY_DEPARTMENT+" =\'"+selection+"\'";

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") ModelRecord modelRecord=new ModelRecord(
                        ""+cursor.getInt(cursor.getColumnIndex(Constant.KEY_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_DEPARTMENT)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_IMAGE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_TITLE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_DESCRIPTION)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_IMAGE_GALLERY)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_TIME_ADD))
                );
                recordsList.add(modelRecord);
            } while (cursor.moveToNext());
        }
        db.close();
        return recordsList;
    }

    public ArrayList<ModelRecord> delDepartETK(String depart){
        ArrayList<ModelRecord> recordsList=new ArrayList<>();
        String selectQuery="DELETE FROM "+Constant.TABLE_NAME_ETK+
                " WHERE "+Constant.KEY_DEPARTMENT+" =\'"+depart+"\'";

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") ModelRecord modelRecord=new ModelRecord(
                        ""+cursor.getInt(cursor.getColumnIndex(Constant.KEY_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_DEPARTMENT)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_IMAGE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_TITLE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_DESCRIPTION)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_IMAGE_GALLERY)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_TIME_ADD))
                );
                recordsList.add(modelRecord);
            } while (cursor.moveToNext());
        }
        db.close();
        return recordsList;
    }

    public ArrayList<ModelRecord> delTitleETK(String depart, String title){
        ArrayList<ModelRecord> recordsList=new ArrayList<>();
        String selectQuery="DELETE FROM "+Constant.TABLE_NAME_ETK+
                " WHERE "+Constant.KEY_DEPARTMENT+" =\'"+depart+"\'"+ " AND "+Constant.KEY_TITLE+" =\'"+title+"\'";

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") ModelRecord modelRecord=new ModelRecord(
                        ""+cursor.getInt(cursor.getColumnIndex(Constant.KEY_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_DEPARTMENT)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_IMAGE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_TITLE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_DESCRIPTION)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_IMAGE_GALLERY)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_TIME_ADD))
                );
                recordsList.add(modelRecord);
            } while (cursor.moveToNext());
        }
        db.close();
        return recordsList;
    }

    public ArrayList<ModelRecord> searchRecords(String query, String depart){
        ArrayList<ModelRecord> recordsList=new ArrayList<>();
        String selectQuery="SELECT * FROM "+Constant.TABLE_NAME_ETK+" WHERE "+
                Constant.KEY_DEPARTMENT+" =\'"+depart+"\'"+" and "+Constant.KEY_TITLE+" LIKE '%"+query+"%'"
                + " AND "+Constant.KEY_DESCRIPTION +" is not "+"'null'";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") ModelRecord modelRecord=new ModelRecord(
                        ""+cursor.getInt(cursor.getColumnIndex(Constant.KEY_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_DEPARTMENT)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_IMAGE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_TITLE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_DESCRIPTION)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_IMAGE_GALLERY)),
                        ""+cursor.getString(cursor.getColumnIndex(Constant.KEY_TIME_ADD))
                );
                recordsList.add(modelRecord);
            } while (cursor.moveToNext());
        }
        db.close();
        return recordsList;
    }
}
