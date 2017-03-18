package com.tom.waterqualityex.welcome;

import android.content.Context;
import android.support.v4.view.ViewPager;

import com.tom.waterqualityex.adapter.ViewPagerAdapter;
import com.tom.waterqualityex.model.WelcomeResponse;

/**
 * Created by mengxin on 17-3-16.
 */

public class WelcomePresenter implements WelcomeContract.Presenter {

    WelcomeContract.View mView;
    WelcomeResponse mWelcomeResponse;
    private ViewPagerAdapter mViewPagerAdapter;

    public WelcomePresenter(WelcomeContract.View view, WelcomeResponse welcomeResponse) {
        this.mView = view;
        this.mWelcomeResponse = welcomeResponse;
        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void setWelcomeViewPagerAdapter(ViewPager welcomePicViewPager, Context context) {
        mViewPagerAdapter = new ViewPagerAdapter(mWelcomeResponse.getWelcomePicList(), context);
        welcomePicViewPager.setAdapter(mViewPagerAdapter);
    }
}
