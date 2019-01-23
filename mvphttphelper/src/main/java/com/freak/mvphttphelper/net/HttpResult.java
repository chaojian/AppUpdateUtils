package com.freak.mvphttphelper.net;


/**
 * 此方法是根据接口返回的数据去定义的，抽取出返回json数据的对象进行去解析
 *
 * @param <T> result是接口数据的一个对象，bean类中的数据书写也是书写这个json数据的对象的字段即可
 */
public class HttpResult<T> {

    private int errorCode;
    private T data;
    private String errorMsg;


    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (null != data) {
            sb.append(data.toString());
        }
        return sb.toString();
    }
}
