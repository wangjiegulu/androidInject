package com.wangjie.androidinject.annotation.listener;

import android.util.Log;
import android.view.View;
import com.wangjie.androidinject.annotation.present.AIPresent;

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

    public synchronized static OnLongClickViewListener obtainListener(AIPresent present, String clickMethodName){
        String identifier = present.toString() + "_" + clickMethodName;
        OnLongClickViewListener onClickViewListener = listeners.get(identifier);
        if(null == onClickViewListener){
            onClickViewListener = new OnLongClickViewListener(present, clickMethodName);
            listeners.put(identifier, onClickViewListener);
        }
        return onClickViewListener;
    }

    private static final String TAG = OnLongClickViewListener.class.getSimpleName();
    private AIPresent present;
    private String callbackMethodName;

    private OnLongClickViewListener(AIPresent present, String callbackMethodName) {
        this.present = present;
        this.callbackMethodName = callbackMethodName;
    }

    @Override
    public boolean onLongClick(View v) {
        Boolean result = true;
        try {
            Method callbackMethod = present.getClass().getDeclaredMethod(callbackMethodName, View.class);
            callbackMethod.setAccessible(true);
            Object obj = callbackMethod.invoke(present, v);
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
