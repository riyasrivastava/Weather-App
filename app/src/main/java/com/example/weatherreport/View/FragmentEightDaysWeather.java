package com.example.weatherreport.View;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherreport.R;
import com.example.weatherreport.ViewModel.WeatherViewModel;
import com.example.weatherreport.ViewModel.WeatherViewModelFactory;
import com.google.android.gms.location.LocationServices;

import java.util.Objects;

public class FragmentEightDaysWeather extends Fragment {

    private RecyclerView dailyWeatherRecyclerView;
    private WeatherViewModel viewModel;
    private static final String API_KEY = "7cc03f1c69e5a31f837468766f3ab4b2";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_eightdays_weather, null);
        dailyWeatherRecyclerView = root.findViewById(R.id.weatherRecycler);
        dailyWeatherRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return root;
    }

    private void observeViewModel(){
        viewModel.getWeatherReport().observe(this,weatherDataModel -> {
            DailyWeatherListAdapter adapter = new DailyWeatherListAdapter(weatherDataModel.daily,getActivity());
            dailyWeatherRecyclerView.setAdapter(adapter);
            Log.i("REpo","temprature "+weatherDataModel.current.temp);
            Log.i("REpo","1st day data "+weatherDataModel.daily.get(0).dt+" "+weatherDataModel.daily.get(0).weather.get(0).description+" "+weatherDataModel.daily.get(0).temp.max);
            Log.i("REpo","6st day data "+weatherDataModel.daily.get(5).dt+" "+weatherDataModel.daily.get(5).weather.get(0).description+" "+weatherDataModel.daily.get(5).temp.max);
        });
    }

    public void getData(double lat,double lang){
        WeatherViewModelFactory params = new WeatherViewModelFactory(lat, lang, API_KEY);
        viewModel = ViewModelProviders.of(this, params).get(WeatherViewModel.class);
        observeViewModel();
    }
}
