package com.vladislav.weatherforecast.utils;

import com.vladislav.weatherforecast.model.ForecastItem;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;

import androidx.core.util.Pair;


public class AdapterUtilsTest {

    @Test
    public void findMinMaxTemp_isCorrect() {
        List<ForecastItem> forecastItems = new ArrayList<>();
        ForecastItem item1 = new ForecastItem();
        item1.setTemp(20d);
        ForecastItem item2 = new ForecastItem();
        item2.setTemp(25d);
        ForecastItem item3 = new ForecastItem();
        item3.setTemp(15d);
        forecastItems.add(item1);
        forecastItems.add(item2);
        forecastItems.add(item3);
        Pair<Double, Double> pair = AdapterUtils.findMinMaxTemp(forecastItems);
        assertThat(pair.first, is(15d));
        assertThat(pair.second, is(25d));
    }

    @Test
    public void getFormattedTime_isCorrect() {
        int unixTimestamp = 1557468000;
        String pattern = "HH:mm";
        String result = AdapterUtils.getFormattedTime(unixTimestamp, pattern);
        assertThat(result, is("09:00"));
    }

}
