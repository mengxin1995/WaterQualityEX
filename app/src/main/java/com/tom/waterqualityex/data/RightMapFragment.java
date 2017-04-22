package com.tom.waterqualityex.data;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.tom.waterqualityex.global.GlobalConstants;
import com.tom.waterqualityex.utils.SpUtil;

/**
 * Created by mengxin on 17-4-8.
 */

public class RightMapFragment extends Fragment {

    private static final String TAG = "RightMapFragment";
    private View mView;
    private MapView mMapView;
    private BaiduMap baiduMap;
    private View pop;

    private LatLng mZafu_donghu = new LatLng(30.2609970000,119.7355840000);
    private DataActivity mActivity;
    private TextView tv_title;
    private TextView shuiwen;
    private TextView diandaolv;
    private TextView ph;
    private TextView rongjieyang;
    private TextView andan;
    private TextView zonglin;
    private TextView zhuodu;

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

//        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
        baiduMap.setTrafficEnabled(false);

        //设置地图中心点为浙江农林大学
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(mZafu_donghu);
        baiduMap.animateMapStatus(mapStatusUpdate);
        mapStatusUpdate = MapStatusUpdateFactory.zoomTo(18);
        baiduMap.setMapStatus(mapStatusUpdate);

        initMarker();

        setOnClickMarker();

        setOnMapTouchListener();
    }

    private void setOnMapTouchListener() {
        baiduMap.setOnMapTouchListener(new BaiduMap.OnMapTouchListener() {
            /**
             * 当用户触摸地图时回调函数
             * @param event 触摸事件
             */
            public void onTouch(MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Log.d(TAG, "onTouchEvent: " + "down");
                        mActivity.sendCancleMessages();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d(TAG, "onTouchEvent: " + "move");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d(TAG, "onTouchEvent: " + "up");
                        mActivity.sendMessages();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        break;
                    default:
                        break;
                }
            }
        });
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
                    shuiwen = (TextView) pop.findViewById(R.id.shuiwenzhi);
                    diandaolv = (TextView) pop.findViewById(R.id.diaodaolvzhi);
                    ph = (TextView) pop.findViewById(R.id.phzhi);
                    rongjieyang = (TextView) pop.findViewById(R.id.rongjieyangzhi);
                    andan = (TextView) pop.findViewById(R.id.andanzhi);
                    zonglin = (TextView) pop.findViewById(R.id.zonglinzhi);
                    zhuodu = (TextView) pop.findViewById(R.id.zhuoduzhi);
                    mMapView.addView(pop, createLayoutParams(marker.getPosition()));
                } else {
                    mMapView.updateViewLayout(pop, createLayoutParams(marker.getPosition()));
                }
                tv_title.setText(marker.getTitle());
                shuiwen.setText(SpUtil.getString(getActivity(), GlobalConstants.SHUI_WEN + "℃", "16℃"));
                diandaolv.setText(SpUtil.getString(getActivity(), GlobalConstants.DIAN_DAO_LV, ""));
                ph.setText(SpUtil.getString(getActivity(), GlobalConstants.PH, "8"));
                rongjieyang.setText(SpUtil.getString(getActivity(), GlobalConstants.RONG_JIE_YANG, ""));
                andan.setText(SpUtil.getString(getActivity(), GlobalConstants.AN_DAN, ""));
                zonglin.setText(SpUtil.getString(getActivity(), GlobalConstants.ZONG_LIN, ""));
                zhuodu.setText(SpUtil.getString(getActivity(), GlobalConstants.ZHUO_DU, ""));
                return true;
            }
        });
    }

    /**
     * 初始化标志物
     */
    private void initMarker() {
        MarkerOptions options = new MarkerOptions();
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.mipmap.location_icon);
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
