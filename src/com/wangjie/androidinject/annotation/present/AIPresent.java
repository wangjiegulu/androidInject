package com.wangjie.androidinject.annotation.present;

import android.content.Context;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: wangjie  email: tiantian.china.2@gmail.com
 * Date: 13-11-30
 * Time: 下午7:40
 * To change this template use File | Settings | File Templates.
 */
public interface AIPresent {

    public Context getContext();

    /**
     * 调用findviewbyid方法进行绑定的对象
     *
     * @return
     */
    public Object getFindViewView();

    /**
     * 类型注解额外的解析操作，这个可以交给子类去实现
     *
     * @param clazz
     */
    public void parserTypeAnnotations(Class clazz) throws Exception;

    /**
     * 方法注解额外的解析操作，这个可以交给子类去实现
     *
     * @param method
     */
    public void parserMethodAnnotations(Method method) throws Exception;

    /**
     * 属性注解额外的解析操作，这个可以交给子类去实现
     *
     * @param field
     */
    public void parserFieldAnnotations(Field field) throws Exception;


}
