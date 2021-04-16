package com.example.weather;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;


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
        String [][] arr = new String[7][5];
        arr[0][0] = "11.03";
        arr[0][1] = "-25";
        arr[0][2] = "-20";
        arr[0][3] = "-19";
        arr[0][4] = "-22";

        arr[1][0] = "12.03";
        arr[1][1] = "-25";
        arr[1][2] = "-25";
        arr[1][3] = "-25";
        arr[1][4] = "-25";

        arr[2][0] = "13.03";
        arr[2][1] = "-25";
        arr[2][2] = "-25";
        arr[2][3] = "-25";
        arr[2][4] = "-25";

        arr[3][0] = "14.03";
        arr[3][1] = "-25";
        arr[3][2] = "-25";
        arr[3][3] = "-25";
        arr[3][4] = "-25";

        arr[4][0] = "15.03";
        arr[4][1] = "-25";
        arr[4][2] = "-25";
        arr[4][3] = "-25";
        arr[4][4] = "-25";

        arr[5][0] = "16.03";
        arr[5][1] = "-25";
        arr[5][2] = "-25";
        arr[5][3] = "-25";
        arr[5][4] = "-25";

        arr[6][0] = "17.03";
        arr[6][1] = "-25";
        arr[6][2] = "-25";
        arr[6][3] = "-25";
        arr[6][4] = "-25";

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
        item0.setPadding(45, 0, 45, 0);
        item1.setPadding(45, 0, 45, 0);
        item2.setPadding(45,0,45,0);
        item3.setPadding(45,0,45,0);
        item4.setPadding(45,0,45,0);
        item5.setPadding(45,0,45,0);
        item6.setPadding(45,0,45,0);
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
            item0.setPadding(45, 0, 45, 0);
            item1.setPadding(45, 0, 45, 0);
            item2.setPadding(45,0,45,0);
            item3.setPadding(45,0,45,0);
            item4.setPadding(45,0,45,0);
            item5.setPadding(45,0,45,0);
            item6.setPadding(45,0,45,0);
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
