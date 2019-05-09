package com.vladislav.weatherforecast.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.vladislav.weatherforecast.Adapters.ForecastRecyclerAdapter;
import com.vladislav.weatherforecast.Model.Forecast;
import com.vladislav.weatherforecast.R;
import com.vladislav.weatherforecast.Viewmodel.ForecastViewModel;

public class ForecastActivity extends AppCompatActivity {

    ForecastViewModel viewmodel;
    ForecastRecyclerAdapter adapter;
    RecyclerView rv_forecast;
    LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        rv_forecast = (RecyclerView) findViewById(R.id.rv_forecast);
        viewmodel = ViewModelProviders.of(this).get(ForecastViewModel.class);

        Observer<String> errorObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(ForecastActivity.this, s, Toast.LENGTH_LONG).show();
            }
        };

        Observer<Forecast> forecastObserver = new Observer<Forecast>() {
            @Override
            public void onChanged(Forecast forecast) {
                adapter = new ForecastRecyclerAdapter(forecast);
                linearLayoutManager = new LinearLayoutManager(ForecastActivity.this);
                rv_forecast.setLayoutManager(linearLayoutManager);
                rv_forecast.setAdapter(adapter);
            }
        };

        viewmodel.errorMessage.observe(this, errorObserver);
        viewmodel.forecastValue.observe(this, forecastObserver);

        viewmodel.getWeather();
    }
}
