package com.vladislav.weatherforecast.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vladislav.weatherforecast.adapters.ForecastRecyclerAdapter;
import com.vladislav.weatherforecast.model.ForecastItem;
import com.vladislav.weatherforecast.R;
import com.vladislav.weatherforecast.viewmodel.ForecastViewModel;

import java.util.List;
import java.util.Map;

public class ForecastActivity extends AppCompatActivity {

    private ForecastViewModel viewmodel;
    private ForecastRecyclerAdapter adapter;
    private RecyclerView rv_forecast;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar progressBar;
    private TextView introText;
    private ImageView introIcon;

    private static final int GET_LOCATION = 91;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        rv_forecast = findViewById(R.id.rv_forecast);
        progressBar = findViewById(R.id.progressBar);
        introText = findViewById(R.id.intro_text);
        introIcon = findViewById(R.id.intro_icon);

        viewmodel = ViewModelProviders.of(this).get(ForecastViewModel.class);

        Observer<String> errorObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(ForecastActivity.this, s, Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        };

        Observer<List<ForecastItem>> mainObserver = new Observer<List<ForecastItem>>() {
            @Override
            public void onChanged(List<ForecastItem> forecastItems) {
                if (forecastItems.size() != 0) {
                    Map<Integer, List<ForecastItem>> dayMap = viewmodel.DivideForecastByDays(forecastItems);
                    adapter = new ForecastRecyclerAdapter(dayMap, forecastItems);
                    linearLayoutManager = new LinearLayoutManager(ForecastActivity.this);
                    rv_forecast.setLayoutManager(linearLayoutManager);
                    rv_forecast.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                    introIcon.setVisibility(View.GONE);
                    introText.setVisibility(View.GONE);
                }
            }
        };

        viewmodel.errorMessage.observe(this, errorObserver);
        viewmodel.forecastItems.observe(this, mainObserver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.forecast_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.map_dest) {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivityForResult(intent, GET_LOCATION);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == GET_LOCATION) {
            if (resultCode == Activity.RESULT_OK) {
                Double lat = data.getDoubleExtra(MapsActivity.LAT_KEY, 0);
                Double lng = data.getDoubleExtra(MapsActivity.LNG_KEY, 0);
                viewmodel.SetNewCoords(lat, lng);
                progressBar.setVisibility(View.VISIBLE);

            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "No choice", Toast.LENGTH_LONG).show();
            }
        }
    }
}
