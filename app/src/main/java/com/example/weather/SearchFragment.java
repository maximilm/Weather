package com.example.weather;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;


public class SearchFragment extends Fragment {
    DBUser dbuser;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbuser = DBUser.getDbuser(getActivity());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        EditText search_field = v.findViewById(R.id.search_field);
        TextView search = v.findViewById(R.id.search);
        v.findViewById(R.id.search_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Geocoder geocoder = new Geocoder(getActivity());
                List<Address> addresses;
                try {
                    City city = new City();
                    addresses = geocoder.getFromLocationName(search_field.getText().toString(), 1);
                    if (addresses.size() > 0) {
                        double latitude = addresses.get(0).getLatitude();
                        double longitude = addresses.get(0).getLongitude();
                        city.setName(search_field.getText().toString());
                        city.setLat(Double.toString(latitude));
                        city.setLon(Double.toString(longitude));
                        String url = "https://api.openweathermap.org/data/2.5/onecall?lat="
                                + Double.toString(latitude)+"&lon="+ Double.toString(longitude)+
                                "&exclude=minutely,hourly,alerts&appid=42d99cf98d8705825d19066e14689be6&units=imperial&lang=ru";
                        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {

                                    JSONObject current = response.getJSONObject("current");
                                    String tempNow = Double.toString(current.getDouble("temp"));
                                    city.setTempNow(tempNow);

                                    String humidityNow = Double.toString(current.getDouble("humidity"));
                                    city.setTempNow(humidityNow);

                                    JSONArray weatherNow = current.getJSONArray("weather");
                                    JSONObject weatherN = weatherNow.getJSONObject(0);
                                    String descriptionNow = weatherN.getString("description");
                                    city.setDescriptionNow(descriptionNow);

                                    JSONArray daily = response.getJSONArray("daily");
                                    JSONObject day = daily.getJSONObject(0);

                                    String sunriseToday = Double.toString(day.getDouble("sunrise"));
                                    city.setSunriseToday(sunriseToday);

                                    String sunsetToday = Double.toString(day.getDouble("sunset"));
                                    city.setSunsetToday(sunsetToday);

                                    JSONObject tempToday = day.getJSONObject("temp");

                                    String maxTempToday = Double.toString(tempToday.getDouble("max"));
                                    city.setSunriseToday(maxTempToday);

                                    String minTempToday = Double.toString(tempToday.getDouble("min"));
                                    city.setSunsetToday(minTempToday);

                                    ArrayList<ArrayList<String>> temperatures = new ArrayList<ArrayList<String>>();
                                    ArrayList<String> descriptions = new ArrayList<String>();
                                    ArrayList<String> dates = new ArrayList<String>();
                                    long k = response.getInt("timezone_offset");
                                    long epoch = System.currentTimeMillis()/1000 + k;
                                    String date = new java.text.SimpleDateFormat("dd/MM HH:mm").format(new java.util.Date (epoch*1000));
                                    city.setTimeNow(date);
                                    for (int i = 0; i < 7; i++){
                                        day = daily.getJSONObject(i);
                                        JSONObject tempDay = day.getJSONObject("temp");
                                        temperatures.add(new ArrayList<String>());
                                        temperatures.get(i).add(Double.toString(tempDay.getDouble("morn")));
                                        temperatures.get(i).add(Double.toString(tempDay.getDouble("day")));
                                        temperatures.get(i).add(Double.toString(tempDay.getDouble("eve")));
                                        temperatures.get(i).add(Double.toString(tempDay.getDouble("night")));

                                        JSONArray weatherDay = day.getJSONArray("weather");
                                        JSONObject weatherD = weatherDay.getJSONObject(0);
                                        descriptions.add(weatherD.getString("description"));

                                        epoch = day.getLong("dt") + k;
                                        date = new java.text.SimpleDateFormat("dd/MM").format(new java.util.Date (epoch*1000));
                                        dates.add(date);
                                    }
                                    city.setTemperatures(temperatures);
                                    city.setDescriptions(descriptions);
                                    city.setDates(dates);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        dbuser.setCity(city);
                        dbuser.uploadDatabase(city);
                        RequestQueue queue = Volley.newRequestQueue(getActivity());
                        queue.add(jor);
                        if (dbuser.check()) {
                            search.setText("12345678");
                        }
                    } else {
                        search.setText("Введен некорректный адрес");
                    }
                } catch (IOException ex){
                    search.setText("Вы ничего не ввели или возникли проблемы с подключением к интернету");
                }
            }
        });
        return v;
    }
}