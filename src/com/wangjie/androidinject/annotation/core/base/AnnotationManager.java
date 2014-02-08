package com.wangjie.androidinject.annotation.core.base;

import android.content.Context;
import android.util.Log;
import com.wangjie.androidinject.annotation.present.AIPresent;

/**
 * 注解core，用于实现AIPresent中的注解
 *
 * Created with IntelliJ IDEA.
 * Author: wangjie  email: tiantian.china.2@gmail.com
 * Date: 13-11-29
 * Time: 下午3:52
 */
public class AnnotationManager {
    public static final String TAG = AnnotationManager.class.getSimpleName();

    public static final String FIND_VIEW_METHOD_NAME = "findViewById";
    public static final String SET_LAYOUT_METHOD_NAME = "setContentView";



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
//        Resources res = context.getResources();
        try{

//            initLayoutAnnotation(); // 实现present布局注解功能

            RealizeTypeAnnotation.getInstance(present).processAnnotation(); // 实现present布局注解功能

//            initViewAnnotation(); // 实现present控件注解功能
            RealizeFieldAnnotation.getInstance(present).processAnnotation();

//            initMethodAnnotation(); // 实现present方法注解功能
            RealizeMethodAnnotation.getInstance(present).processAnnotation();


        }catch (Exception ex){
            Log.e(TAG, "annotations init error: ", ex);
        }


    }

    /**************************************布局注解实现 BEGIN*********************************************/



    /**************************************布局注解实现 END*********************************************/

    /**************************************控件注解实现 BEGIN*********************************************/



    /**************************************控件注解实现 END*********************************************/

    /**************************************方法注解实现 BEGIN*********************************************/







    /**************************************方法注解实现 END*********************************************/

}
