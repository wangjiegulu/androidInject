package com.wangjie.androidinject.annotation.cache.common.cached;

import com.wangjie.androidinject.annotation.cache.common.Cacheable;

import java.lang.annotation.Annotation;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/26/15.
 */
public class CachedPresentType implements Cacheable {
    private Annotation[] annotations;

    public Annotation[] getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Annotation[] annotations) {
        this.annotations = annotations;
    }
}
