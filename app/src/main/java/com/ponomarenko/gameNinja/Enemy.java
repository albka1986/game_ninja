package com.ponomarenko.gameNinja;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.Random;

public class Enemy {

    int x, y;
    private int speed;

    private int width, height;

    private GameView gameView;
    private Bitmap bmp;
    private Bitmap enemyImage;

    public Enemy(Context context, GameView gameView) {
        this.gameView = gameView;
        this.enemyImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball);

        Random rnd = new Random();
        this.x = Utilities.getWidthPx(context) + 100;
        this.y = rnd.nextInt((int) (Utilities.getHeightPx(context) * 0.8));
        this.speed = rnd.nextInt(10) + 1;

        this.width = 9; // width of an enemy
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

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
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

    public GameView getGameView() {
        return gameView;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public Bitmap getBmp() {
        return bmp;
    }

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }

    public Bitmap getEnemyImage() {
        return enemyImage;
    }

    public void setEnemyImage(Bitmap enemyImage) {
        this.enemyImage = enemyImage;
    }

    private void update() {
        x -= speed;
    }

    void onDraw(Canvas c) {
        update();
        c.drawBitmap(bmp, x, y, null);
    }

}
