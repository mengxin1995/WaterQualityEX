package com.tom.waterqualityex.data;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.tom.waterqualityex.R;
import com.tom.waterqualityex.domain.gson.Weather;
import com.tom.waterqualityex.global.GlobalConstants;
import com.tom.waterqualityex.http.entity.WaterData;
import com.tom.waterqualityex.http.http.HttpMethods;
import com.tom.waterqualityex.http.subscribers.SimpleHttpSubscriber;
import com.tom.waterqualityex.http.subscribers.SubscriberOnNextListener;
import com.tom.waterqualityex.utils.Engine;
import com.tom.waterqualityex.utils.HttpUtil;
import com.tom.waterqualityex.utils.SpUtil;
import com.tom.waterqualityex.utils.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class RightFragment extends Fragment {

    private static final int DELAYED_MESSAGE = 1;
    private String mProvince;
    private String mCity;
    private String mCounty;
    private View mRightView;
    private TextView aqi_zhishu;
    private TextView pm_zhishu;
    private TextView shushidu;
    private TextView xichezhishu;
    private TextView yunxingjianyi;
    private LineChart mChart;
    private TextView ph;
    private TextView shuiwen;
    private TextView diandaolv;
    private TextView rongjieyang;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case DELAYED_MESSAGE:

                    //获取一次天气信息
                    getLocalWeatherInfo();
                    //获取一次图表信息
                    fillTheChart();
                    //获取一次常规参数信息
                    fillCommonInfo();
                    mHandler.sendEmptyMessageDelayed(DELAYED_MESSAGE, DelayedTime);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private long DelayedTime = 1000 * 60 * 5;
    private TextView tianqi;
    private TextView wendu;

    public RightFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRightView = inflater.inflate(R.layout.fragment_right, container, false);
        initUI();
        //获取一次天气信息
        getLocalWeatherInfo();
        //获取一次图表信息
        fillTheChart();
        //获取一次常规参数信息
        fillCommonInfo();

        mHandler.sendEmptyMessageDelayed(DELAYED_MESSAGE, DelayedTime);
        return mRightView;
    }

    private void fillCommonInfo() {
        HttpMethods.getInstance().getWaterData(new SimpleHttpSubscriber<List<WaterData>>(new SubscriberOnNextListener<List<WaterData>>() {
            @Override
            public void onNext(List<WaterData> waterDatas) {
                WaterData waterData = waterDatas.get(waterDatas.size() - 1);
                shuiwen.setText(waterData.getShuiWen() + "");
                diandaolv.setText(waterData.getDianDaoLv() + "");
                ph.setText(waterData.getPh() + "");
                rongjieyang.setText(waterData.getRongJieYang() + "");
            }
        }), "D01", "2017-04-12");
    }


    /**
     * 填充图表
     */
    private void fillTheChart() {
        HttpMethods.getInstance().getWaterData(new SimpleHttpSubscriber<List<WaterData>>(new SubscriberOnNextListener<List<WaterData>>() {
            @Override
            public void onNext(List<WaterData> waterDatas) {
                System.out.println("123123" + waterDatas);
                List<Entry> entries = new ArrayList<Entry>();
                for (WaterData w :
                        waterDatas) {
                    String t = w.getTime();
                    String[] sp = t.split(" ");

                    String[] split = sp[1].split(":");
                    float time = Float.parseFloat(split[0]) * 60 + Float.parseFloat(split[1]);
                    float anDan = w.getAnDan();
                    entries.add(new Entry(time, anDan));
                }

                LineDataSet dataSet = new LineDataSet(entries, "非常规参数--氨氮"); // add entries to dataset
                dataSet.setColor(Color.BLUE);
                dataSet.setValueTextColor(Color.WHITE); // styling, ...
                LineData lineData = new LineData(dataSet);
                mChart.setData(lineData);
                //设置描述信息
                Description mChartDescription = new Description();
//                mChartDescription.setText("非常规参数--氨氮");
//                mChartDescription.setTextSize(15);
//                mChartDescription.setTextColor(mActivity.getResources().getColor(R.color.textColor));
                mChartDescription.setEnabled(false);
                mChart.setDescription(mChartDescription);


                //设置x轴坐标
                XAxis xAxis = mChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setDrawAxisLine(true);
                xAxis.setDrawGridLines(true);
                xAxis.setDrawAxisLine(true);
                xAxis.setEnabled(true);
                xAxis.setAxisLineWidth(2f);
                xAxis.setTextColor(getActivity().getResources().getColor(R.color.textColor));
                xAxis.setLabelCount(7);
                // the labels that should be drawn on the XAxis
                final String[] quarters = new String[] { "0h", "1h", "2h", "3h",
                        "4h", "5h", "6h", "7h", "8h", "9h", "10h", "11h", "12h", "13h",
                        "14h", "15h", "16h", "17h", "18h",
                        "19h", "20h", "21h", "22h", "23h", "24h"};
                IAxisValueFormatter formatter = new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return quarters[(int) value / 60];
                    }
                };
                xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
                xAxis.setValueFormatter(formatter);

                //设置Y轴属性
                YAxis leftAxis = mChart.getAxisLeft();
                leftAxis.setAxisLineWidth(2f);
                leftAxis.setTextColor(getActivity().getResources().getColor(R.color.textColor));
                YAxis rightAxis = mChart.getAxisRight();
                rightAxis.setAxisLineWidth(2f);
                rightAxis.setTextColor(getActivity().getResources().getColor(R.color.textColor));

                mChart.invalidate(); // refresh
            }
        }), "D01", "2017-04-12");
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

        ph = (TextView) mRightView.findViewById(R.id.phzhi);
        shuiwen = (TextView) mRightView.findViewById(R.id.shuiwenzhi);
        diandaolv = (TextView) mRightView.findViewById(R.id.diandaolvzhi);
        rongjieyang = (TextView) mRightView.findViewById(R.id.rongjieyangzhi);
        mChart = (LineChart) mRightView.findViewById(R.id.chart);

        tianqi = (TextView) mRightView.findViewById(R.id.tianqi);
        wendu = (TextView) mRightView.findViewById(R.id.wendu);
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

        wendu.setText(weather.now.temperature + "℃");
        tianqi.setText(weather.now.more.info);
    }
}
