package com.tom.waterqualityex.welcome;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.rd.PageIndicatorView;
import com.rd.animation.AnimationType;
import com.tom.waterqualityex.adapter.ViewPagerAdapter;
import com.tom.waterqualityex.model.WelcomeResponse;

import java.util.ArrayList;

/**
 * Created by mengxin on 17-3-16.
 */

public class WelcomePresenter implements WelcomeContract.Presenter {

    private static final String TAG = "WelcomePresenter";
    private static final int AUTO_PLAY = 1;
    private WelcomeContract.View mView;
    private WelcomeResponse mWelcomeResponse;
    private ViewPagerAdapter mViewPagerAdapter;
    private ViewPager mWelcomePicViewPager;
    private ArrayList<Integer> mWelcomePicList;
    private Context mContext;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AUTO_PLAY:
                    int currentItem = mWelcomePicViewPager.getCurrentItem();
                    currentItem++;
                    if (currentItem > mWelcomePicList.size() - 1) {
                        currentItem = 0;
                    }
                    mWelcomePicViewPager.setCurrentItem(currentItem, false);
                    mHandler.sendEmptyMessageDelayed(AUTO_PLAY, 3000);
                    break;
                default:
                    break;
            }
        }
    };

    public WelcomePresenter(WelcomeContract.View view, WelcomeResponse welcomeResponse) {
        this.mView = view;
        this.mWelcomeResponse = welcomeResponse;
    }

    @Override
    public void start() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void setWelcomeViewPagerAdapter(ViewPager welcomePicViewPager, PageIndicatorView pageIndicatorView, Context context) {
        mWelcomePicViewPager = welcomePicViewPager;
        mWelcomePicList = mWelcomeResponse.getWelcomePicList();
        mViewPagerAdapter = new ViewPagerAdapter(mWelcomePicList, context);
        mWelcomePicViewPager.setAdapter(mViewPagerAdapter);
        customizeIndicator(pageIndicatorView);
        setWelcomeViewPagerAutoSlide();
    }


    /**
     * 定制指示器
     * @param pageIndicatorView
     */
    private void customizeIndicator(PageIndicatorView pageIndicatorView) {
        pageIndicatorView.setViewPager(mWelcomePicViewPager);
        pageIndicatorView.setAnimationDuration(3000);
        pageIndicatorView.setAnimationType(AnimationType.DROP);
        pageIndicatorView.setInteractiveAnimation(true);
    }

    /**
     * 每隔三秒钟切换图片
     */
    private void setWelcomeViewPagerAutoSlide() {
        mHandler.sendEmptyMessageDelayed(AUTO_PLAY, 3000);
        mWelcomePicViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d(TAG, "onTouch: " + "ACTION_DOWN");
                        mHandler.removeCallbacksAndMessages(null);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        break;
                    case MotionEvent.ACTION_UP:
                        mHandler.removeCallbacksAndMessages(null);
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
