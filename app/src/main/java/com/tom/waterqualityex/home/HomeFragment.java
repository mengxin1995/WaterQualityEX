package com.tom.waterqualityex.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tom.waterqualityex.R;
import com.tom.waterqualityex.model.impl.HomeResponseImpl;
import com.tom.waterqualityex.model.local.HomeLocalResponse;

public class HomeFragment extends Fragment implements HomeContract.View{

    private HomeContract.Presenter mPersenter;

    public HomeFragment() {
        mPersenter = new HomePresenter(this, new HomeResponseImpl(new HomeLocalResponse()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {

    }
}
