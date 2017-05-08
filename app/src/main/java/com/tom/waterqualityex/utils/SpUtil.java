package com.tom.waterqualityex.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.tom.waterqualityex.global.GlobalConstants;


/**
 * Created by mengxin on 16-9-23.
 */
public class SpUtil {
    private static final String TAG = "SpUtil";
    private static int count = 0;

    public static synchronized boolean getBoolean(Context context, String key, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(GlobalConstants.CONFIG,
                Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    public static synchronized void setBoolean(Context context, String key, boolean value){
        SharedPreferences sp = context.getSharedPreferences(GlobalConstants.CONFIG,
                Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    public static synchronized String getString(Context context, String key, String defValue) {
        count++;
        Log.d(TAG, "getString: " + count);
        SharedPreferences sp = null;
        if(context != null) {
            Log.d(TAG, "getString: " + context.toString());
            sp = context.getSharedPreferences(GlobalConstants.CONFIG,
                    Context.MODE_PRIVATE);
        }else{
            Log.d(TAG, "getString: " + "null 了");
        }
        return sp.getString(key, defValue);
    }

    public static synchronized void setString(Context context, String key, String value){
        SharedPreferences sp = context.getSharedPreferences(GlobalConstants.CONFIG,
                Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }
}
