package com.example.weatherreport.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Daily {

    @SerializedName("dt")
    @Expose
    public long dt;

    @SerializedName("sunrise")
    @Expose
    public long sunrise;

    @SerializedName("sunset")
    @Expose
    public long sunset;

    @SerializedName("temp")
    @Expose
    public Temp temp;

    @SerializedName("humidity")
    @Expose
    public int humidity;

    @SerializedName("wind_speed")
    @Expose
    public double wind_speed;

    @SerializedName("weather")
    @Expose
    public List<DailyWeather> weather;
}
