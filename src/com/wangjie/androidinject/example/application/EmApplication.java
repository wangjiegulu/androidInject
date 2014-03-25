package com.wangjie.androidinject.example.application;

import android.app.Application;
import com.wangjie.androidinject.annotation.core.thread.ThreadPool;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:wangjie@cyyun.com
 * Date: 14-3-25
 * Time: 下午5:19
 */
public class EmApplication extends Application{
    @Override
    public void onCreate() {
        ThreadPool.initThreadPool(7); // 初始化线程池


    }


}
