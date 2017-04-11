package com.yk.notification.http.httputil;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Silence on 2017/4/8.
 */

public class HttpUtils {

    public static Retrofit mRetrofit;

    /**
     * 获取
     * @param baseUrl
     * @return
     */
    public static  Retrofit getRetrofit(String baseUrl){
        mRetrofit=new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return mRetrofit;
    }

}
