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
            throw new Exception("@AILongClick[" + method.getName() + "] value(ids) can not be empty!");
        }
        for (int id : ids) {
            Object obj = present.findViewById_(id);
            if (null == obj) {
                throw new Exception("new such resource id[" + id + "]");
            }
            if(!View.class.isAssignableFrom(obj.getClass())){
                throw new Exception("view[" + obj + "] is not View's subclass");
            }
            ((View) obj).setOnLongClickListener(OnLongClickViewListener.obtainListener(present, method.getName()));
        }
    }
}
