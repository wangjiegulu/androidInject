package com.wangjie.androidinject.annotation.cache;

import com.wangjie.androidinject.annotation.present.AIPresent;

import java.lang.annotation.Annotation;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/23/15.
 */
@Deprecated
public class TypeCache {
    private static final String TAG = TypeCache.class.getSimpleName();
    @Deprecated
    public static class CachedType {
        private Annotation[] annotations;

        public Annotation[] getAnnotations() {
            return annotations;
        }

        public void setAnnotations(Annotation[] annotations) {
            this.annotations = annotations;
        }
    }

    private static TypeCache instance;

    public synchronized static TypeCache getInstance() {
        if (null == instance) {
            instance = new TypeCache();
        }
        return instance;
    }

    private TypeCache() {
    }

    private ConcurrentHashMap<Class<? extends AIPresent>, CachedType> typeMapper = new ConcurrentHashMap<>();

    public CachedType getCache(Class<? extends AIPresent> key) {
        CachedType cachedType = typeMapper.get(key);
        if (null == cachedType) {
            cachedType = new CachedType();
            cachedType.setAnnotations(key.getAnnotations());
            typeMapper.put(key, cachedType);
//            Logger.i(TAG, key.getName() + " type find with reflect..., type: " + cachedType);
        }
        return cachedType;
    }


}
