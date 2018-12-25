package com.android.freak.appupdatautils;

import com.android.freak.appupdatautils.net.BasePresenter;
import com.android.freak.appupdatautils.net.BaseView;

/**
 * Created by Administrator on 2018/12/25.
 */

public interface MainContract {
    interface View extends BaseView<Presenter>{

    }
    interface Presenter extends BasePresenter{

    }
}
