package com.ponomarenko.shootingRange;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import static java.lang.Math.sqrt;

public class Bullet {

    //position
    private long positionX;
    private long positionY;

    private long directionX;
    private long directionY;

    private Bitmap bulletImage;

    //speed by X
    private  int mSpeed = 60;

    private double angle;

    private int width, height;

    private GameView gameView;


    public Bullet(Context context, GameView gameView, Player player) {
        this.bulletImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet_circle);
        this.gameView = gameView;
        this.positionX = player.getX(); // position by X
        this.positionY = (int) (player.getY() * 0.95); // position by Y

        this.height = bulletImage.getHeight();
        this.width = bulletImage.getWidth();

        //угол полета пули в зависипости от координаты косания к экрану
        this.angle = Math.atan((double) (positionY - gameView.shotY) / (positionX - gameView.shotX));
        directionX = gameView.shotX;
        directionY = gameView.shotY;

    }


    public long getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public long getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
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
   /*     positionX += mSpeed * Math.cos(angle);
        positionY += mSpeed * Math.sin(angle);


        positionX = Math.round(positionX + mSpeed * Math.cos(angle));
        positionY = Math.round(positionY + mSpeed * Math.sin(angle));*/

 /*       if (gameView.shotY > positionY) {
            gameView.shotY = positionY;
        }*/

        if (directionX < gameView.player.getX()) {

            if (positionX <= directionX || positionY <= directionY) {
                positionX = Math.round(positionX - mSpeed * Math.cos(angle));
                positionY = Math.round(positionY - mSpeed * Math.sin(angle));
            } else {
                double longA = positionY - directionX;
                double longB = positionX - directionX;
                double longC = (int) sqrt(longA * longA + longB * longB);
                double cosB = longB / longC;
                double shortB = (mSpeed * cosB);
                double shortA = sqrt(mSpeed * mSpeed - shortB * shortB);

                positionX = (long) (positionX - shortB);
                positionY = (long) (positionY - shortA);
            }

        } else {
            positionX = Math.round(positionX + mSpeed * Math.cos(angle));
            positionY = Math.round(positionY + mSpeed * Math.sin(angle));
        }
    }


    public void onDraw(Canvas canvas) {
        update();
        canvas.drawBitmap(bulletImage, getPositionX(), getPositionY(), null);
    }

}
