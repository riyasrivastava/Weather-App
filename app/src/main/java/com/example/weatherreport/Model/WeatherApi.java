package com.example.weatherreport.Model;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("onecall")
    Single<WeatherDataModel> getCurrentWeather(@Query("lat") double lat , @Query("lon") double lon, @Query("exclude") String exclude,@Query("units") String units,@Query("appid") String API_KEY);
}
