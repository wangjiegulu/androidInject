package com.wangjie.androidinject.annotation.core.base;

import com.wangjie.androidbucket.log.Logger;
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
    private Class<?> clazz;


    /**
     * 实现present类注解功能
     *
     * @throws Exception
     */
    @Override
    public void processAnnotation() throws Exception {
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {
            Class<? extends AIAnnotationProcessor> processorClass = AnnoProcessorAlias.getAnnotationProcessor(annotation.annotationType());
            if (null == processorClass) {
                continue;
            }
            AIAnnotationProcessor processor = processorClass.newInstance();
            if (null == processor) {
                continue;
            }
            try {
                processor.process(present, clazz);
            } catch (Exception ex) {
                Logger.e(TAG, ex);
            }
        }
        present.parserTypeAnnotations(clazz);

    }


    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public void setPresent(AIPresent present) {
        this.present = present;
    }


}
