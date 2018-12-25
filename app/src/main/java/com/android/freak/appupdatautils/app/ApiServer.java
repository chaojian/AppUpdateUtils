package com.android.freak.appupdatautils.app;




import com.android.freak.appupdatautils.bean.LoginBean;
import com.android.freak.appupdatautils.net.HttpResult;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiServer {

    @GET("app/user/login")
    Observable<HttpResult<LoginBean>> login(@Query("s") String s);

}
