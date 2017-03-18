package com.tom.waterqualityex.welcome;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.tom.waterqualityex.utils.BackgroundMusic;

public class WelcomeService extends Service {

    private BackgroundMusic mBackmusicMusicInstance;

    public WelcomeService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initMusic();
    }

    private void initMusic() {
        mBackmusicMusicInstance = BackgroundMusic.getInstance(getApplicationContext());
        mBackmusicMusicInstance.playBackgroundMusic("backmusic.mp3", true);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
