package com.example.weatherreport.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherreport.Model.Daily;
import com.example.weatherreport.R;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class DailyWeatherListAdapter extends RecyclerView.Adapter<DailyWeatherListAdapter.DailyWeatherListViewHolder> {

    private List<Daily> dailyWeatherList;
    private Context context;

    public DailyWeatherListAdapter(List<Daily> dailyWeatherList,Context context){
        this.dailyWeatherList = dailyWeatherList;
        this.context = context;
    }

    @NonNull
    @Override
    public DailyWeatherListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_item,parent,false);
        return new DailyWeatherListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyWeatherListViewHolder holder, int position) {
        TextView weatherItemDate = holder.itemView.findViewById(R.id.weather_item_date);
        TextView weatherItemTempDesc = holder.itemView.findViewById(R.id.weather_item_temp_desc);
        ImageView weatherItemImage = holder.itemView.findViewById(R.id.weather_item_image);
        TextView weatherItemMinTemp = holder.itemView.findViewById(R.id.weather_item_min_temp);
        TextView weatherItemMaxTemp = holder.itemView.findViewById(R.id.weather_item_max_temp);
        LinearLayout bottomLayout = holder.itemView.findViewById(R.id.bottom_layout);
        RelativeLayout headerLayout = holder.itemView.findViewById(R.id.header_layout);
        TextView weatherItemWind = holder.itemView.findViewById(R.id.weather_item_wind);
        TextView weatherItemHumidity = holder.itemView.findViewById(R.id.weather_item_humidity);
        TextView weatherItemSunRiseSet = holder.itemView.findViewById(R.id.weather_item_sun_rise_set);

        LocalDate date = Instant.ofEpochMilli(dailyWeatherList.get(position).dt * 1000).atZone(ZoneId.systemDefault()).toLocalDate();
        String strDate = date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
        weatherItemDate.setText(strDate);
        weatherItemTempDesc.setText(dailyWeatherList.get(position).weather.get(0).description);
        weatherItemMinTemp.setText(String.valueOf(Math.round(dailyWeatherList.get(position).temp.min))+"\u2103");
        weatherItemMaxTemp.setText(String.valueOf(Math.round(dailyWeatherList.get(position).temp.max))+"\u2103");
        weatherItemWind.setText(String.valueOf(dailyWeatherList.get(position).wind_speed)+" m/s");
        weatherItemHumidity.setText(String.valueOf(dailyWeatherList.get(position).humidity)+" %");

        Timestamp stamp1 = new Timestamp(dailyWeatherList.get(position).sunrise*1000);
        Date date1 = new Date(stamp1.getTime());
        SimpleDateFormat sdf1 = new SimpleDateFormat("h:mm a");
        sdf1.setTimeZone(TimeZone.getTimeZone("GMT"));
        String sunRiseFormattedDate = sdf1.format(date1);

        Timestamp stamp2 = new Timestamp(dailyWeatherList.get(position).sunset*1000);
        Date date2 = new Date(stamp2.getTime());
        SimpleDateFormat sdf2 = new SimpleDateFormat("h:mm a");
        sdf2.setTimeZone(TimeZone.getTimeZone("GMT"));
        String sunSetFormattedDate = sdf2.format(date2);

        weatherItemSunRiseSet.setText(sunRiseFormattedDate+"/"+sunSetFormattedDate);
        Glide.with(context)
             .load("https://openweathermap.org/img/w/"+dailyWeatherList.get(position).weather.get(0).icon+".png")
             .into(weatherItemImage);
        headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bottomLayout.getVisibility() == View.GONE){
                    bottomLayout.setVisibility(View.VISIBLE);
                }
                else{
                    bottomLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dailyWeatherList.size();
    }

    public class DailyWeatherListViewHolder extends RecyclerView.ViewHolder{
        public View itemView;
        public DailyWeatherListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }
}
