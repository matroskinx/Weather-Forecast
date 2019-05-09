package com.vladislav.weatherforecast.Viewmodel;

import com.vladislav.weatherforecast.Model.Forecast;
import com.vladislav.weatherforecast.Repository.WeatherRemoteRepository;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ForecastViewModel extends ViewModel {
    public MutableLiveData<Forecast> forecastValue = new MutableLiveData<>();

    public MutableLiveData<String> errorMessage = new MutableLiveData<>();

    private WeatherRemoteRepository weatherRemoteRepo = new WeatherRemoteRepository();

    public void getWeather() {

        weatherRemoteRepo.getWeatherData(new WeatherRemoteRepository.OnRequestComplete() {
            @Override
            public void onSuccess(Forecast forecast) {
                forecastValue.postValue(forecast);
            }

            @Override
            public void onFailure(String exceptionMessage) {
                errorMessage.postValue(exceptionMessage);
            }
        });
    }
}

