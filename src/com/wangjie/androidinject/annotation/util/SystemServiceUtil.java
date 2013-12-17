package com.wangjie.androidinject.annotation.util;

import android.app.*;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.PowerManager;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email: tiantian.china.2@gmail.com
 * Date: 13-12-2
 * Time: 下午4:43
 */
public class SystemServiceUtil {

    public static Object getSystemServiceByClazz(Context context, Class<?> clazz) throws Exception{
        String serviceName = null;

        if(clazz == WindowManager.class){
            serviceName = Context.WINDOW_SERVICE;
        }else if(clazz == LayoutInflater.class){
            serviceName = Context.LAYOUT_INFLATER_SERVICE;
        }else if(clazz == ActivityManager.class){
            serviceName = Context.ACTIVITY_SERVICE;
        }else if(clazz == PowerManager.class){
            serviceName = Context.POWER_SERVICE;
        }else if(clazz == AlarmManager.class){
            serviceName = Context.ALARM_SERVICE;
        }else if(clazz == NotificationManager.class){
            serviceName = Context.NOTIFICATION_SERVICE;
        }else if(clazz == KeyguardManager.class){
            serviceName = Context.KEYGUARD_SERVICE;
        }else if(clazz == LocationManager.class){
            serviceName = Context.LOCATION_SERVICE;
        }else if(clazz == SearchManager.class){
            serviceName = Context.SEARCH_SERVICE;
        }else if(clazz == Vibrator.class){
            serviceName = Context.VIBRATOR_SERVICE;
        }else if(clazz == ConnectivityManager.class){
            serviceName = Context.CONNECTIVITY_SERVICE;
        }else if(clazz == WifiManager.class){
            serviceName = Context.WIFI_SERVICE;
        }else if(clazz == InputMethodManager.class){
            serviceName = Context.INPUT_METHOD_SERVICE;
        }else if(clazz == UiModeManager.class){
            serviceName = Context.UI_MODE_SERVICE;
        }else if(clazz == DownloadManager.class){
            serviceName = Context.DOWNLOAD_SERVICE;
        }

        if(null == serviceName){
            throw new Exception(clazz.getName() + " is not a SystemService ! ");
        }

        return context.getSystemService(serviceName);
    }

}
