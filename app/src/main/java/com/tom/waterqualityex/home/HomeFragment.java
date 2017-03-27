package com.tom.waterqualityex.home;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tom.waterqualityex.R;
import com.tom.waterqualityex.model.impl.HomeResponseImpl;
import com.tom.waterqualityex.model.local.HomeLocalResponse;

public class HomeFragment extends Fragment implements HomeContract.View{

    private HomeContract.Presenter mPersenter;
    private View mHomeView;
    private ViewPager mHomeViewPager;
    private Context mContext;

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
