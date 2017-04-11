package com.yk.notification.http.interfaceable;

import com.yk.notification.model.TestModel;
import com.yk.notification.model.WeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Silence on 2017/4/8.
 */

public interface APIInterface {

    /**
     * 请求的地址为：baseUrl + "/users/{user}"
     *
     * @param user user表示占位作用
     * @return TestModel 类型的实例
     */
    @GET("/users/{user}")
    Call<TestModel> repo(@Path("user") String user);

    /**
     * 根据城市ID获取天气信息
     * @param cityId
     * @return
     */
    @GET("adat/sk/{cityId}.html")
    Call<WeatherModel> getWeather(@Path("cityId") String cityId);
}
