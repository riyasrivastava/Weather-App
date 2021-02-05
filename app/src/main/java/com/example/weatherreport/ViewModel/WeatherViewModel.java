package com.example.weatherreport.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherreport.Model.Weather;
import com.example.weatherreport.Model.WeatherDataModel;
import com.example.weatherreport.Model.WeatherService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class WeatherViewModel extends ViewModel {

    private MutableLiveData<WeatherDataModel> weatherMutable= new MutableLiveData<>();
    private WeatherService service;
    private double latitude = 0.0;
    private  double longitude = 0.0;
    private String API_KEY = "";

    public WeatherViewModel(double lat ,double lang, String apikey){
        service = new WeatherService();
        this.latitude = lat;
        this.longitude = lang;
        this.API_KEY = apikey;
        fetchWeatherReport();
    }

    public LiveData<WeatherDataModel> getWeatherReport(){
        return weatherMutable;
    }

    private void fetchWeatherReport(){
        service.getWeatherReport(latitude,longitude,"minutely,alert,hourly","metric",API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<WeatherDataModel>() {
                    @Override
                    public void onSuccess(WeatherDataModel value) {
                        weatherMutable.setValue(value);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("REpo","Error! "+e.getMessage());
                    }
                });
    }
}
