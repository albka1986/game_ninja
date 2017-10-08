package com.ponomarenko.gameNinja;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Player {

    private GameView gameView;

    private Bitmap bmp;

    private int x;
    private int y;

    public Player(Context context, GameView gameView, Bitmap bmp) {
        this.gameView = gameView;
        this.bmp = bmp;

        this.x = (int) (Utilities.getWidthPx(context) * 0.03);
        this.y = Utilities.getHeightPx(context) / 2;
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

    public void onDraw(Canvas c) {
        c.drawBitmap(bmp, x, y, null);
    }


}
