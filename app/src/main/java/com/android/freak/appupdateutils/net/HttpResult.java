package com.android.freak.appupdateutils.net;


import com.freak.mvphttphelper.net.AbstractHttpResult;

/**
 * Created by Administrator on 2019/1/25.
 */

public class HttpResult<T> extends AbstractHttpResult<T> {
    private String respCode;

    private String respDesc;

    private T respData;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespDesc() {
        return respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }

    public T getRespData() {
        return respData;
    }

    public void setRespData(T respData) {
        this.respData = respData;
    }

    @Override
    protected T getResultData() {
        return getRespData();
    }

    @Override
    protected int getIntSuccessCode() {
        return 0;
    }

    @Override
    protected String getStringSuccessCode() {
        return "0000";
    }

    @Override
    protected String getResultErrorMsg() {
        return getRespDesc();
    }

    @Override
    protected int getIntResultCode() {
        return 0;
    }

    @Override
    protected String getStringResultCode() {
        return getRespCode();
    }

    @Override
    protected int[] getIntOtherCode() {
        return new int[]{0, 200};
    }

    @Override
    protected String[] getStringOtherCode() {
        return new String[]{"0027", "0051"};
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (null != respData) {
            sb.append(respData.toString());
        }
        return sb.toString();
    }
}
