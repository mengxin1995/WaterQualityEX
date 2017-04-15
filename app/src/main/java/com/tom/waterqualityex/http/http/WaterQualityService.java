package com.tom.waterqualityex.http.http;


import com.tom.waterqualityex.http.entity.HttpResult;
import com.tom.waterqualityex.http.entity.WaterData;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by mengxin on 17-2-12.
 */

public interface WaterQualityService {

    /**
     * 获得指定一天，指定机器的水质数据
     * @return
     */
    @GET("app/limitdatas")
    Observable<HttpResult<List<WaterData>>> getWaterData(@Query("instrument_id") String id,
                                                         @Query("date") String data);
}
