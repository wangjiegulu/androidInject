package com.wangjie.androidinject.annotation;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.wangjie.androidinject.annotation.annotations.InitLayout;
import com.wangjie.androidinject.annotation.annotations.InitView;
import com.wangjie.androidinject.annotation.listener.OnClickViewListener;
import com.wangjie.androidinject.annotation.listener.OnItemClickViewListener;
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
                InitView annView = field.getAnnotation(InitView.class);

                viewFindAnnontation(annView, field); // 绑定控件注解

                View view = (View)field.get(activity);

                viewBindClick(annView, view); // 绑定控件点击事件注解

                viewBindLongClick(annView, view); // 绑定控件点击事件注解

                viewBindItemClick(annView, view); // 绑定控件item点击事件注解


            }


        }
    }

    /**
     * 绑定控件注解
     * @param annView
     * @param field
     * @throws Exception
     */
    private void viewFindAnnontation(InitView annView, Field field) throws Exception{
        int viewId = annView.id(); // 绑定控件注解
        field.setAccessible(true);
        Method method = clazz.getMethod("findViewById", int.class);
        field.set(activity, method.invoke(activity, viewId));
    }

    /**
     * 绑定控件点击事件注解
     * @param annView
     * @param view
     */
    private void viewBindClick(InitView annView, View view){
        String clickMethodName = annView.clickMethod();
        if(!"".equals(clickMethodName)){
            view.setOnClickListener(OnClickViewListener.obtainListener(activity, clickMethodName));
        }
    }

    /**
     * 绑定控件点击事件注解
     * @param annView
     * @param view
     */
    private void viewBindLongClick(InitView annView, View view){
        String longClickMethodName = annView.longClickMethod();
        if(!"".equals(longClickMethodName)){
            view.setOnLongClickListener(OnLongClickViewListener.obtainListener(activity, longClickMethodName));
        }
    }

    /**
     * 绑定控件item点击事件注解
     * @param annView
     * @param view
     */
    private void viewBindItemClick(InitView annView, View view){
        // 如果view是AdapterView的子类(ListView, GridView, ExpandableListView...)
        if(AdapterView.class.isAssignableFrom(view.getClass())){
            AdapterView adapterView = (AdapterView)view;
            String itemClickMethodName = annView.itemClickMethod();
            if(!"".equals(itemClickMethodName)){
                adapterView.setOnItemClickListener(OnItemClickViewListener.obtainListener(activity, itemClickMethodName));
            }
        }

    }



}
