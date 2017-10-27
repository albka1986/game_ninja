package com.ponomarenko.shootingRange;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static com.ponomarenko.shootingRange.ResultGameActivity.KEY_AMOUNT_KILLED_ENEMIES;
import static com.ponomarenko.shootingRange.ResultGameActivity.KEY_SPENT_TIME;
import static com.ponomarenko.shootingRange.core.MyApplication.screenHeightPx;
import static com.ponomarenko.shootingRange.core.MyApplication.screenWidthPx;

class GameView extends SurfaceView {

    private static final int DELAY_INTENT = 0;
    private static final long DELAY_TIME_3 = 1000;
    private long gameTime;
    private SoundPool sounds;
    private int sExplosion;
    private int enemyAmount;
    private GameThread mThread;
    private boolean running = false;
    private List<Bullet> bulletList = new ArrayList<>();
    public Player player;
    private Timer timer;
    public int shotY;
    public int shotX;
    private Handler handler;
    private int enemySpeed;

    private static long startTime;
    private long remainTime = gameTime;

    protected static List<Enemy> enemies = new ArrayList<>();
    private int sShooting;
    private Bitmap scaledBackground;


    public GameView(Context context) {

        super(context);
        Map<String, Integer> config = Utilities.readConfig(context);
        int time = config.get("time") * 1000;
        gameTime = (time == 0) ? 50000 : time;

        int amount = config.get("countTarget");
        enemyAmount = (amount == 0) ? 20 : amount;

        int speed = config.get("speed");
        enemySpeed = (speed == 0) ? 50 : speed;


        mThread = new GameThread(this);
        getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                handler = new CustomHandler();
                mThread.setRunning(true);
                mThread.start();
                startTime = System.currentTimeMillis();
                Message msg = new Message();
                msg.what = DELAY_INTENT;
                handler.sendMessageDelayed(msg, gameTime);

                Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.background_image_game);
                float scale = (float) background.getHeight() / (float) getHeight();
                int newWidth = Math.round(background.getWidth() / scale);
                int newHeight = Math.round(background.getHeight() / scale);
                scaledBackground = Bitmap.createScaledBitmap(background, newWidth, newHeight, true);
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                handler.removeCallbacksAndMessages(null);

                boolean retry = true;
                mThread.setRunning(false);

                while (retry) {
                    try {
                        mThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        player = new Player(context, this);
        timer = new Timer(context, this);
        for (int i = 0; i < enemyAmount; i++) {
            enemies.add(new Enemy(context, this, enemySpeed));
        }
        sounds = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        sExplosion = sounds.load(context, R.raw.bubble_explosion, 1);
        sShooting = sounds.load(context, R.raw.sound_rifle_shoot, 1);
    }

    private class GameThread extends Thread {

        private GameView view;

        GameThread(GameView view) {
            this.view = view;
        }

        public void setRunning(boolean run) {
            running = run;
        }

        @Override
        public void run() {
            while (running) {
                Canvas canvas = null;
                try {
                    canvas = view.getHolder().lockCanvas();
                    synchronized (view.getHolder()) {
                        draw(canvas);
                        testCollision();
                        if (enemies.size() == 0) {
                            Log.d(GameView.class.getSimpleName(), "run: ");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (canvas != null) {
                        view.getHolder().unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawBitmap(scaledBackground, 0, 0, null);

        Iterator<Bullet> j = bulletList.iterator();
        while (j.hasNext()) {
            Bullet bullet = j.next();
            if (bullet.getPositionX() >= 1000 || bullet.getPositionX() <= 1000) {
                bullet.onDraw(canvas);
            } else {
                j.remove();
            }
        }

        Iterator<Enemy> i = enemies.iterator();
        while (i.hasNext()) {
            Enemy enemy = i.next();
            if (enemy.getX() >= 1000 || enemy.getX() <= 1000) {
                enemy.onDraw(canvas);
            } else {
                i.remove();
            }
        }

        updateRemainTime();
        timer.onDraw(canvas, String.valueOf(remainTime));


        canvas.drawBitmap(player.getPlayerImage(), player.getX(), player.getY(), null);
    }

    private void updateRemainTime() {
        remainTime = (gameTime / 1000) - getSpentTime();
    }

    public Bullet createBullet(Player player) {
        return new Bullet(getContext(), this, player);
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        Log.d(GameView.class.getSimpleName(), "onTouchEvent: x = " + e.getX() + ", y = " + e.getY());

        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            if (e.getY() >= player.getY()) {
                return false;
            }
        }

        new Handler().post(new Runnable() {
            public void run() {
                sounds.play(sShooting, 0.5f, 0.5f, 0, 0, 1.5f);
            }
        });

        shotX = (int) e.getX();
        shotY = (int) e.getY();

        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            bulletList.add(createBullet(player));
        }

        return false;
    }

    private void testCollision() {
        Iterator<Bullet> bullets = this.bulletList.iterator();
        while (bullets.hasNext()) {
            Bullet bullet = bullets.next();
            if (bullet.getPositionX() < 0 || bullet.getPositionX() > screenWidthPx || bullet.getPositionY() < 0 || bullet.getPositionY() > screenHeightPx) {
                bulletList.remove(bullet);
            }

            Iterator<Enemy> i = enemies.iterator();
            while (i.hasNext()) {
                Enemy enemy = i.next();

                if ((Math.abs(bullet.getPositionX() - enemy.getX()) <= (bullet.getWidth() + enemy.getWidth()) / 2f) && (Math.abs(bullet.getPositionY() - enemy.getY()) <= (bullet.getHeight() + enemy.getHeight()))) {
                    sounds.play(sExplosion, 1.0f, 1.0f, 0, 0, 1.5f);
                    i.remove();
                    bullets.remove();

                    if (enemies.size() == 0) {
                        Message msg = new Message();
                        msg.what = DELAY_INTENT;
                        handler.sendMessageDelayed(msg, DELAY_TIME_3);
                    }
                }
            }
        }
    }

    private static int getSpentTime() {
        return (int) ((System.currentTimeMillis() - startTime) / 1000);
    }

    class CustomHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DELAY_INTENT:
                    launchResultActivity();

                    break;
            }
            super.handleMessage(msg);
        }

    }

    private void launchResultActivity() {
        Intent intent = new Intent(getContext(), ResultGameActivity.class);
        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(KEY_AMOUNT_KILLED_ENEMIES, enemyAmount - enemies.size());

        int spentTime = getSpentTime();
        intent.putExtra(KEY_SPENT_TIME, spentTime);

        getContext().startActivity(intent);
    }
}
