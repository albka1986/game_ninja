package com.ponomarenko.gameNinja.core;

import android.app.Application;

import com.ponomarenko.gameNinja.Utilities;

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
