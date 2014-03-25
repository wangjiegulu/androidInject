package com.wangjie.androidinject.annotation.util;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 14-2-8
 * Time: 上午10:41
 */
public class Params extends HashMap<String, String>{


    public Params add(String key, String value){
        put(key, value);
        return this;
    }



}
