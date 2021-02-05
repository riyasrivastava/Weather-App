package com.example.weatherreport.View;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.weatherreport.Model.WeatherDataModel;
import com.example.weatherreport.R;
import com.example.weatherreport.ViewModel.WeatherViewModel;
import com.example.weatherreport.ViewModel.WeatherViewModelFactory;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class FragmentTodayWeather extends Fragment {

    TextView temprature;
    ImageView weatherImage;
    TextView tempDesc;
    TextView tempFeelsLike;
    private WeatherViewModel viewModel;
    private FusedLocationProviderClient mFusedLocationClient;
    private static final int locationRequestCode = 1000;
    private static final String API_KEY = "7cc03f1c69e5a31f837468766f3ab4b2";
    private double wayLatitude = 0.0, wayLongitude = 0.0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_today_weather, null);
        temprature = root.findViewById(R.id.temp_text);
        weatherImage = root.findViewById(R.id.weather_image);
        tempDesc = root.findViewById(R.id.temp_desc);
        tempFeelsLike = root.findViewById(R.id.temp_feels);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));
        checkLocationPermission();
        return root;
    }

    private void checkLocationPermission(){
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    locationRequestCode);
        }
        else {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), location -> {
                if (location != null) {
                    try {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();
                        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(wayLatitude, wayLongitude, 1);
                        String[] address = addresses.get(0).getAddressLine(0).split(",");
                        if(address.length>=2){
                            ((MainActivity) getActivity()).setActionBarTitle(address[2]);
                        }
                        else{
                            ((MainActivity) getActivity()).setActionBarTitle(address[0]);
                        }
                        WeatherViewModelFactory params = new WeatherViewModelFactory(wayLatitude, wayLongitude, API_KEY);
                        viewModel = ViewModelProviders.of(this, params).get(WeatherViewModel.class);
                        observeViewModel();
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                // If request is cancelled, the result arrays are empty.
                if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mFusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), location -> {
                        if (location != null) {
                            try {
                                wayLatitude = location.getLatitude();
                                wayLongitude = location.getLongitude();
                                Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                                List<Address> addresses = geocoder.getFromLocation(wayLatitude, wayLongitude, 1);
                                String[] address = addresses.get(0).getAddressLine(0).split(",");
                                if(address.length>=2){
                                    ((MainActivity) getActivity()).setActionBarTitle(address[2]);
                                }
                                else{
                                    ((MainActivity) getActivity()).setActionBarTitle(address[0]);
                                }
                                WeatherViewModelFactory params = new WeatherViewModelFactory(wayLatitude, wayLongitude, API_KEY);
                                viewModel = ViewModelProviders.of(this, params).get(WeatherViewModel.class);
                                observeViewModel();
                            }
                            catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    private void observeViewModel(){
        viewModel.getWeatherReport().observe(this,weatherDataModel -> {
            temprature.setText(String.valueOf(Math.round(weatherDataModel.current.temp))+"\u2103");
            tempFeelsLike.setText("Feels like "+String.valueOf(Math.round(weatherDataModel.current.feels_like))+"\u2103");
            tempDesc.setText(weatherDataModel.current.weather.get(0).description);
            Glide
                .with(this)
                .load("https://openweathermap.org/img/w/"+weatherDataModel.current.weather.get(0).icon+".png")
                .into(weatherImage);
            Log.i("REpo","temprature "+weatherDataModel.current.temp);

        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFusedLocationClient.flushLocations();
    }
}
