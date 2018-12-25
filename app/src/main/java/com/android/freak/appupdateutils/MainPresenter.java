package com.android.freak.appupdateutils;

import android.support.annotation.Nullable;
import android.util.Log;

import com.android.freak.appupdateutils.app.ApiServer;
import com.android.freak.appupdateutils.app.Constants;
import com.android.freak.appupdateutils.bean.FrequentlyBean;
import com.android.freak.appupdateutils.bean.LoginBean;
import com.android.freak.appupdateutils.net.ApiCallback;
import com.android.freak.appupdateutils.net.HttpMethods;
import com.android.freak.appupdateutils.net.HttpResultFunc;
import com.android.freak.appupdateutils.net.RxPresenter;
import com.android.freak.appupdateutils.net.SubscriberCallBack;
import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Observable;


public class MainPresenter extends RxPresenter implements MainContract.Presenter {
    ApiServer apiServer = HttpMethods.getInstance().create(ApiServer.class);
    private MainContract.View mView;

    @Override
    public void start() {
        doLogin();
    }

    public MainPresenter(@Nullable MainContract.View mView) {
        this.mView = mView;
//        this.mView.setPresenter(this);
    }

    @Override
    public void doLogin() {
        Observable observable = apiServer.login(Constants.org_number, Constants.user_mobile, Constants.user_password).map(new HttpResultFunc<List<FrequentlyBean>>());
        addSubscription(observable, new SubscriberCallBack(new ApiCallback<List<FrequentlyBean>>() {
            @Override
            public void onSuccess(List<FrequentlyBean> model) {
                Logger.d(model);
                mView.onSuccess(model);
            }

            @Override
            public void onFailure(String msg) {
                Log.e("TAG", msg);
            }
        }));

    }
}
