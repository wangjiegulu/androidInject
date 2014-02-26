package com.wangjie.androidinject.annotation.present;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.wangjie.androidinject.annotation.core.base.AnnotationManager;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email: tiantian.china.2@gmail.com
 * Date: 13-12-4
 * Time: 下午4:21
 */
public class AISupportFragmentActivity extends FragmentActivity implements AIPresent{
    private static String TAG = AISupportFragmentActivity.class.getSimpleName();
    public Context context;
    private Class<?> clazz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        long start = System.currentTimeMillis();
        super.onCreate(savedInstanceState);
        context = this;
        clazz = this.getClass();
        new AnnotationManager(this).initAnnotations();
        Log.d(TAG, "[" + this.getClass().getSimpleName() + "]onCreate supper(parser annotations) takes: " + (System.currentTimeMillis() - start) + "ms");
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public Object getFindViewView() {
        return this;
    }


}
