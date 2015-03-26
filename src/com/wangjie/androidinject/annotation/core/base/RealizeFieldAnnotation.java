package com.wangjie.androidinject.annotation.core.base;

import com.wangjie.androidbucket.log.Logger;
import com.wangjie.androidinject.annotation.cache.common.CommonCache;
import com.wangjie.androidinject.annotation.cache.common.cached.CachedField;
import com.wangjie.androidinject.annotation.cache.common.cached.CachedPresentFields;
import com.wangjie.androidinject.annotation.cache.common.generator.CachedPresentFieldsGenerator;
import com.wangjie.androidinject.annotation.core.base.process.AIAnnotationProcessor;
import com.wangjie.androidinject.annotation.present.AIPresent;
import com.wangjie.androidinject.annotation.present.common.AnnoProcessorAlias;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: wangjie  email: tiantian.china.2@gmail.com
 * Date: 13-11-30
 * Time: 下午7:23
 * To change this template use File | Settings | File Templates.
 */
public class RealizeFieldAnnotation implements RealizeAnnotation {
    private static final String TAG = RealizeFieldAnnotation.class.getSimpleName();
//    private static Map<Class<?>, RealizeFieldAnnotation> map = new HashMap<Class<?>, RealizeFieldAnnotation>();

    public static RealizeFieldAnnotation getInstance(AIPresent present) {
//        Class clazz = present.getClass();
//        RealizeFieldAnnotation realize = map.get(clazz);
//        if (null == realize) {
//            realize = new RealizeFieldAnnotation();
//            map.put(clazz, realize);
//        }
        RealizeFieldAnnotation realize = new RealizeFieldAnnotation();
        realize.setPresent(present);
        realize.setClazz(present.getClass());
        return realize;
    }


    private AIPresent present;
    private Class<? extends AIPresent> clazz;

    private CachedPresentFieldsGenerator cachedPresentFieldsGenerator = new CachedPresentFieldsGenerator();

    /**
     * 实现present控件注解功能
     *
     * @throws Exception
     */
    @Override
    public void processAnnotation() throws Exception {
//        List<FieldCache.CachedField> cachedFields = FieldCache.getInstance().getCache(clazz);
//        for (FieldCache.CachedField cachedField : cachedFields) {
//            Annotation[] annotations = cachedField.getAnnotations();
//            Field field = cachedField.getField();
//            for (Annotation annotation : annotations) {
//                AIAnnotationProcessor processor = ProcessorCache.getInstance().getAnnotationProcessor(annotation.annotationType());
//                if (null == processor) {
//                    continue;
//                }
//                try {
//                    processor.process(present, field);
//                } catch (Exception ex) {
//                    Logger.e(TAG, ex);
//                }
//            }
//            present.parserFieldAnnotations(field);
//        }


        cachedPresentFieldsGenerator.setClazz(clazz);
        CachedPresentFields cachedPresentFields = CommonCache.getInstance().getCache(CachedPresentFields.class, clazz, cachedPresentFieldsGenerator);

        for (CachedField cachedField : cachedPresentFields.getCachedFields()) {
            Annotation[] annotations = cachedField.getAnnotations();
            Field field = cachedField.getField();
            for (Annotation annotation : annotations) {
                AIAnnotationProcessor processor = AnnoProcessorAlias.getCachedAnnotationProcessor(annotation.annotationType());
                if (null == processor) {
                    continue;
                }
                try {
                    processor.process(present, field);
                } catch (Exception ex) {
                    Logger.e(TAG, ex);
                }
            }
            present.parserFieldAnnotations(field);
        }


    }

    public void setClazz(Class<? extends AIPresent> clazz) {
        this.clazz = clazz;
    }

    public void setPresent(AIPresent present) {
        this.present = present;
    }


}
