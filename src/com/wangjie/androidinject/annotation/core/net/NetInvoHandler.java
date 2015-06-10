package com.wangjie.androidinject.annotation.core.net;

import android.text.TextUtils;
import com.google.gson.GsonBuilder;
import com.wangjie.androidbucket.log.Logger;
import com.wangjie.androidbucket.services.network.http.ABHttpMethod;
import com.wangjie.androidbucket.services.network.http.ABHttpUtil;
import com.wangjie.androidbucket.services.network.http.HttpAccessParameter;
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.androidinject.annotation.annotations.net.*;
import com.wangjie.androidinject.annotation.util.Params;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA. Author: wangjie email:tiantian.china.2@gmail.com
 * Date: 14-2-7 Time: 下午1:40
 */
public class NetInvoHandler implements InvocationHandler {

    private static final String TAG = NetInvoHandler.class.getSimpleName();

    private static HashMap<Class<?>, NetInvoHandler> invoHandlers = new HashMap<Class<?>, NetInvoHandler>();

    private Object proxy; // 代理对象

    public synchronized static <T> T getWorker(Class<T> clazz) {
        NetInvoHandler netInvoHandler = invoHandlers.get(clazz);
        if (null == netInvoHandler) {
            netInvoHandler = new NetInvoHandler();
            netInvoHandler.setProxy(Proxy.newProxyInstance(
                    clazz.getClassLoader(), new Class[]{clazz},
                    netInvoHandler));
            invoHandlers.put(clazz, netInvoHandler);
        }
        return (T) netInvoHandler.getProxy();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        args = null == args ? new Object[0] : args;

        String domain = null;
        Class<?>[] inters = proxy.getClass().getInterfaces();
        if (null == inters || inters.length <= 0) {
            Logger.e(TAG, "Invalid NetWorker Proxy Object!!");
            domain = "";
        } else {
            Class<?> interClazz = inters[0];
            domain = interClazz.isAnnotationPresent(AIMapper.class) ? interClazz.getAnnotation(AIMapper.class).value() : "";
        }

        try {
            // get请求（非上传文件）
            if (method.isAnnotationPresent(AIGet.class)) {
                return getHttpGetResponse(method, args, domain);
            }

            // delete请求（非上传文件）
            if (method.isAnnotationPresent(AIDelete.class)) {
                return getHttpDeleteResponse(method, args, domain);
            }


            // post请求（非上传文件）
            if (method.isAnnotationPresent(AIPost.class)) {
                return getHttpPostResponse(method, args, domain);
            }

            if (method.isAnnotationPresent(AIRaw.class)) {
                return getHttpRawResponse(method, args, domain);
            }

        } catch (Exception ex) {
            Logger.e(TAG, "invoke error: ", ex);
            throw ex;
        }
        return null;
    }

    private Object getHttpDeleteResponse(Method method, Object[] args, String domain) throws Exception {
        AIDelete aiDelete = method.getAnnotation(AIDelete.class);
        String url = domain + aiDelete.value();
        if (TextUtils.isEmpty(url)) {
            throw new Exception("net work [" + method.getName()
                    + "]@AIDelete value()[url] is empty!!");
        }
        Annotation[][] annotations = method.getParameterAnnotations();
        for (int i = 0; i < args.length; i++) {
            url = generateUrl(args, url, annotations, i, args[i]);

        }

        Logger.d(TAG, "Delete url[" + method.getName() + "]: " + url);

        aiDelete.sessionEnable();

        String result = ABHttpUtil.getHttpResponse(
                new HttpAccessParameter()
                        .setMethod(ABHttpMethod.DELETE)
                        .setWebApi(url)
                        .setSessionEnableMethod(aiDelete.sessionEnable())
        );
        if (null == result) {
            return null;
        }
        Type returnType = method.getGenericReturnType();
        return generateReturnValue(returnType, result);
    }

    /**
     * 获取HttpRaw返回反射AIRaw注解结果
     *
     * @param method Worker声明的方法
     * @param args   Worker声明方法参数
     * @param domain Http Domain
     * @return 返回请求后反射解析结果
     * @throws Exception
     */
    private Object getHttpRawResponse(Method method, Object[] args, String domain) throws Exception {
        AIRaw aiRaw = method.getAnnotation(AIRaw.class);
        String url = domain + aiRaw.value();
        if (TextUtils.isEmpty(url)) {
            throw new Exception("net work [" + method.getName()
                    + "]@AIRaw value()[url] is empty!!");
        }
//        Object entity;
        if (ABTextUtil.isEmpty(args)) {
            throw new Exception(String.format("Please declare at least a HttpEntity in parameter list for method [%s]!!!", method.getName()));
        }

        HttpEntity httpEntity = null;
        Annotation[][] annotations = method.getParameterAnnotations();
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];

            // 根据参数生成URL
            if (!HttpEntity.class.isAssignableFrom(arg.getClass())) {
                url = generateUrl(args, url, annotations, i, arg);
            } else { // 如果是HttpEntity则转换为HttpEntity
                if (null != httpEntity) {
                    throw new Exception(String.format("Only one HttpEntity allowed in parameter list for method [%s]!!!", method.getName()));
                }
                httpEntity = (HttpEntity) arg;
            }
        }
        if (null == httpEntity) {
            throw new Exception(String.format("Please declare HttpEntity in parameter list for method [%s]!!!", method.getName()));
        }

        String contentType = aiRaw.contentType();

        HttpAccessParameter accessParameter = new HttpAccessParameter()
                .setMethod(ABHttpMethod.POST)
                .setWebApi(url)
                .setRawEntity(httpEntity)
                .setSessionEnableMethod(aiRaw.sessionEnable());

        if (!ABTextUtil.isEmpty(contentType)) {
            accessParameter.setHeadNameValuePairs(new BasicNameValuePair(HTTP.CONTENT_TYPE, contentType));
        }

        // 获取
        String result = ABHttpUtil.getHttpResponse(accessParameter);
        if (null == result) {
            return null;
        }
        Type returnType = method.getGenericReturnType();
        return generateReturnValue(returnType, result);
    }


    /**
     * 生成URL
     *
     * @param args
     * @param url
     * @param annotations
     * @param i
     * @param arg
     * @return
     */
    private String generateUrl(Object[] args, String url, Annotation[][] annotations, int i, Object arg) {
        if (Params.class.isAssignableFrom(arg.getClass())) { // 如果属性为Params，则追加在后面
            url = appendParamsAfterUrl(url, (Params) arg);
        } else { // 如果属性添加了@AIParam注解，则替换链接中#{xxx}
            String repName = ((AIParam) annotations[i][0]).value();
            url = url.replace("#{" + repName + "}", args[i] + "");
        }
        return url;
    }

    /**
     * 获取HttpGet返回反射AIPost注解结果
     *
     * @param method Worker声明的方法
     * @param args   Worker声明方法参数
     * @param domain Http Domain
     * @return 返回请求后反射解析结果
     * @throws Exception
     */
    private Object getHttpPostResponse(Method method, Object[] args, String domain) throws Exception {
        AIPost aiPost = method.getAnnotation(AIPost.class);
        String url = domain + aiPost.value();
        if (TextUtils.isEmpty(url)) {
            throw new Exception("net work [" + method.getName()
                    + "]@AIPost value()[url] is empty!!");
        }
        Annotation[][] annotations = method.getParameterAnnotations();
        Params map = new Params();
        for (int i = 0; i < args.length; i++) {
            if (Params.class.isAssignableFrom(args[i].getClass())) { // 如果属性为Params，则追加在后面
                map.putAll((Params) args[i]);
            } else {
                String repName = ((AIParam) annotations[i][0]).value();
                if (url.contains("#{" + repName + "}")) {
                    url = url.replace("#{" + repName + "}", args[i] + "");
                } else {
                    map.add(repName, args[i] + "");
                }

            }

        }

        // 打印log
        logUrlInfo(url, map);

        String result = ABHttpUtil.getHttpResponse(
                new HttpAccessParameter()
                        .setMethod(ABHttpMethod.POST)
                        .setWebApi(url)
                        .setParamNameValuePairs(map.getNameValuePairs())
                        .setSessionEnableMethod(aiPost.sessionEnable())
        );
        if (null == result) {
            return null;
        }
        Type returnType = method.getGenericReturnType();
        return generateReturnValue(returnType, result);
    }

    /**
     * 获取HttpGet返回反射AIGet注解结果
     *
     * @param method Worker声明的方法
     * @param args   Worker声明方法参数
     * @param domain Http Domain
     * @return 反射AIGet注解结果
     * @throws Exception
     */
    private Object getHttpGetResponse(Method method, Object[] args, String domain) throws Exception {
        AIGet aiGet = method.getAnnotation(AIGet.class);
        String url = domain + aiGet.value();
        if (TextUtils.isEmpty(url)) {
            throw new Exception("net work [" + method.getName()
                    + "]@AIGet value()[url] is empty!!");
        }
        Annotation[][] annotations = method.getParameterAnnotations();
        for (int i = 0; i < args.length; i++) {
            url = generateUrl(args, url, annotations, i, args[i]);

        }

        Logger.d(TAG, "GET url[" + method.getName() + "]: " + url);

        aiGet.sessionEnable();

        String result = ABHttpUtil.getHttpResponse(
                new HttpAccessParameter()
                        .setMethod(ABHttpMethod.GET)
                        .setWebApi(url)
                        .setSessionEnableMethod(aiGet.sessionEnable())
        );
        if (null == result) {
            return null;
        }
        Type returnType = method.getGenericReturnType();
        return generateReturnValue(returnType, result);
    }


    /**
     * 通过返回的类型和请求的结果，生产返回值
     *
     * @param returnType
     * @param str
     * @return
     */
    private Object generateReturnValue(Type returnType, String str) {
        if (Void.TYPE == returnType) {
            return null;
        }
        if (String.class == returnType) {
            return str;
        }

        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss:SSS").create().fromJson(str, returnType);
    }

    /**
     * 打印log，便于调试
     *
     * @param url
     * @param params
     */
    private void logUrlInfo(String url, Params params) {
        if (!Logger.debug && !Logger.logFile) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (NameValuePair pair : params.getNameValuePairs()) {
            sb.append(", ").append(pair.getName()).append("=").append(pair.getValue());
        }
        Logger.d(TAG, url + "?" + (ABTextUtil.isEmpty(sb) ? "" : sb.toString().substring(1)));
    }

    public Object getProxy() {
        return proxy;
    }

    public void setProxy(Object proxy) {
        this.proxy = proxy;
    }

    private String appendParamsAfterUrl(String url, Params params) {
        if (null == params || ABTextUtil.isEmpty(params.getNameValuePairs())) {
            return url;
        }
        url = url.endsWith("?") ? url : url + "?";
        return url + URLEncodedUtils.format(params.getNameValuePairs(), "utf-8");
    }

}
