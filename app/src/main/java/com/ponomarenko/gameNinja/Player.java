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




    public void onDraw(Canvas c) {
        c.drawBitmap(bmp, x, y, null);
    }


}
