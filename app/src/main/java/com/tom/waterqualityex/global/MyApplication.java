package com.tom.waterqualityex.global;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePalApplication;

/**
 * Created by mengxin on 17-3-31.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        //LitePal数据库初始化
        LitePalApplication.initialize(context);
    }

    public static Context getContext(){
        return context;
    }
}
