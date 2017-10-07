package com.ponomarenko.gameNinja;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

class GameView extends SurfaceView implements Runnable {


    private SoundPool sounds;
    private int sExplosion;
    private static final int ENEMY_AMOUNT = 10;
    private GameThread mThread;
    private boolean running = false;
    private List<Bullet> ball = new ArrayList<>();
    private Player player;
    Bitmap players;
    public int shotY;
    public int shotX;

    private List<Enemy> enemy = new ArrayList<>();
    Bitmap enemies;
    private Thread thread = new Thread(this);


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

        players = BitmapFactory.decodeResource(getResources(), R.drawable.ninja);
        player = new Player(this, players);
        enemies = BitmapFactory.decodeResource(getResources(), R.drawable.ghost);
        for (int i = 0; i < ENEMY_AMOUNT; i++) {
            enemy.add(new Enemy(this, enemies));
        }
        sounds = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        sExplosion = sounds.load(context, R.raw.bubble_explosion, 1);


    }

    @Override
    public void run() {

        while (true) {
            Random rnd = new Random();
            try {
                thread.sleep(rnd.nextInt(2000));
                enemy.add(new Enemy(this, enemies));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public class GameThread extends Thread {

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
                        onDraw(canvas);
                        testCollision();
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
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        Iterator<Bullet> j = ball.iterator();
        while (j.hasNext()) {
            Bullet b = j.next();
            if (b.x >= 1000 || b.x <= 1000) {
                b.onDraw(canvas);
            } else {
                j.remove();
            }
        }

        Iterator<Enemy> i = enemy.iterator();
        while (i.hasNext()) {
            Enemy e = i.next();
            if (e.x >= 1000 || e.x <= 1000) {
                e.onDraw(canvas);
            } else {
                i.remove();
            }
        }


        canvas.drawBitmap(player.bmp, 5, 120, null);
    }

    public Bullet createSpirit(int resource) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
        return new Bullet(this, bmp);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        shotX = (int) e.getX();
        shotY = (int) e.getY();

        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            ball.add(createSpirit(R.drawable.bullet));
        }

        return true;
    }

    private void testCollision() {
        Iterator<Bullet> b = ball.iterator();
        while (b.hasNext()) {
            Bullet balls = b.next();
            Iterator<Enemy> i = enemy.iterator();
            while (i.hasNext()) {
                Enemy enemies = i.next();

                if ((Math.abs(balls.x - enemies.x) <= (balls.width + enemies.width) / 2f) && (Math.abs(balls.y - enemies.y) <= (balls.height + enemies.height))) {
                    sounds.play(sExplosion, 1.0f, 1.0f, 0, 0, 1.5f);
                    i.remove();
                    b.remove();
                }
            }
        }
    }


}
