package com.vladislav.weatherforecast.repository;

import com.vladislav.weatherforecast.model.Forecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface WeatherAPI {
    @GET("forecast")
    Call<Forecast> getForecast(
        @Query("lat") double lat,
        @Query("lon") double lon,
        @Query("units") String units,
        @Query("appid") String key
    );
}
