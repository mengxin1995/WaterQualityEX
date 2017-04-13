package com.tom.waterqualityex.home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.tom.waterqualityex.R;
import com.tom.waterqualityex.data.DataActivity;
import com.tom.waterqualityex.model.impl.HomeResponseImpl;
import com.tom.waterqualityex.model.local.HomeLocalResponse;
import com.tom.waterqualityex.simple.HelpingActivity;
import com.tom.waterqualityex.simple.WushuiActivity;

public class HomeFragment extends Fragment implements HomeContract.View{

    private HomeContract.Presenter mPersenter;
    private View mHomeView;
    private ViewPager mHomeViewPager;
    private Context mContext;
    private ImageButton dataButton;
    private ImageButton project;
    private ImageButton introduce;
    private ImageButton helping;
    private ImageButton wushuigongzhi;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPersenter = new HomePresenter(this, new HomeResponseImpl(new HomeLocalResponse()));
        mContext = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mHomeView = inflater.inflate(R.layout.fragment_home, container, false);
        initUI();
        return mHomeView;
    }

    private void initUI() {
        mHomeViewPager = (ViewPager) mHomeView.findViewById(R.id.vp_home);
        mPersenter.setHomeViewPagerAdapter(mHomeViewPager, mContext);
        dataButton = (ImageButton) mHomeView.findViewById(R.id.data);
        dataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DataActivity.class));
            }
        });

        project = (ImageButton) mHomeView.findViewById(R.id.project);
        project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPersenter.setProjectItem();
            }
        });

        introduce = (ImageButton) mHomeView.findViewById(R.id.introduce);
        introduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPersenter.setIntroduceItem();
            }
        });

        helping = (ImageButton) mHomeView.findViewById(R.id.helping);
        helping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, HelpingActivity.class));
            }
        });

        wushuigongzhi = (ImageButton) mHomeView.findViewById(R.id.wushuigongzhi);
        wushuigongzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, WushuiActivity.class));
            }
        });
    }


    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPersenter = presenter;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPersenter.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPersenter.destroy();
    }
}
