package com.wangjie.androidinject.annotation.core;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import com.wangjie.androidinject.annotation.annotations.AIClick;
import com.wangjie.androidinject.annotation.annotations.AIItemClick;
import com.wangjie.androidinject.annotation.annotations.AILayout;
import com.wangjie.androidinject.annotation.annotations.AILongClick;
import com.wangjie.androidinject.annotation.listener.OnClickViewListener;
import com.wangjie.androidinject.annotation.listener.OnItemClickViewListener;
import com.wangjie.androidinject.annotation.listener.OnLongClickViewListener;
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
public class RealizeMethodAnnotation {
    private static final String TAG = RealizeMethodAnnotation.class.getSimpleName();
    private static Map<Class<?>, RealizeMethodAnnotation> map = new HashMap<Class<?>, RealizeMethodAnnotation>();

    public synchronized static RealizeMethodAnnotation getInstance(AIPresent present){
        Class clazz = present.getClazz();
        RealizeMethodAnnotation realize = map.get(clazz);
        if(null == realize){
            realize = new RealizeMethodAnnotation();
            map.put(clazz, realize);
        }
        realize.setPresent(present);
        realize.setClazz(clazz);
        return realize;
    }


    private AIPresent present;
    private Class<?> clazz;

    public void initMethodAnnotation() throws Exception{
        Method[] methods = clazz.getDeclaredMethods();

        for(Method method : methods){
            if(method.isAnnotationPresent(AIClick.class)){ // 如果设置了点击的注解
                methodBindClick(method); // 绑定某方法设置的所有控件的点击事件
            }
            if(method.isAnnotationPresent(AILongClick.class)){ // 如果设置了长按的注解
                methodBindLongClick(method); // 绑定某方法设置的所有控件的长按事件
            }
            if(method.isAnnotationPresent(AIItemClick.class)){ // 如果设置了item的注解
                methodBindItemClick(method); // 绑定某方法设置的所有控件的item点击事件
            }



        }



    }

    /**
     * 绑定某方法设置的所有控件的点击事件
     * @param method
     * @throws Exception
     */
    private void methodBindClick(Method method) throws Exception{
        AIClick aiClick = method.getAnnotation(AIClick.class);
        int[] ids = aiClick.value();
        if(null == ids || ids.length <=0){
            return;
        }
        Method m = clazz.getMethod(AnnotationManager.FIND_VIEW_METHOD_NAME, int.class);
        for(int id : ids){
            Object obj = m.invoke(present, id);
            if(null == obj || !View.class.isAssignableFrom(obj.getClass())){
                continue;
            }
            ((View)obj).setOnClickListener(OnClickViewListener.obtainListener(present, method.getName()));

        }
    }

    /**
     * 绑定某方法设置的所有控件的长按事件
     * @param method
     * @throws Exception
     */
    private void methodBindLongClick(Method method) throws Exception{
        AILongClick aiLongClick = method.getAnnotation(AILongClick.class);
        int[] ids = aiLongClick.value();
        if(null == ids || ids.length <=0){
            return;
        }
        Method m = clazz.getMethod(AnnotationManager.FIND_VIEW_METHOD_NAME, int.class);
        for(int id : ids){
            Object obj = m.invoke(present, id);
            if(null == obj || !View.class.isAssignableFrom(obj.getClass())){
                continue;
            }
            ((View)obj).setOnLongClickListener(OnLongClickViewListener.obtainListener(present, method.getName()));
        }


    }

    /**
     * 绑定某方法设置的所有控件的item点击事件
     * @param method
     * @throws Exception
     */
    private void methodBindItemClick(Method method) throws Exception{
        AIItemClick aiItemClick = method.getAnnotation(AIItemClick.class);
        int[] ids = aiItemClick.value();
        if(null == ids || ids.length <=0){
            return;
        }
        Method m = clazz.getMethod(AnnotationManager.FIND_VIEW_METHOD_NAME, int.class);
        for(int id : ids){
            Object obj = m.invoke(present, id);
            if(null == obj){
                throw new Exception("new such resource id[" + id + "]");
            }
            if(!AdapterView.class.isAssignableFrom(obj.getClass())){
                throw new Exception("view[" + obj + "] is not AdapterView's subclass");
            }
            AdapterView adapterView = (AdapterView)obj;
            if(!"".equals(method.getName())){
                adapterView.setOnItemClickListener(OnItemClickViewListener.obtainListener(present, method.getName()));
            }
        }


    }




    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public void setPresent(AIPresent present) {
        this.present = present;
    }


}
