package com.vladislav.weatherforecast.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vladislav.weatherforecast.R;
import com.vladislav.weatherforecast.Repository.SharedPrerencesRepository;
import com.vladislav.weatherforecast.Viewmodel.ForecastViewModel;

import static com.vladislav.weatherforecast.Repository.WeatherRepository.SP_LAT;
import static com.vladislav.weatherforecast.Repository.WeatherRepository.SP_LNG;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng currentPosition;
    private SharedPrerencesRepository sharedPrerencesRepo;

    public static final String LAT_KEY = "key_lat";
    public static final String LNG_KEY = "key_lng";

    @Override
    public boolean onSupportNavigateUp() {
        Toast.makeText(getApplicationContext(), "Up click", Toast.LENGTH_LONG).show();
        setResult(Activity.RESULT_CANCELED);
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra(LAT_KEY, currentPosition.latitude);
                returnIntent.putExtra(LNG_KEY, currentPosition.longitude);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        sharedPrerencesRepo = new SharedPrerencesRepository(getApplication()
                .getSharedPreferences(ForecastViewModel.SHARED_PREFS_NAME, Context.MODE_PRIVATE));
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                currentPosition = mMap.getCameraPosition().target;
                mMap.addMarker(new MarkerOptions().position(currentPosition));
            }
        });

        mMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {
                mMap.clear();
            }
        });

        if (sharedPrerencesRepo.hasLatLng()) {
            double lat = sharedPrerencesRepo.getLat();
            double lng = sharedPrerencesRepo.getLng();
            currentPosition = new LatLng(lat, lng);
        } else {
            //TODO get current position
            currentPosition = new LatLng(53, 24);
        }

        mMap.addMarker(new MarkerOptions().position(currentPosition));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentPosition));
    }
}
