package com.tom.waterqualityex.global;

import android.app.Application;
import android.content.Context;

/**
 * Created by mengxin on 17-3-31.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
