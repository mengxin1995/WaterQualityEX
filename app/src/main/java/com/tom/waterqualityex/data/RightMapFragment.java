package com.tom.waterqualityex.data;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MapViewLayoutParams;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.tom.waterqualityex.R;

/**
 * Created by mengxin on 17-4-8.
 */

public class RightMapFragment extends Fragment {

    private View mView;
    private MapView mMapView;
    private BaiduMap baiduMap;
    private View pop;

    private LatLng mZafu_donghu = new LatLng(30.2609970000,119.7355840000);
    private DataActivity mActivity;
    private TextView tv_title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = (DataActivity) getActivity();
        mView = inflater.inflate(R.layout.fragment_right_map, container, false);
        initUI();
        return mView;
    }

    private void initUI() {
        mMapView = (MapView) mView.findViewById(R.id.bmapView);

        baiduMap = mMapView.getMap();

        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        baiduMap.setTrafficEnabled(false);

        //设置地图中心点为浙江农林大学
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(mZafu_donghu);
        baiduMap.animateMapStatus(mapStatusUpdate);
        mapStatusUpdate = MapStatusUpdateFactory.zoomTo(18);
        baiduMap.setMapStatus(mapStatusUpdate);

        initMarker();

        setOnClickMarker();
    }

    /**
     * 设置标志物的掉级时间
     */
    private void setOnClickMarker() {
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // 显示一个泡泡
                if (pop == null) {
                    pop = View.inflate(mActivity, R.layout.pop, null);
                    tv_title = (TextView) pop.findViewById(R.id.tv_title);
                    mMapView.addView(pop, createLayoutParams(marker.getPosition()));
                } else {
                    mMapView.updateViewLayout(pop, createLayoutParams(marker.getPosition()));
                }
                tv_title.setText(marker.getTitle());
                return true;
            }
        });
    }

    /**
     * 初始化标志物
     */
    private void initMarker() {
        MarkerOptions options = new MarkerOptions();
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.mipmap.icon_eat);
        options.position(mZafu_donghu)      // 位置
                .title("农大站点")      // title
                .icon(icon)         // 图标
                .draggable(false);   // 设置图标可以拖动
        baiduMap.addOverlay(options);
    }

    /**
     * 创建一个布局参数
     * @param position
     * @return
     */
    private MapViewLayoutParams createLayoutParams(LatLng position) {
        MapViewLayoutParams.Builder buidler = new MapViewLayoutParams.Builder();
        buidler.layoutMode(MapViewLayoutParams.ELayoutMode.mapMode);    // 指定坐标类型为经纬度
        buidler.position(position);     // 设置标志的位置
        buidler.yOffset(-25);           // 设置View往上偏移
        MapViewLayoutParams params = buidler.build();
        return params;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
