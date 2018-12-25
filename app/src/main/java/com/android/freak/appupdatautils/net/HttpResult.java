package com.android.freak.appupdatautils.net;


/**
 * 此方法是根据接口返回的数据去定义的，抽取出返回json数据的对象进行去解析
 *
 * @param <T> result是接口数据的一个对象，bean类中的数据书写也是书写这个json数据的对象的字段即可
 */
public class HttpResult<T> {


    private int respCode;

    private T respData;

    private String respDesc;

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public T getRespData() {
        return respData;
    }

    public void setRespData(T respData) {
        this.respData = respData;
    }

    public String getRespDesc() {
        return respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
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
