package com.vladislav.weatherforecast.database;

import android.content.Context;

import com.vladislav.weatherforecast.model.ForecastItem;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ForecastItem.class}, version = 1, exportSchema = false)
public abstract class ForecastRoomDatabase extends RoomDatabase {
    public abstract ForecastDao forecastItemDao();

    private static ForecastRoomDatabase INSTANCE;

    public static ForecastRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    ForecastRoomDatabase.class, "forecast_database")
                    .build();
        }

        return INSTANCE;
    }
}
