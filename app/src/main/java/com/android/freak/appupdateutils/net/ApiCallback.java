package com.android.freak.appupdateutils.net;

public interface ApiCallback<T> {
    void onSuccess(T model);

    void onFailure(String msg);
}
