package com.wangjie.androidinject.annotation.present;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import com.wangjie.androidinject.annotation.core.AnnotationManager;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:wangjie@cyyun.com
 * Date: 13-11-29
 * Time: 下午2:02
 */
public class AIActivity extends Activity implements AIPresent {
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
    public Class<?> getClazz() {
        return clazz;
    }


    public void onClickCallback(View view){};
    public void onLongClickCallback(View view){};
    public void onItemClickCallback(AdapterView<?> adapterView, View view, int i, long l) {}


}
