package com.android.freak.appupdateutils;

import com.android.freak.appupdateutils.app.ApiServer;
import com.android.freak.appupdateutils.bean.LoginBean;
import com.freak.mvphttphelper.net.ApiCallback;
import com.freak.mvphttphelper.net.HttpMethods;
import com.freak.mvphttphelper.net.HttpResultFunc;
import com.freak.mvphttphelper.net.RxPresenter;
import com.freak.mvphttphelper.net.SubscriberCallBack;
import com.orhanobut.logger.Logger;

import rx.Observable;


public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {
    ApiServer apiServer = HttpMethods.getInstance().create(ApiServer.class);



    @Override
    public void doLogin(String userName,String pwd) {
        Observable observable = apiServer.login(userName,pwd).map(new HttpResultFunc<LoginBean>());
        addSubscription(observable, new SubscriberCallBack(new ApiCallback<LoginBean>() {
            @Override
            public void onSuccess(LoginBean model) {
                Logger.d(model);
                mView.onSuccess(model);
                mView.showResult(model.toString());
            }

            @Override
            public void onFailure(String msg) {
                mView.onError(msg);
                mView.showResult("错误信息或code--》"+msg);
            }
        }));

    }



}
