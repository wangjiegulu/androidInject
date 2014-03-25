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
@Target(ElementType.TYPE)
public @interface AITable
{
    /**
     * 表示要映射到的表的名称，不填写或未增加该注解则默认以类名小写为表名
     * @return
     */
    String value() default "";

}
