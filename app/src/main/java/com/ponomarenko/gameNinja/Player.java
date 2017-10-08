package com.ponomarenko.gameNinja;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Player {

    private Bitmap playerImage;
    private GameView gameView;
    private int x;
    private int y;

    public Player(Context context, GameView gameView) {
        this.gameView = gameView;
        playerImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.shooter);
        this.x = (Utilities.getWidthPx(context) - getPlayerImage().getWidth()) / 2;
        this.y = Utilities.getHeightPx(context) - playerImage.getHeight();
    }

    public Bitmap getPlayerImage() {
        return playerImage;
    }

    public void setPlayerImage(Bitmap playerImage) {
        this.playerImage = playerImage;
    }

    public GameView getGameView() {
        return gameView;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
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
        c.drawBitmap(playerImage, x, y, null);
    }


}
