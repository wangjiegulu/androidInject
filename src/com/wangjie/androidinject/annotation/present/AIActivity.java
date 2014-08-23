package com.wangjie.androidinject.annotation.present;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import com.wangjie.androidbucket.present.ABActivity;
import com.wangjie.androidinject.annotation.core.base.AnnotationManager;
import com.wangjie.androidinject.annotation.present.common.CallbackSample;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email: tiantian.china.2@gmail.com
 * Date: 13-11-29
 * Time: 下午2:02
 */
public class AIActivity extends ABActivity implements AIPresent, CallbackSample {
    private static final String TAG = AIActivity.class.getSimpleName();
    public Context context;
    public Class<?> clazz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        long start = System.currentTimeMillis();
        super.onCreate(savedInstanceState);
        context = this;
        clazz = ((Object)this).getClass();
//        clazz = AIActivity.class;
        new AnnotationManager(this).initAnnotations();
        Log.d(TAG, "[" + clazz.getSimpleName() + "]onCreate supper(parser annotations) takes: " + (System.currentTimeMillis() - start) + "ms");
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
    public void onClickCallbackSample(View view) {}
    @Override
    public void onLongClickCallbackSample(View view) {}
    @Override
    public void onItemClickCallbackSample(AdapterView<?> adapterView, View view, int i, long l) {}
    @Override
    public void onItemLongClickCallbackSample(AdapterView<?> adapterView, View view, int i, long l) {}




}
