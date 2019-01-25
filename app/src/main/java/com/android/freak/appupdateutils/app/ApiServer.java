package com.android.freak.appupdateutils.app;


import com.android.freak.appupdateutils.bean.LoginBean;
import com.android.freak.appupdateutils.net.HttpResult;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @author Administrator
 */
public interface ApiServer {
    /**
     * 用户登陆
     *
     * @return
     */
    @POST("/app/user/login")
    Observable<HttpResult<LoginBean>> login(@Query("org_number") String org_number,
                                            @Query("user_mobile") String user_mobile,
                                            @Query("user_password") String user_password);

    /**
     * apk文件下载
     * @param apkUrl
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadApk(@Url String apkUrl);
}
