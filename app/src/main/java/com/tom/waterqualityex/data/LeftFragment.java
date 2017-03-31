package com.tom.waterqualityex.data;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tom.waterqualityex.R;


public class LeftFragment extends Fragment {

    private View mLeftFragmentView;

    public LeftFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLeftFragmentView = inflater.inflate(R.layout.fragment_left, container, false);
        initView();
        return mLeftFragmentView;
    }

    private void initView() {
        ImageButton backButton = (ImageButton)mLeftFragmentView.findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        TextView item1 = (TextView)mLeftFragmentView.findViewById(R.id.item1);
        final TextView item1_select = (TextView) mLeftFragmentView.findViewById(R.id.item1_select);
        final LinearLayout item1_next = (LinearLayout) mLeftFragmentView.findViewById(R.id.item1_next);
        TextView item2 = (TextView)mLeftFragmentView.findViewById(R.id.item2);
        final TextView item2_select = (TextView) mLeftFragmentView.findViewById(R.id.item2_select);
        TextView item3 = (TextView)mLeftFragmentView.findViewById(R.id.item3);
        final TextView item3_select = (TextView) mLeftFragmentView.findViewById(R.id.item3_select);
        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //展开next
                item1_next.setVisibility(View.VISIBLE);
                //设置选中色
                item1_select.setBackgroundColor(getResources().getColor(R.color.holo_orange_dark));
                //回恢复其他颜色
                item2_select.setBackgroundColor(getResources().getColor(R.color.fragment_left_background));
                item3_select.setBackgroundColor(getResources().getColor(R.color.fragment_left_background));
            }
        });

        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //展开next
                item1_next.setVisibility(View.GONE);
                //设置选中色
                item2_select.setBackgroundColor(getResources().getColor(R.color.holo_orange_dark));
                //回恢复其他颜色
                item1_select.setBackgroundColor(getResources().getColor(R.color.fragment_left_background));
                item3_select.setBackgroundColor(getResources().getColor(R.color.fragment_left_background));
            }
        });

        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //展开next
                item1_next.setVisibility(View.GONE);
                //设置选中色
                item3_select.setBackgroundColor(getResources().getColor(R.color.holo_orange_dark));
                //回恢复其他颜色
                item1_select.setBackgroundColor(getResources().getColor(R.color.fragment_left_background));
                item2_select.setBackgroundColor(getResources().getColor(R.color.fragment_left_background));
            }
        });
    }
}
