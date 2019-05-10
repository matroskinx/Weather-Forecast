package com.vladislav.weatherforecast.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vladislav.weatherforecast.Model.ListItem;
import com.vladislav.weatherforecast.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

public class ForecastRecyclerAdapter extends RecyclerView.Adapter<ForecastRecyclerAdapter.ForecastHolder> {

    private Map<Integer, List<ListItem>> dayMap;

    public ForecastRecyclerAdapter(Map<Integer, List<ListItem>> dayMap) {
        this.dayMap = dayMap;
    }

    @NonNull
    @Override
    public ForecastRecyclerAdapter.ForecastHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View inflatedView = inflater.inflate(R.layout.rv_forecast_row, parent, false);
        return new ForecastHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastRecyclerAdapter.ForecastHolder holder, int position) {
        ArrayList<List<ListItem>> dayMapValues = new ArrayList<>(dayMap.values());
        List<ListItem> dayForecast = dayMapValues.get(position);
        holder.bindForecastItem(dayForecast);
    }

    @Override
    public int getItemCount() {
        return dayMap.size();
    }

    class ForecastHolder extends RecyclerView.ViewHolder {
        View view;
        List<ListItem> dayForecast;
        ImageView forecastIcon;
        TextView forecastDesc;
        TextView forecastText;
        TextView forecastTempHigh;
        TextView forecastTempLow;

        ForecastHolder(View v) {
            super(v);
            this.view = v;
            forecastIcon = view.findViewById(R.id.weather_icon);
            forecastDesc = view.findViewById(R.id.weather_desc);
            forecastText = view.findViewById(R.id.weather_date);
            forecastTempHigh = view.findViewById(R.id.weather_temp_high);
            forecastTempLow = view.findViewById(R.id.weather_temp_low);
        }

        void bindForecastItem(List<ListItem> dayForecast) {
            this.dayForecast = dayForecast;

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(dayForecast.get(0).getDt() * 1000L);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            String formattedDate = dateFormat.format(cal.getTime());

            Pair<Double, Double> minMaxTemp = findMinMaxTemp(dayForecast);
            String desc = dayForecast.get(0).getWeather().get(0).getDescription();
            double min = minMaxTemp.first;
            double max = minMaxTemp.second;
            String weatherCondition = dayForecast.get(0).getWeather().get(0).getIcon();
            int weatherIcon = getIconId(weatherCondition);

            forecastIcon.setImageResource(weatherIcon);
            forecastText.setText(formattedDate);
            forecastDesc.setText(desc);
            forecastTempLow.setText(Double.toString(Math.round(min)) + "\u00b0");
            forecastTempHigh.setText(Double.toString(Math.round(max)) + "\u00b0");
        }
    }

    private int getIconId(String code) {
        code = code.substring(0, 2);
        int value;
        switch (code) {
            case "01":
                value = R.drawable.ic_sunrise;
                break;
            case "02":
                value = R.drawable.ic_cloudy;
                break;
            case "03":
                value = R.drawable.ic_cloudy;
                break;
            case "04":
                value = R.drawable.ic_cloudy;
                break;
            case "09":
                value = R.drawable.ic_rainy;
                break;
            case "10":
                value = R.drawable.ic_rainy;
                break;
            case "11":
                value = R.drawable.ic_thunderstorm;
                break;
            case "13":
                value = R.drawable.ic_snowy;
                break;
            case "50":
                value = R.drawable.ic_foggy;
                break;
            default:
                value = R.drawable.ic_sunrise;
                break;
        }
        return value;
    }

    private Pair<Double, Double> findMinMaxTemp(List<ListItem> day) {

        Double min = day.get(0).getMain().getTemp();
        Double max = day.get(0).getMain().getTemp();

        for(ListItem item: day) {
            Double temp = item.getMain().getTemp();
            if(temp > max) {
                max = temp;
            }
            else if (temp < min) {
                min = temp;
            }
        }
        return new Pair<>(min, max);
    }
}
