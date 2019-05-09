package com.vladislav.weatherforecast.Repository;

import com.vladislav.weatherforecast.Model.Forecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {
    @GET("forecast")
    Call<Forecast> getForecast(
        @Query("lat") double lat,
        @Query("lon") double lon,
        @Query("appid") String key
    );
}
