package com.example.weatherreport.Model;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherService {

    public static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";

    private WeatherApi api;

    public WeatherService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        api = retrofit.create(WeatherApi.class);
    }

    public Single<WeatherDataModel> getWeatherReport(double lat,double lon, String exclude, String units,String apikey){
        return api.getCurrentWeather(lat,lon,exclude,units,apikey);
    }
}
