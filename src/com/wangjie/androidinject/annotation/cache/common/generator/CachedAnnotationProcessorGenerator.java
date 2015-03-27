package com.wangjie.androidinject.annotation.cache.common.generator;

import com.wangjie.androidbucket.log.Logger;
import com.wangjie.androidinject.annotation.core.base.process.AIAnnotationProcessor;
import com.wangjie.androidinject.annotation.present.common.AnnoProcessorAlias;

import java.lang.annotation.Annotation;

/**
 * 注解处理器的缓存生成器
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/26/15.
 */
public class CachedAnnotationProcessorGenerator implements CachedGenerator<AIAnnotationProcessor> {
    private static final String TAG = CachedAnnotationProcessorGenerator.class.getSimpleName();
    private Class<? extends Annotation> annotationClazz;

    public void setAnnotationClazz(Class<? extends Annotation> annotationClazz) {
        this.annotationClazz = annotationClazz;
    }

    @Override
    public AIAnnotationProcessor generate() {
        // 如果没有，则先找出该处理器类型，然后实例化并放入缓存中
        AnnoProcessorAlias annotationProcessorAlias = AnnoProcessorAlias.getAnnotationProcessorAlias(annotationClazz);
        Class<? extends AIAnnotationProcessor> annotationProcessorClazz;
        if (null == annotationProcessorAlias || null == (annotationProcessorClazz = annotationProcessorAlias.getProcessorClazz())) {
            return null;
        }
        AIAnnotationProcessor annotationProcessor = null;
        try {
            annotationProcessor = annotationProcessorClazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            Logger.e(TAG, e);
        }
        return annotationProcessor;
    }
}
