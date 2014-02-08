package com.wangjie.androidinject.annotation.core.net;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.wangjie.androidinject.annotation.annotations.net.AIGet;
import com.wangjie.androidinject.annotation.annotations.net.AIParam;
import com.wangjie.androidinject.annotation.annotations.net.AIPost;
import com.wangjie.androidinject.annotation.util.Params;
import com.wangjie.androidinject.annotation.util.StringUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:wangjie@cyyun.com
 * Date: 14-2-7
 * Time: 下午1:40
 */
public class NetInvoHandler implements InvocationHandler{
    private static HashMap<Class<?>, NetInvoHandler> invoHandlers = new HashMap<Class<?>, NetInvoHandler>();

    private Object proxy; // 代理对象

    public synchronized  static<T> T getWorker(Class<T> clazz){
        NetInvoHandler netInvoHandler = invoHandlers.get(clazz);
        if(null == netInvoHandler){
            netInvoHandler = new NetInvoHandler();
            netInvoHandler.setProxy(Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, netInvoHandler));
            invoHandlers.put(clazz, netInvoHandler);
        }
        return (T)netInvoHandler.getProxy();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // get请求
        if(method.isAnnotationPresent(AIGet.class)){
            AIGet aiGet = method.getAnnotation(AIGet.class);
            String url = aiGet.value();
            if(TextUtils.isEmpty(url)){
                throw new Exception("net work [" + method.getName() + "]@AIGet value()[url] is empty!!");
            }
            Annotation[][] annotaions = method.getParameterAnnotations();
            for(int i = 0; i < args.length; i++){
                if(Params.class.isAssignableFrom(args[i].getClass())){ // 如果属性为Params，则追加在后面
                    url = StringUtil.appendParamsAfterUrl(url, (Params)args[i]);
                }else{ // 如果属性添加了@AIParam注解，则替换链接中#{xxx}
                    String repName = ((AIParam)annotaions[i][0]).value();
                    url = url.replace("#{" + repName + "}", args[i] + "");
                }

            }
            StringBuilder sb = NetWork.getStringFromUrl(url);
            if(null == sb){
                return null;
            }
            return new Gson().fromJson(sb.toString(), method.getReturnType());
        }

        // post请求
        if(method.isAnnotationPresent(AIPost.class)){
            AIPost aiPost = method.getAnnotation(AIPost.class);
            String url = aiPost.value();
            if(TextUtils.isEmpty(url)){
                throw new Exception("net work [" + method.getName() + "]@AIPost value()[url] is empty!!");
            }
            Annotation[][] annotaions = method.getParameterAnnotations();
            Map<String, String> map = new HashMap<String, String>();
            for(int i = 0; i < args.length; i++){
                if(Params.class.isAssignableFrom(args[i].getClass())){ // 如果属性为Params，则追加在后面
                    map.putAll((Params)args[i]);
                }else{
                    String repName = ((AIParam)annotaions[i][0]).value();
                    map.put(repName, args[i] + "");
                }

            }
            StringBuilder sb = NetWork.postStringFromUrl(url, map);
            if(null == sb){
                return null;
            }
            return new Gson().fromJson(sb.toString(), method.getReturnType());

        }


        return null;
    }


    public Object getProxy() {
        return proxy;
    }

    public void setProxy(Object proxy) {
        this.proxy = proxy;
    }
}
