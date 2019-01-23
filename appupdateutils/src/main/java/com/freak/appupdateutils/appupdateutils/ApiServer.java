package com.freak.appupdateutils.appupdateutils;



import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

public interface ApiServer {
    /**
     * apk文件下载
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadApk(@Url String apkUrl);
}
