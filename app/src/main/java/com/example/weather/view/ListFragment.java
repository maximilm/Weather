package com.example.weather.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.weather.DAO.DBUser;
import com.example.weather.R;
import com.example.weather.model.City;
import com.example.weather.viewmodel.ListViewModel;
import com.example.weather.viewmodel.SearchViewModel;

import java.util.ArrayList;
import java.util.List;


public class ListFragment extends Fragment {
    DBUser dbuser;
    private int HEIGHT = 10;
    private Button[] cells1;
    private Button[] cells2;
    TextView tw;
    ListViewModel vm;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(this).get(ListViewModel.class);
        vm.setCities(getActivity());
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
        vm.getCities().observe(getViewLifecycleOwner(), new Observer<List<City>>() {
            @Override
            public void onChanged(List<City> cities) {
                for (int i = 0; i < cities.size(); i++) {
                    cells1[i].setText(cities.get(i).getId() + " " + cities.get(i).getName());
                }
                for (int i = cities.size(); i < HEIGHT; i++) {
                    cells1[i].setVisibility(View.GONE);
                }
            }
        });

    }

    void generate2(View v){
        vm.getCities().observe(getViewLifecycleOwner(), new Observer<List<City>>() {
            @Override
            public void onChanged(List<City> cities) {
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
        });
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
                    vm.delete(y);
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