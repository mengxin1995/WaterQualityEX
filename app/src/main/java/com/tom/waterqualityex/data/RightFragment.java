package com.tom.waterqualityex.data;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tom.waterqualityex.R;
import com.tom.waterqualityex.domain.gson.Weather;
import com.tom.waterqualityex.global.GlobalConstants;
import com.tom.waterqualityex.utils.Engine;
import com.tom.waterqualityex.utils.HttpUtil;
import com.tom.waterqualityex.utils.SpUtil;
import com.tom.waterqualityex.utils.ToastUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class RightFragment extends Fragment {

    private String mProvince;
    private String mCity;
    private String mCounty;
    private View mRightView;
    private TextView aqi_zhishu;
    private TextView pm_zhishu;
    private TextView shushidu;
    private TextView xichezhishu;
    private TextView yunxingjianyi;

    public RightFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRightView = inflater.inflate(R.layout.fragment_right, container, false);
        initUI();
        getLocalWeatherInfo();
        return mRightView;
    }

    private void getLocalWeatherInfo() {
        //获取当地天气信息
        String weatherString = SpUtil.getString(getActivity(), GlobalConstants.WEATHER, null);
        if(weatherString != null){
            //有缓存直接解析天气数据
            Weather weather = Engine.handleWeatherResponse(weatherString);
            showWeatherInfo(weather);
        }else{
            //获取当地天气信息
            getLocalWeather();
        }

    }

    private void initUI() {
        aqi_zhishu = (TextView) mRightView.findViewById(R.id.aqi_zhishu);
        pm_zhishu = (TextView) mRightView.findViewById(R.id.pm_zhishu);
        shushidu = (TextView) mRightView.findViewById(R.id.shushidumiaoshu);
        xichezhishu = (TextView) mRightView.findViewById(R.id.xichezhishumiaoshu);
        yunxingjianyi = (TextView) mRightView.findViewById(R.id.yunxingjianyimiaoshu);
    }

    /**
     * 获取当地天气信息
     */
    private void getLocalWeather() {
        mProvince = SpUtil.getString(getActivity(), GlobalConstants.PROVINCE, "浙江省");
        mCity = SpUtil.getString(getActivity(), GlobalConstants.CITY, "杭州市");
        mCounty = SpUtil.getString(getActivity(), GlobalConstants.DISTRICT, "临安");
        Intent intent = new Intent(getActivity(), AutoUpdateService.class);
        getActivity().startService(intent);
        Engine.findWeatherURL(mProvince, mCity, mCounty, new Engine.WeatherIdFinded() {
            @Override
            public void success(String weatherId) {
                String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=a615da100d8e4631a3e80367a5c046f8";
                HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.show(getActivity(), "获取天气信息失败");
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String responseText = response.body().string();
                        final Weather weather = Engine.handleWeatherResponse(responseText);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(weather != null && "ok".equals(weather.status)){
                                    SpUtil.setString(getActivity(), GlobalConstants.WEATHER, responseText);
                                    showWeatherInfo(weather);
                                }else{
                                    ToastUtil.show(getActivity(), "获取天气信息失败");
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    /**
     * 处理接受到的天气数据
     * @param weather
     */
    private void showWeatherInfo(Weather weather) {
        aqi_zhishu.setText(weather.aqi.city.aqi);
        pm_zhishu.setText(weather.aqi.city.pm25);

        //生活建议模块
        shushidu.setText(weather.suggestion.comfort.info);
        xichezhishu.setText(weather.suggestion.carWash.info);
        yunxingjianyi.setText(weather.suggestion.sport.info);
    }
}
