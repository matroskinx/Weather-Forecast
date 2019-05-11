package com.vladislav.weatherforecast.Database;

import com.vladislav.weatherforecast.Model.ForecastItem;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ForecastDao {
    @Insert
    void insertAll(List<ForecastItem> forecastItems);

    @Query("DELETE FROM Forecast_table")
    void clear();

    @Query("SELECT * FROM FORECAST_TABLE order by dt asc")
    LiveData<List<ForecastItem>> getAllForecastItems();
}
