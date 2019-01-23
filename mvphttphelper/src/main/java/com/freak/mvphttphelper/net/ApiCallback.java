package com.freak.mvphttphelper.net;

public interface ApiCallback<T> {
    void onSuccess(T model);

    void onFailure(String msg);
}
