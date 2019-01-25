package com.freak.mvphttphelper.net;

/**
 * @author freak
 * @date 2019/01/25
 */
public interface ApiCallback<T> {
    /**
     * 请求成功回调
     *
     * @param model
     */
    void onSuccess(T model);

    /**
     * 请求失败回调
     *
     * @param msg
     */
    void onFailure(String msg);
}
