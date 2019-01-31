package com.android.freak.appupdateutils;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.freak.appupdateutils.app.BaseActivity;
import com.android.freak.appupdateutils.bean.LoginBean;
import com.android.freak.appupdateutils.event.RxEvent;
import com.freak.appupdateutils.appupdateutils.AppUtils;
import com.freak.mvphttphelper.net.RxBus;
import com.orhanobut.logger.Logger;

import rx.Subscription;
import rx.functions.Action1;


/**
 * @author Administrator
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    private final static String TAG = "MainActivity";
    private EditText username, pwd;
    private TextView tvResult;
    private Subscription mSubscribe;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {
        username = findViewById(R.id.username);
        pwd = findViewById(R.id.pwd);
        tvResult = findViewById(R.id.result);
        mSubscribe = RxBus.getDefault().tObservable(RxEvent.class).subscribe(new Action1<RxEvent>() {
            @Override
            public void call(RxEvent rxEvent) {
                if (rxEvent.getCode() == 1000) {
                    username.setText(rxEvent.getUserName());
                    pwd.setText(rxEvent.getPassWord());
                }
            }
        });
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }


    @Override
    public void onSuccess(LoginBean loginBean) {
        Logger.e("onSuccess");
        Logger.d(loginBean);
    }

    @Override
    public void onError(String msg) {
        Log.e(TAG, msg);
    }


    public void update(View view) {
        //        AppUtils appUtils = new AppUtils(this, "https://www.pgyertest.hangmuxitong.com/", BuildConfig.APPLICATION_ID + ".fileProvider");
        AppUtils appUtils = new AppUtils(this, null, BuildConfig.APPLICATION_ID + ".fileProvider");
//        AppUtils appUtils = new AppUtils(this, "https://www.pgyertest.hangmuxitong.com/", null);
        appUtils.setApkURL("https://www.pgyertest.hangmuxitong.com/uploads/20190102/android/5c2cb3f369cc2.apk")
                .setFileName("蜂鸟普惠")
                .setVersionCode(4)
                .setNotificationTitle("ajsfjka")
                .setVersionName("6.0.0")
                .setAddContent("1、现场pos\n1、现场pos\n1、现场pos\n1、现场pos\n1、现场pos\n1、现场pos\n")
                .setDialogStyle(AppUtils.UPDATE_DIALOG_PARTICULAR)
                .build();
    }


    public void login(View view) {
        mPresenter.doLogin(username.getText().toString().trim(), pwd.getText().toString().trim());
    }

    @Override
    public void showResult(String result) {
        tvResult.setText(result);
    }

    public void rxBusOnclick(View view) {
        RxBusActivity.startAction(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscribe != null) {
            if (mSubscribe.isUnsubscribed()) {
                mSubscribe.unsubscribe();
            }
        }
    }
}
