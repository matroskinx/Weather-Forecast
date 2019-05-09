package com.vladislav.weatherforecast.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        TextView forecastDesc;
        TextView forecastText;

        ForecastHolder(View v) {
            super(v);
            this.view = v;
            forecastDesc = view.findViewById(R.id.weather_decs);
            forecastText = view.findViewById(R.id.weather_text);
        }

        void bindForecastItem(ListItem forecastItem) {
            this.forecastItem = forecastItem;
            forecastDesc.setText(forecastItem.getWeather().get(0).getDescription());
            forecastText.setText(Integer.toString(forecastItem.getDt()));
        }
    }
}
