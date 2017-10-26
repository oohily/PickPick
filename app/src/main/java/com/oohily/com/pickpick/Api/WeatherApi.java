package com.oohily.com.pickpick.Api;

import com.oohily.com.pickpick.models.HttpResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Zhongsz on 2017/8/2.
 */

public interface WeatherApi {
    @GET("now")
    Observable<HttpResult> getNowWeather(@Query("city") String city,
                                         @Query("key") String key);
}
