package com.wangjie.androidinject.annotation.cache.common.generator;

import com.wangjie.androidinject.annotation.cache.common.cached.CachedPresentType;
import com.wangjie.androidinject.annotation.present.AIPresent;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/26/15.
 */
public class CachedPresentTypeGenerator implements CachedGenerator<CachedPresentType> {
    private Class<? extends AIPresent> clazz;

    public void setClazz(Class<? extends AIPresent> clazz) {
        this.clazz = clazz;
    }

    @Override
    public CachedPresentType generate() {
        if (null == clazz) {
            return null;
        }
        CachedPresentType cachedPresentType = new CachedPresentType();
        cachedPresentType.setAnnotations(clazz.getAnnotations());
        return cachedPresentType;
    }
}
