package com.example.weather.view;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.weather.DAO.DBUser;
import com.example.weather.R;
import com.example.weather.model.City;

import java.util.ArrayList;
import java.util.List;


public class CityFragment extends Fragment {
    DBUser dbuser;
    ArrayList<City> cities;
    City city;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbuser = DBUser.getDbuser(getActivity());
        dbuser.downloadDatabase();
        cities = dbuser.getCities();
        Bundle bundle = getArguments();
        int id = Integer.parseInt(bundle.getString("id"));
        city = cities.get(id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city, container, false);
        TextView cityname = view.findViewById(R.id.city_name);
        cityname.setText(city.getName());
        TextView tempnow = view.findViewById(R.id.temperature);
        tempnow.setText(city.getTempNow());
        TextView min_max_temp = view.findViewById(R.id.max_min_temp);
        min_max_temp.setText(city.getMaxTempToday() + "/" + city.getMinTempToday());
        TextView date_time = view.findViewById(R.id.date_time);
        date_time.setText(city.getTimeNow());
        TextView weather = view.findViewById(R.id.weather_id);
        weather.setText(city.getDescriptionNow());
        TextView sunrise = view.findViewById(R.id.sunrise);
        sunrise.setText(city.getSunriseToday());
        TextView sunset = view.findViewById(R.id.sunset);
        sunset.setText(city.getSunsetToday());
        TextView humidity = view.findViewById(R.id.humidity);
        humidity.setText(city.getHumidityNow());
        List<List<String>> tab = city.getTemperatures();
        List<String> da = city.getDates();
        String [][] arr = new String[7][5];
        for (int i = 0; i < 7; i++){
            arr[i][0] = da.get(i);
            for (int j = 1; j < 5; j++){
                arr[i][j] = tab.get(i).get(j - 1);
            }
        }

        TableLayout temptable = view.findViewById(R.id.temperature_row);
        temptable.removeAllViews();
        TableRow row = new TableRow(getActivity());
        TextView item0 = new TextView(getActivity());
        TextView item1 = new TextView(getActivity());
        TextView item2 = new TextView(getActivity());
        TextView item3 = new TextView(getActivity());
        TextView item4 = new TextView(getActivity());
        TextView item5 = new TextView(getActivity());
        TextView item6 = new TextView(getActivity());
        item0.setPadding(25,0,25, 0);
        item1.setPadding(25,0,25, 0);
        item2.setPadding(25,0,25,0);
        item3.setPadding(25,0,25,0);
        item4.setPadding(25,0,25,0);
        item5.setPadding(25,0,25,0);
        item6.setPadding(25,0,25,0);
        row.setPadding(0, 0, 0, 0);
        item0.setText("   ");
        item0.setTextSize(20);
        row.addView(item0, 0);
        item1.setText("Утро");
        item1.setTextColor(Color.WHITE);
        item1.setTextSize(20);
        row.addView(item1, 1);
        item2.setText("День");
        item2.setTextColor(Color.WHITE);
        item2.setTextSize(20);
        row.addView(item2, 2);
        item3.setText("Вечер");
        item3.setTextColor(Color.WHITE);
        item3.setTextSize(20);
        row.addView(item3,3);
        item4.setText("Ночь");
        item4.setTextColor(Color.WHITE);
        item4.setTextSize(20);
        row.addView(item4,4);
        row.addView(item5,5);
        row.addView(item6,6);
        temptable.addView(row);

        for (int i = 0; i < 7;i++){
            row = new TableRow(getActivity());
            item0 = new TextView(getActivity());
            item1 = new TextView(getActivity());
            item2 = new TextView(getActivity());
            item3 = new TextView(getActivity());
            item4 = new TextView(getActivity());
            item5 = new TextView(getActivity());
            item6 = new TextView(getActivity());
            item0.setPadding(25,0,25, 0);
            item1.setPadding(25,0,25, 0);
            item2.setPadding(25,0,25,0);
            item3.setPadding(25,0,25,0);
            item4.setPadding(25,0,25,0);
            item5.setPadding(25,0,25,0);
            item6.setPadding(25,0,25,0);
            row.setPadding(0, 20, 0, 20);
            item0.setText(arr[i][0]);
            item0.setTextSize(20);
            item0.setTextColor(Color.WHITE);
            row.addView(item0);
            item1.setText(arr[i][1]);
            item1.setTextColor(Color.GRAY);
            item1.setTextSize(20);
            row.addView(item1);
            item2.setText(arr[i][2]);
            item2.setTextColor(Color.GRAY);
            item2.setTextSize(20);
            row.addView(item2);
            item3.setText(arr[i][3]);
            item3.setTextColor(Color.GRAY);
            item3.setTextSize(20);
            row.addView(item3);
            item4.setText(arr[i][4]);
            item4.setTextColor(Color.GRAY);
            item4.setTextSize(20);
            row.addView(item4);
            temptable.addView(row);
        }

        return view;
    }

}
