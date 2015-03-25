package com.wangjie.androidinject.annotation.cache;

import com.wangjie.androidinject.annotation.present.AIPresent;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/23/15.
 */
public class MethodCache {
    private static final String TAG = MethodCache.class.getSimpleName();

    public static class CachedMethod {
        private Method method;
        private Annotation[] annotations;

        public Annotation[] getAnnotations() {
            return annotations;
        }

        public void setAnnotations(Annotation[] annotations) {
            this.annotations = annotations;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }
    }

    private static MethodCache instance;

    public synchronized static MethodCache getInstance() {
        if (null == instance) {
            instance = new MethodCache();
        }
        return instance;
    }

    private MethodCache() {
    }

    private ConcurrentHashMap<Class<? extends AIPresent>, List<CachedMethod>> cacheMapper = new ConcurrentHashMap<>();

    public List<CachedMethod> getCache(Class<? extends AIPresent> key) {
        List<CachedMethod> cachedMethods = cacheMapper.get(key);
        if (null == cachedMethods) {
            cachedMethods = new ArrayList<>();

            Method[] methods = key.getDeclaredMethods();
            for (Method method : methods) {
                CachedMethod cachedMethod = new CachedMethod();
                cachedMethod.setMethod(method);
                cachedMethod.setAnnotations(method.getAnnotations());
                cachedMethods.add(cachedMethod);
            }
            cacheMapper.put(key, cachedMethods);
//            Logger.i(TAG, key.getName() + " melthods find with reflect..., methods: " + cachedMethods);
        }
        return cachedMethods;
    }
}
