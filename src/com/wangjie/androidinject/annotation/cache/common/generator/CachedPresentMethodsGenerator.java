package com.wangjie.androidinject.annotation.cache.common.generator;

import com.wangjie.androidinject.annotation.cache.common.cached.CachedMethod;
import com.wangjie.androidinject.annotation.cache.common.cached.CachedPresentMethods;
import com.wangjie.androidinject.annotation.present.AIPresent;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/26/15.
 */
public class CachedPresentMethodsGenerator implements CachedGenerator<CachedPresentMethods> {
    private Class<? extends AIPresent> clazz;

    public void setClazz(Class<? extends AIPresent> clazz) {
        this.clazz = clazz;
    }

    @Override
    public CachedPresentMethods generate() {
        CachedPresentMethods cs = new CachedPresentMethods();
        List<CachedMethod> cachedMethods = new ArrayList<>();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            CachedMethod cachedField = new CachedMethod();
            cachedField.setMethod(method);
            cachedField.setAnnotations(method.getAnnotations());
            cachedMethods.add(cachedField);
        }
        cs.setCachedMethods(cachedMethods);
        return cs;
    }
}
