package com.example.weather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class DBUser {
    private static DBUser dbuser;
    private SQLiteDatabase database;
    private Context context;
    private DBHelper dbHelper;
    private ArrayList<City> cities = new ArrayList<City>();

    private DBUser (Context context){
        this.context = context;
        dbHelper = new DBHelper(context);
    }

    public static DBUser getDbuser(Context context){
        if (dbuser == null){
            dbuser = new DBUser(context);
        }
        return dbuser;
    }

    public boolean check(){
        database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        Cursor cursor = database.query(DBHelper.TABLE_CITIES, null, null,
                null, null, null, null);

        if (cursor.moveToFirst()){
            cursor.close();
            database.close();
            return true;
        } else {
            cursor.close();
            database.close();
            return false;
        }
    }

    public void setCity(City city) {
        cities.add(city);
    }

    public void uploadDatabase(City c) {
        database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.KEY_NAME, c.getName());
        contentValues.put(DBHelper.KEY_TIME, c.getTimeNow());
        contentValues.put(DBHelper.KEY_LAT, c.getLat());

        database.insert(DBHelper.TABLE_CITIES, null, contentValues);
        /*database.execSQL("DROP TABLE IF EXISTS " + "TABLE_CITIES");
        database.execSQL("CREATE TABLE TABLE_CITIES (name STRING, lat STRING, lon STRING, tempNow STRING, sunriseToday STRING, sunsetToday STRING, " +
                "maxTempToday STRING, minTempToday STRING, humidityNow STRING, descriptionNow STRING, timeNow STRING, temperatures STRING, " +
                "descriptions STRING, dates STRING, id STRING)");
        for (City i : cities) {
            String data = "'" + i.getName() + "', '" + i.getLat() + "', '" + i.getLon() + "', '" + i.getTempNow() + "', '"
                    + i.getSunriseToday() + "', '" + i.getSunsetToday() + "', '" + i.getMaxTempToday() + "', '" + i.getMinTempToday() + "', '"
                    + i.getHumidityNow() + "', '" + i.getDescriptionNow() + "', '" + i.getTimeNow() + "', '" + i.convertArrayToString("temperatures")
                    + "', '" + i.convertArrayToString("descriptions") + "', '" + i.convertArrayToString("dates") + "', '" + i.getId() + "'";
            database.execSQL(" INSERT INTO TABLE_CITIES " + "(name, lat, lon, tempNow, sunriseToday, sunsetToday, maxTempToday, " +
                    "minTempToday, humidityNow, descriptionNow, timeNow, temperatures, descriptions, dates, id)" +
                    "VALUES (" + data + ")");
        }

        database.close();*/
    }

    public int getCitiesLength(){
        return cities.size();
    }
}
