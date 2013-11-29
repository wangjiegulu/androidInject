package com.wangjie.androidinject.annotation.listener;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:wangjie@cyyun.com
 * Date: 13-11-29
 * Time: 下午4:01
 */
public class OnLongClickViewListener implements View.OnLongClickListener{
    private static Map<String, OnLongClickViewListener> listeners = new HashMap<String, OnLongClickViewListener>();

    public synchronized static OnLongClickViewListener obtainListener(Activity activity, String clickMethodName){
        String identifier = activity.toString() + "_" + clickMethodName;
        OnLongClickViewListener onClickViewListener = listeners.get(identifier);
        if(null == onClickViewListener){
            onClickViewListener = new OnLongClickViewListener(activity, clickMethodName);
            listeners.put(identifier, onClickViewListener);
        }
        return onClickViewListener;
    }

    private static final String TAG = OnLongClickViewListener.class.getSimpleName();
    private Activity activity;
    private String callbackMethodName;

    private OnLongClickViewListener(Activity activity, String callbackMethodName) {
        this.activity = activity;
        this.callbackMethodName = callbackMethodName;
    }

    @Override
    public boolean onLongClick(View v) {
        Boolean result = true;
        try {
            Method callbackMethod = activity.getClass().getMethod(callbackMethodName, View.class);
            Object obj = callbackMethod.invoke(activity, v);
            if(obj instanceof Boolean){
                result = (Boolean)obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "e: ", e);
        }
        return result;
    }

}
