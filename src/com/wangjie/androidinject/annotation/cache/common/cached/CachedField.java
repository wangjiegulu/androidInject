package com.wangjie.androidinject.annotation.cache.common.cached;

import com.wangjie.androidinject.annotation.cache.common.Cacheable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/25/15.
 */
public class CachedField implements Cacheable {
    private Field field;
    private Annotation[] annotations;

    public Annotation[] getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Annotation[] annotations) {
        this.annotations = annotations;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

}
