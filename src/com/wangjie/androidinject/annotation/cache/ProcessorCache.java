package com.wangjie.androidinject.annotation.cache;

import com.wangjie.androidbucket.log.Logger;
import com.wangjie.androidinject.annotation.core.base.process.AIAnnotationProcessor;
import com.wangjie.androidinject.annotation.present.common.AnnoProcessorAlias;

import java.lang.annotation.Annotation;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/23/15.
 */
public class ProcessorCache {
    private static final String TAG = ProcessorCache.class.getSimpleName();

    private ProcessorCache() {
    }

    private static ProcessorCache instance;

    public synchronized static ProcessorCache getInstance() {
        if (null == instance) {
            instance = new ProcessorCache();
        }
        return instance;
    }

    /**
     * 缓存所有注解的处理器实例
     */
    private ConcurrentHashMap<Class<? extends Annotation>, AIAnnotationProcessor> processorMapper = new ConcurrentHashMap<>();

    public AIAnnotationProcessor getAnnotationProcessor(Class<? extends Annotation> annotationClazz) {
        // 如果缓存中有该处理器，则直接复用
        AIAnnotationProcessor annotationProcessor = processorMapper.get(annotationClazz);
        if (null != annotationProcessor) {
            return annotationProcessor;
        }

        // 如果没有，则先找出该处理器类型，然后实例化并放入缓存中
        AnnoProcessorAlias annotationProcessorAlias = AnnoProcessorAlias.getAnnotationProcessorAlias(annotationClazz);
        Class<? extends AIAnnotationProcessor> annotationProcessorClazz;
        if (null == annotationProcessorAlias || null == (annotationProcessorClazz = annotationProcessorAlias.getProcessorClazz())) {
            return null;
        }
        try {
            annotationProcessor = annotationProcessorClazz.newInstance();
            processorMapper.put(annotationClazz, annotationProcessor);
//            Logger.i(TAG, "processor new instance: " + annotationProcessorClazz.getSimpleName());
        } catch (InstantiationException | IllegalAccessException e) {
            Logger.e(TAG, e);
        }
        return annotationProcessor;
    }


}
