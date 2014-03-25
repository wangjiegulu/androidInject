package com.wangjie.androidinject.annotation.annotations.orm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 14-3-24
 * Time: 下午4:39
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AIPrimaryKey
{
    /**
     * 表示插入数据时是否同时也插入主键到表。默认为false，即表的主键应该为自动生成
     * @return
     */
    boolean insertable() default false;

}
