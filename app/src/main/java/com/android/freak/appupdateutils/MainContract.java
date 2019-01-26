package com.android.freak.appupdateutils;

import com.android.freak.appupdateutils.app.BaseView;
import com.android.freak.appupdateutils.bean.LoginBean;
import com.freak.mvphttphelper.net.BasePresenter;

/**
 * Created by Administrator on 2018/12/25.
 */

public interface MainContract {
    interface View extends BaseView {
        void onSuccess(LoginBean loginBean);
        void onError(String msg);
    }

    interface Presenter extends BasePresenter<View> {
        void doLogin(String userName,String pwd);

    }
}
