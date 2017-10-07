package com.ponomarenko.gameNinja;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class MusicService extends Service {

    public static final String TAG = "MusicService";
    MediaPlayer player;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "My Service Created", Toast.LENGTH_SHORT).show();

        player = MediaPlayer.create(this, R.raw.background_music);
        player.setLooping(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "My Service Stopped", Toast.LENGTH_SHORT).show();
        player.stop();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Toast.makeText(this, "My Service Started", Toast.LENGTH_SHORT).show();
        player.start();
    }
}
