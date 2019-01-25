package com.freak.mvphttphelper.net;

/**
 * @author freak
 * @date 2019/01/25
 */
public class ApiException extends RuntimeException {
    public ApiException(String detailMessage) {
        super(detailMessage);
    }
}
