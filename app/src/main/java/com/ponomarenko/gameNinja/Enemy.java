package com.ponomarenko.gameNinja;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

public class Enemy {

    public int x, y;
    public int speed;

    public int width, height;

    public GameView gameView;
    public Bitmap bmp;

    public Enemy(Context context, GameView gameView, Bitmap bmp) {
        this.gameView = gameView;
        this.bmp = bmp;

        Random rnd = new Random();
        this.x = Utilities.getWidthPx(context) + 100;
        this.y = rnd.nextInt((int) (Utilities.getHeightPx(context) * 0.8));
        this.speed = rnd.nextInt(10) + 1;

        this.width = 9; // width of an enemy
        this.height = 8; // height of an enemy
    }

    public void update() {
        x -= speed;
    }

    public void onDraw(Canvas c) {
        update();
        c.drawBitmap(bmp, x, y, null);
    }

}
