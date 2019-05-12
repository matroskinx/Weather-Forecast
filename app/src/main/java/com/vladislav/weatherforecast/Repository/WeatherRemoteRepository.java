package com.vladislav.weatherforecast.Repository;

import android.util.Log;

import com.vladislav.weatherforecast.Model.Forecast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherRemoteRepository {
    public interface OnRequestComplete {
        void onSuccess(Forecast forecast);

        void onFailure(String exceptionMessage);
    }

    public void getWeatherData(Double lat, Double lng, final OnRequestComplete listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String key = "a839af55ddf7332d5ea98ca734692bfe";
        WeatherAPI api = retrofit.create(WeatherAPI.class);
        Call<Forecast> call = api.getForecast(lat, lng, "metric", key);

        call.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {

                if (!response.isSuccessful()) {
                    Log.d("RETROFIT", "code:" + response.code());
                    listener.onFailure("Response code:" + response.code());
                    return;
                }

                Forecast forecast = response.body();
                listener.onSuccess(forecast);
                Log.d("RETROFIT", "response received");
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                Log.d("RETROFIT", "exception" + t.getMessage());
                listener.onFailure("Network request failed");
            }
        });
    }
}
