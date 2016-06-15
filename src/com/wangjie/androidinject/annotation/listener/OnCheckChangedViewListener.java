package com.wangjie.androidinject.annotation.listener;

import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
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
public class OnCheckChangedViewListener implements CompoundButton.OnCheckedChangeListener{
    private static Map<String, OnCheckChangedViewListener> listeners = new HashMap<String, OnCheckChangedViewListener>();

    public synchronized static OnCheckChangedViewListener obtainListener(AIPresent present, String clickMethodName){
        String identifier = present.toString() + "_" + clickMethodName;
        OnCheckChangedViewListener listener = listeners.get(identifier);
        if(null == listener){
            listener = new OnCheckChangedViewListener(present, clickMethodName);
            listeners.put(identifier, listener);
        }
        return listener;
    }

    private static final String TAG = OnCheckChangedViewListener.class.getSimpleName();
    private AIPresent present;
    private String callbackMethodName;

    private OnCheckChangedViewListener(AIPresent present, String callbackMethodName) {
        this.present = present;
        this.callbackMethodName = callbackMethodName;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        try {
            Method callbackMethod = present.getClass().getDeclaredMethod(callbackMethodName, CompoundButton.class, boolean.class);
            callbackMethod.setAccessible(true);
            callbackMethod.invoke(present, buttonView, isChecked);
        } catch (Exception e) {
            Log.e(TAG, "e: ", e);
        }
    }

    public static void removeListener(AIPresent present) {
        String keyName = present.toString();
        Iterator<Map.Entry<String, OnCheckChangedViewListener>> iterator = listenerMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, OnCheckChangedViewListener> next = iterator.next();
            if (next.getKey().contains(keyName)) {
                listenerMap.remove(next.getKey());
            }
        }
    }
}
