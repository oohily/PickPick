package com.oohily.com.pickpick.models;

import java.util.ArrayList;

/**
 * Created by Zhongsz on 2017/8/2.
 */

public class HttpResult {
    private ArrayList HeWeather5;

    public ArrayList getHeWeather5() {
        return HeWeather5;
    }

    public void setHeWeather5(ArrayList heWeather5) {
        HeWeather5 = heWeather5;
    }

    public HttpResult(ArrayList heWeather5) {
        HeWeather5 = heWeather5;
    }
}
