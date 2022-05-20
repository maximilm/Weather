package com.example.weather.viewmodel;

import android.location.Address;
import android.location.Geocoder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.List;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<List<Address>> address = new MutableLiveData<>();

    public LiveData<List<Address>> getAddress() {
        return address;
    }

    public void setAddress(String s, Geocoder geocoder) throws IOException {
        address.setValue(geocoder.getFromLocationName(s, 1));
    }
}
