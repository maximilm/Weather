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
        contentValues.put(DBHelper.KEY_LAT, c.getLat());
        contentValues.put(DBHelper.KEY_LON, c.getLon());
        contentValues.put(DBHelper.KEY_TEMPNOW, c.getTempNow());
        contentValues.put(DBHelper.KEY_SUNRISETODAY, c.getSunriseToday());
        contentValues.put(DBHelper.KEY_SUNSETTODAY, c.getSunsetToday());
        contentValues.put(DBHelper.KEY_MAXTEMPTODAY, c.getMaxTempToday());
        contentValues.put(DBHelper.KEY_MINTEMPTODAY, c.getMinTempToday());
        contentValues.put(DBHelper.KEY_TIMENOW, c.getTimeNow());
        contentValues.put(DBHelper.KEY_HUMIDITYNOW , c.getHumidityNow());
        contentValues.put(DBHelper.KEY_DESCRIPTIONNOW, c.getDescriptionNow());
        contentValues.put(DBHelper.KEY_TEMPERATURES, c.convertArrayToString("temperatures"));
        contentValues.put(DBHelper.KEY_DESCRIPTIONS, c.convertArrayToString("descriptions"));
        contentValues.put(DBHelper.KEY_DATES, c.convertArrayToString("dates"));


        database.insert(DBHelper.TABLE_CITIES, null, contentValues);

    }

    public void downloadDatabase(){
        cities = new ArrayList<City>();
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_CITIES, null, null,
                null, null, null, null);

        if (cursor.moveToFirst()){
            int indexID = cursor.getColumnIndex(DBHelper.KEY_ID);
            int indexNAME = cursor.getColumnIndex(DBHelper.KEY_NAME);
            int indexLAT = cursor.getColumnIndex(DBHelper.KEY_LAT);
            int indexLON = cursor.getColumnIndex(DBHelper.KEY_LON);
            int indexTEMPNOW = cursor.getColumnIndex(DBHelper.KEY_TEMPNOW);
            int indexSUNRISETODAY = cursor.getColumnIndex(DBHelper.KEY_SUNRISETODAY);
            int indexSUNSETTODAY = cursor.getColumnIndex(DBHelper.KEY_SUNSETTODAY);
            int indexMAXTEMPTODAY = cursor.getColumnIndex(DBHelper.KEY_MAXTEMPTODAY);
            int indexMINTEMPTODAY = cursor.getColumnIndex(DBHelper.KEY_MINTEMPTODAY);
            int indexTIMENOW = cursor.getColumnIndex(DBHelper.KEY_TIMENOW);
            int indexHUMIDITYNOW = cursor.getColumnIndex(DBHelper.KEY_HUMIDITYNOW);
            int indexDESCRIPTIONNOW = cursor.getColumnIndex(DBHelper.KEY_DESCRIPTIONNOW);
            int indexTEMPERATURES = cursor.getColumnIndex(DBHelper.KEY_TEMPERATURES);
            int indexDESCRIPTIONS = cursor.getColumnIndex(DBHelper.KEY_DESCRIPTIONS);
            int indexDATES = cursor.getColumnIndex(DBHelper.KEY_DATES);
            do {
                City c = new City();
                c.setId(cursor.getString(indexID));
                c.setName(cursor.getString(indexNAME));
                c.setLat(cursor.getString(indexLAT));
                c.setLon(cursor.getString(indexLON));
                c.setTempNow(cursor.getString(indexTEMPNOW));
                c.setSunriseToday(cursor.getString(indexSUNRISETODAY));
                c.setSunsetToday(cursor.getString(indexSUNSETTODAY));
                c.setMaxTempToday(cursor.getString(indexMAXTEMPTODAY));
                c.setMinTempToday(cursor.getString(indexMINTEMPTODAY));
                c.setTimeNow(cursor.getString(indexTIMENOW));
                c.setHumidityNow(cursor.getString(indexHUMIDITYNOW));
                c.setDescriptionNow(cursor.getString(indexDESCRIPTIONNOW));
                c.setTemperatures(City.covertStringtoArrayArray(cursor.getString(indexTEMPERATURES)));
                c.setDescriptions(City.convertStringtoArray(cursor.getString(indexDESCRIPTIONS)));
                c.setDates(City.convertStringtoArray(cursor.getString(indexDATES)));

                cities.add(c);
            } while(cursor.moveToNext());
        }

        cursor.close();

    }


    public int getCitiesLength(){
        return cities.size();
    }

    public void del(){
        database.close();
        dbHelper.close();
    }

    public ArrayList<City> getCities(){
        return cities;
    }

}
