package com.wangjie.androidinject.annotation.core.base;

import com.wangjie.androidinject.annotation.core.base.process.AIAnnotationProcessor;
import com.wangjie.androidinject.annotation.present.AIPresent;
import com.wangjie.androidinject.annotation.present.common.AnnoProcessorAlias;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wangjie  email: tiantian.china.2@gmail.com
 * Date: 13-11-30
 * Time: 下午7:23
 * To change this template use File | Settings | File Templates.
 */
public class RealizeTypeAnnotation implements RealizeAnnotation {
    private static final String TAG = RealizeTypeAnnotation.class.getSimpleName();
    private static Map<Class<?>, RealizeTypeAnnotation> map = new HashMap<>();

    public synchronized static RealizeTypeAnnotation getInstance(AIPresent present) {
        Class clazz = present.getClass();
        RealizeTypeAnnotation realize = map.get(clazz);
        if (null == realize) {
            realize = new RealizeTypeAnnotation();
            map.put(clazz, realize);
        }
        realize.setPresent(present);
        realize.setClazz(clazz);
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
            processor.process(present, clazz);
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
