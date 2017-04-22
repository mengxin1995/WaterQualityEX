package com.tom.waterqualityex.http.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mengxin on 17-2-12.
 */

public class WaterData {
    @SerializedName("date_time")
    private String time;
    private float ph;
    @SerializedName("conductivity")
    private float dianDaoLv;
    @SerializedName("water_temperature")
    private float shuiWen;
    @SerializedName("ammonia_nitrogen")
    private float anDan;
    @SerializedName("dissolved_oxygen")
    private float rongJieYang;
    @SerializedName("P")
    private float zonglin;
    @SerializedName("ntu")
    private float zhuodu;

    @Override
    public String toString() {
        return "WaterData{" +
                "time='" + time + '\'' +
                ", ph=" + ph +
                ", dianDaoLv=" + dianDaoLv +
                ", shuiWen=" + shuiWen +
                ", anDan=" + anDan +
                ", rongJieYang=" + rongJieYang +
                ", zonglin=" + zonglin +
                ", zhuodu=" + zhuodu +
                '}';
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getPh() {
        return ph;
    }

    public void setPh(float ph) {
        this.ph = ph;
    }

    public float getDianDaoLv() {
        return dianDaoLv;
    }

    public void setDianDaoLv(float dianDaoLv) {
        this.dianDaoLv = dianDaoLv;
    }

    public float getShuiWen() {
        return shuiWen;
    }

    public void setShuiWen(float shuiWen) {
        this.shuiWen = shuiWen;
    }

    public float getAnDan() {
        return anDan;
    }

    public void setAnDan(float anDan) {
        this.anDan = anDan;
    }

    public float getRongJieYang() {
        return rongJieYang;
    }

    public void setRongJieYang(float rongJieYang) {
        this.rongJieYang = rongJieYang;
    }

    public float getZonglin() {
        return zonglin;
    }

    public void setZonglin(float zonglin) {
        this.zonglin = zonglin;
    }

    public float getZhuodu() {
        return zhuodu;
    }

    public void setZhuodu(float zhuodu) {
        this.zhuodu = zhuodu;
    }
}
