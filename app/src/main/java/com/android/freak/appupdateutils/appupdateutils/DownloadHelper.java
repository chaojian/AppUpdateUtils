package com.android.freak.appupdateutils.appupdateutils;


import com.android.freak.appupdateutils.app.Constants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 下载助手 包含进度监听
 *
 * @author Administrator
 * @date 2019/1/2
 */

public class DownloadHelper {
    //这是连接网络的时间
    //原来是5s
    private static final int DEFAULT_TIMEOUT = 10;

    private Retrofit retrofit;

    private static OkHttpClient.Builder builder;

    private DownloadHelper() {
        builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        builder.readTimeout(60, TimeUnit.SECONDS);
        builder.writeTimeout(60, TimeUnit.SECONDS);

    }

    //在访问时创建单例
    private static class SingletonHolder {
        private static final DownloadHelper INSTANCE = new DownloadHelper();
    }


    //获取单例
    public static DownloadHelper getInstance() {

        return SingletonHolder.INSTANCE;

    }


    public <S>S createApiService(Class<S> serviceClass, final ProgressListener progressListener){

        builder.addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //本身的response
                Response proceed = chain.proceed(chain.request());
                //创建自定义的ProgressResponseBody
                ProgressResponseBody progressResponseBody = new ProgressResponseBody(proceed.body(),progressListener);

                //替换我们自己的进度监听的progressResponseBody
                return proceed.newBuilder()
                        .body(progressResponseBody)
                        .build();
            }
        });

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Constants.BASE_URL).build();

        return retrofit.create(serviceClass);
    }

}
