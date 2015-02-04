package com.wangjie.androidinject.annotation.core.base.process.type;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import com.wangjie.androidbucket.customviews.sublayout.SubLayout;
import com.wangjie.androidinject.annotation.annotations.base.AILayout;
import com.wangjie.androidinject.annotation.core.base.AnnotationManager;
import com.wangjie.androidinject.annotation.core.base.process.AIAnnotationProcessor;
import com.wangjie.androidinject.annotation.present.AIPresent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 实现布局注解
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2/4/15.
 */
public class AILayoutTypeProcessor implements AIAnnotationProcessor<Class<?>> {
    private static final String TAG = AILayoutTypeProcessor.class.getSimpleName();

    @Override
    public void process(AIPresent present, Class<?> clazz) throws Exception {
        // 如果不是Activity和SubLayout
        if (!Activity.class.isAssignableFrom(clazz) && !SubLayout.class.isAssignableFrom(clazz)) {
            Log.d(TAG, present.getClass() + " layout bind unsuccessed in " + TAG);
            return;
        }

        // 布局类注解setContentView
        AILayout cv = clazz.getAnnotation(AILayout.class);
        if (null == cv) {
            Log.w(TAG, "Present[" + present + "]had not added @AILayout annotation!");
            return;
        }

        int layoutId = cv.value();

        // 如果是SubLayout
        if (SubLayout.class.isAssignableFrom(clazz)) {
            Field field = clazz.getField(AnnotationManager.FIELD_LAYOUT);
            field.setAccessible(true);
            field.set(present, LayoutInflater.from(present.getContext()).inflate(layoutId, null));
            return;
        }

        // 如果是Activity
        Method method = clazz.getMethod(AnnotationManager.METHOD_NAME_SET_LAYOUT, int.class);
        method.invoke(present, layoutId);
    }
}
