package com.ponomarenko.gameNinja;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Bullet {

    //position
    private int x, y;

    private Bitmap bulletImage;

    //speed by X
    private int mSpeed = 25;

    private double angle;

    private int width, height;


    public Bullet(Context context, GameView gameView, Player player) {
        this.bulletImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet_circle);

        this.x = player.getX() + player.getPlayerImage().getWidth(); // position by X
        this.y = player.getY(); // position by Y

        this.height = 27;
        this.width = 40;

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
        x += mSpeed * Math.cos(angle);
        y += mSpeed * Math.sin(angle);
    }

    public void onDraw(Canvas canvas) {
        update();
        canvas.drawBitmap(bulletImage, getX(), getY(), null);
    }

}
