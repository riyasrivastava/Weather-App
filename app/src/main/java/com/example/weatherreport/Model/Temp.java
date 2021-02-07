package com.example.weatherreport.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Temp {

    @SerializedName("min")
    @Expose
    public double min;

    @SerializedName("max")
    @Expose
    public double max;
}
