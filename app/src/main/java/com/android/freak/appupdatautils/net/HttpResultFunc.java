package com.android.freak.appupdatautils.net;

import rx.functions.Func1;

/**
 * 此方法是接口返回数据的解析
 * @param <T>
 */
public class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
    @Override
    public T call(HttpResult<T> tHttpResult) {
        if (tHttpResult.getCode() != 200) {
            throw new ApiException(tHttpResult.getMsg());
        }
        return tHttpResult.getData();
    }
}
