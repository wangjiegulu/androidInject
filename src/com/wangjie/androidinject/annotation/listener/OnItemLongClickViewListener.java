package com.wangjie.androidinject.annotation.listener;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
public class OnItemLongClickViewListener implements AdapterView.OnItemLongClickListener{
    private static Map<String, OnItemLongClickViewListener> listeners = new HashMap<String, OnItemLongClickViewListener>();

    public synchronized static OnItemLongClickViewListener obtainListener(AIPresent present, String clickMethodName){
        String identifier = present.toString() + "_" + clickMethodName;
        OnItemLongClickViewListener onItemLongClickViewListener = listeners.get(identifier);
        if(null == onItemLongClickViewListener){
            onItemLongClickViewListener = new OnItemLongClickViewListener(present, clickMethodName);
            listeners.put(identifier, onItemLongClickViewListener);
        }
        return onItemLongClickViewListener;
    }

    private static final String TAG = OnItemLongClickViewListener.class.getSimpleName();
    private AIPresent present;
    private String callbackMethodName;

    private OnItemLongClickViewListener(AIPresent present, String callbackMethodName) {
        this.present = present;
        this.callbackMethodName = callbackMethodName;
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        Boolean result = true;
        try {
            Method callbackMethod = present.getClass().getDeclaredMethod(callbackMethodName, AdapterView.class, View.class, int.class, long.class);
            callbackMethod.setAccessible(true);
            Object obj = callbackMethod.invoke(present, adapterView, view, i, l);
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
