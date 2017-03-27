package com.tom.waterqualityex.home;

import android.support.v4.app.Fragment;

import com.tom.waterqualityex.BaseFragmentActivity;
import com.tom.waterqualityex.R;

public class HomeActivity extends BaseFragmentActivity {

    @Override
    protected Fragment creatFragment() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_home;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.fragment_container;
    }
}
