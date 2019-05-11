package com.vladislav.weatherforecast.Repository;

import android.app.Application;
import android.os.AsyncTask;

import com.vladislav.weatherforecast.Database.ForecastDao;
import com.vladislav.weatherforecast.Database.ForecastRoomDatabase;
import com.vladislav.weatherforecast.Model.ForecastItem;

import java.util.List;

import androidx.lifecycle.LiveData;

public class LocalWeatherRepository {
    private ForecastDao forecastDao;
    private LiveData<List<ForecastItem>> forecastItems;

    public LiveData<List<ForecastItem>> getForecastItems() {
        return forecastItems;
    }

    public LocalWeatherRepository(Application application) {
        ForecastRoomDatabase db = ForecastRoomDatabase.getDatabase(application);
        forecastDao = db.forecastItemDao();
        forecastItems = forecastDao.getAllForecastItems();
    }

    public void insertAll(List<ForecastItem> forecastItems) {
        new InsertAllAsyncTask(forecastDao).execute(forecastItems);
    }

    private static class InsertAllAsyncTask extends AsyncTask<List<ForecastItem>, Void, Void> {
        private ForecastDao mAsyncTaskDao;

        InsertAllAsyncTask(ForecastDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(List<ForecastItem>... lists) {
            mAsyncTaskDao.insertAll(lists[0]);
            return null;
        }
    }
}

