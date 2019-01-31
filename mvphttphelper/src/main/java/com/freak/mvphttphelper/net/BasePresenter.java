package com.freak.mvphttphelper.net;

/**
 * @author freak
 * @date 2019/01/25
 */

public interface BasePresenter<T extends RxBaseView> {
    /**
     * 绑定view
     *
     * @param view view
     */
    void attachView(T view);

    /**
     * 分离view
     */
    void detachView();
}
