package com.freak.mvphttphelper.net;

import rx.functions.Func1;

/**
 * 此方法是接口返回数据的解析
 * @param <T>
 */
public class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
    @Override
    public T call(HttpResult<T> tHttpResult) {
        if (tHttpResult.getErrorCode() != 0) {
            throw new ApiException(tHttpResult.getErrorMsg());
        }
        return tHttpResult.getData();
    }
}
