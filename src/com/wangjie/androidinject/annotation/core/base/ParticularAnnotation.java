package com.wangjie.androidinject.annotation.core.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.wangjie.androidinject.annotation.annotations.base.AILayout;
import com.wangjie.androidinject.annotation.present.AIPresent;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email: tiantian.china.2@gmail.com
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
