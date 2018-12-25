package com.android.freak.appupdateutils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.android.freak.appupdateutils.bean.FrequentlyBean;
import com.android.freak.appupdateutils.bean.LoginBean;
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
        mPresenter.doLogin();
    }
}
