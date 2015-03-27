package com.wangjie.androidinject.annotation.annotations.net;

import com.wangjie.androidbucket.services.network.http.HttpAccessParameter;
import com.wangjie.androidbucket.services.network.http.HttpConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Hubert He
 * @version V1.0
 * @Description
 * @Createdate 14-9-5 10:34
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AIRaw {

    String value() default "";

    /**
     * connectionTimeout, 连接一个url的连接等待时间
     *
     * @return 默认返回-1，时间由全局变量实现, Bucket包中HttpConfig配置
     */
    int connTimeout() default -1;

    /**
     * SocketTimeout, 连接上一个url，获取response的返回等待时间
     *
     * @return 默认返回-1，时间由全局变量实现, Bucket包中HttpConfig配置
     */
    int soTimeout() default -1;

    String contentType() default HttpConstants.CONTENT_TYPE_JSON;

    /**
     * 是否session连接
     *
     * @return 默认返回true
     */
    HttpAccessParameter.SessionEnableMethod sessionEnable() default HttpAccessParameter.SessionEnableMethod.AUTO;

}
