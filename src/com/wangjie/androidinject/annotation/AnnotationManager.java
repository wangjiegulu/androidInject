package com.wangjie.androidinject.annotation;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import com.wangjie.androidinject.annotation.annotations.InitLayout;
import com.wangjie.androidinject.annotation.annotations.InitView;
import com.wangjie.androidinject.annotation.listener.OnClickViewListener;
import com.wangjie.androidinject.annotation.listener.OnLongClickViewListener;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 注解core，用于实现Activity中的注解
 *
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmial.com
 * Date: 13-11-29
 * Time: 下午3:52
 */
public class AnnotationManager {
    public static final String TAG = AnnotationManager.class.getSimpleName();
    private Context context;
    private Activity activity;
    private Class<?> clazz;

    public AnnotationManager(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        this.clazz = activity.getClass();
    }

    /**
     * 反射实现注解功能
     */
    public void initAnnotations() {
//        Resources res = context.getResources();
        try{

            initLayoutAnnotation(); // 实现activity布局注解功能

            initViewAnnotation(); // 实现activity控件注解功能

        }catch (Exception ex){
            Log.e(TAG, "ex: ", ex);
        }


    }

    /**
     * 实现activity布局注解功能
     * @throws Exception
     */
    private void initLayoutAnnotation() throws Exception{
        // 布局类注解setContentView
        InitLayout cv = clazz.getAnnotation(InitLayout.class);
        if(null == cv){
            return;
        }

        int layoutId = clazz.getAnnotation(InitLayout.class).value();
        Method method = clazz.getMethod("setContentView", int.class);
        method.invoke(activity, layoutId);

    }

    /**
     * 实现activity控件注解功能
     * @throws Exception
     */
    private void initViewAnnotation() throws Exception{
        // 属性控件注解findViewById
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            if(field.isAnnotationPresent(InitView.class)){

                // 绑定控件注解
                InitView annView = field.getAnnotation(InitView.class);
                int viewId = annView.id();
                field.setAccessible(true);
                Method method = clazz.getMethod("findViewById", int.class);
                field.set(activity, method.invoke(activity, viewId));

                View view = (View)field.get(activity);

                // 绑定控件点击事件注解
                String clickMethodName = annView.clickMethod();
                if(!"".equals(clickMethodName)){
                    view.setOnClickListener(OnClickViewListener.obtainListener(activity, clickMethodName));
                }

                // 绑定控件点击事件注解
                String longClickMethodName = annView.longClickMethod();
                if(!"".equals(longClickMethodName)){
                    view.setOnLongClickListener(OnLongClickViewListener.obtainListener(activity, longClickMethodName));
                }


            }


        }
    }



}
