package com.tom.waterqualityex.welcome;

import android.content.Context;
import android.support.v4.view.ViewPager;

import com.tom.waterqualityex.BasePresenter;
import com.tom.waterqualityex.BaseView;

/**
 * Created by mengxin on 17-3-16.
 */

public interface WelcomeContract {
    interface Presenter extends BasePresenter{
        void setWelcomeViewPagerAdapter(ViewPager welcomePicViewPager, Context context);
    }

    interface View extends BaseView<Presenter>{

    }
}
