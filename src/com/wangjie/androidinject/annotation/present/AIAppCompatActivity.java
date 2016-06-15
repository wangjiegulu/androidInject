package com.wangjie.androidinject.annotation.present;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;

import com.wangjie.androidbucket.log.Logger;
import com.wangjie.androidbucket.present.ABAppCompatActivity;
import com.wangjie.androidinject.annotation.core.base.AnnotationManager;
import com.wangjie.androidinject.annotation.present.common.CallbackSample;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 6/10/15.
 */
public class AIAppCompatActivity extends ABAppCompatActivity implements AIPresent, CallbackSample {
    private static final String TAG = AIAppCompatActivity.class.getSimpleName();
    public Context context;
    public Class<?> clazz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        long start = System.currentTimeMillis();
        super.onCreate(savedInstanceState);
        context = this;
        clazz = ((Object) this).getClass();
//        clazz = AIActivity.class;
        try {
            new AnnotationManager(this).initAnnotations();
        } catch (Exception e) {
            onInjectFailed(e);
        }
        Log.d(TAG, "[" + clazz.getSimpleName() + "]onCreate supper(parser annotations) takes: " + (System.currentTimeMillis() - start) + "ms");
    }


    @Override
    public void onInjectFailed(Exception exception) {
        Logger.e(TAG, "inject failed!!: ", exception);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解决内存泄露bug
        OnClickViewListener.removeListener(this);
        OnCheckChangedViewListener.removeListener(this);
        OnItemClickViewListener.removeListener(this);
        OnItemLongClickViewListener.removeListener(this);
        OnLongClickViewListener.removeListener(this);
    }


}