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
import java.util.Random;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static com.ponomarenko.shootingRange.core.MyApplication.screenHeightPx;
import static com.ponomarenko.shootingRange.core.MyApplication.screenWidthPx;

class GameView extends SurfaceView implements Runnable {

    private static final int DELAY_INTENT = 0;
    private static final long DELAY_TIME = 2500;
    private SoundPool sounds;
    private int sExplosion;
    private static final int ENEMY_AMOUNT = 50;
    private GameThread mThread;
    private boolean running = false;
    private List<Bullet> bulletList = new ArrayList<>();
    public Player player;
    public int shotY;
    public int shotX;

    private List<Enemy> enemies = new ArrayList<>();
    private Thread thread = new Thread(this);
    private int sShooting;


    public GameView(Context context) {
        super(context);
        mThread = new GameThread(this);

        getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                mThread.setRunning(true);
                mThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
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
        for (int i = 0; i < ENEMY_AMOUNT; i++) {
            enemies.add(new Enemy(context, this));
        }
        sounds = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        sExplosion = sounds.load(context, R.raw.bubble_explosion, 1);
        sShooting = sounds.load(context, R.raw.sound_rifle_shoot, 1);
    }

    @Override
    public void run() {

        while (true) {
            Random rnd = new Random();
            try {
                thread.sleep(rnd.nextInt(2000));
                enemies.add(new Enemy(getContext(), this));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private class GameThread extends Thread {

        private GameView view;

        public GameThread(GameView view) {
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
                            Log.e("Test", "run: ");
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

        Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.background_image_game);
        Bitmap scaled = Bitmap.createScaledBitmap(background, Utilities.getWidthPx(getContext()), Utilities.getHeightPx(getContext()), true);

        canvas.drawBitmap(scaled, 0, 0, null);

        Iterator<Bullet> j = bulletList.iterator();
        while (j.hasNext()) {
            Bullet bullet = j.next();
            if (bullet.getX() >= 1000 || bullet.getX() <= 1000) {
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

        canvas.drawBitmap(player.getPlayerImage(), player.getX(), player.getY(), null);
    }

    public Bullet createBullet(Player player) {
        return new Bullet(getContext(), this, player);
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        Log.e("Test", "onTouchEvent: x = " + e.getX() + ", y = " + e.getY());

        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            if (e.getY() >= player.getY()) {
                return false;
            }
        }

        new Handler().post(new Runnable() {
            public void run() {
                sounds.play(sShooting, 1.0f, 1.0f, 0, 0, 1.5f);
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
            if (bullet.getX() < 0 || bullet.getX() > screenWidthPx || bullet.getY() < 0 || bullet.getY() > screenHeightPx) {
                bulletList.remove(bullet);
            }

            Iterator<Enemy> i = enemies.iterator();
            while (i.hasNext()) {
                Enemy enemy = i.next();

                if ((Math.abs(bullet.getX() - enemy.getX()) <= (bullet.getWidth() + enemy.getWidth()) / 2f) && (Math.abs(bullet.getY() - enemy.getY()) <= (bullet.getHeight() + enemy.getHeight()))) {
                    sounds.play(sExplosion, 1.0f, 1.0f, 0, 0, 1.5f);
                    i.remove();
                    bullets.remove();

                    if (enemies.size() == 0) {
                        Message msg = new Message();
                        msg.what = DELAY_INTENT;
                        splashHandler.sendMessageDelayed(msg, DELAY_TIME);

                    }

                }
            }
        }

        Log.e("Test", "Amount of bullets: " + bulletList.size());
    }

    private Handler splashHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DELAY_INTENT:

                    Intent intent = new Intent(getContext(), ResultGameActivity.class);
                    intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                    getContext().startActivity(intent);

                    break;
            }
            super.handleMessage(msg);
        }
    };

}
