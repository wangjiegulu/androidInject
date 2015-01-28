package com.wangjie.androidinject.annotation.core.base;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import com.wangjie.androidbucket.mvp.ABBasePresenter;
import com.wangjie.androidbucket.mvp.ABNoneInteractorImpl;
import com.wangjie.androidbucket.mvp.ABInteractor;
import com.wangjie.androidinject.annotation.annotations.base.AIBean;
import com.wangjie.androidinject.annotation.annotations.base.AISystemService;
import com.wangjie.androidinject.annotation.annotations.base.AIView;
import com.wangjie.androidinject.annotation.annotations.dimens.AIScreenSize;
import com.wangjie.androidinject.annotation.annotations.mvp.AIPresenter;
import com.wangjie.androidinject.annotation.annotations.net.AINetWorker;
import com.wangjie.androidinject.annotation.core.net.NetInvoHandler;
import com.wangjie.androidinject.annotation.listener.OnClickViewListener;
import com.wangjie.androidinject.annotation.listener.OnItemClickViewListener;
import com.wangjie.androidinject.annotation.listener.OnItemLongClickViewListener;
import com.wangjie.androidinject.annotation.listener.OnLongClickViewListener;
import com.wangjie.androidinject.annotation.present.AIPresent;
import com.wangjie.androidinject.annotation.util.SystemServiceUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wangjie  email: tiantian.china.2@gmail.com
 * Date: 13-11-30
 * Time: 下午7:23
 * To change this template use File | Settings | File Templates.
 */
public class RealizeFieldAnnotation implements RealizeAnnotation {
    private static final String TAG = RealizeFieldAnnotation.class.getSimpleName();
    private static Map<Class<?>, RealizeFieldAnnotation> map = new HashMap<Class<?>, RealizeFieldAnnotation>();

    public synchronized static RealizeFieldAnnotation getInstance(AIPresent present) {
        Class clazz = present.getClass();
        RealizeFieldAnnotation realize = map.get(clazz);
        if (null == realize) {
            realize = new RealizeFieldAnnotation();
            map.put(clazz, realize);
        }
        realize.setPresent(present);
        realize.setClazz(clazz);
        return realize;
    }


    private AIPresent present;
    private Class<?> clazz;

    /**
     * 实现present控件注解功能
     *
     * @throws Exception
     */
    @Override
    public void processAnnotation() throws Exception {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(AIView.class)) { // 如果设置了控件注解
                AIView aiView = field.getAnnotation(AIView.class);

                viewFindAnnontation(aiView, field); // 绑定控件注解

                View view = (View) field.get(present);

                viewBindClick(aiView, view); // 绑定控件点击事件注解

                viewBindLongClick(aiView, view); // 绑定控件点击事件注解

                viewBindItemClick(aiView, view); // 绑定控件item点击事件注解

                viewBindItemLongClick(aiView, view);
            }

            if (field.isAnnotationPresent(AIBean.class)) { // 如果设置了bean注解
                beanNewInstance(field);
            }

            if (field.isAnnotationPresent(AISystemService.class)) { // 如果设置了SystemService注解
                systemServiceBind(field);
            }

            if (field.isAnnotationPresent(AIScreenSize.class)) { // 如果需要注入屏幕大小
                sSizeBind(field);
            }

            if (field.isAnnotationPresent(AINetWorker.class)) { // 如果需要注入NetWorker
                netWorkerBind(field);
            }

            if (field.isAnnotationPresent(AIPresenter.class)) { // MVP注入
                bindMvpPresenter(field);
            }

            present.parserFieldAnnotations(field);

        }
    }


    /**
     * 绑定控件注解
     *
     * @param aiView
     * @param field
     * @throws Exception
     */
    private void viewFindAnnontation(AIView aiView, Field field) throws Exception {
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
    private void viewBindClick(AIView aiView, View view) {
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
    private void viewBindLongClick(AIView aiView, View view) {
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
    private void viewBindItemClick(AIView aiView, View view) throws Exception {
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
    private void viewBindItemLongClick(AIView aiView, View view) throws Exception {
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

    /**
     * 生成一个bean对象
     *
     * @param field
     * @throws Exception
     */
    private void beanNewInstance(Field field) throws Exception {
        try {
            field.getType().getConstructor();
        } catch (NoSuchMethodException e) {
            throw new Exception(field.getType() + " must has a default constructor (a no-args constructor)! ");
        }
        field.setAccessible(true);
        field.set(present, field.getType().newInstance());

    }

    /**
     * 获得相应SystemService的对象，并初始化属性
     *
     * @param field
     * @throws Exception
     */
    private void systemServiceBind(Field field) throws Exception {
        field.setAccessible(true);
        field.set(present, SystemServiceUtil.getSystemServiceByClazz(present.getContext(), field.getType()));
    }

    /**
     * 设置当前设备屏幕宽和高
     *
     * @param field
     * @throws Exception
     */
    private void sSizeBind(Field field) throws Exception {
        field.setAccessible(true);
        if (!Point.class.isAssignableFrom(field.getType())) {
            throw new Exception("field [" + field.getName() + "] must be a Point or its subclasses");
        }
        Display display = ((Activity) present.getContext()).getWindowManager().getDefaultDisplay();
        Point point = (Point) field.getType().newInstance();
        display.getSize(point);
        field.set(present, point);
    }

    /**
     * 注入NetWorker
     *
     * @param field
     * @throws Exception
     */
    private void netWorkerBind(Field field) throws Exception {
        field.setAccessible(true);
        field.set(present, NetInvoHandler.getWorker(field.getType()));
    }


    /**
     * MVP注入
     *
     * @param field
     * @throws Exception
     */
    private void bindMvpPresenter(Field field) throws Exception {
        field.setAccessible(true);

        AIPresenter aiPresenter = field.getAnnotation(AIPresenter.class);
        String presenterClazzName = aiPresenter.presenter().getName();
        String interactorClazzName = aiPresenter.interactor().getName();

        // Viewer层（Activity）中注入presenter
        ABBasePresenter presenter = (ABBasePresenter) Class.forName(presenterClazzName).newInstance();
        field.set(present, presenter);

        Class<?> superPresenterClazz = presenter.getClass();
        while (ABBasePresenter.class != superPresenterClazz) {
            superPresenterClazz = superPresenterClazz.getSuperclass();
        }

        /**
         * 在presenter中注入viewer和interactor（presenter中需要有viewer和interactor的引用）
         */
        // 把viewer注入到presenter中
        Field viewerField = superPresenterClazz.getDeclaredField("viewer");
        viewerField.setAccessible(true);
        viewerField.set(presenter, present);

        if (ABNoneInteractorImpl.class != aiPresenter.interactor()) {
            // 把interactor注入到presenter中
            ABInteractor interactor = (ABInteractor) Class.forName(interactorClazzName).newInstance();
            Field interactorField = superPresenterClazz.getDeclaredField("interactor");
            interactorField.setAccessible(true);
            interactorField.set(presenter, interactor);
        }

        present.registerPresenter(presenter);
    }


    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public void setPresent(AIPresent present) {
        this.present = present;
    }


}
