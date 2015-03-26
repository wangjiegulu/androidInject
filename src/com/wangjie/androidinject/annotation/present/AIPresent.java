package com.wangjie.androidinject.annotation.present;

import android.content.Context;
import android.view.View;
import com.wangjie.androidbucket.mvp.TaskController;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: wangjie  email: tiantian.china.2@gmail.com
 * Date: 13-11-30
 * Time: 下午7:40
 * To change this template use File | Settings | File Templates.
 */
public interface AIPresent extends TaskController {

    Context getContext();

    void setContentView_(int layoutResID);

    View findViewById_(int resId);

    /**
     * 类型注解额外的解析操作，这个可以交给子类去实现
     *
     * @param clazz
     */
    void parserTypeAnnotations(Class clazz) throws Exception;

    /**
     * 方法注解额外的解析操作，这个可以交给子类去实现
     *
     * @param method
     */
    void parserMethodAnnotations(Method method) throws Exception;

    /**
     * 属性注解额外的解析操作，这个可以交给子类去实现
     *
     * @param field
     */
    void parserFieldAnnotations(Field field) throws Exception;


}
