package com.wangjie.androidinject.annotation.present;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import com.wangjie.androidbucket.customviews.sublayout.SubLayout;
import com.wangjie.androidinject.annotation.core.base.AnnotationManager;
import com.wangjie.androidinject.annotation.present.common.CallbackSample;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by wangjie on 14-5-4.
 */
public class AISubLayout extends SubLayout implements AIPresent, CallbackSample {
    private static final String TAG = AISubLayout.class.getSimpleName();

    public AISubLayout(Context context) {
        this(context, false);
    }

    public AISubLayout(Context context, boolean autoBindActivityLifeCycle) {
        super(context, autoBindActivityLifeCycle);
        long start = System.currentTimeMillis();
        new AnnotationManager(this).initAnnotations();
        Log.d(TAG, "[" + ((Object) this).getClass().getSimpleName() + "]AISubLayout(parser annotations) takes: " + (System.currentTimeMillis() - start) + "ms");
    }

    @Override
    public void initLayout() {
        super.initLayout();

    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void setContentView_(int layoutResID) {
        setContentView(layoutResID);
    }

    @Override
    public View findViewById_(int resId) {
        return findViewById(resId);
    }

    @Override
    public void parserTypeAnnotations(Class clazz) throws Exception {
    }

    @Override
    public void parserMethodAnnotations(Method method) throws Exception {
    }

    @Override
    public void parserFieldAnnotations(Field field) throws Exception {
    }

    @Override
    public void onClickCallbackSample(View view) {
    }

    @Override
    public void onLongClickCallbackSample(View view) {
    }

    @Override
    public void onItemClickCallbackSample(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onItemLongClickCallbackSample(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onCheckedChangedCallbackSample(CompoundButton buttonView, boolean isChecked) {
    }


}
