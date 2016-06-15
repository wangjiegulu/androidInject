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
public class OnItemClickViewListener implements AdapterView.OnItemClickListener{
    private static Map<String, OnItemClickViewListener> listeners = new HashMap<String, OnItemClickViewListener>();

    public synchronized static OnItemClickViewListener obtainListener(AIPresent present, String clickMethodName){
        String identifier = present.toString() + "_" + clickMethodName;
        OnItemClickViewListener onItemClickViewListener = listeners.get(identifier);
        if(null == onItemClickViewListener){
            onItemClickViewListener = new OnItemClickViewListener(present, clickMethodName);
            listeners.put(identifier, onItemClickViewListener);
        }
        return onItemClickViewListener;
    }

    private static final String TAG = OnItemClickViewListener.class.getSimpleName();
    private AIPresent present;
    private String callbackMethodName;

    private OnItemClickViewListener(AIPresent present, String callbackMethodName) {
        this.present = present;
        this.callbackMethodName = callbackMethodName;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            Method callbackMethod = present.getClass().getDeclaredMethod(callbackMethodName, AdapterView.class, View.class, int.class, long.class);
            callbackMethod.setAccessible(true);
            callbackMethod.invoke(present, parent, view, position, id);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "e: ", e);
        }
    }

    public static void removeListener(AIPresent present) {
        String keyName = present.toString();
        Iterator<Map.Entry<String, OnClickViewListener>> iterator = listenerMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, OnClickViewListener> next = iterator.next();
            if (next.getKey().contains(keyName)) {
                listenerMap.remove(next.getKey());
            }
        }
    }



}
