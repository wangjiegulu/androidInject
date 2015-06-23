package com.wangjie.androidinject.annotation.exception;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 6/19/15.
 */
public class AINoSuchAnnotationProcessorException extends AIBaseException{
    public AINoSuchAnnotationProcessorException() {
    }

    public AINoSuchAnnotationProcessorException(String detailMessage) {
        super(detailMessage);
    }

    public AINoSuchAnnotationProcessorException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public AINoSuchAnnotationProcessorException(Throwable throwable) {
        super(throwable);
    }
}
