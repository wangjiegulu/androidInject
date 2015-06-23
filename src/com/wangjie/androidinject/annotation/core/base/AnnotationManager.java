package com.wangjie.androidinject.annotation.core.base;

import android.content.Context;
import com.wangjie.androidbucket.log.Logger;
import com.wangjie.androidinject.annotation.present.AIPresent;

/**
 * 注解core，用于实现AIPresent中的注解
 * <p/>
 * Created with IntelliJ IDEA.
 * Author: wangjie  email: tiantian.china.2@gmail.com
 * Date: 13-11-29
 * Time: 下午3:52
 */
public class AnnotationManager {
    public static final String TAG = AnnotationManager.class.getSimpleName();

    private Context context;
    private AIPresent present;
    private Class<?> clazz;

    public AnnotationManager(AIPresent present) {
        this.context = present.getContext();
        this.present = present;
        this.clazz = present.getClass();
    }

    /**
     * 反射实现注解功能
     */
    public void initAnnotations() throws Exception {
        Logger.i(TAG, "[=============================================");
        long start = System.nanoTime();
        RealizeTypeAnnotation.getInstance(present).processAnnotation();
        Logger.i(TAG, clazz.getSimpleName() + ", realize type takes: " + (System.nanoTime() - start));

        start = System.nanoTime();
        RealizeFieldAnnotation.getInstance(present).processAnnotation();
        Logger.i(TAG, clazz.getSimpleName() + ", realize field takes: " + (System.nanoTime() - start));

        start = System.nanoTime();
        RealizeMethodAnnotation.getInstance(present).processAnnotation();
        Logger.i(TAG, clazz.getSimpleName() + ", realize method takes: " + (System.nanoTime() - start));
        Logger.i(TAG, "=============================================]");
    }

}
