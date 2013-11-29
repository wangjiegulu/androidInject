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
public class OnItemClickViewListener implements View.OnClickListener{
    private static Map<String, OnItemClickViewListener> listeners = new HashMap<String, OnItemClickViewListener>();

    public synchronized static OnItemClickViewListener obtainListener(Activity activity, String clickMethodName){
        String identifier = activity.toString() + "_" + clickMethodName;
        OnItemClickViewListener onClickViewListener = listeners.get(identifier);
        if(null == onClickViewListener){
            onClickViewListener = new OnItemClickViewListener(activity, clickMethodName);
            listeners.put(identifier, onClickViewListener);
        }
        return onClickViewListener;
    }

    private static final String TAG = OnItemClickViewListener.class.getSimpleName();
    private Activity activity;
    private String callbackMethodName;

    private OnItemClickViewListener(Activity activity, String callbackMethodName) {
        this.activity = activity;
        this.callbackMethodName = callbackMethodName;
    }

    @Override
    public void onClick(View v) {
        try {
            Method callbackMethod = activity.getClass().getMethod(callbackMethodName, View.class);
            callbackMethod.invoke(activity, v);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "e: ", e);
        }

    }


}
