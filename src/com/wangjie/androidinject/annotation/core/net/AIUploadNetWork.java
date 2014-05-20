package com.wangjie.androidinject.annotation.core.net;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 14-2-10
 * Time: 上午11:40
 */
public class AIUploadNetWork {
    public static String uploadFiles(String baseUrl, List<File> files, int connTimeout, int soTimeout) throws Exception{
        if(null == files || files.isEmpty()){
            return null;
        }
        HttpEntity resEntity = null;

        HttpClient httpClient = baseUrl.startsWith("https") ?
                AINetWork.getDefaultHttpClient(connTimeout, soTimeout) : AINetWork.getSSLHttpClient(connTimeout, soTimeout);

        try{
            //设置通信协议版本
            httpClient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
            HttpPost httpPost = new HttpPost(baseUrl);

    //        httpPost.setHeader(AJAX_UPLOAD_HEADER, "");

            int size = files.size();
            MultipartEntity mpEntity = new MultipartEntity(); //文件传输
            for(int i = 0; i < size; i++){
                ContentBody cbFile = new FileBody(files.get(i));
                mpEntity.addPart("file", cbFile); // <input type="file" name="file" />  对应的
                httpPost.setEntity(mpEntity);
            }

            HttpResponse response = httpClient.execute(httpPost);
            resEntity = response.getEntity();
            return EntityUtils.toString(resEntity, "utf-8");

        }catch(Exception ex){
            throw ex;
        }finally {
            if (null != resEntity) {
                resEntity.consumeContent();
            }
            httpClient.getConnectionManager().shutdown();
        }

    }





}
