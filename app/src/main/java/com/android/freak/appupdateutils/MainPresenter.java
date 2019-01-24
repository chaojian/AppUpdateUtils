package com.android.freak.appupdateutils;

import android.support.annotation.Nullable;

import com.android.freak.appupdateutils.app.MyApiServer;
import com.freak.mvphttphelper.net.HttpMethods;
import com.freak.mvphttphelper.net.RxPresenter;


public class MainPresenter extends RxPresenter implements MainContract.Presenter {
    MyApiServer apiServer = HttpMethods.getInstance().create(MyApiServer.class);
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
//        Observable observable = apiServer.login(Constants.org_number, Constants.user_mobile, Constants.user_password).map(new HttpResultFunc<List<FrequentlyBean>>());
//        addSubscription(observable, new SubscriberCallBack(new ApiCallback<List<FrequentlyBean>>() {
//            @Override
//            public void onSuccess(List<FrequentlyBean> model) {
//                Logger.d(model);
//                mView.onSuccess(model);
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                Log.e("TAG", msg);
//            }
//        }));

    }
}
