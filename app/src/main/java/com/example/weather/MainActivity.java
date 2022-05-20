package com.example.weather;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.example.weather.DAO.DBHelper;
import com.example.weather.DAO.DBUser;
import com.example.weather.view.ListFragment;
import com.example.weather.view.SearchFragment;

public class MainActivity extends AppCompatActivity {
    DBUser dbuser;
    DBHelper dbHelper;
    SQLiteDatabase database;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        dbuser = DBUser.getDbuser(getApplicationContext());
        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();
        //database.delete(DBHelper.TABLE_CITIES, null, null);
        dbHelper.close();
        b = findViewById(R.id.item_add);
        if(dbuser.check()){
            ListFragment LF = new ListFragment();
            FragmentTransaction FT = getSupportFragmentManager().beginTransaction();
            FT.replace(R.id.conteiner, LF);
            FT.commit();

        } else {
            SearchFragment SF = new SearchFragment();
            FragmentTransaction FT = getSupportFragmentManager().beginTransaction();
            FT.replace(R.id.conteiner, SF);
            FT.commit();
        }
    }

    public void click (MenuItem item){
        SearchFragment SF = new SearchFragment();
        FragmentTransaction FT = getSupportFragmentManager().beginTransaction();
        FT.replace(R.id.conteiner, SF);
        FT.commit();
    }

    public void menuclick (MenuItem item){
        ListFragment LF = new ListFragment();
        FragmentTransaction FT = getSupportFragmentManager().beginTransaction();
        FT.replace(R.id.conteiner, LF);
        FT.commit();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

}
