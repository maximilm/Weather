package com.example.weather;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class ListFragment extends Fragment {
    DBUser dbuser;
    ArrayList<City> cities;
    private int HEIGHT = 10;
    private Button[] cells1;
    private Button[] cells2;
    TextView tw;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbuser = DBUser.getDbuser(getActivity());
        dbuser.downloadDatabase();
        dbuser.updateDataBase();
        cities = dbuser.getCities();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        tw = v.findViewById(R.id.tw);
        makeCells1(v);
        makeCells2(v);
        generate1();
        generate2(v);
        return v;
    }

    void generate1(){
        for (int i = 0; i < cities.size(); i++){
            cells1[i].setText(cities.get(i).getName() + " " + cities.get(i).getId());
        }
        for (int i = cities.size(); i < HEIGHT; i++){
            cells1[i].setVisibility(View.GONE);
        }

    }

    void generate2(View v){
        Drawable drawable;
        GridLayout cells2grid = (GridLayout) v.findViewById(R.id.cells2grid);
        for (int i = 0; i < cities.size(); i++){
            drawable = cells2grid.getResources().getDrawable(R.drawable.ic_baseline_close_24);
            cells2[i].setBackground(drawable);
        }
        for (int i = cities.size(); i < HEIGHT; i++){
            cells2[i].setVisibility(View.GONE);
        }

    }

    void makeCells1(View v){
        cells1 = new Button[HEIGHT];
        GridLayout cells1grid = (GridLayout) v.findViewById(R.id.cells1Grid);
        cells1grid.removeAllViews();
        for (int i = 0; i < HEIGHT; i++){
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            cells1[i] = (Button) inflater.inflate(R.layout.cell1, cells1grid,false);

            cells1grid.addView(cells1[i]);

            cells1[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button tappedbutton = (Button) v;
                    int x = getX(tappedbutton);
                    CityFragment CF = new CityFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(x));
                    CF.setArguments(bundle);
                    FragmentTransaction FT = getActivity().getSupportFragmentManager().beginTransaction();
                    FT.replace(R.id.conteiner, CF);
                    FT.commit();

                }
            });
            cells1[i].setTag(i+",");


        }
    }
    void makeCells2(View v){
        cells2 = new Button[HEIGHT];
        GridLayout cells2grid = (GridLayout) v.findViewById(R.id.cells2grid);
        cells2grid.removeAllViews();
        for (int i = 0; i < HEIGHT; i++){
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            cells2[i] = (Button) inflater.inflate(R.layout.cell2, cells2grid,false);

            cells2grid.addView(cells2[i]);
            cells2[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button tappedbutton2 = (Button) v;
                    int y = getY((tappedbutton2));
                    cells2[y].setVisibility(View.GONE);
                    cells1[y].setVisibility(View.GONE);
                    dbuser.delete(cities.get(y).getId());

                }
            });
            cells2[i].setTag(i+",");
        }
    }
    int getX(View v) {
        return Integer.parseInt(((String) v.getTag()).split(",")[0]);
    }

    int getY(View v){
        return Integer.parseInt(((String)v.getTag()).split(",")[0]);
    }









}