package com.example.weatherreport.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Current {

    @SerializedName("dt")
    @Expose
    public long dt;

    @SerializedName("temp")
    @Expose
    public double temp;

    @SerializedName("feels_like")
    @Expose
    public double feels_like;

    @SerializedName("weather")
    @Expose
    public List<Weather> weather;
}
