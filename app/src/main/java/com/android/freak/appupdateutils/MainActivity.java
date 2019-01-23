package com.android.freak.appupdateutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.freak.appupdateutils.bean.FrequentlyBean;
import com.freak.appupdateutils.appupdateutils.AppUtils;
import com.orhanobut.logger.Logger;

import java.util.List;


public class MainActivity extends AppCompatActivity implements MainContract.View {
    private MainContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainPresenter(this);

    }

    @Override
    public void onSuccess(List<FrequentlyBean> loginBean) {
        Logger.d(loginBean);
    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
//        mPresenter=checkNotNull(presenter);
    }

    public void login(View view) {
        AppUtils appUtils = new AppUtils(this, "https://www.pgyertest.hangmuxitong.com/");
        appUtils.setApkURL("https://www.pgyertest.hangmuxitong.com/uploads/20190102/android/5c2cb3f369cc2.apk")
                .setForce(false)
                .setFileName("蜂鸟普惠")
                .setVersionCode(4)
                .setVersionName("6.0.0")
                .setAddContent("1、现场pos")
                .setDialogStyle(AppUtils.UPDATE_DIALOG_PARTICULAR)
                .build();
    }
}
