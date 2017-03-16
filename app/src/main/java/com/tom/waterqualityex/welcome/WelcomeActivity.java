package com.tom.waterqualityex.welcome;

import android.support.v4.app.Fragment;

import com.tom.waterqualityex.BaseFragmentActivity;
import com.tom.waterqualityex.R;

public class WelcomeActivity extends BaseFragmentActivity {

    @Override
    protected Fragment creatFragment() {
        return new WelcomeFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.fragment_container;
    }
}
