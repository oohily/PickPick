package com.oohily.com.pickpick.Api;

import android.content.Context;
import android.content.SharedPreferences;

import com.oohily.com.pickpick.MyApplication;
import com.oohily.com.pickpick.configs.StringConstant;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Zhongsz on 2017/8/2.
 */

public class ApiManager {
    private static ApiManager apiManager;
    private final Object object = new Object();
    private final SharedPreferences preferences = MyApplication.getApplication().getSharedPreferences(StringConstant.USER_PREFERENCE, Context.MODE_PRIVATE);

    public static ApiManager getInstance(){
        if (apiManager == null){
            synchronized (ApiManager.class){
                if (apiManager == null){
                    apiManager = new ApiManager();
                }
            }
        }
        return apiManager;
    }

    private WeatherApi weatherApi;
    public WeatherApi getWeatherApi(){
        if (weatherApi == null){
            synchronized (object){
                if (weatherApi == null){
                    weatherApi = new Retrofit.Builder()
                            .baseUrl(StringConstant.WEATHER_SERVER_HOME)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(WeatherApi.class);
                }
            }
        }
        return weatherApi;
    }
}
