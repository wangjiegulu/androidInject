package com.wangjie.androidinject.annotation.annotations.orm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:wangjie@cyyun.com
 * Date: 14-3-24
 * Time: 下午4:39
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AIColumn
{
    /**
     * 表示要映射到的表字段名称，不填写则默认以属性名作为表字段名
     * @return
     */
    String value() default "";

}
