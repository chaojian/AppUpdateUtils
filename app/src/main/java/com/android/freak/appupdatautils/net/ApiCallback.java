package com.android.freak.appupdatautils.net;

public interface ApiCallback<T> {
    void onSuccess(T model);

    void onFailure(String msg);
}
