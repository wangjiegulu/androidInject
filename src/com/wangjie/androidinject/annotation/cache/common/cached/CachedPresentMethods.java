package com.wangjie.androidinject.annotation.cache.common.cached;

import com.wangjie.androidinject.annotation.cache.common.Cacheable;

import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/26/15.
 */
public class CachedPresentMethods implements Cacheable {
    private List<CachedMethod> cachedMethods;

    public List<CachedMethod> getCachedMethods() {
        return cachedMethods;
    }

    public void setCachedMethods(List<CachedMethod> cachedMethods) {
        this.cachedMethods = cachedMethods;
    }
}
