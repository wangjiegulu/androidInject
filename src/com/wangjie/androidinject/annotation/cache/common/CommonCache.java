package com.wangjie.androidinject.annotation.cache.common;

import com.wangjie.androidbucket.log.Logger;
import com.wangjie.androidinject.annotation.cache.common.generator.CachedGenerator;

import java.util.HashMap;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/25/15.
 */
public class CommonCache {
    private static final String TAG = CommonCache.class.getSimpleName();

    private static CommonCache instance;

    public synchronized static CommonCache getInstance() {
        if (null == instance) {
            instance = new CommonCache();
        }
        return instance;
    }

    private CommonCache() {
    }

    /**
     * 缓存map，tag表示缓存的分组
     * 每一个分组中都有一个HashMap，这个HashMap中根据key进行储存
     */
    private HashMap<
            String,
            HashMap<Object, Cacheable>
            >
            cacheMapper = new HashMap<>();

    public <T extends Cacheable> T getCache(Class<T> cacheableClazz, Object key, CachedGenerator<T> cachedGenerator) {
        return getCache(cacheableClazz, cacheableClazz.getName(), key, cachedGenerator);
    }

    public <T extends Cacheable> T getCache(Class<T> cacheableClazz, String tag, Object key, CachedGenerator<T> cachedGenerator) {
        HashMap<Object, Cacheable> cachedableMapper = cacheMapper.get(tag);
        if (null == cachedableMapper) {
            cachedableMapper = new HashMap<>();
            cacheMapper.put(tag, cachedableMapper);
        }
        T cacheable = (T) cachedableMapper.get(key);
        if (null == cacheable) {
            cacheable = cachedGenerator.generate();
            if (null != cacheable) {
                cachedableMapper.put(key, cacheable);
                Logger.i(TAG, "CommonCache generate instance to cache, tag: " + tag + ", key: " + key + ", cacheable: " + cacheable);
            } else {
                Logger.i(TAG, "[WARN Cacheable generate null]CommonCache generate instance to cache, tag: " + tag + ", key: " + key);
            }
        }
        return cacheable;
    }
}
