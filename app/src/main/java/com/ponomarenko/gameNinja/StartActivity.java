package com.ponomarenko.gameNinja;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;


public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int STOP_SPLASH = 0;
    private static final long SPLASH_TIME = 5000; //time of displaying Splash Screen in milliseconds
    private RelativeLayout splash;

    private Handler splashHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STOP_SPLASH:
                    splash.setVisibility(View.GONE);
                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // если хотим, чтобы приложение было полноэкранным
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);

/*        splash = (RelativeLayout) findViewById(R.id.splash_screen);
        Message msg = new Message();
        msg.what = STOP_SPLASH;
        splashHandler.sendMessageDelayed(msg, SPLASH_TIME);*/
        startService(new Intent(this, MusicService.class));
        Button startButton = (Button) findViewById(R.id.startBtn);
        startButton.setOnClickListener(this);

        Button exitButton = (Button) findViewById(R.id.exitBtn);
        exitButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startBtn:
                Intent intent = new Intent();
                intent.setClass(this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.exitBtn:
                stopService(new Intent(this, MusicService.class));
                finish();
                break;


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopService(new Intent(this, MusicService.class));
        finish();
    }
}
