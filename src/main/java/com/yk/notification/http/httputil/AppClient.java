package com.yk.notification.http.httputil;

import android.content.Context;

import com.yk.notification.BuildConfig;
import com.yk.notification.util.Constants;
import com.yk.notification.util.NetWorkUtils;

import java.io.File;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit工具
 * Created by Silence on 2017/4/8.
 */

public class AppClient {
    public static Retrofit mRetrofit = null;

    public static Retrofit retrofit(final Context context) {
        if (mRetrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            /**
             * 设置缓存
             */
            File cacheFile = new File(context.getExternalCacheDir(), "Silence");
            final Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    if (!NetWorkUtils.isConnectedByState(context)) {
                        request = request.newBuilder()
                                .cacheControl(CacheControl.FORCE_CACHE)
                                .build();
                    }
                    Response response = chain.proceed(request);
                    if (NetWorkUtils.isConnectedByState(context)) {
                        int maxAge = 0;
                        // 有网络时 设置缓存超时时间0个小时
                        response.newBuilder().header("Cache-Control", "public, max-age=" + maxAge)
                                // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                                .removeHeader("Silence")
                                .build();
                    } else {
                        //有网络时 设置缓存超时时间为4周
                        int maxStale = 60 * 60 * 24 * 28;
                        response.newBuilder().header("Cache-Control", "public, only-if-cached, " +
                                "max-stale=" + maxStale)
                                .removeHeader("nyn").build();
                    }

                    return response;
                }
            };
            builder.cache(cache).addInterceptor(interceptor);
            /**
             * 设置公共参数
             */
            Interceptor addQueryParameterInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    //请求源
                    Request originalRequest = chain.request();
                    Request request;
                    //请求方法
                    String method = originalRequest.method();
                    //请求头
                    Headers headers = originalRequest.headers();
                    HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                            .addQueryParameter("platform", "android")
                            .addQueryParameter("version", "1.0.0")
                            .build();
                    request = originalRequest.newBuilder().url(modifiedUrl).build();
                    return chain.proceed(request);
                }
            };
            //公共参数
            builder.addInterceptor(addQueryParameterInterceptor);
            /**
             * 设置头
             */
            final Interceptor headerInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request headerRequest = chain.request();
                    Request.Builder requestBuilder = headerRequest.newBuilder()
                            .header("AppType", "TPOS")
                            .header("Content-Type", "application/json")
                            .header("Accept", "application/json")
                            .method(headerRequest.method(), headerRequest.body());
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            };
            //设置头
            builder.addInterceptor(headerInterceptor);
            /**
             * 设置LOG
             */
            if (BuildConfig.DEBUG) {
               //Log信息拦截器
                HttpLoggingInterceptor loggingInterceptor= new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                //设置Debug log模式
                builder.addInterceptor(loggingInterceptor);
            }
            /**
             * 设置cookie
             */
            CookieManager cookieManager= new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            builder.cookieJar(new JavaNetCookieJar(cookieManager));
            /**
             * 设置超时和重连
             */
            builder.connectTimeout(15, TimeUnit.SECONDS);
            builder.readTimeout(20,TimeUnit.SECONDS);
            builder.writeTimeout(20,TimeUnit.SECONDS);
            //错误重连
            builder.retryOnConnectionFailure(true);

            //以上结束，才能build()，不然设置不起作用
            OkHttpClient okHttpClient=builder.build();

            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    //设置json转换器
                    .addConverterFactory(GsonConverterFactory.create())
                    //Rxjava适配器
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return mRetrofit;
    }

}
