package com.tom.waterqualityex;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by mengxin on 17-3-16.
 */

public abstract class BaseFragmentActivity extends AppCompatActivity {

    private static final String TAG = "BaseFragmentActivity";

    protected abstract Fragment creatFragment();

    protected void init() {
    }

    protected abstract int getLayoutResId();

    protected abstract int getFragmentContainerId();

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(getFragmentContainerId());

        if (null == fragment) {
            fragment = creatFragment();
            fm.beginTransaction()
                    .add(getFragmentContainerId(), fragment)
                    .commit();
        }
        init();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
    }
}
