package com.vladislav.weatherforecast.database;

import com.vladislav.weatherforecast.model.ForecastItem;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public abstract class ForecastDao {
    @Insert
    public abstract void insertAll(List<ForecastItem> forecastItems);

    @Query("DELETE FROM Forecast_table")
    abstract void deleteAll();

    @Query("SELECT * FROM FORECAST_TABLE order by dt asc")
    public abstract LiveData<List<ForecastItem>> getAllForecastItems();

    @Transaction
    public void dropThenInsertAll(List<ForecastItem> forecastItems) {
        deleteAll();
        insertAll(forecastItems);
    }
}
