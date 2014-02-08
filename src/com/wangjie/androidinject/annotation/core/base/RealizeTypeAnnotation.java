package com.wangjie.androidinject.annotation.core.base;

import android.app.Activity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import com.wangjie.androidinject.annotation.annotations.base.AIFullScreen;
import com.wangjie.androidinject.annotation.annotations.base.AILayout;
import com.wangjie.androidinject.annotation.annotations.base.AINoTitle;
import com.wangjie.androidinject.annotation.present.AIPresent;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wangjie  email: tiantian.china.2@gmail.com
 * Date: 13-11-30
 * Time: 下午7:23
 * To change this template use File | Settings | File Templates.
 */
public class RealizeTypeAnnotation implements RealizeAnnotation{
    private static final String TAG = RealizeTypeAnnotation.class.getSimpleName();
    private static Map<Class<?>, RealizeTypeAnnotation> map = new HashMap<Class<?>, RealizeTypeAnnotation>();

    public synchronized static RealizeTypeAnnotation getInstance(AIPresent present){
        Class clazz = present.getClass();
        RealizeTypeAnnotation realize = map.get(clazz);
        if(null == realize){
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
     * @throws Exception
     */
    @Override
    public void processAnnotation() throws Exception {

        if(clazz.isAnnotationPresent(AIFullScreen.class)){ // 全屏注解
            fullScreenBind();
        }

        if(clazz.isAnnotationPresent(AINoTitle.class)){ // 不显示Title注解
            noTitleBind();
        }

        if(clazz.isAnnotationPresent(AILayout.class)){ // 布局注解
            layoutBind();
        }


    }

    /**
     * 实现全屏注解
     * @throws Exception
     */
    private void fullScreenBind() throws Exception{
        if(!Activity.class.isAssignableFrom(clazz)){ // 如果不是Activity
            throw new Exception(clazz.getName() + " is not Activity ! can not use @AIFullScreen Annotation. ");
        }

        // 设置Activity全屏
        ((Activity)present.getContext()).getWindow()
                .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    /**
     * 实现不显示title注解
     * @throws Exception
     */
    private void noTitleBind() throws Exception{
        if(!Activity.class.isAssignableFrom(clazz)){ // 如果不是Activity
            throw new Exception(clazz.getName() + " is not Activity ! can not use @AINoTitle Annotation. ");
        }

        // 设置不显示Title
        ((Activity)present.getContext()).requestWindowFeature(Window.FEATURE_NO_TITLE);

    }

    /**
     * 实现布局注解
     * @throws Exception
     */
    private void layoutBind() throws Exception{
        if(!Activity.class.isAssignableFrom(clazz)){ // 如果不是Activity
            Log.i(TAG, present.getClass() + " layout bind unsuccessed in " + TAG);
            return;
        }

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
