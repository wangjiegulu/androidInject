package com.wangjie.androidinject.annotation.core.base.process.type;

import android.app.Activity;
import android.view.Window;
import com.wangjie.androidinject.annotation.core.base.process.AIAnnotationProcessor;
import com.wangjie.androidinject.annotation.present.AIPresent;

/**
 * 实现不显示title注解
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2/4/15.
 */
public class AINoTitleTypeProcessor implements AIAnnotationProcessor<Class<?>> {
    @Override
    public void process(AIPresent present, Class<?> clazz) throws Exception {
        if (!Activity.class.isAssignableFrom(clazz)) { // 如果不是Activity
            throw new Exception(clazz.getName() + " is not Activity ! can not use @AINoTitle Annotation. ");
        }

        // 设置不显示Title
        ((Activity) present.getContext()).requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
}
