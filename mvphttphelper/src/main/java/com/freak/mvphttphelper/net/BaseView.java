package com.freak.mvphttphelper.net;

/**
 * Created by Administrator on 2018/2/4.
 * View基类
 */

public interface BaseView<T> {
    //    void showError(String msg);
    void setPresenter(T presenter);
}
