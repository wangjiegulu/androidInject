package com.wangjie.androidinject.annotation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:wangjie@cyyun.com
 * Date: 13-12-2
 * Time: 上午10:29
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AIBean {

}
