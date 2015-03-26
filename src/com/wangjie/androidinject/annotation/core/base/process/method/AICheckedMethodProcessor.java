package com.wangjie.androidinject.annotation.core.base.process.method;

import android.widget.CompoundButton;
import com.wangjie.androidinject.annotation.annotations.base.AIChecked;
import com.wangjie.androidinject.annotation.core.base.process.AIAnnotationProcessor;
import com.wangjie.androidinject.annotation.listener.OnCheckChangedViewListener;
import com.wangjie.androidinject.annotation.present.AIPresent;

import java.lang.reflect.Method;

/**
 * 绑定某方法设置所有控件的CheckedChange事件
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2/4/15.
 */
public class AICheckedMethodProcessor implements AIAnnotationProcessor<Method> {
    @Override
    public void process(AIPresent present, Method method) throws Exception {
        AIChecked aiClick = method.getAnnotation(AIChecked.class);
        int[] ids = aiClick.value();
        if (null == ids || ids.length <= 0) {
            return;
        }
        for (int id : ids) {
            Object obj = present.findViewById_(id);
            if (null == obj || !CompoundButton.class.isAssignableFrom(obj.getClass())) {
                continue;
            }
            ((CompoundButton) obj).setOnCheckedChangeListener(OnCheckChangedViewListener.obtainListener(present, method.getName()));
        }
    }
}
