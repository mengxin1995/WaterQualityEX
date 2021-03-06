package com.tom.waterqualityex.data;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.tom.waterqualityex.domain.gson.Weather;
import com.tom.waterqualityex.global.GlobalConstants;
import com.tom.waterqualityex.utils.BackgroundMusic;
import com.tom.waterqualityex.utils.Engine;
import com.tom.waterqualityex.utils.HttpUtil;
import com.tom.waterqualityex.utils.SpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AutoUpdateService extends Service {

    private BackgroundMusic mBackmusicMusicInstance;

    public AutoUpdateService() {
    }

    @Override
    public void onCreate() {
        initMusic();
        super.onCreate();
    }

    private void initMusic() {
        mBackmusicMusicInstance = BackgroundMusic.getInstance(getApplicationContext());
        mBackmusicMusicInstance.playBackgroundMusic("backmusic.mp3", true);
        //这里暂时把音量关了
        //mBackmusicMusicInstance.setBackgroundVolume(0f);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateWeather();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 60 * 60 * 1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this, AutoUpdateService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);

        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWeather() {
        String weatherString = SpUtil.getString(this, GlobalConstants.WEATHER, null);
        if(weatherString != null){
            Weather weather = Engine.handleWeatherResponse(weatherString);
            String weatherId = weather.basic.weatherId;
            String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=a615da100d8e4631a3e80367a5c046f8";
            HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseText = response.body().string();
                    Weather weather = Engine.handleWeatherResponse(responseText);
                    if(weather != null && "ok".equals(weather.status)){
                        SpUtil.setString(AutoUpdateService.this, GlobalConstants.WEATHER, responseText);
                    }
                }
            });
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
