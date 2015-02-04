package com.wangjie.androidinject.annotation.core.base.process.method;

import android.widget.AdapterView;
import com.wangjie.androidinject.annotation.annotations.base.AIItemLongClick;
import com.wangjie.androidinject.annotation.core.base.AnnotationManager;
import com.wangjie.androidinject.annotation.core.base.process.AIAnnotationProcessor;
import com.wangjie.androidinject.annotation.listener.OnItemLongClickViewListener;
import com.wangjie.androidinject.annotation.present.AIPresent;

import java.lang.reflect.Method;

/**
 * 绑定某方法设置的所有控件的item长按事件
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2/4/15.
 */
public class AIItemLongClickMethodProcessor implements AIAnnotationProcessor<Method> {
    @Override
    public void process(AIPresent present, Method method) throws Exception {
        AIItemLongClick aiItemLongClick = method.getAnnotation(AIItemLongClick.class);
        int[] ids = aiItemLongClick.value();
        if (null == ids || ids.length <= 0) {
            return;
        }
        Method m = present.getFindViewView().getClass().getMethod(AnnotationManager.METHOD_NAME_FIND_VIEW, int.class);
        for (int id : ids) {
            Object obj = m.invoke(present.getFindViewView(), id);
            if (null == obj) {
                throw new Exception("new such resource id[" + id + "]");
            }
            if (!AdapterView.class.isAssignableFrom(obj.getClass())) {
                throw new Exception("view[" + obj + "] is not AdapterView's subclass");
            }
            AdapterView adapterView = (AdapterView) obj;
            if (!"".equals(method.getName())) {
                adapterView.setOnItemLongClickListener(OnItemLongClickViewListener.obtainListener(present, method.getName()));
            }
        }
    }
}
