package com.wangjie.androidinject.annotation.core;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.wangjie.androidinject.annotation.annotations.AILayout;
import com.wangjie.androidinject.annotation.present.AIPresent;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:wangjie@cyyun.com
 * Date: 13-12-4
 * Time: 下午5:12
 */
public class ParticularAnnotation {

    public static View realizeLayoutAnnotation(AIPresent present, LayoutInflater inflater, ViewGroup container){
        if(!present.getClass().isAnnotationPresent(AILayout.class)){
            return null;
        }

        AILayout aiLayout = present.getClass().getAnnotation(AILayout.class);
        return inflater.inflate(aiLayout.value(), container, false);

    }


}
