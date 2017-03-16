package com.tom.waterqualityex;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by mengxin on 17-3-16.
 */

public abstract class BaseFragmentActivity extends AppCompatActivity {
    protected abstract Fragment creatFragment();

    protected void init(){
    }

    protected abstract int getLayoutResId();

    protected abstract int getFragmentContainerId();

    @Override
    protected final void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(getFragmentContainerId());

        if(null == fragment){
            fragment = creatFragment();
            fm.beginTransaction()
                    .add(getFragmentContainerId(), fragment)
                    .commit();
        }
        init();
    }
}
