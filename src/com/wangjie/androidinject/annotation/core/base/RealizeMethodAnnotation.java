package com.wangjie.androidinject.annotation.core.base;

import com.wangjie.androidbucket.log.Logger;
import com.wangjie.androidinject.annotation.cache.MethodCache;
import com.wangjie.androidinject.annotation.cache.ProcessorCache;
import com.wangjie.androidinject.annotation.core.base.process.AIAnnotationProcessor;
import com.wangjie.androidinject.annotation.present.AIPresent;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

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

    @Override
    public void processAnnotation() throws Exception {
        List<MethodCache.CachedMethod> cachedMethods = MethodCache.getInstance().getCache(clazz);

        for (MethodCache.CachedMethod cachedMethod : cachedMethods) {
            Annotation[] annotations = cachedMethod.getAnnotations();
            Method method = cachedMethod.getMethod();
            for (Annotation annotation : annotations) {
                AIAnnotationProcessor processor = ProcessorCache.getInstance().getAnnotationProcessor(annotation.annotationType());
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
