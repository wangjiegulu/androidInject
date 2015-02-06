package com.wangjie.androidinject.annotation.core.base;

import com.wangjie.androidinject.annotation.core.base.process.AIAnnotationProcessor;
import com.wangjie.androidinject.annotation.present.AIPresent;
import com.wangjie.androidinject.annotation.present.common.AnnoProcessorAlias;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wangjie  email: tiantian.china.2@gmail.com
 * Date: 13-11-30
 * Time: 下午7:23
 * To change this template use File | Settings | File Templates.
 */
public class RealizeFieldAnnotation implements RealizeAnnotation {
    private static final String TAG = RealizeFieldAnnotation.class.getSimpleName();
    private static Map<Class<?>, RealizeFieldAnnotation> map = new HashMap<Class<?>, RealizeFieldAnnotation>();

    public synchronized static RealizeFieldAnnotation getInstance(AIPresent present) {
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
    private Class<?> clazz;

    /**
     * 实现present控件注解功能
     *
     * @throws Exception
     */
    @Override
    public void processAnnotation() throws Exception {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations) {
                Class<? extends AIAnnotationProcessor> processorClass = AnnoProcessorAlias.getAnnotationProcessor(annotation.annotationType());
                if (null == processorClass) {
                    continue;
                }
                AIAnnotationProcessor processor = processorClass.newInstance();
                processor.process(present, field);
            }
            present.parserFieldAnnotations(field);
        }
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public void setPresent(AIPresent present) {
        this.present = present;
    }


}
