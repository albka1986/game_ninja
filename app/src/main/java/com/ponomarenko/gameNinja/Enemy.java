package com.ponomarenko.gameNinja;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.Random;

public class Enemy {

    private int x, y; // position at x and y coordinates
    private int speed;

    private int width, height;

    private Bitmap enemyImage;

    public Enemy(Context context, GameView gameView) {
        this.enemyImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball);

        Random rnd = new Random();
        this.x = Utilities.getWidthPx(context) + 100;
        this.y = rnd.nextInt((int) (Utilities.getHeightPx(context) * 0.8));
        this.speed = rnd.nextInt(10) + 1;

        this.width = 8; // width of an enemy
        this.height = 8; // height of an enemy
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
        setX(x - speed);
    }

    void onDraw(Canvas c) {
        update();
        c.drawBitmap(enemyImage, x, y, null);
    }

}
