package com.freak.mvphttphelper.net;

/**
 *
 * @author freak
 * @date 2019/01/25
 */

public interface BasePresenter<T extends RxBaseView> {
    void attachView(T view);

    void detachView();
}
