package com.wangjie.androidinject.annotation.cache.common.cached;

import com.wangjie.androidinject.annotation.cache.common.Cacheable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/26/15.
 */
public class CachedMethod implements Cacheable {
    private Method method;
    private Annotation[] annotations;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Annotation[] getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Annotation[] annotations) {
        this.annotations = annotations;
    }
}
