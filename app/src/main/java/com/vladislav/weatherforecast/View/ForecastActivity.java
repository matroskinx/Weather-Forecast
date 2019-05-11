package com.vladislav.weatherforecast.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.vladislav.weatherforecast.Adapters.ForecastRecyclerAdapter;
import com.vladislav.weatherforecast.Model.ForecastItem;
import com.vladislav.weatherforecast.R;
import com.vladislav.weatherforecast.Viewmodel.ForecastViewModel;

import java.util.List;
import java.util.Map;

public class ForecastActivity extends AppCompatActivity {

    ForecastViewModel viewmodel;
    ForecastRecyclerAdapter adapter;
    RecyclerView rv_forecast;
    LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = this.getIntent();
        Double lat = intent.getDoubleExtra(MapsActivity.LAT_KEY, 0);
        Double lng = intent.getDoubleExtra(MapsActivity.LNG_KEY, 0);

        setContentView(R.layout.activity_forecast);

        rv_forecast = (RecyclerView) findViewById(R.id.rv_forecast);
        viewmodel = ViewModelProviders.of(this).get(ForecastViewModel.class);

        Observer<String> errorObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(ForecastActivity.this, s, Toast.LENGTH_LONG).show();
            }
        };

        Observer<List<ForecastItem>> flatForecastObserver = new Observer<List<ForecastItem>>() {
            @Override
            public void onChanged(List<ForecastItem> forecastItems) {
                Map<Integer, List<ForecastItem>> dayMap = viewmodel.DivideForecastByDays(forecastItems);
                adapter = new ForecastRecyclerAdapter(dayMap, forecastItems);
                linearLayoutManager = new LinearLayoutManager(ForecastActivity.this);
                rv_forecast.setLayoutManager(linearLayoutManager);
                rv_forecast.setAdapter(adapter);
            }
        };

        Observer<List<ForecastItem>> localObserver = new Observer<List<ForecastItem>>() {
            @Override
            public void onChanged(List<ForecastItem> forecastItems) {
                Map<Integer, List<ForecastItem>> dayMap = viewmodel.DivideForecastByDays(forecastItems);
                adapter = new ForecastRecyclerAdapter(dayMap, forecastItems);
                linearLayoutManager = new LinearLayoutManager(ForecastActivity.this);
                rv_forecast.setLayoutManager(linearLayoutManager);
                rv_forecast.setAdapter(adapter);
            }
        };

        viewmodel.errorMessage.observe(this, errorObserver);
        viewmodel.forecastItems.observe(this, flatForecastObserver);
        viewmodel.localForecastItems.observe(this, localObserver);
        viewmodel.getWeather(lat, lng);
    }
}
