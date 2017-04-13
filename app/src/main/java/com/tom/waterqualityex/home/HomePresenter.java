package com.tom.waterqualityex.home;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;

import com.tom.waterqualityex.adapter.HomeViewPagerAdapter;
import com.tom.waterqualityex.model.HomeResponse;

import java.util.ArrayList;

/**
 * Created by mengxin on 17-3-26.
 */

public class HomePresenter implements HomeContract.Presenter {
    private static final int AUTO_PLAY = 1;
    private HomeContract.View mView;
    private HomeResponse mHomeResponse;
    private ArrayList<String> mHomeContent;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AUTO_PLAY:
                    int currentItem = mHomeViewPager.getCurrentItem();
                    currentItem++;
                    if (currentItem > mHomeContent.size() - 2) {
                        currentItem = 0;
                    }
                    mHomeViewPager.setCurrentItem(currentItem, false);
                    mHandler.sendEmptyMessageDelayed(AUTO_PLAY, 3000);
                    break;
                default:
                    break;
            }
        }
    };
    private ViewPager mHomeViewPager;

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

    @Override
    public void setHomeViewPagerAdapter(ViewPager mHomeViewPager, Context mContext) {
        this.mHomeViewPager = mHomeViewPager;
        mHomeContent = mHomeResponse.getHomeContent();
        mHomeViewPager.setAdapter(new HomeViewPagerAdapter(mHomeContent, mContext));
        setWelcomeViewPagerAutoSlide();
    }

    @Override
    public void setProjectItem() {
        mHomeViewPager.setCurrentItem(mHomeContent.size() - 1, false);
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void setIntroduceItem() {
        mHomeViewPager.setCurrentItem(0, false);
        mHandler.sendEmptyMessageDelayed(AUTO_PLAY, 3000);
    }


    /**
     * 每隔三秒切换内容
     */
    private void setWelcomeViewPagerAutoSlide() {
        mHandler.sendEmptyMessageDelayed(AUTO_PLAY, 3000);
        mHomeViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mHandler.removeCallbacksAndMessages(null);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        mHandler.sendEmptyMessageDelayed(AUTO_PLAY, 3000);
                        break;
                    case MotionEvent.ACTION_UP:
                        mHandler.sendEmptyMessageDelayed(AUTO_PLAY, 3000);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }
}
