package com.vladislav.weatherforecast.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.vladislav.weatherforecast.model.ForecastItem;
import com.vladislav.weatherforecast.R;
import com.vladislav.weatherforecast.databinding.RvForecastRowBinding;
import com.vladislav.weatherforecast.databinding.RvTodayRowBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

public class ForecastRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TODAY_FORECAST = 0;
    private static final int LATER_FORECAST = 1;

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
            RvTodayRowBinding rowBinding = RvTodayRowBinding.inflate(inflater, parent, false);
            return new ForecastMainHolder(rowBinding);

        } else {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            RvForecastRowBinding forecastBinding = RvForecastRowBinding.inflate(inflater, parent, false);
            return new ForecastHolder(forecastBinding);
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
        RvForecastRowBinding binding;
        List<ForecastItem> dayForecast;

        ForecastHolder(RvForecastRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
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

            binding.setIcon(weatherIcon);
            binding.setDate(formattedDate);
            binding.setDescription(desc);
            binding.setTemperatureLow(tempLow);
            binding.setTemperatureHigh(tempHigh);
        }
    }

    class ForecastMainHolder extends RecyclerView.ViewHolder {
        RvTodayRowBinding binding;
        List<ForecastItem> fiveItemsForecast;

        ForecastMainHolder(RvTodayRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindMainForecastItem(List<ForecastItem> fiveItemsForecast) {
            this.fiveItemsForecast = fiveItemsForecast;

            int iconOneId = getIconId(fiveItemsForecast.get(0).getIcon());
            int iconTwoId = getIconId(fiveItemsForecast.get(1).getIcon());
            int iconThreeId = getIconId(fiveItemsForecast.get(2).getIcon());
            int iconFourId = getIconId(fiveItemsForecast.get(3).getIcon());
            int iconFiveId = getIconId(fiveItemsForecast.get(4).getIcon());

            String timeOne = getFormattedTime(fiveItemsForecast.get(0).getDt(), "HH:mm");
            String timeTwo = getFormattedTime(fiveItemsForecast.get(1).getDt(), "HH:mm");
            String timeThree = getFormattedTime(fiveItemsForecast.get(2).getDt(), "HH:mm");
            String timeFour = getFormattedTime(fiveItemsForecast.get(3).getDt(), "HH:mm");
            String timeFive = getFormattedTime(fiveItemsForecast.get(4).getDt(), "HH:mm");

            String temp = String.format("%.0f\u00b0", fiveItemsForecast.get(0).getTemp());

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM");
            String currentDate = dateFormat.format(cal.getTime());

            binding.setDate(currentDate);
            binding.setDescription(fiveItemsForecast.get(0).getDescription());
            binding.setTemperature(temp);
            binding.setIconMainRes(iconOneId);
            binding.setIconOne(iconOneId);
            binding.setIconTwo(iconTwoId);
            binding.setIconThree(iconThreeId);
            binding.setIconFour(iconFourId);
            binding.setIconFive(iconFiveId);
            binding.setTimeOne(timeOne);
            binding.setTimeTwo(timeTwo);
            binding.setTimeThree(timeThree);
            binding.setTimeFour(timeFour);
            binding.setTimeFive(timeFive);
            binding.setCity(fiveItemsForecast.get(0).getCity());
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
