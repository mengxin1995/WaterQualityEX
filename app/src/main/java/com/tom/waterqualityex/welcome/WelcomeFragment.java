package com.tom.waterqualityex.welcome;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tom.waterqualityex.R;
import com.tom.waterqualityex.adapter.ViewPagerAdapter;
import com.tom.waterqualityex.model.WelcomeResponseImpl;
import com.tom.waterqualityex.model.local.WelcomePicLocalResponse;


public class WelcomeFragment extends Fragment implements WelcomeContract.View {

    private WelcomeContract.Presenter mPresenter;
    private ViewPager mWelcomePicViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new WelcomePresenter(this,
                new WelcomeResponseImpl(
                new WelcomePicLocalResponse()
        ));
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        mWelcomePicViewPager = (ViewPager) view.findViewById(R.id.vp_welcome);

        mViewPagerAdapter = new ViewPagerAdapter(mPresenter.getWelcomePicList(), mContext);
        mWelcomePicViewPager.setAdapter(mViewPagerAdapter);
    }

    @Override
    public void setPresenter(WelcomeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mPresenter != null){
            mPresenter.start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter != null){
            mPresenter.destroy();
        }
    }
}
