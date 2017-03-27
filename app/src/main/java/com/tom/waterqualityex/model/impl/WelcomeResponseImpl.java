package com.tom.waterqualityex.model.impl;

import com.tom.waterqualityex.model.WelcomeResponse;
import com.tom.waterqualityex.model.local.WelcomePicLocalResponse;

import java.util.ArrayList;

/**
 * Created by mengxin on 17-3-16.
 */

public class WelcomeResponseImpl implements WelcomeResponse {
    private static final String TAG = "WelcomeResponseImpl";
    private WelcomePicLocalResponse mWelcomePicLocalResponse;

    public WelcomeResponseImpl(WelcomePicLocalResponse welcomePicLocalResponse) {
        this.mWelcomePicLocalResponse = welcomePicLocalResponse;
    }

    @Override
    public ArrayList<Integer> getWelcomePicList() {
        return mWelcomePicLocalResponse.getWelcomePicList();
    }
}
