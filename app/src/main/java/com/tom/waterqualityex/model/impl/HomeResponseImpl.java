package com.tom.waterqualityex.model.impl;

import com.tom.waterqualityex.model.HomeResponse;
import com.tom.waterqualityex.model.local.HomeLocalResponse;

import java.util.ArrayList;

/**
 * Created by mengxin on 17-3-26.
 */

public class HomeResponseImpl implements HomeResponse {

    private final HomeLocalResponse mHomeLocalResponse;

    public HomeResponseImpl(HomeLocalResponse mHomeLocalResponse) {
        this.mHomeLocalResponse = mHomeLocalResponse;
    }

    @Override
    public ArrayList<String> getHomeContent() {
        return mHomeLocalResponse.getHomeContent();
    }
}
