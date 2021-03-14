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
    public static final String KEY_TIME = "_time"; // текущее время в городе
    public static final String KEY_LAT = "_lat";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_CITIES + "(" + KEY_ID + " integer," + KEY_NAME +
                " text," + KEY_TIME + " text," + KEY_LAT + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
