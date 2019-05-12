package com.vladislav.weatherforecast.utils;

import com.vladislav.weatherforecast.model.ForecastItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.core.util.Pair;

public class AdapterUtils {
    public static Pair<Double, Double> findMinMaxTemp(List<ForecastItem> day) {

        Double min = day.get(0).getTemp();
        Double max = day.get(0).getTemp();

        for (ForecastItem item : day) {
            Double temp = item.getTemp();
            if (temp > max) {
                max = temp;
            } else if (temp < min) {
                min = temp;
            }
        }
        return new Pair<>(min, max);
    }

    public static String  getFormattedTime(int timestamp, String pattern) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp * 1000L);
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return dateFormat.format(cal.getTime());
    }
}
