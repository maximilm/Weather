package com.example.weather;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "citiesWeatherB"; // название бд
    public static final int VERSION = 1; // версия базы данных
    public static final String TABLE_CITIES = "cities"; // таблица

    public static final String KEY_ID = "_id"; // ID
    public static final String KEY_NAME = "_name"; // название города
    public static final String KEY_LAT = "_lat"; // текущее время в городе
    public static final String KEY_LON = "_lon"; // текущее время в городе
    public static final String KEY_TEMPNOW = "_tempnow"; // текущее время в городе
    public static final String KEY_SUNRISETODAY = "_sunrisetoday"; // текущее время в городе
    public static final String KEY_SUNSETTODAY = "_sunsettoday"; // текущее время в городе
    public static final String KEY_MAXTEMPTODAY = "_maxtemptoday"; // текущее время в городе
    public static final String KEY_MINTEMPTODAY = "_mintemptoday"; // текущее время в городе
    public static final String KEY_TIMENOW = "_timenow"; // текущее время в городе
    public static final String KEY_HUMIDITYNOW = "_humiditynow"; // текущее время в городе
    public static final String KEY_DESCRIPTIONNOW = "_descriptionnow"; // текущее время в городе
    public static final String KEY_TEMPERATURES = "_temperatures"; // текущее время в городе
    public static final String KEY_DESCRIPTIONS = "_descriptions"; // текущее время в городе
    public static final String KEY_DATES = "_dates"; // текущее время в городе




    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_CITIES + "(" + KEY_ID + " integer primary key," + KEY_NAME + " text," + KEY_LAT
                + " text," + KEY_LON  + " text," + KEY_TEMPNOW  + " text," + KEY_SUNRISETODAY  + " text," + KEY_SUNSETTODAY  + " text,"  +
                KEY_MAXTEMPTODAY + " text," + KEY_MINTEMPTODAY + " text," + KEY_TIMENOW + " text," + KEY_HUMIDITYNOW +
                " text," + KEY_DESCRIPTIONNOW + " text," + KEY_TEMPERATURES + " text," + KEY_DESCRIPTIONS + " text," + KEY_DATES + " text" + ")");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2){
            //db.execSQL("alter table TABLE_CITIES add column KEY_LAT text;");
        }
    }
}
