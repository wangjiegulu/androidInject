package com.wangjie.androidinject.annotation.present;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import com.wangjie.androidbucket.present.ABSupportFragmentActivity;
import com.wangjie.androidinject.annotation.core.base.AnnotationManager;
import com.wangjie.androidinject.annotation.present.common.CallbackSample;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email: tiantian.china.2@gmail.com
 * Date: 13-12-4
 * Time: 下午4:21
 */
public class AISupportFragmentActivity extends ABSupportFragmentActivity implements AIPresent, CallbackSample {
    private static String TAG = AISupportFragmentActivity.class.getSimpleName();
    public Context context;
    public Class<?> clazz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        long start = System.currentTimeMillis();
        super.onCreate(savedInstanceState);
        context = this;
        clazz = ((Object)this).getClass();
        new AnnotationManager(this).initAnnotations();
        Log.d(TAG, "[" + clazz.getSimpleName() + "]onCreate supper(parser annotations) takes: " + (System.currentTimeMillis() - start) + "ms");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState); // 解决Fragment切换时发生重叠现象
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public Object getFindViewView() {
        return this;
    }
    @Override
    public void parserTypeAnnotations(Class clazz) throws Exception {}
    @Override
    public void parserMethodAnnotations(Method method) throws Exception {}
    @Override
    public void parserFieldAnnotations(Field field) throws Exception {}

    @Override
    public void onClickCallbackSample(View view) {}
    @Override
    public void onLongClickCallbackSample(View view) {}
    @Override
    public void onItemClickCallbackSample(AdapterView<?> parent, View view, int position, long id) {}
    @Override
    public void onItemLongClickCallbackSample(AdapterView<?> parent, View view, int position, long id) {}
    @Override
    public void onCheckedChangedCallbackSample(CompoundButton buttonView, boolean isChecked) {}


}
