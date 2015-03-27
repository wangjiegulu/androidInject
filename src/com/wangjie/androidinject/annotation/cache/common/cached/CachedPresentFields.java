package com.wangjie.androidinject.annotation.cache.common.cached;

import com.wangjie.androidinject.annotation.cache.common.Cacheable;

import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/25/15.
 */
public class CachedPresentFields implements Cacheable{
    private List<CachedField> cachedFields;

    public List<CachedField> getCachedFields() {
        return cachedFields;
    }

    public void setCachedFields(List<CachedField> cachedFields) {
        this.cachedFields = cachedFields;
    }
}
