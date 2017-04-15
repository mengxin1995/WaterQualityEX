package com.tom.waterqualityex.home;

import android.content.Context;
import android.support.v4.view.ViewPager;

import com.tom.waterqualityex.BasePresenter;
import com.tom.waterqualityex.BaseView;

/**
 * Created by mengxin on 17-3-26.
 */

public interface HomeContract{
    interface Presenter extends BasePresenter{
        void setHomeViewPagerAdapter(ViewPager mHomeViewPager, Context mContext);

        void setProjectItem();

        void setIntroduceItem();
    }

    interface View extends BaseView<Presenter>{

    }
}
