package com.wangjie.androidinject.annotation.core.base;

import android.content.Context;
import android.util.Log;
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

    public static final String METHOD_NAME_FIND_VIEW = "findViewById";
    public static final String METHOD_NAME_SET_LAYOUT = "setContentView";
    public static final String FIELD_LAYOUT = "layout";


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
    public void initAnnotations() {
        try {

            RealizeTypeAnnotation.getInstance(present).processAnnotation();

            RealizeFieldAnnotation.getInstance(present).processAnnotation();

            RealizeMethodAnnotation.getInstance(present).processAnnotation();

        } catch (Exception ex) {
            Log.e(TAG, "annotations init error: ", ex);
        }


    }

}
