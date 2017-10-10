package com.ponomarenko.shootingRange;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import static java.lang.Math.sqrt;

public class Bullet {

    //position
    private int x, y;

    private Bitmap bulletImage;

    //speed by X
    private int mSpeed = 60;

    private double angle;

    private int width, height;

    private GameView gameView;


    public Bullet(Context context, GameView gameView, Player player) {
        this.bulletImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet_circle);
        this.gameView = gameView;
        this.x = player.getX(); // position by X
        this.y = (int) (player.getY() * 0.95); // position by Y

        this.height = bulletImage.getHeight();
        this.width = bulletImage.getWidth();

        //угол полета пули в зависипости от координаты косания к экрану
        angle = Math.atan((double) (y - gameView.shotY) / (x - gameView.shotX));


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

    public void setY(int y) {
        this.y = y;
    }

    public int getmSpeed() {
        return mSpeed;
    }

    public void setmSpeed(int mSpeed) {
        this.mSpeed = mSpeed;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private void update() {
//        x += mSpeed * Math.cos(angle);
//        y += mSpeed * Math.sin(angle);
        double longA = getY() - gameView.shotY;
        double longB = getX() - gameView.shotX;
        double longC = (int) sqrt(longA * longA + longB * longB);
        double cosB = longB / longC;
        int shortB = (int) (mSpeed * cosB);
        int shortA = (int) sqrt(mSpeed * mSpeed - shortB * shortB);

        x = x - shortB;
        y = y - shortA;


    }

    public void onDraw(Canvas canvas) {
        update();
        canvas.drawBitmap(bulletImage, getX(), getY(), null);
    }

}
