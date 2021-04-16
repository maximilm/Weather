package com.example.weather;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class SecondFragmentActivity extends AppCompatActivity {

    private int HEIGHT = 10;
    private Button[] cells1;
    private Button[] cells2;

    @Override
    protected void onCreate (Bundle savedinstanceState) {
        super.onCreate(savedinstanceState);
        setContentView(R.layout.fragment_list);

    }
    void generate1(){
        for (int i = 0;i<HEIGHT; i++){
            cells1[i].setText("Москва   Облачно   Влажность 93%");
        }
    }

    void generate2(){
        for (int i = 0;i<HEIGHT; i++){
            cells2[i].setText("Удалить");
        }

    }

    void makeCells1(){
        cells1 = new Button[HEIGHT];
        GridLayout cells1grid = (GridLayout) findViewById(R.id.cells1Grid);
        cells1grid.removeAllViews();
        for (int i = 0; i < HEIGHT; i++){
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            cells1[i] = (Button) inflater.inflate(R.layout.cell1, cells1grid,false);
        }
    }
    void makeCells2(){
        cells2 = new Button[HEIGHT];
        GridLayout cells2grid = (GridLayout) findViewById(R.id.cells2grid);
        cells2grid.removeAllViews();
        for (int i = 0; i < HEIGHT; i++){
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            cells2[i] = (Button) inflater.inflate(R.layout.cell2, cells2grid,false);
        }
    }




}
