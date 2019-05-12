package com.vladislav.weatherforecast;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

class ImageResourceBindingAdapter {
    @BindingAdapter("android:src")
    public static void setImageResource(ImageView imageView, int resourceId) {
        imageView.setImageResource(resourceId);
    }
}
