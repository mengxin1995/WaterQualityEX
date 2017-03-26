package com.tom.waterqualityex.home;

import com.tom.waterqualityex.model.HomeResponse;

/**
 * Created by mengxin on 17-3-26.
 */

public class HomePresenter implements HomeContract.Presenter {
    private HomeContract.View mView;
    private HomeResponse mHomeResponse;

    public HomePresenter(HomeContract.View mView, HomeResponse mHomeResponse) {
        this.mView = mView;
        this.mHomeResponse = mHomeResponse;
    }

    @Override
    public void start() {

    }

    @Override
    public void destroy() {

    }
}
