package com.tom.waterqualityex.welcome;

import com.tom.waterqualityex.BasePresenter;
import com.tom.waterqualityex.BaseView;

import java.util.ArrayList;

/**
 * Created by mengxin on 17-3-16.
 */

public interface WelcomeContract {
    interface Presenter extends BasePresenter{
        ArrayList<Integer> getWelcomePicList();
    }

    interface View extends BaseView<Presenter>{

    }
}
