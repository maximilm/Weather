package com.example.weather.view;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weather.DAO.DBUser;
import com.example.weather.R;
import com.example.weather.model.City;
import com.example.weather.viewmodel.SearchViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {
    DBUser dbuser = DBUser.getDbuser(getActivity());
    City city = new City();
    boolean k = true;
    SearchViewModel vm;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(this).get(SearchViewModel.class);
        dbuser.downloadDatabase();
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
                if (!dbuser.checkCity(search_field.getText().toString())) {
                    Geocoder geocoder = new Geocoder(getActivity());
                    try {
                        vm.setAddress(search_field.getText().toString(), geocoder);
                        vm.getAddress().observe(getViewLifecycleOwner(), new Observer<List<Address>>() {
                            @Override
                            public void onChanged(List<Address> addresses) {
                                if (addresses.size() > 0) {
                                    double latitude = addresses.get(0).getLatitude();
                                    double longitude = addresses.get(0).getLongitude();
                                    String s = search_field.getText().toString().substring(0, 1).toUpperCase() + search_field.getText().toString().substring(1, search_field.getText().toString().length());
                                    city.setName(s);
                                    city.setLat(Double.toString(latitude));
                                    city.setLon(Double.toString(longitude));
                                    city.setId(String.valueOf(dbuser.getCities().size()));
                                    String url = "https://api.openweathermap.org/data/2.5/onecall?lat="
                                            + Double.toString(latitude) + "&lon=" + Double.toString(longitude) +
                                            "&exclude=minutely,hourly,alerts&appid=42d99cf98d8705825d19066e14689be6&units=metric&lang=ru";

                                    System.out.println(url);
                                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                                    JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                System.out.println(response.names());

                                                JSONObject current = response.getJSONObject("current"); // сегодняшняя погода
                                                String tempNow = Double.toString(current.getDouble("temp")); // температура сегодня
                                                city.setTempNow(tempNow);
                                                String humidityNow = Double.toString(current.getDouble("humidity")); // влажность сегодня
                                                city.setHumidityNow(humidityNow);

                                                JSONArray weatherNow = current.getJSONArray("weather"); // погода сегодня
                                                JSONObject weatherN = weatherNow.getJSONObject(0);
                                                String descriptionNow = weatherN.getString("description"); // описание погоды сегодня
                                                city.setDescriptionNow(descriptionNow);

                                                JSONArray daily = response.getJSONArray("daily"); // дни вперед
                                                JSONObject day = daily.getJSONObject(0);
                                                long k = response.getInt("timezone_offset"); // часовой пояс
                                                long d = (day.getLong("sunrise")); //
                                                d += k;
                                                String sunriseToday = new java.text.SimpleDateFormat("dd/MM HH:mm").format(new java.util.Date(d * 1000));
                                                city.setSunriseToday(sunriseToday);


                                                d = (day.getLong("sunset"));
                                                d += k;
                                                String sunsetToday = new java.text.SimpleDateFormat("dd/MM HH:mm").format(new java.util.Date(d * 1000));
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
                                                dbuser.uploadDatabase(city);
                                                dbuser.setCity(city);
                                                ListFragment LF = new ListFragment();
                                                FragmentTransaction FT = getActivity().getSupportFragmentManager().beginTransaction();
                                                FT.replace(R.id.conteiner, LF);
                                                FT.commit();
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
                                } else {
                                    search.setText("Введен некорректный адрес");
                                }
                            }
                        });
                    } catch (IOException ex) {
                        search.setText("Вы ничего не ввели или возникли проблемы с подключением к интернету");
                    }
                } else{
                    search.setText("Город уже добавлен");
                }
            }
        });
        return v;
    }
}