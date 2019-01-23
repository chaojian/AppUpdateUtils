package com.android.freak.appupdateutils;

import com.android.freak.appupdateutils.bean.FrequentlyBean;
import com.freak.mvphttphelper.net.BasePresenter;
import com.freak.mvphttphelper.net.BaseView;

import java.util.List;

/**
 * Created by Administrator on 2018/12/25.
 */

public interface MainContract {
    interface View extends BaseView<Presenter> {
        void onSuccess(List<FrequentlyBean> loginBean);
        void onError(String msg);
    }

    interface Presenter extends BasePresenter {
        void doLogin();
    }
}
