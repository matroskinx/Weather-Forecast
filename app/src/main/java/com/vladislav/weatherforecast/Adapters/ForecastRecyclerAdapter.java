package com.vladislav.weatherforecast.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vladislav.weatherforecast.Model.ForecastItem;
import com.vladislav.weatherforecast.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

public class ForecastRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static final int TODAY_FORECAST = 0;
    static final int LATER_FORECAST = 1;

    private Map<Integer, List<ForecastItem>> dayMap;
    private List<ForecastItem> forecastItems;

    public ForecastRecyclerAdapter(Map<Integer, List<ForecastItem>> dayMap, List<ForecastItem> forecastItems) {
        this.dayMap = dayMap;
        this.forecastItems = forecastItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == TODAY_FORECAST) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View inflatedView = inflater.inflate(R.layout.rv_today_row, parent, false);
            return new ForecastMainHolder(inflatedView);

        } else {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View inflatedView = inflater.inflate(R.layout.rv_forecast_row, parent, false);
            return new ForecastHolder(inflatedView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder.getItemViewType() == TODAY_FORECAST) {
            ForecastMainHolder forecastHolder = (ForecastMainHolder) holder;
            ArrayList<ForecastItem> fiveItemsForecast = new ArrayList<>();

            for (int i = 0; i < 5; i++) {
                fiveItemsForecast.add(forecastItems.get(i));
            }

            forecastHolder.bindMainForecastItem(fiveItemsForecast);

        } else {
            ArrayList<List<ForecastItem>> dayMapValues = new ArrayList<>(dayMap.values());
            List<ForecastItem> dayForecast = dayMapValues.get(position);
            ForecastHolder forecastHolder = (ForecastHolder) holder;
            forecastHolder.bindForecastItem(dayForecast);
        }
    }

    @Override
    public int getItemCount() {
        return dayMap.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TODAY_FORECAST;
        }
        return LATER_FORECAST;
    }

    class ForecastHolder extends RecyclerView.ViewHolder {
        View view;
        List<ForecastItem> dayForecast;
        ImageView forecastIcon;
        TextView forecastDesc;
        TextView forecastText;
        TextView forecastTempHigh;
        TextView forecastTempLow;

        ForecastHolder(View v) {
            super(v);
            this.view = v;
            forecastIcon = view.findViewById(R.id.weather_icon);
            forecastDesc = view.findViewById(R.id.weather_desc);
            forecastText = view.findViewById(R.id.weather_date);
            forecastTempHigh = view.findViewById(R.id.weather_temp_high);
            forecastTempLow = view.findViewById(R.id.weather_temp_low);
        }

        void bindForecastItem(List<ForecastItem> dayForecast) {
            this.dayForecast = dayForecast;

            String formattedDate = getFormattedTime(dayForecast.get(0).getDt(), "dd MMMM");
            Pair<Double, Double> minMaxTemp = findMinMaxTemp(dayForecast);
            String desc = dayForecast.get(0).getDescription();
            double min = minMaxTemp.first;
            double max = minMaxTemp.second;
            String tempLow = String.format("%.0f\u00b0", min);
            String tempHigh = String.format("%.0f\u00b0", max);

            String weatherCondition = dayForecast.get(0).getIcon();
            int weatherIcon = getIconId(weatherCondition);

            forecastIcon.setImageResource(weatherIcon);
            forecastText.setText(formattedDate);
            forecastDesc.setText(desc);
            forecastTempLow.setText(tempLow);
            forecastTempHigh.setText(tempHigh);
        }
    }

    class ForecastMainHolder extends RecyclerView.ViewHolder {

        View view;
        List<ForecastItem> fiveItemsForecast;

        ImageView forecastIconOne;
        ImageView forecastIconTwo;
        ImageView forecastIconThree;
        ImageView forecastIconFour;
        ImageView forecastIconFive;

        ImageView weatherIconMain;

        TextView weatherTimeOne;
        TextView weatherTimeTwo;
        TextView weatherTimeThree;
        TextView weatherTimeFour;
        TextView weatherTimeFive;

        TextView weatherTodayTemp;
        TextView weatherTodayCity;
        TextView weatherTodayDesc;
        TextView weatherTodayDate;

        ForecastMainHolder(View v) {
            super(v);
            this.view = v;
            forecastIconOne = view.findViewById(R.id.weather_icon_one);
            forecastIconTwo = view.findViewById(R.id.weather_icon_two);
            forecastIconThree = view.findViewById(R.id.weather_icon_three);
            forecastIconFour = view.findViewById(R.id.weather_icon_four);
            forecastIconFive = view.findViewById(R.id.weather_icon_five);

            weatherIconMain = view.findViewById(R.id.weather_icon_main);

            weatherTimeOne = view.findViewById(R.id.weather_time_one);
            weatherTimeTwo = view.findViewById(R.id.weather_time_two);
            weatherTimeThree = view.findViewById(R.id.weather_time_three);
            weatherTimeFour = view.findViewById(R.id.weather_time_four);
            weatherTimeFive = view.findViewById(R.id.weather_time_five);

            weatherTodayTemp = view.findViewById(R.id.weather_today_temp_high);
            weatherTodayCity = view.findViewById(R.id.weather_today_city);
            weatherTodayDesc = view.findViewById(R.id.weather_today_desc);
            weatherTodayDate = view.findViewById(R.id.weather_today_date);
        }

        void bindMainForecastItem(List<ForecastItem> fiveItemsForecast) {
            this.fiveItemsForecast = fiveItemsForecast;

            int iconOneId = getIconId(fiveItemsForecast.get(0).getIcon());
            weatherIconMain.setImageResource(iconOneId);
            forecastIconOne.setImageResource(iconOneId);
            int iconTwoId = getIconId(fiveItemsForecast.get(1).getIcon());
            forecastIconTwo.setImageResource(iconTwoId);
            int iconThreeId = getIconId(fiveItemsForecast.get(2).getIcon());
            forecastIconThree.setImageResource(iconThreeId);
            int iconFourId = getIconId(fiveItemsForecast.get(3).getIcon());
            forecastIconFour.setImageResource(iconFourId);
            int iconFiveId = getIconId(fiveItemsForecast.get(4).getIcon());
            forecastIconFive.setImageResource(iconFiveId);

            String timeOne = getFormattedTime(fiveItemsForecast.get(0).getDt(), "HH:mm");
            weatherTimeOne.setText(timeOne);
            String timeTwo = getFormattedTime(fiveItemsForecast.get(1).getDt(), "HH:mm");
            weatherTimeTwo.setText(timeTwo);
            String timeThree = getFormattedTime(fiveItemsForecast.get(2).getDt(), "HH:mm");
            weatherTimeThree.setText(timeThree);
            String timeFour = getFormattedTime(fiveItemsForecast.get(3).getDt(), "HH:mm");
            weatherTimeFour.setText(timeFour);
            String timeFive = getFormattedTime(fiveItemsForecast.get(4).getDt(), "HH:mm");
            weatherTimeFive.setText(timeFive);

            String temp = String.format("%.0f\u00b0", fiveItemsForecast.get(0).getTemp());

            weatherTodayTemp.setText(temp);
            weatherTodayCity.setText(fiveItemsForecast.get(0).getCity());
            weatherTodayDesc.setText(fiveItemsForecast.get(0).getDescription());

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM");
            String currentDate = dateFormat.format(cal.getTime());
            weatherTodayDate.setText(currentDate);
        }
    }

    private int getIconId(String code) {
        code = code.substring(0, 2);
        int value;
        switch (code) {
            case "01":
                value = R.drawable.ic_sunrise;
                break;
            case "02":
                value = R.drawable.ic_cloudy;
                break;
            case "03":
                value = R.drawable.ic_cloudy;
                break;
            case "04":
                value = R.drawable.ic_cloudy;
                break;
            case "09":
                value = R.drawable.ic_rainy;
                break;
            case "10":
                value = R.drawable.ic_rainy;
                break;
            case "11":
                value = R.drawable.ic_thunderstorm;
                break;
            case "13":
                value = R.drawable.ic_snowy;
                break;
            case "50":
                value = R.drawable.ic_foggy;
                break;
            default:
                value = R.drawable.ic_sunrise;
                break;
        }
        return value;
    }

    private Pair<Double, Double> findMinMaxTemp(List<ForecastItem> day) {

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

    private String getFormattedTime(int timestamp, String pattern) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp * 1000L);
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(cal.getTime());
    }
}
