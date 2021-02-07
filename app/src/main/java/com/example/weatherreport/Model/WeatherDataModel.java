package com.example.weatherreport.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherDataModel {
    @SerializedName("current")
    @Expose
    public Current current;

    @SerializedName("daily")
    @Expose
    public List<Daily> daily;
}
