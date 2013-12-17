package com.wangjie.androidinject.annotation.listener;

import android.util.Log;
import android.view.View;
import com.wangjie.androidinject.annotation.present.AIPresent;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email: tiantian.china.2@gmail.com
 * Date: 13-11-29
 * Time: 下午4:01
 */
public class OnClickViewListener implements View.OnClickListener{
    private static Map<String, OnClickViewListener> listeners = new HashMap<String, OnClickViewListener>();

    public synchronized static OnClickViewListener obtainListener(AIPresent present, String clickMethodName){
        String identifier = present.toString() + "_" + clickMethodName;
        OnClickViewListener onClickViewListener = listeners.get(identifier);
        if(null == onClickViewListener){
            onClickViewListener = new OnClickViewListener(present, clickMethodName);
            listeners.put(identifier, onClickViewListener);
        }
        return onClickViewListener;
    }

    private static final String TAG = OnClickViewListener.class.getSimpleName();
    private AIPresent present;
    private String callbackMethodName;

    private OnClickViewListener(AIPresent present, String callbackMethodName) {
        this.present = present;
        this.callbackMethodName = callbackMethodName;
    }

    @Override
    public void onClick(View v) {
        try {
            Method callbackMethod = present.getClass().getDeclaredMethod(callbackMethodName, View.class);
            callbackMethod.setAccessible(true);
            callbackMethod.invoke(present, v);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "e: ", e);
        }

    }


}
