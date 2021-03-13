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

    public void check(){

        ContentValues contentValues = new ContentValues();

        Cursor cursor = database.query(DBHelper.TABLE_CITIES, null, null,
                null, null, null, null);

        if (cursor.moveToFirst()){
            /*
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
            int timeIndex = cursor.getColumnIndex(DBHelper.KEY_TIME);
            do{
                Log.d("mLog", "ID = " + cursor.getInt(idIndex) + ", name = " + cursor.getString(nameIndex) +
                        ", time = " + cursor.getString(timeIndex));
            } while (cursor.moveToNext());*/
            cursor.close();
        } else {
            cursor.close();
            SearchFragment SF = new SearchFragment();
            FragmentTransaction FT = getSupportFragmentManager().beginTransaction();
            FT.replace(R.id.conteiner, SF);
            FT.commit();
        }
    }

    public void onClick(View v){
        /*
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.KEY_NAME, "vvvv");
        contentValues.put(DBHelper.KEY_TIME, "vvvv");
        database.insert(DBHelper.TABLE_CITIES, null, contentValues);*/
        database.delete(DBHelper.TABLE_CITIES, null, null);
        Cursor cursor = database.query(DBHelper.TABLE_CITIES, null, null,
                null, null, null, null);

        if (cursor.moveToFirst()){
            /*
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
            int timeIndex = cursor.getColumnIndex(DBHelper.KEY_TIME);
            do{
                Log.d("mLog", "ID = " + cursor.getInt(idIndex) + ", name = " + cursor.getString(nameIndex) +
                        ", time = " + cursor.getString(timeIndex));
            } while (cursor.moveToNext());*/
        } else {

        }

        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

}
