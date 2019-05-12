package com.vladislav.weatherforecast.viewmodel;

import android.app.Application;
import android.content.Context;

import com.vladislav.weatherforecast.model.ForecastItem;
import com.vladislav.weatherforecast.repository.WeatherRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ForecastViewModel extends AndroidViewModel {
    private WeatherRepository forecastRepo;
    public LiveData<List<ForecastItem>> forecastItems;
    public MutableLiveData<String> errorMessage;

    public static String SHARED_PREFS_NAME = "LatLng";

    public ForecastViewModel(Application application) {
        super(application);
        forecastRepo = new WeatherRepository(application, getApplication().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE));
        forecastItems = forecastRepo.getWeather();
        errorMessage = forecastRepo.getRepoErrorMessage();
    }

    public void SetNewCoords(Double lat, Double lng) {
        forecastRepo.refreshDataWithCoords(lat, lng);
    }

    public Map<Integer, List<ForecastItem>> DivideForecastByDays(List<ForecastItem> forecastItems) {

        Map<Integer, List<ForecastItem>> dayMap = new LinkedHashMap<>();
        for (ForecastItem item : forecastItems) {
            int dt = item.getDt();
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(dt * 1000L);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            if (!dayMap.containsKey(day)) {
                ArrayList<ForecastItem> list = new ArrayList<>();
                list.add(item);
                dayMap.put(day, list);
            } else {
                dayMap.get(day).add(item);
            }
        }
        Map<Integer, List<ForecastItem>> fakeDayMap = new LinkedHashMap<>();
        if (dayMap.size() == 5) {

            /*
                Insert fake day entry if we have no forecasts left for today
                and have 8 forecasts for each of the five next days.
                In general case, map have 6 entries.
            */
            fakeDayMap.put(-1, new ArrayList<ForecastItem>());
            fakeDayMap.putAll(dayMap);
            return fakeDayMap;
        }

        return dayMap;
    }
}

