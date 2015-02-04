package com.wangjie.androidinject.annotation.core.base.process.field;

import android.content.res.Resources;
import android.view.View;
import android.widget.AdapterView;
import com.wangjie.androidinject.annotation.annotations.base.AIView;
import com.wangjie.androidinject.annotation.core.base.AnnotationManager;
import com.wangjie.androidinject.annotation.core.base.process.AIAnnotationProcessor;
import com.wangjie.androidinject.annotation.listener.OnClickViewListener;
import com.wangjie.androidinject.annotation.listener.OnItemClickViewListener;
import com.wangjie.androidinject.annotation.listener.OnItemLongClickViewListener;
import com.wangjie.androidinject.annotation.listener.OnLongClickViewListener;
import com.wangjie.androidinject.annotation.present.AIPresent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2/4/15.
 */
public class AIViewFieldProcessor implements AIAnnotationProcessor<Field> {
    @Override
    public void process(AIPresent present, Field field) throws Exception {
        AIView aiView = field.getAnnotation(AIView.class);

        viewFindAnnontation(present, aiView, field); // 绑定控件注解

        View view = (View) field.get(present);

        viewBindClick(present, aiView, view); // 绑定控件点击事件注解

        viewBindLongClick(present, aiView, view); // 绑定控件点击事件注解

        viewBindItemClick(present, aiView, view); // 绑定控件item点击事件注解

        viewBindItemLongClick(present, aiView, view);
    }

    /**
     * 绑定控件注解
     *
     * @param aiView
     * @param field
     * @throws Exception
     */
    private void viewFindAnnontation(AIPresent present, AIView aiView, Field field) throws Exception {
        int viewId = aiView.value(); // 绑定控件注解
        // @AIView注解的value和id值均代表控件redId，如果之前的value是-1，则使用id值
        if (-1 == viewId) {
            viewId = aiView.id();
        }

        if (-1 == viewId) { // 如果resId没有设置，则默认查找id名跟属性名相同的id
            Resources res = present.getContext().getResources();
            viewId = res.getIdentifier(field.getName(), "id", present.getContext().getPackageName());
            if (0 == viewId) { // 属性同名的id没有找到
                throw new Exception("no such identifier[R.id." + field.getName() + "] ! ");
            }
        }

        field.setAccessible(true);
        Method method = present.getFindViewView().getClass().getMethod(AnnotationManager.METHOD_NAME_FIND_VIEW, int.class);
        try {
            field.set(present, method.invoke(present.getFindViewView(), viewId));
        } catch (Exception ex) {
            Exception injectEx = new Exception("Field[" + field.getName() + "] inject error!");
            injectEx.setStackTrace(ex.getStackTrace());
            throw injectEx;
        }

    }

    /**
     * 绑定控件点击事件注解
     *
     * @param aiView
     * @param view
     */
    private void viewBindClick(AIPresent present, AIView aiView, View view) {
        String clickMethodName = aiView.clickMethod();
        if (!"".equals(clickMethodName)) {
            view.setOnClickListener(OnClickViewListener.obtainListener(present, clickMethodName));
        }
    }

    /**
     * 绑定控件点击事件注解
     *
     * @param aiView
     * @param view
     */
    private void viewBindLongClick(AIPresent present, AIView aiView, View view) {
        String longClickMethodName = aiView.longClickMethod();
        if (!"".equals(longClickMethodName)) {
            view.setOnLongClickListener(OnLongClickViewListener.obtainListener(present, longClickMethodName));
        }
    }

    /**
     * 绑定控件item点击事件注解
     *
     * @param aiView
     * @param view
     */
    private void viewBindItemClick(AIPresent present, AIView aiView, View view) throws Exception {
        // 如果view是AdapterView的子类(ListView, GridView, ExpandableListView...)
        String itemClickMethodName = aiView.itemClickMethod();
        if ("".equals(itemClickMethodName)) {
            return;
        }

        if (AdapterView.class.isAssignableFrom(view.getClass())) {
            AdapterView adapterView = (AdapterView) view;
            adapterView.setOnItemClickListener(OnItemClickViewListener.obtainListener(present, itemClickMethodName));
        } else {
            throw new Exception("view[" + view + "] is not AdapterView's subclass");
        }

    }

    /**
     * 绑定控件item长按事件注解
     *
     * @param aiView
     * @param view
     */
    private void viewBindItemLongClick(AIPresent present, AIView aiView, View view) throws Exception {
        // 如果view是AdapterView的子类(ListView, GridView, ExpandableListView...)
        String itemClickMethodName = aiView.itemLongClickMethod();
        if ("".equals(itemClickMethodName)) {
            return;
        }

        if (AdapterView.class.isAssignableFrom(view.getClass())) {
            AdapterView adapterView = (AdapterView) view;
            adapterView.setOnItemLongClickListener(OnItemLongClickViewListener.obtainListener(present, itemClickMethodName));

        } else {
            throw new Exception("view[" + view + "] is not AdapterView's subclass");
        }

    }


}
