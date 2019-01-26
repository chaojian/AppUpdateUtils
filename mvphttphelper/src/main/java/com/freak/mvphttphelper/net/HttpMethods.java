package com.freak.mvphttphelper.net;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * okHttp请求类封装
 *
 * @author freak
 * @date 2019/01/25
 */
public class HttpMethods {
    //这是连接网络的时间
    private static final int DEFAULT_TIMEOUT = 10;
    private static String BaseUrl;
    public static Converter.Factory mFactory;

    private Retrofit retrofit;
    private static HttpMethods mHttpMethods;

    public static void setBaseUrl(String url) {
        BaseUrl = url;
    }

    public static void setFactory(Converter.Factory factory) {
        mFactory = factory;
    }

    public HttpMethods() {
        /**
         * 创建okHttpClient
         */
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        /**
         * 设置网络超时时间 时间按秒计算
         */
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        /**
         * addConverterFactory 添加格式转换器工程  GsonConverterFactory
         * addCallAdapterFactory 添加调用适配器工程 RxJavaCallAdapterFactory
         * baseUrl 基础地址
         */
        retrofit = new Retrofit.Builder().client(builder.build())
                .addConverterFactory(mFactory == null?GsonConverterFactory.create():mFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BaseUrl)
                .build();
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final Retrofit INSTANCE = new HttpMethods().getServer();
    }

    public static HttpMethods getInstance() {
        if (mHttpMethods == null) {
            Class var0 = HttpMethods.class;
            synchronized (HttpMethods.class) {
                mHttpMethods = new HttpMethods();
            }
        }

        return mHttpMethods;
    }

    public <T> T create(Class<T> service) {
        return this.retrofit.create(service);
    }

    /**
     * 获取单例
     *
     * @return 返回一个Retrofit对象
     */
//    public static Retrofit getInstance() {
//        return SingletonHolder.INSTANCE;
//    }
    public Retrofit getServer() {
        return retrofit;
    }
}
