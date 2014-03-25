package com.wangjie.androidinject.annotation.core.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 14-2-26
 * Time: 下午3:37
 */
public class ThreadPool {
    static ExecutorService threadPool;

    public synchronized static ExecutorService getInstances(){
        if(null == threadPool){
            threadPool = Executors.newCachedThreadPool();
        }

        threadPool.execute(new Runtask<Integer, Integer>() {
            @Override
            public Integer runInBackground() {
                return null;
            }

            @Override
            public void onResult(Integer result) {
                super.onResult(result);
            }
        });


        return threadPool;
    }





}
