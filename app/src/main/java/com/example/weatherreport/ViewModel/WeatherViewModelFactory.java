package com.example.weatherreport.ViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class WeatherViewModelFactory implements ViewModelProvider.Factory {
    private String mParam;
    private double lat;
    private double lang;


    public WeatherViewModelFactory(double a,double b, String param) {
        this.lat = a;
        this.lang = b;
        this.mParam = param;
    }
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new WeatherViewModel(lat,lang, mParam);
    }
}
