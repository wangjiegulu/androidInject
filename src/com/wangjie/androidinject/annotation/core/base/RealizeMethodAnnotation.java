package com.wangjie.androidinject.annotation.core.base;

import com.wangjie.androidbucket.log.Logger;
import com.wangjie.androidinject.annotation.cache.common.CommonCache;
import com.wangjie.androidinject.annotation.cache.common.cached.CachedMethod;
import com.wangjie.androidinject.annotation.cache.common.cached.CachedPresentMethods;
import com.wangjie.androidinject.annotation.cache.common.generator.CachedPresentMethodsGenerator;
import com.wangjie.androidinject.annotation.core.base.process.AIAnnotationProcessor;
import com.wangjie.androidinject.annotation.present.AIPresent;
import com.wangjie.androidinject.annotation.present.common.AnnoProcessorAlias;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: wangjie  email: tiantian.china.2@gmail.com
 * Date: 13-11-30
 * Time: 下午7:23
 * To change this template use File | Settings | File Templates.
 */
public class RealizeMethodAnnotation implements RealizeAnnotation {
    private static final String TAG = RealizeMethodAnnotation.class.getSimpleName();
//    private static Map<Class<?>, RealizeMethodAnnotation> map = new HashMap<Class<?>, RealizeMethodAnnotation>();

    public static RealizeMethodAnnotation getInstance(AIPresent present) {
//        Class clazz = present.getClass();
//        RealizeMethodAnnotation realize = map.get(clazz);
//        if (null == realize) {
//            realize = new RealizeMethodAnnotation();
//            map.put(clazz, realize);
//        }
        RealizeMethodAnnotation realize = new RealizeMethodAnnotation();
        realize.setPresent(present);
        realize.setClazz(present.getClass());
        return realize;
    }


    private AIPresent present;
    private Class<? extends AIPresent> clazz;
    private CachedPresentMethodsGenerator cachedPresentFieldsGenerator = new CachedPresentMethodsGenerator();

    @Override
    public void processAnnotation() throws Exception {
//        List<MethodCache.CachedMethod> cachedMethods = MethodCache.getInstance().getCache(clazz);
//
//        for (MethodCache.CachedMethod cachedMethod : cachedMethods) {
//            Annotation[] annotations = cachedMethod.getAnnotations();
//            Method method = cachedMethod.getMethod();
//            for (Annotation annotation : annotations) {
//                Class<? extends AIAnnotationProcessor> processorClass = AnnoProcessorAlias.getAnnotationProcessor(annotation.annotationType());
//                if (null == processorClass) {
//                    continue;
//                }
//                AIAnnotationProcessor processor = processorClass.newInstance();
//                if (null == processor) {
//                    continue;
//                }
//                try {
//                    processor.process(present, method);
//                } catch (Exception ex) {
//                    Logger.e(TAG, ex);
//                }
//            }
//
//            present.parserMethodAnnotations(method);
//        }
        cachedPresentFieldsGenerator.setClazz(clazz);
        CachedPresentMethods cachedPresentMethods = CommonCache.getInstance().getCache(CachedPresentMethods.class, clazz, cachedPresentFieldsGenerator);
        for (CachedMethod cachedMethod : cachedPresentMethods.getCachedMethods()) {
            Annotation[] annotations = cachedMethod.getAnnotations();
            Method method = cachedMethod.getMethod();
            for (Annotation annotation : annotations) {
                AIAnnotationProcessor processor = AnnoProcessorAlias.getCachedAnnotationProcessor(annotation.annotationType());
                if (null == processor) {
                    continue;
                }
                try {
                    processor.process(present, method);
                } catch (Exception ex) {
                    Logger.e(TAG, ex);
                }
            }
            present.parserMethodAnnotations(method);
        }


    }


    public void setClazz(Class<? extends AIPresent> clazz) {
        this.clazz = clazz;
    }

    public void setPresent(AIPresent present) {
        this.present = present;
    }


}
