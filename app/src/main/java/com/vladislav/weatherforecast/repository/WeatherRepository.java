package com.vladislav.weatherforecast.repository;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.vladislav.weatherforecast.model.Forecast;
import com.vladislav.weatherforecast.model.ForecastItem;
import com.vladislav.weatherforecast.model.ListItem;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class WeatherRepository {

    private LocalWeatherRepository localRepo;
    private WeatherRemoteRepository remoteRepo;
    private SharedPreferencesRepository sharedPrefsRepo;

    private MutableLiveData<String> repoErrorMessage = new MutableLiveData<>();

    public static String SP_LAT = "sp_lat";
    public static String SP_LNG = "sp_lng";

    public MutableLiveData<String> getRepoErrorMessage() {
        return repoErrorMessage;
    }

    public WeatherRepository(Application application, SharedPreferences sharedPreferences) {
        localRepo = new LocalWeatherRepository(application);
        remoteRepo = new WeatherRemoteRepository();
        sharedPrefsRepo = new SharedPreferencesRepository(sharedPreferences);
    }

    public LiveData<List<ForecastItem>> getWeather() {

        if (sharedPrefsRepo.hasLatLng()) {
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
                repoErrorMessage.postValue(exceptionMessage);
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
