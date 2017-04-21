package com.tom.waterqualityex.simple;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import com.tom.waterqualityex.R;
import com.tom.waterqualityex.adapter.ViewPagerAdapter;

import java.util.ArrayList;

public class WushuiActivity extends AppCompatActivity {

    private static final int AUTO_PLAY = 1;
    private ViewPager vp_wushui;
    private ArrayList<Integer> picList = new ArrayList<Integer>();
    private ViewPagerAdapter viewPagerAdapter;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AUTO_PLAY:
                    int currentItem = vp_wushui.getCurrentItem();
                    currentItem++;
                    if (currentItem > picList.size() - 1) {
                        currentItem = 0;
                    }
                    vp_wushui.setCurrentItem(currentItem, false);
                    mHandler.sendEmptyMessageDelayed(AUTO_PLAY, 3000);
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wushui);

        ImageButton back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initViewPager();
    }

    private void initViewPager() {
        vp_wushui = (ViewPager) findViewById(R.id.vp_wushui);
        initData();
        viewPagerAdapter = new ViewPagerAdapter(picList, this);
        vp_wushui.setAdapter(viewPagerAdapter);

        setWelcomeViewPagerAutoSlide();
    }

    private void initData() {
        picList.add(R.mipmap.p1);
        picList.add(R.mipmap.p2);
        picList.add(R.mipmap.p3);
        picList.add(R.mipmap.p4);
        picList.add(R.mipmap.p5);
        picList.add(R.mipmap.p6);
        picList.add(R.mipmap.p7);
        picList.add(R.mipmap.p8);
    }

    /**
     * 每隔三秒钟切换图片
     */
    private void setWelcomeViewPagerAutoSlide() {
        mHandler.sendEmptyMessageDelayed(AUTO_PLAY, 3000);
        vp_wushui.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
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
