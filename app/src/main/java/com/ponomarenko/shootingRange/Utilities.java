package com.ponomarenko.shootingRange;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class Utilities {

    public static int getWidthPx(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static int getHeightPx(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }




}
