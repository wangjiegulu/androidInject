package com.wangjie.androidinject.annotation.core.net;

import android.text.TextUtils;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.wangjie.androidbucket.log.Logger;
import com.wangjie.androidinject.annotation.annotations.net.AIGet;
import com.wangjie.androidinject.annotation.annotations.net.AIParam;
import com.wangjie.androidinject.annotation.annotations.net.AIPost;
import com.wangjie.androidinject.annotation.annotations.net.AIUpload;
import com.wangjie.androidinject.annotation.util.AITextUtil;
import com.wangjie.androidinject.annotation.util.Params;

import java.io.File;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 14-2-7
 * Time: 下午1:40
 */
public class NetInvoHandler implements InvocationHandler{
    private static final String TAG = NetInvoHandler.class.getSimpleName();

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
                    url = AITextUtil.appendParamsAfterUrl(url, (Params) args[i]);
                }else{ // 如果属性添加了@AIParam注解，则替换链接中#{xxx}
                    String repName = ((AIParam)annotaions[i][0]).value();
                    url = url.replace("#{" + repName + "}", args[i] + "");
                }

            }
            Logger.d(TAG, "GET url[" + method.getName() + "]: " + url);
            StringBuilder sb = AINetWork.getStringFromUrl(
                    AINetWork.getDefaultHttpClient(aiGet.connTimeout(), aiGet.soTimeout()),
                    url);

            if(null == sb){
                return null;
            }
            Type returnType = method.getGenericReturnType();
            return generateReturnValue(returnType, sb.toString());
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
            logUrlInfo(url, map); // 打印log
            StringBuilder sb = AINetWork.postStringFromUrl(
                    AINetWork.getDefaultHttpClient(aiPost.connTimeout(), aiPost.soTimeout()),
                    url, map);
            if(null == sb){
                return null;
            }
            Type returnType = method.getGenericReturnType();
            return generateReturnValue(returnType, sb.toString());

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
            Type returnType = method.getGenericReturnType();
            return generateReturnValue(returnType, str);
        }

        return null;
    }

    /**
     * 通过返回的类型和请求的结果，生产返回值
     * @param returnType
     * @param str
     * @return
     */
    private Object generateReturnValue(Type returnType, String str){
        if(Void.TYPE == returnType){
            return null;
        }
        if(String.class == returnType){
            return str;
        }

        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss:SSS")
                .create()
                .fromJson(str, returnType);
    }

    /**
     * 打印log，便于调试
     * @param url
     * @param params
     */
    private void logUrlInfo(String url, Map<String, String> params){
        if(!Logger.debug && !Logger.logFile){
            return;
        }
        Set set = params.keySet();
        StringBuilder sb = new StringBuilder();
        for(Object s : set){
            sb.append(", ").append(s).append("=").append(params.get(s));
        }
        Logger.d(TAG, url + "?" + sb.toString().substring(1));
    }

    public Object getProxy() {
        return proxy;
    }

    public void setProxy(Object proxy) {
        this.proxy = proxy;
    }
}
