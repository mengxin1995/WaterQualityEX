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

import com.blankj.utilcode.utils.TimeUtils;
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


public class RightFragment extends Fragment{

    public static final String DEFAULT_PATTERN = "yyyy-MM-dd";
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
    private String now_temp = GlobalConstants.AN_DAN;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DELAYED_MESSAGE:
                    //获取一次天气信息
                    getLocalWeatherInfo();
                    //获取一次图表信息
                    fillTheChart(now_temp);
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
    private TextView andan;
    private TextView zhuodu;
    private TextView zonglin;

    private TextView pht;
    private TextView andant;
    private TextView zhuodut;
    private TextView zonglint;
    private TextView shuiwent;
    private TextView diandaolvt;
    private TextView rongjieyangt;

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
        fillTheChart(now_temp);
        //获取一次常规参数信息
        fillCommonInfo();

        mHandler.sendEmptyMessageDelayed(DELAYED_MESSAGE, DelayedTime);
        return mRightView;
    }

    private void fillCommonInfo() {
        HttpMethods.getInstance().getWaterData(new SimpleHttpSubscriber<List<WaterData>>(new SubscriberOnNextListener<List<WaterData>>() {
            @Override
            public void onNext(List<WaterData> waterDatas) {
                if (waterDatas.size() != 0) {
                    WaterData waterData = waterDatas.get(waterDatas.size() - 1);
                    fillIntoSP(waterData);
                    shuiwen.setText(waterData.getShuiWen() + "℃");
                    diandaolv.setText(waterData.getDianDaoLv() + "");
                    ph.setText(waterData.getPh() + "");
                    rongjieyang.setText(waterData.getRongJieYang() + "");
                    andan.setText(waterData.getAnDan() + "");
                    zonglin.setText(waterData.getZonglin() + "");
                    zhuodu.setText(waterData.getZhuodu() + "");
                }
            }
        }), "D01", TimeUtils.getNowTimeString(DEFAULT_PATTERN));
    }

    private void fillIntoSP(WaterData waterData) {
        SpUtil.setString(getActivity(), GlobalConstants.SHUI_WEN, waterData.getShuiWen() + "");
        SpUtil.setString(getActivity(), GlobalConstants.DIAN_DAO_LV, waterData.getDianDaoLv() + "");
        SpUtil.setString(getActivity(), GlobalConstants.PH, waterData.getPh() + "");
        SpUtil.setString(getActivity(), GlobalConstants.RONG_JIE_YANG, waterData.getRongJieYang() + "");
        SpUtil.setString(getActivity(), GlobalConstants.AN_DAN, waterData.getAnDan() + "");
        SpUtil.setString(getActivity(), GlobalConstants.ZONG_LIN, waterData.getZonglin() + "");
        SpUtil.setString(getActivity(), GlobalConstants.ZHUO_DU, waterData.getZhuodu() + "");
    }


    /**
     * 填充图表
     *
     * @param temp
     */
    private void fillTheChart(final String temp) {
        HttpMethods.getInstance().getWaterData(new SimpleHttpSubscriber<List<WaterData>>(new SubscriberOnNextListener<List<WaterData>>() {
            @Override
            public void onNext(List<WaterData> waterDatas) {
                if (waterDatas.size() != 0) {
                    System.out.println("123123" + waterDatas);
                    List<Entry> entries = new ArrayList<Entry>();
                    for (WaterData w :
                            waterDatas) {
                        String t = w.getTime();
                        String[] sp = t.split(" ");

                        String[] split = sp[1].split(":");
                        float time = Float.parseFloat(split[0]) * 60 + Float.parseFloat(split[1]);
                        float y = 0;
                        switch (temp) {
                            case GlobalConstants.AN_DAN:
                                y = w.getAnDan();
                                break;
                            case GlobalConstants.SHUI_WEN:
                                y = w.getShuiWen();
                                break;
                            case GlobalConstants.DIAN_DAO_LV:
                                y = w.getDianDaoLv();
                                break;
                            case GlobalConstants.PH:
                                y = w.getPh();
                                break;
                            case GlobalConstants.RONG_JIE_YANG:
                                y = w.getRongJieYang();
                                break;
                            case GlobalConstants.ZONG_LIN:
                                y = w.getZonglin();
                                break;
                            case GlobalConstants.ZHUO_DU:
                                y = w.getZhuodu();
                                break;
                            default:
                                break;
                        }
                        entries.add(new Entry(time, y));
                    }

                    LineDataSet dataSet = null;

                    switch (temp) {
                        case GlobalConstants.AN_DAN:
                            dataSet = new LineDataSet(entries, "非常规参数--氨氮"); // add entries to dataset
                            break;
                        case GlobalConstants.SHUI_WEN:
                            dataSet = new LineDataSet(entries, "常规参数--水温"); // add entries to dataset
                            break;
                        case GlobalConstants.DIAN_DAO_LV:
                            dataSet = new LineDataSet(entries, "常规参数--电导率"); // add entries to dataset
                            break;
                        case GlobalConstants.PH:
                            dataSet = new LineDataSet(entries, "常规参数--PH值"); // add entries to dataset
                            break;
                        case GlobalConstants.RONG_JIE_YANG:
                            dataSet = new LineDataSet(entries, "常规参数--溶解氧"); // add entries to dataset
                            break;
                        case GlobalConstants.ZONG_LIN:
                            dataSet = new LineDataSet(entries, "常规参数--总磷"); // add entries to dataset
                            break;
                        case GlobalConstants.ZHUO_DU:
                            dataSet = new LineDataSet(entries, "常规参数--浊度"); // add entries to dataset
                            break;
                        default:
                            break;
                    }
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
                    IAxisValueFormatter formatter = new IAxisValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            int h = (int) (value / 60);
                            int m = (int) (value % 60);
                            String mm;
                            if (m < 10) {
                                mm = "0" + m;
                            } else {
                                mm = m + "";
                            }
                            return h + ":" + mm;
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
            }
        }), "D01", TimeUtils.getNowTimeString(DEFAULT_PATTERN));
    }

    private void getLocalWeatherInfo() {
        //获取当地天气信息
        String weatherString = SpUtil.getString(getActivity(), GlobalConstants.WEATHER, null);
        if (weatherString != null) {
            //有缓存直接解析天气数据
            Weather weather = Engine.handleWeatherResponse(weatherString);
            showWeatherInfo(weather);
        } else {
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
        ph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTheChart(GlobalConstants.PH);
                now_temp = GlobalConstants.PH;
            }
        });
        shuiwen = (TextView) mRightView.findViewById(R.id.shuiwenzhi);
        shuiwen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTheChart(GlobalConstants.SHUI_WEN);
                now_temp = GlobalConstants.SHUI_WEN;
            }
        });
        diandaolv = (TextView) mRightView.findViewById(R.id.diandaolvzhi);
        diandaolv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTheChart(GlobalConstants.DIAN_DAO_LV);
                now_temp = GlobalConstants.DIAN_DAO_LV;
            }
        });
        rongjieyang = (TextView) mRightView.findViewById(R.id.rongjieyangzhi);
        rongjieyang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTheChart(GlobalConstants.RONG_JIE_YANG);
                now_temp = GlobalConstants.RONG_JIE_YANG;
            }
        });
        andan = (TextView) mRightView.findViewById(R.id.andanzhi);
        andan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTheChart(GlobalConstants.AN_DAN);
                now_temp = GlobalConstants.AN_DAN;
            }
        });
        zhuodu = (TextView) mRightView.findViewById(R.id.zhuoduzhi);
        zhuodu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTheChart(GlobalConstants.ZHUO_DU);
                now_temp = GlobalConstants.ZHUO_DU;
            }
        });
        zonglin = (TextView) mRightView.findViewById(R.id.zonglinzhi);
        zonglin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTheChart(GlobalConstants.ZONG_LIN);
                now_temp = GlobalConstants.ZONG_LIN;
            }
        });

        pht = (TextView) mRightView.findViewById(R.id.ph);
        pht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTheChart(GlobalConstants.PH);
                now_temp = GlobalConstants.PH;
            }
        });
        shuiwent = (TextView) mRightView.findViewById(R.id.shuiwen);
        shuiwent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTheChart(GlobalConstants.SHUI_WEN);
                now_temp = GlobalConstants.SHUI_WEN;
            }
        });
        diandaolvt = (TextView) mRightView.findViewById(R.id.diandaolv);
        diandaolvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTheChart(GlobalConstants.DIAN_DAO_LV);
                now_temp = GlobalConstants.DIAN_DAO_LV;
            }
        });
        rongjieyangt = (TextView) mRightView.findViewById(R.id.rongjieyang);
        rongjieyangt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTheChart(GlobalConstants.RONG_JIE_YANG);
                now_temp = GlobalConstants.RONG_JIE_YANG;
            }
        });
        andant = (TextView) mRightView.findViewById(R.id.andan);
        andant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTheChart(GlobalConstants.AN_DAN);
                now_temp = GlobalConstants.AN_DAN;
            }
        });
        zhuodut = (TextView) mRightView.findViewById(R.id.zhuodu);
        zhuodut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTheChart(GlobalConstants.ZHUO_DU);
                now_temp = GlobalConstants.ZHUO_DU;
            }
        });
        zonglint = (TextView) mRightView.findViewById(R.id.zonglin);
        zonglint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillTheChart(GlobalConstants.ZONG_LIN);
                now_temp = GlobalConstants.ZONG_LIN;
            }
        });

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
                                if (weather != null && "ok".equals(weather.status)) {
                                    SpUtil.setString(getActivity(), GlobalConstants.WEATHER, responseText);
                                    showWeatherInfo(weather);
                                } else {
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
     *
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
