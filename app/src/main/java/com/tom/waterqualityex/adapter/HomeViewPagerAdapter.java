package com.tom.waterqualityex.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tom.waterqualityex.R;

import java.util.ArrayList;

/**
 * Created by mengxin on 17-3-16.
 */

public class HomeViewPagerAdapter extends PagerAdapter {

    private ArrayList<String> mList;
    private Context mContext;
    private final LayoutInflater inflater;
    private TextView mHomeContentID;
    private TextView mHomeContent;

    public HomeViewPagerAdapter(ArrayList<String> mList, Context context) {
        this.mList = mList;
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = null;
        if (position == mList.size() - 1)
            view = inflater.inflate(R.layout.viewpager_home_content_ex, null);
        else
            view = inflater.inflate(R.layout.viewpager_home_content, null);
        fillTheItem(view, position);
        container.addView(view);
        return view;
    }

    private void fillTheItem(View view, int position) {
        initUI(view);
        if (position == mList.size() - 1) {
            mHomeContent.setText(mList.get(position));
        } else {
            mHomeContentID.setText("0" + (position + 1));
            mHomeContent.setText(mList.get(position));
        }
    }

    private void initUI(View view) {
        mHomeContentID = (TextView) view.findViewById(R.id.home_content_id);
        mHomeContent = (TextView) view.findViewById(R.id.home_content);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
