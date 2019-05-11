package com.vladislav.weatherforecast.Repository;

import android.content.SharedPreferences;

import static com.vladislav.weatherforecast.Repository.WeatherRepository.SP_LAT;
import static com.vladislav.weatherforecast.Repository.WeatherRepository.SP_LNG;

public class SharedPrerencesRepository {

    SharedPreferences sharedPrefs;

    public SharedPrerencesRepository(SharedPreferences sharedPreferences) {
        sharedPrefs = sharedPreferences;
    }

    public boolean hasLatLng() {
        return sharedPrefs.contains(SP_LAT) && sharedPrefs.contains(SP_LNG);
    }

    public double getLat() {
        return Double.longBitsToDouble(sharedPrefs.getLong(SP_LAT, 0));
    }

    public double getLng() {
        return Double.longBitsToDouble(sharedPrefs.getLong(SP_LNG, 0));
    }

    public void saveLatLng(double lat, double lng) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putLong(SP_LAT, Double.doubleToLongBits(lat));
        editor.putLong(SP_LNG, Double.doubleToLongBits(lng));
        editor.apply();
    }
}
