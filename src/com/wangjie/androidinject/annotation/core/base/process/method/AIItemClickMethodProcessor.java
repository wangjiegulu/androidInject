package com.wangjie.androidinject.annotation.core.base.process.method;

import android.widget.AdapterView;
import com.wangjie.androidinject.annotation.annotations.base.AIItemClick;
import com.wangjie.androidinject.annotation.core.base.AnnotationManager;
import com.wangjie.androidinject.annotation.core.base.process.AIAnnotationProcessor;
import com.wangjie.androidinject.annotation.listener.OnItemClickViewListener;
import com.wangjie.androidinject.annotation.present.AIPresent;

import java.lang.reflect.Method;

/**
 * 绑定某方法设置的所有控件的item点击事件
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2/4/15.
 */
public class AIItemClickMethodProcessor implements AIAnnotationProcessor<Method> {
    @Override
    public void process(AIPresent present, Method method) throws Exception {
        AIItemClick aiItemClick = method.getAnnotation(AIItemClick.class);
        int[] ids = aiItemClick.value();
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
                adapterView.setOnItemClickListener(OnItemClickViewListener.obtainListener(present, method.getName()));
            }
        }
    }
}
