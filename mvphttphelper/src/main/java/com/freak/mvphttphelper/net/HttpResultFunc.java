package com.freak.mvphttphelper.net;

import android.text.TextUtils;
import android.util.Log;

import rx.functions.Func1;

/**
 * 此方法是接口返回数据的解析
 *
 * @param <T>
 * @author freak
 * @date 2019/01/25
 */
public class HttpResultFunc<T> implements Func1<AbstractHttpResult<T>, T> {
    @Override
    public T call(AbstractHttpResult<T> tHttpResult) {
        //int类型结果码
        if (TextUtils.isEmpty(tHttpResult.getStringResultCode())) {
            if (tHttpResult.getIntResultCode() != tHttpResult.getIntSuccessCode()) {
                int[] otherCode = tHttpResult.getIntOtherCode();
                for (int code : otherCode) {
                    if (tHttpResult.getIntResultCode() == code) {
                        throw new ApiException(tHttpResult.getIntResultCode() + "");
                    }
                }
                throw new ApiException(tHttpResult.getResultErrorMsg());
            }
            return tHttpResult.getResultData();
        } else {
            //string类型结果码
            Log.e("HttpResultFunc", "结果码" + tHttpResult.getStringResultCode() + "\n设置的结果码" + tHttpResult.getStringSuccessCode());
            if (!tHttpResult.getStringSuccessCode().equals(tHttpResult.getStringResultCode())) {
                String[] otherCode = tHttpResult.getStringOtherCode();
                for (String code : otherCode) {
                    if (tHttpResult.getStringSuccessCode().equals(code)) {
                        throw new ApiException(tHttpResult.getStringResultCode());
                    }
                }
                throw new ApiException(tHttpResult.getResultErrorMsg());
            }
            return tHttpResult.getResultData();
        }
    }
}
