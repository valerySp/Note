package com.sparnyuk.notes.DBHelper;

public class Constant {
    public static final int DATABASE_VERSION =1;
    public static final String DATABASE_NAME ="OGE";
    public static final String TABLE_NAME_WATER ="WATER";
    public static final String TABLE_NAME_ETK ="ETK";
    public static final String TABLE_NAME_GAS ="GAS";
    public static final String TABLE_NAME_CLIMATE ="CLIMATE";
    public static final String TABLE_NAME_DEV ="DEV";
    public static final String TABLE_NAME_STR ="STR";
    public static final String TABLE_NAME_OTD ="OTD";

    public static final String TABLE_NAME_OTHER ="OTHER";
    public static final String TABLE_NAME_NOTI ="NOTI";
    public static final String KEY_ID="_id";
    public static final String KEY_DEPARTMENT="department";
    public static final String KEY_IMAGE="image";

    public static final String KEY_TITLE="title";
    public static final String KEY_DESCRIPTION ="discr";
    public static final String KEY_IMAGE_GALLERY="gallery";
    public static final String KEY_TIME_ADD="time_add";
    public static final String KEY_TIME_NOTI ="time_noti";

    public static final String CREATE_TABLE_ETK="CREATE TABLE " + TABLE_NAME_ETK +" ("
            + KEY_ID + " INTEGER PRIMARY KEY , "
            + KEY_DEPARTMENT + " TEXT,"
            + KEY_IMAGE + " TEXT, "
            + KEY_TITLE + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_IMAGE_GALLERY + " TEXT, "
            + KEY_TIME_ADD + " TEXT, "
            + KEY_TIME_NOTI + " TEXT, "
            + " TEXT);";
    public static final String CREATE_TABLE_WATER="CREATE TABLE " + TABLE_NAME_WATER +" ("
            + KEY_ID + " INTEGER PRIMARY KEY , "
            + KEY_DEPARTMENT + " TEXT,"
            + KEY_IMAGE + " TEXT,"
            + KEY_TITLE + " TEXT,"
            + KEY_DESCRIPTION + " TEXT,"
            + KEY_IMAGE_GALLERY + " TEXT,"
            + KEY_TIME_ADD + " TEXT,"
            + KEY_TIME_NOTI + " TEXT,"
            + " TEXT);";
    public static final String CREATE_TABLE_GAS="CREATE TABLE " + TABLE_NAME_GAS +" ("
            + KEY_ID + " INTEGER PRIMARY KEY , "
            + KEY_DEPARTMENT + " TEXT, "
            + KEY_IMAGE + " TEXT, "
            + KEY_TITLE + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_IMAGE_GALLERY + " TEXT, "
            + KEY_TIME_ADD + " TEXT, "
            + KEY_TIME_NOTI + " TEXT, "
            + " TEXT);";
    public static final String CREATE_TABLE_CLIMATE="CREATE TABLE " + TABLE_NAME_CLIMATE +" ("
            + KEY_ID + " INTEGER PRIMARY KEY , "
            + KEY_DEPARTMENT + " TEXT, "
            + KEY_IMAGE + " TEXT, "
            + KEY_TITLE + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_IMAGE_GALLERY + " TEXT, "
            + KEY_TIME_ADD + " TEXT, "
            + KEY_TIME_NOTI + " TEXT, "
            + " TEXT);";
    public static final String CREATE_TABLE_DEV="CREATE TABLE " + TABLE_NAME_DEV +" ("
            + KEY_ID + " INTEGER PRIMARY KEY , "
            + KEY_DEPARTMENT + " TEXT, "
            + KEY_IMAGE + " TEXT, "
            + KEY_TITLE + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_IMAGE_GALLERY + " TEXT, "
            + KEY_TIME_ADD + " TEXT, "
            + KEY_TIME_NOTI + " TEXT, "
            + " TEXT);";
    public static final String CREATE_TABLE_NOTI="CREATE TABLE " + TABLE_NAME_NOTI +" ("
            + KEY_ID + " INTEGER PRIMARY KEY , "
            + KEY_DEPARTMENT + " TEXT, "
            + KEY_TITLE + " TEXT, "
            + KEY_TIME_NOTI + " TEXT, "
            + " TEXT);";

    public static final String CREATE_TABLE_STR="CREATE TABLE " + TABLE_NAME_STR +" ("
            + KEY_ID + " INTEGER PRIMARY KEY , "
            + KEY_DEPARTMENT + " TEXT,"
            + KEY_IMAGE + " TEXT, "
            + KEY_TITLE + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_IMAGE_GALLERY + " TEXT, "
            + KEY_TIME_ADD + " TEXT, "
            + KEY_TIME_NOTI + " TEXT, "
            + " TEXT);";

    public static final String CREATE_TABLE_OTD="CREATE TABLE " + TABLE_NAME_OTD +" ("
            + KEY_ID + " INTEGER PRIMARY KEY , "
            + KEY_DEPARTMENT + " TEXT,"
            + KEY_IMAGE + " TEXT, "
            + KEY_TITLE + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_IMAGE_GALLERY + " TEXT, "
            + KEY_TIME_ADD + " TEXT, "
            + KEY_TIME_NOTI + " TEXT, "
            + " TEXT);";

    public static final String CREATE_TABLE_OTHER="CREATE TABLE " + TABLE_NAME_OTHER +" ("
            + KEY_ID + " INTEGER PRIMARY KEY , "
            + KEY_DEPARTMENT + " TEXT,"
            + KEY_IMAGE + " TEXT, "
            + KEY_TITLE + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_IMAGE_GALLERY + " TEXT, "
            + KEY_TIME_ADD + " TEXT, "
            + KEY_TIME_NOTI + " TEXT, "
            + " TEXT);";
}
