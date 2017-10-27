package com.ponomarenko.shootingRange;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Utilities {

    public static int getWidthPx(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static int getHeightPx(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    public static Map<String, Integer> readConfig(Context context) {
        Map<String, Integer> config = new HashMap<>();
        String configFileName = "input.txt";

        try {
            AssetManager am = context.getAssets();
            InputStream is = am.open(configFileName);

            BufferedReader r = new BufferedReader(new InputStreamReader(is));

            String newLine;
            while ((newLine = r.readLine()) != null) {
                String[] line = newLine.trim().split("=");
                config.put(line[0], Integer.valueOf(line[1]));
            }

        } catch (IOException e) {
            Log.e(Utilities.class.getSimpleName(), "File Read Error");
        }

        Log.e(Utilities.class.getSimpleName(), "readConfig: " + config.toString());
        return config;
    }


}
