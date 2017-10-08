package com.ponomarenko.gameNinja;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import java.util.Random;

import static com.ponomarenko.gameNinja.core.MyApplication.screenHeightPx;
import static com.ponomarenko.gameNinja.core.MyApplication.screenWidthPx;
import static java.lang.Math.PI;

public class Enemy {

    private int x, y; // position at x and y coordinates
    private int mSpeed;
    private Context context;

    private int width, height;
    private Bitmap enemyImage;
    private double startDirectionAngle;
    private double currentAngle;
    private GameView gameView;


    public Enemy(Context context, GameView gameView) {
        this.enemyImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball);
        this.gameView = gameView;
        Random rnd = new Random();
        this.context = context;
        this.x = (int) (rnd.nextInt((int) (screenWidthPx * 0.4)) + screenWidthPx * 0.3);
        this.y = (int) (rnd.nextInt((int) (screenHeightPx * 0.4)) + screenHeightPx * 0.3);

        this.mSpeed = 50;
//        this.mSpeed = rnd.nextInt(12) + 4;

        this.width = enemyImage.getWidth(); // for accuracy
        this.height = enemyImage.getHeight(); // for accuracy

        //random startDirectionAngle of start direction
        this.startDirectionAngle = 45;
        Log.e("Test", "Enemy.startDirectionAngle: " + startDirectionAngle);
        currentAngle = this.startDirectionAngle;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private void update() {

        if (x <= 0 || x >= gameView.getWidth() - width) {
            currentAngle = reflectVertical(currentAngle);
            x += mSpeed * Math.cos(currentAngle);
            y += mSpeed * Math.sin(currentAngle);
            Log.e("Test", "vertical reflect: " + currentAngle);

        } else if (y <= 0 || y >= gameView.getHeight() - height) {
            currentAngle = reflectHorizontal(currentAngle);
            x += mSpeed * Math.cos(currentAngle);
            y += mSpeed * Math.sin(currentAngle);

            Log.e("Test", "vertical reflect: " + currentAngle);
        } else {
            x += mSpeed * Math.cos(currentAngle);
            y += mSpeed * Math.sin(currentAngle);
        }

    }


    void onDraw(Canvas c) {
        update();
        c.drawBitmap(enemyImage, x, y, null);
    }

    /**
     * Отражение мячика от вертикали
     */
    private double reflectVertical(double angle) {
        if (angle > 0 && angle < PI)
            angle = PI - angle;
        else
            angle = 3 * PI - angle;

        return angle;
    }


    private double reflectHorizontal(double angle) {
        return 2 * PI - angle;
    }


}
