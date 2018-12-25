package com.android.freak.appupdateutils.app;




import com.android.freak.appupdateutils.bean.FrequentlyBean;
import com.android.freak.appupdateutils.bean.LoginBean;
import com.android.freak.appupdateutils.net.HttpResult;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiServer {

    @GET("app/user/login")
    Observable<HttpResult<List<FrequentlyBean>>> login(@Query("org_number") String org_number,
                                                       @Query("user_mobile") String user_mobile,
                                                       @Query("user_password") String user_password);


}
