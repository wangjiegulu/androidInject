package com.wangjie.androidinject.annotation.core.base.process.method;

import android.view.View;
import com.wangjie.androidinject.annotation.annotations.base.AILongClick;
import com.wangjie.androidinject.annotation.core.base.process.AIAnnotationProcessor;
import com.wangjie.androidinject.annotation.listener.OnLongClickViewListener;
import com.wangjie.androidinject.annotation.present.AIPresent;

import java.lang.reflect.Method;

/**
 * 绑定某方法设置的所有控件的长按事件
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2/4/15.
 */
public class AILongClickMethodProcessor implements AIAnnotationProcessor<Method> {
    @Override
    public void process(AIPresent present, Method method) throws Exception {
        AILongClick aiLongClick = method.getAnnotation(AILongClick.class);
        int[] ids = aiLongClick.value();
        if (null == ids || ids.length <= 0) {
            return;
        }
        for (int id : ids) {
            Object obj = present.findViewById_(id);
            if (null == obj || !View.class.isAssignableFrom(obj.getClass())) {
                continue;
            }
            ((View) obj).setOnLongClickListener(OnLongClickViewListener.obtainListener(present, method.getName()));
        }
    }
}
