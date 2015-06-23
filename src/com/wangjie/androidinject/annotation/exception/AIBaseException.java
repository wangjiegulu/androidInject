package com.wangjie.androidinject.annotation.exception;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 6/19/15.
 */
public class AIBaseException extends Exception{
    public AIBaseException() {
    }

    public AIBaseException(String detailMessage) {
        super(detailMessage);
    }

    public AIBaseException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public AIBaseException(Throwable throwable) {
        super(throwable);
    }
}
