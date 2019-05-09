package com.vladislav.weatherforecast.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vladislav.weatherforecast.Model.Forecast;
import com.vladislav.weatherforecast.Model.ListItem;
import com.vladislav.weatherforecast.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ForecastRecyclerAdapter extends RecyclerView.Adapter<ForecastRecyclerAdapter.ForecastHolder> {

    private Forecast forecast;

    public ForecastRecyclerAdapter(Forecast forecast) {
        this.forecast = forecast;
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
        ListItem forecastItem = forecast.getList().get(position);
        holder.bindForecastItem(forecastItem);
    }

    @Override
    public int getItemCount() {
        return forecast.getList().size();
    }

    class ForecastHolder extends RecyclerView.ViewHolder {
        View view;
        ListItem forecastItem;
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

        void bindForecastItem(ListItem forecastItem) {
            this.forecastItem = forecastItem;

            int dt = forecastItem.getDt();
            String desc = forecastItem.getWeather().get(0).getDescription();
            double min = forecastItem.getMain().getTemp_min();
            double max = forecastItem.getMain().getTemp_max();
            String weatherCondition = forecastItem.getWeather().get(0).getIcon();
            int weatherIcon = getIconId(weatherCondition);

            forecastIcon.setImageResource(weatherIcon);
            forecastText.setText(Integer.toString(dt));
            forecastDesc.setText(desc);
            forecastTempLow.setText(Double.toString(min));
            forecastTempHigh.setText(Double.toString(max));
        }
    }

    private int getIconId(String code) {
        code = code.substring(0, 2);
        int value;
        switch (code) {
            case "01":
                value = R.drawable.ic_sunny;
                break;
            case "02":
                value = R.drawable.ic_cloud;
                break;
            case "03":
                value = R.drawable.ic_cloud;
                break;
            case "04":
                value = R.drawable.ic_cloud;
                break;
            case "09":
                value = R.drawable.ic_rain;
                break;
            case "10":
                value = R.drawable.ic_rain;
                break;
            case "11":
                value = R.drawable.ic_thunder;
                break;
            case "13":
                value = R.drawable.ic_snow;
                break;
            case "50":
                value = R.drawable.ic_mist;
                break;
            default:
                value = R.drawable.ic_sunny;
                break;
        }
        return value;
    }
}
