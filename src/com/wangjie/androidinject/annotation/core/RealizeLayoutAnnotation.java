package com.wangjie.androidinject.annotation.core;

import android.util.Log;
import com.wangjie.androidinject.annotation.annotations.AILayout;
import com.wangjie.androidinject.annotation.present.AIPresent;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wangjie
 * Date: 13-11-30
 * Time: 下午7:23
 * To change this template use File | Settings | File Templates.
 */
public class RealizeLayoutAnnotation implements RealizeAnnotation{
    private static final String TAG = RealizeLayoutAnnotation.class.getSimpleName();
    private static Map<Class<?>, RealizeLayoutAnnotation> map = new HashMap<Class<?>, RealizeLayoutAnnotation>();

    public synchronized static RealizeLayoutAnnotation getInstance(AIPresent present){
        Class clazz = present.getClazz();
        RealizeLayoutAnnotation realize = map.get(clazz);
        if(null == realize){
            realize = new RealizeLayoutAnnotation();
            map.put(clazz, realize);
        }
        realize.setPresent(present);
        realize.setClazz(clazz);
        return realize;
    }

    private AIPresent present;
    private Class<?> clazz;


    /**
     * 实现present布局注解功能
     * @throws Exception
     */
    @Override
    public void processAnnotation() throws Exception {
        // 布局类注解setContentView
        AILayout cv = clazz.getAnnotation(AILayout.class);
        if(null == cv){
            Log.w(TAG, "Present[" + present + "]had not added @AILayout annotation!");
            return;
        }

        int layoutId = clazz.getAnnotation(AILayout.class).value();
        Method method = clazz.getMethod(AnnotationManager.SET_LAYOUT_METHOD_NAME, int.class);
        method.invoke(present, layoutId);

    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public void setPresent(AIPresent present) {
        this.present = present;
    }



}
