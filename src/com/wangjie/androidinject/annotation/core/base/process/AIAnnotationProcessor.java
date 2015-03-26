package com.wangjie.androidinject.annotation.core.base.process;

import com.wangjie.androidinject.annotation.cache.common.Cacheable;
import com.wangjie.androidinject.annotation.present.AIPresent;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2/4/15.
 */
public interface AIAnnotationProcessor<T> extends Cacheable {
    void process(AIPresent present, T obj) throws Exception;
}
