package com.wangjie.androidinject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.wangjie.androidinject.annotation.AnnotationManager;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:wangjie@cyyun.com
 * Date: 13-11-29
 * Time: 下午2:02
 */
public class BaseActivity extends Activity {
    public static final String TAG = BaseActivity.class.getSimpleName();
    public Context context;
    private Class<?> clazz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        clazz = this.getClass();
        new AnnotationManager(context, this).initAnnotations();

    }




}
