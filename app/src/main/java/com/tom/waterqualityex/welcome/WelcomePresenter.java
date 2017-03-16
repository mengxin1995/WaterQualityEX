package com.tom.waterqualityex.welcome;

import com.tom.waterqualityex.model.WelcomeResponse;

import java.util.ArrayList;

/**
 * Created by mengxin on 17-3-16.
 */

public class WelcomePresenter implements WelcomeContract.Presenter {

    WelcomeContract.View mView;
    WelcomeResponse mWelcomeResponse;

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
    public ArrayList<Integer> getWelcomePicList() {
        return mWelcomeResponse.getWelcomePicList();
    }
}
