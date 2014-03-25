package com.wangjie.androidinject.annotation.core.thread;

import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 14-2-26
 * Time: 下午3:37
 */
public class ThreadPool {
    public static final String TAG = ThreadPool.class.getSimpleName();
    static ExecutorService threadPool;

    public static void initThreadPool(int max){ // 需要在Application中进行配置
        threadPool = Executors.newFixedThreadPool(max);
        Log.d(TAG, "[ThreadPool]ThreadPool init success...");
    }

    public static ExecutorService getInstances(){
        return threadPool;
    }

    public synchronized static<U, R> void go(Runtask<U, R> runtask){
        runtask.onBefore();
        threadPool.execute(runtask);
    }





}
