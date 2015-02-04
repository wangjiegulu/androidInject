package com.wangjie.androidinject.annotation.core.base.process.type;

import android.app.Activity;
import android.view.WindowManager;
import com.wangjie.androidinject.annotation.core.base.process.AIAnnotationProcessor;
import com.wangjie.androidinject.annotation.present.AIPresent;

/**
 * 实现全屏注解
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2/4/15.
 */
public class AIFullScreenTypeProcessor implements AIAnnotationProcessor<Class<?>> {
    @Override
    public void process(AIPresent present, Class<?> clazz) throws Exception {
        if (!Activity.class.isAssignableFrom(clazz)) { // 如果不是Activity
            throw new Exception(clazz.getName() + " is not Activity ! can not use @AIFullScreen Annotation. ");
        }

        // 设置Activity全屏
        ((Activity) present.getContext()).getWindow()
                .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
