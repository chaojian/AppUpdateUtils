package com.android.freak.appupdateutils.app;




import com.android.freak.appupdateutils.bean.FrequentlyBean;
import com.android.freak.appupdateutils.bean.LoginBean;
import com.android.freak.appupdateutils.net.HttpResult;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

public interface ApiServer {

    @GET("wxarticle/chapters/json")
    Observable<HttpResult<List<FrequentlyBean>>> login();

    /**
     * apk文件下载
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadApk(@Url String apkUrl);
}
