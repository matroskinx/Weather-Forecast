package com.vladislav.weatherforecast.Viewmodel;

import android.util.Log;
import com.vladislav.weatherforecast.Model.Forecast;
import com.vladislav.weatherforecast.Model.ListItem;
import com.vladislav.weatherforecast.Repository.WeatherRemoteRepository;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ForecastViewModel extends ViewModel {
    public MutableLiveData<Forecast> forecastValue = new MutableLiveData<>();

    public MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public MutableLiveData<Map<Integer, List<ListItem>>> dayMap = new MutableLiveData<>();

    private WeatherRemoteRepository weatherRemoteRepo = new WeatherRemoteRepository();

    public void getWeather() {

        weatherRemoteRepo.getWeatherData(new WeatherRemoteRepository.OnRequestComplete() {
            @Override
            public void onSuccess(Forecast forecast) {
                forecastValue.postValue(forecast);
                dayMap.postValue(DivideWeatherByDays(forecast));
            }

            @Override
            public void onFailure(String exceptionMessage) {
                errorMessage.postValue(exceptionMessage);
            }
        });
    }

    private Map<Integer, List<ListItem>> DivideWeatherByDays(Forecast forecast) {
        List<ListItem> items = forecast.getList();

        Map<Integer, List<ListItem>> dayMap = new HashMap<>();

        for (ListItem item : items) {
            int dt = item.getDt();

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(dt * 1000L);

            int day = cal.get(Calendar.DAY_OF_MONTH);
            Log.d("DIVISOIN", Integer.toString(day));

            if (!dayMap.containsKey(day)) {
                ArrayList<ListItem> list = new ArrayList<>();
                list.add(item);
                dayMap.put(day, list);
            } else {
                dayMap.get(day).add(item);
            }
        }

        return dayMap;
    }
}

