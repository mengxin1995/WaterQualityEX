package com.tom.waterqualityex.model.local;

import com.tom.waterqualityex.R;
import com.tom.waterqualityex.model.WelcomeResponse;

import java.util.ArrayList;

/**
 * Created by mengxin on 17-3-16.
 */

public class WelcomePicLocalResponse implements WelcomeResponse {

    @Override
    public ArrayList<Integer> getWelcomePicList() {
        ArrayList<Integer> WelcomePicList = new ArrayList<>();
        WelcomePicList.add(R.mipmap.welcome1);
        WelcomePicList.add(R.mipmap.welcome2);
        WelcomePicList.add(R.mipmap.welcome3);
        WelcomePicList.add(R.mipmap.welcome4);
        return WelcomePicList;
    }
}
