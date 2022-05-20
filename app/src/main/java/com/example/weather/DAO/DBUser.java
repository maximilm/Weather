package com.example.weather.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weather.model.City;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
            int indexDATES = cursor.getColumnIndex(DBHelper.KEY_DATES);
            do {
                City c = new City();
                c.setId(String.valueOf(cursor.getInt(indexID)));
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

    public void delete(String id){
        database = dbHelper.getWritableDatabase();
        int delCount = database.delete(DBHelper.TABLE_CITIES, DBHelper.KEY_ID + "=" + id, null);
    }

    public void updateDataBase() {
        for (City i: cities) {
            City city = new City();
            double latitude = Double.valueOf(i.getLat());
            double longitude = Double.valueOf(i.getLon());
            String s = i.getName().substring(0, 1).toUpperCase() + i.getName().substring(1, i.getName().length());
            city.setName(s);
            city.setLat(Double.toString(latitude));
            city.setLon(Double.toString(longitude));
            city.setId(i.getId());
            String url = "https://api.openweathermap.org/data/2.5/onecall?lat="
                    + Double.toString(latitude) + "&lon=" + Double.toString(longitude) +
                    "&exclude=minutely,hourly,alerts&appid=42d99cf98d8705825d19066e14689be6&units=metric&lang=ru";
            RequestQueue queue = Volley.newRequestQueue(context);
            JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        JSONObject current = response.getJSONObject("current");
                        String tempNow = Double.toString(current.getDouble("temp"));
                        city.setTempNow(tempNow);
                        String humidityNow = Double.toString(current.getDouble("humidity"));
                        city.setHumidityNow(humidityNow);

                        JSONArray weatherNow = current.getJSONArray("weather");
                        JSONObject weatherN = weatherNow.getJSONObject(0);
                        String descriptionNow = weatherN.getString("description");
                        city.setDescriptionNow(descriptionNow);

                        JSONArray daily = response.getJSONArray("daily");
                        JSONObject day = daily.getJSONObject(0);
                        long k = response.getInt("timezone_offset");
                        long d = (day.getLong("sunrise"));
                        d += k;
                        String sunriseToday = new java.text.SimpleDateFormat("dd/MM HH:mm").format(new java.util.Date (d*1000));
                        city.setSunriseToday(sunriseToday);


                        d = (day.getLong("sunset"));
                        d += k;
                        String sunsetToday = new java.text.SimpleDateFormat("dd/MM HH:mm").format(new java.util.Date (d*1000));
                        city.setSunsetToday(sunsetToday);

                        JSONObject tempToday = day.getJSONObject("temp");

                        String maxTempToday = Double.toString(tempToday.getDouble("max"));
                        city.setMaxTempToday(maxTempToday);

                        String minTempToday = Double.toString(tempToday.getDouble("min"));
                        city.setMinTempToday(minTempToday);

                        List<List<String>> temperatures = new ArrayList<>();
                        List<String> dates = new ArrayList<>();
                        long epoch = System.currentTimeMillis() / 1000 + k;
                        String date = new java.text.SimpleDateFormat("dd/MM HH:mm").format(new java.util.Date(epoch * 1000));
                        city.setTimeNow(date);
                        for (int i = 0; i < 7; i++) {
                            day = daily.getJSONObject(i);
                            JSONObject tempDay = day.getJSONObject("temp");
                            List<String> t = new ArrayList<>(); // температура за 1 из 7 дней
                            t.add(Double.toString(tempDay.getDouble("morn")));
                            t.add(Double.toString(tempDay.getDouble("day")));
                            t.add(Double.toString(tempDay.getDouble("eve")));
                            t.add(Double.toString(tempDay.getDouble("night")));
                            temperatures.add(t);

                            epoch = day.getLong("dt") + k; // дата 1 из 7 дней
                            date = new java.text.SimpleDateFormat("dd/MM").format(new java.util.Date(epoch * 1000));
                            dates.add(date);
                        }
                        city.setTemperatures(temperatures);
                        city.setDates(dates);
                        dbuser.update(city);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            queue.add(jor);
        }
    }
    public void update (City c){
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
        contentValues.put(DBHelper.KEY_DATES, c.convertArrayToString("dates"));
        int updCount = database.update(DBHelper.TABLE_CITIES, contentValues, DBHelper.KEY_ID + "= ?", new String[] {c.getId()});
    }

    public boolean checkCity(String name){
        for (City i: cities){
            if (i.getName().toLowerCase().equals(name.toLowerCase())){
                return true;
            }
        }
        return false;
    }

}
