package com.vladislav.weatherforecast.Viewmodel;

import com.vladislav.weatherforecast.Model.Forecast;
import com.vladislav.weatherforecast.Model.ForecastItem;
import com.vladislav.weatherforecast.Model.ListItem;
import com.vladislav.weatherforecast.Repository.WeatherRemoteRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ForecastViewModel extends ViewModel {
    public MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public MutableLiveData<List<ForecastItem>> forecastItems = new MutableLiveData<>();

    private WeatherRemoteRepository weatherRemoteRepo = new WeatherRemoteRepository();

    public void getWeather() {

        weatherRemoteRepo.getWeatherData(new WeatherRemoteRepository.OnRequestComplete() {
            @Override
            public void onSuccess(Forecast forecast) {
                List<ForecastItem> flattenedForecastItems = flattenForecastByDays(forecast);
                forecastItems.postValue(flattenedForecastItems);
            }

            @Override
            public void onFailure(String exceptionMessage) {
                errorMessage.postValue(exceptionMessage);
            }
        });
    }

    public Map<Integer, List<ForecastItem>> DivideForecastByDays(List<ForecastItem> forecastItems) {

        Map<Integer, List<ForecastItem>> dayMap = new HashMap<>();
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

        return dayMap;
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

