package com.ponomarenko.shootingRange;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Ponomarenko Oleh on 10/19/2017.
 */

public class Timer {

    private String timeRemain;
    private TextView timeRemainTV;
    private Context context;
    private GameView gameView;
    private final LinearLayout layout;

    public Timer(Context context, GameView gameView) {
        this.context = context;
        this.gameView = gameView;
        this.timeRemainTV = new TextView(context);


        layout = new LinearLayout(context);

        layout.setGravity(Gravity.START);
        layout.setVerticalGravity(Gravity.BOTTOM);
        timeRemainTV.setVisibility(View.VISIBLE);
        timeRemainTV.setTextSize(35);
        timeRemainTV.setTextColor(Color.RED);
        timeRemainTV.setGravity(Gravity.CENTER);
        timeRemainTV.setPadding(30, 0, 0, 30);
        layout.addView(timeRemainTV);
    }

    void onDraw(Canvas canvas, String timeRemain) {
        update(timeRemain);
        layout.measure(canvas.getWidth(), canvas.getHeight());
        layout.layout(0, 0, canvas.getWidth(), canvas.getHeight());

        layout.draw(canvas);
    }

    private void update(String timeRemain) {
        timeRemainTV.setText(timeRemain);
    }

}


