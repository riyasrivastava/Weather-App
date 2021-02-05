package com.example.weatherreport.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherDataModel {
    @SerializedName("current")
    @Expose
    public Current current;
}
