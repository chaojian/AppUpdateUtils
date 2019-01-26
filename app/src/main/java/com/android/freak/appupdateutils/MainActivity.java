package com.android.freak.appupdateutils;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.freak.appupdateutils.app.BaseActivity;
import com.android.freak.appupdateutils.bean.LoginBean;
import com.freak.appupdateutils.appupdateutils.AppUtils;
import com.orhanobut.logger.Logger;


/**
 * @author Administrator
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    private final static String TAG = "MainActivity";
    private EditText username, pwd;
    private TextView tvResult;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {
        username = findViewById(R.id.username);
        pwd = findViewById(R.id.pwd);
        tvResult = findViewById(R.id.result);
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
}
