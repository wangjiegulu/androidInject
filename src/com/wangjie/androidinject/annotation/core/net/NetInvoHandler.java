package com.wangjie.androidinject.annotation.core.net;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.wangjie.androidinject.annotation.annotations.net.AIGet;
import com.wangjie.androidinject.annotation.annotations.net.AIParam;
import com.wangjie.androidinject.annotation.annotations.net.AIPost;
import com.wangjie.androidinject.annotation.annotations.net.AIUpload;
import com.wangjie.androidinject.annotation.util.Params;
import com.wangjie.androidinject.annotation.util.StringUtil;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

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

        // get请求（非上传文件）
        if(method.isAnnotationPresent(AIGet.class) && !method.isAnnotationPresent(AIUpload.class)){
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
            StringBuilder sb = AINetWork.getStringFromUrl(
                    AINetWork.getDefaultHttpClient(aiGet.connTimeout(), aiGet.soTimeout()),
                    url);

            if(null == sb){
                return null;
            }
            Class<?> returnType = method.getReturnType();
            return Void.TYPE == returnType ? null : new Gson().fromJson(sb.toString(), returnType);
        }

        // post请求（非上传文件）
        if(method.isAnnotationPresent(AIPost.class) && !method.isAnnotationPresent(AIUpload.class)){
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
            StringBuilder sb = AINetWork.postStringFromUrl(
                    AINetWork.getDefaultHttpClient(aiPost.connTimeout(), aiPost.soTimeout()),
                    url, map);
            if(null == sb){
                return null;
            }
            Class<?> returnType = method.getReturnType();
            return Void.TYPE == returnType ? null : new Gson().fromJson(sb.toString(), returnType);

        }

        // AIUpload（上传文件，默认是post请求）
        if(method.isAnnotationPresent(AIUpload.class)){
            List<File> files = null;
            for(Object obj : args){
                if(Collection.class.isAssignableFrom(obj.getClass())){
                    files = null == files ? new ArrayList<File>() : files;
                    files.addAll(((Collection<? extends File>)obj));
                }
                if(File.class.isAssignableFrom(obj.getClass())){
                    files = null == files ? new ArrayList<File>() : files;
                    files.add((File)obj);
                }
            }
            AIUpload aiUpload = method.getAnnotation(AIUpload.class);
            String str = AIUploadNetWork.uploadFiles(aiUpload.value(), files, aiUpload.connTimeout(), aiUpload.soTimeout());
            Class<?> returnType = method.getReturnType();
            return Void.TYPE == returnType ? null : new Gson().fromJson(str, returnType);
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
