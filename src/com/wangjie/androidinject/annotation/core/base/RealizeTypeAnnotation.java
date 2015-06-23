package com.wangjie.androidinject.annotation.core.base;

import com.wangjie.androidinject.annotation.cache.common.CommonCache;
import com.wangjie.androidinject.annotation.cache.common.cached.CachedPresentType;
import com.wangjie.androidinject.annotation.cache.common.generator.CachedPresentTypeGenerator;
import com.wangjie.androidinject.annotation.core.base.process.AIAnnotationProcessor;
import com.wangjie.androidinject.annotation.present.AIPresent;
import com.wangjie.androidinject.annotation.present.common.AnnoProcessorAlias;

import java.lang.annotation.Annotation;

/**
 * Created with IntelliJ IDEA.
 * User: wangjie  email: tiantian.china.2@gmail.com
 * Date: 13-11-30
 * Time: 下午7:23
 * To change this template use File | Settings | File Templates.
 */
public class RealizeTypeAnnotation implements RealizeAnnotation {
    private static final String TAG = RealizeTypeAnnotation.class.getSimpleName();
//    private static Map<Class<?>, RealizeTypeAnnotation> map = new HashMap<>();

    public static RealizeTypeAnnotation getInstance(AIPresent present) {
//        Class clazz = present.getClass();
//        RealizeTypeAnnotation realize = map.get(clazz);
//        if (null == realize) {
//            realize = new RealizeTypeAnnotation();
//            map.put(clazz, realize);
//        }
//        realize.setPresent(present);
//        realize.setClazz(clazz);
        RealizeTypeAnnotation realize = new RealizeTypeAnnotation();
        realize.setPresent(present);
        realize.setClazz(present.getClass());
        return realize;
    }

    private AIPresent present;
    private Class<? extends AIPresent> clazz;

    private CachedPresentTypeGenerator cachedPresentTypeGenerator = new CachedPresentTypeGenerator();

    /**
     * 实现present类注解功能
     *
     * @throws Exception
     */
    @Override
    public void processAnnotation() throws Exception {
        cachedPresentTypeGenerator.setClazz(clazz);
        CachedPresentType cachedPresentType = CommonCache.getInstance().getCache(CachedPresentType.class, clazz, cachedPresentTypeGenerator);

        Annotation[] annotations = cachedPresentType.getAnnotations();
        for (Annotation annotation : annotations) {
            AIAnnotationProcessor processor = AnnoProcessorAlias.getCachedAnnotationProcessor(annotation.annotationType());
            if (null == processor) {
                continue;
            }
//            try {
            processor.process(present, clazz);
//            } catch (Exception ex) {
//                Logger.e(TAG, ex);
//            }
        }
        present.parserTypeAnnotations(clazz);

    }


    public void setClazz(Class<? extends AIPresent> clazz) {
        this.clazz = clazz;
    }

    public void setPresent(AIPresent present) {
        this.present = present;
    }


}
