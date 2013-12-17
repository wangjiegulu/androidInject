package com.wangjie.androidinject.annotation.present;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import com.wangjie.androidinject.annotation.annotations.AIFullScreen;
import com.wangjie.androidinject.annotation.annotations.AINoTitle;
import com.wangjie.androidinject.annotation.core.AnnotationManager;
import com.wangjie.androidinject.annotation.present.common.CallbackSample;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email: tiantian.china.2@gmail.com
 * Date: 13-11-29
 * Time: 下午2:02
 */
public class AIActivity extends Activity implements AIPresent, CallbackSample {
    public static final String TAG = AIActivity.class.getSimpleName();
    public Context context;
    private Class<?> clazz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        clazz = this.getClass();
        new AnnotationManager(this).initAnnotations();
    }


    @Override
    public Context getContext() {
        return context;
    }


    @Override
    public Class<?> getFindViewClazz() {
        return clazz;
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
