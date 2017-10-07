package com.ponomarenko.gameNinja;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Bullet {

    private Bitmap bmp;

    //position
    public int x, y;


    //speed by X
    private int mSpeed = 25;

    public double angle;

    public int width;

    public int height;

    public GameView gameView;

    public Bullet(GameView gameView, Bitmap bmp) {
        this.gameView = gameView;
        this.bmp = bmp;

        this.x = 0; // position by X
        this.y = 120; // position by Y
        this.height = 27; //height of this object
        this.width = 40; // width of this project

        //угол полета пули в зависипости от координаты косания к экрану
        angle = Math.atan((double) (y - gameView.shotY) / (x - gameView.shotX));
    }

    private void update() {
        x += mSpeed * Math.cos(angle);
        y += mSpeed * Math.sin(angle);
    }

    public void onDraw(Canvas canvas) {
        update();
        canvas.drawBitmap(bmp, x, y, null);
    }

}
