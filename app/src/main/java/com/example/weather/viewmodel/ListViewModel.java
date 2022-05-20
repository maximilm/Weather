package com.example.weather.viewmodel;

import android.location.Address;
import android.location.Geocoder;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weather.DAO.DBUser;
import com.example.weather.model.City;

import java.io.IOException;
import java.util.List;

public class ListViewModel extends ViewModel {

    private MutableLiveData<List<City>> cities = new MutableLiveData<>();
    DBUser dbuser;

    public LiveData<List<City>> getCities() {
        return cities;
    }

    public void setCities(FragmentActivity f) {
        dbuser = DBUser.getDbuser(f);
        dbuser.downloadDatabase();
        dbuser.updateDataBase();
        cities.setValue(dbuser.getCities());
    }

    public void delete(int y){
        dbuser.delete(cities.getValue().get(y).getId());
    }
}
