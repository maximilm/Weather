package com.example.weather;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    DBUser dbuser;
    DBHelper dbHelper;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        dbuser = DBUser.getDbuser(getApplicationContext());/*
        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();*/
        if(dbuser.check()){

        } else {
            SearchFragment SF = new SearchFragment();
            FragmentTransaction FT = getSupportFragmentManager().beginTransaction();
            FT.replace(R.id.conteiner, SF);
            FT.commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

}
