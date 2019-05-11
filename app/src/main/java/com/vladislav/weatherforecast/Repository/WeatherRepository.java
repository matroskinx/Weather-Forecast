package com.vladislav.weatherforecast.Repository;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.vladislav.weatherforecast.Model.Forecast;
import com.vladislav.weatherforecast.Model.ForecastItem;
import com.vladislav.weatherforecast.Model.ListItem;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;

public class WeatherRepository {

    private LocalWeatherRepository localRepo;
    private WeatherRemoteRepository remoteRepo;
    private SharedPrerencesRepository sharedPrefsRepo;

    public static String SP_LAT = "sp_lat";
    public static String SP_LNG = "sp_lng";

    public WeatherRepository(Application application, SharedPreferences sharedPreferences) {
        localRepo = new LocalWeatherRepository(application);
        remoteRepo = new WeatherRemoteRepository();
        sharedPrefsRepo = new SharedPrerencesRepository(sharedPreferences);
    }

    public LiveData<List<ForecastItem>> getWeather() {

        if(sharedPrefsRepo.hasLatLng()) {
            double lat = sharedPrefsRepo.getLat();
            double lng = sharedPrefsRepo.getLng();
            refreshDataWithCoords(lat, lng);
        }
        return localRepo.getForecastItems();
    }

    public void refreshDataWithCoords(double lat, double lng) {
        remoteRepo.getWeatherData(lat, lng, new WeatherRemoteRepository.OnRequestComplete() {
            @Override
            public void onSuccess(Forecast forecast) {
                List<ForecastItem> flattenedForecastItems = flattenForecastByDays(forecast);
                localRepo.dropAndInsertAll(flattenedForecastItems);
            }

            @Override
            public void onFailure(String exceptionMessage) {
                Log.d("WeatherRepoDB", exceptionMessage);
            }
        });
        sharedPrefsRepo.saveLatLng(lat, lng);
    }

    private List<ForecastItem> flattenForecastByDays(Forecast forecast) {
        List<ForecastItem> flattenedList = new ArrayList<>();

        for (ListItem item : forecast.getList()) {
            ForecastItem obj = new ForecastItem();
            obj.setDt(item.getDt());
            obj.setDescription(item.getWeather().get(0).getDescription());
            obj.setTemp(item.getMain().getTemp());
            obj.setIcon(item.getWeather().get(0).getIcon());
            obj.setCity(forecast.getCity().getName());
            flattenedList.add(obj);
        }

        return flattenedList;
    }
}
