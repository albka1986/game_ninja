package com.ponomarenko.shootingRange.core;

import android.app.Application;
import android.content.Context;

import com.ponomarenko.shootingRange.Utilities;

public class MyApplication extends Application {

    public static int screenHeightPx;
    public static int screenWidthPx;

    @Override
    public void onCreate() {
        super.onCreate();
        screenHeightPx = Utilities.getHeightPx(this);
        screenWidthPx = Utilities.getWidthPx(this);
    }

}
