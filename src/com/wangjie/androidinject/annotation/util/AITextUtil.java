package com.wangjie.androidinject.annotation.util;

import org.apache.http.client.utils.URLEncodedUtils;

import java.net.URLEncoder;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 14-3-24
 * Time: 下午5:40
 */
public class AITextUtil {

    /**
     * 生成占位符
     * @param count
     * @return
     */
    public static String generatePlaceholders(int count){
        if(count <= 0){
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < count; i++){
            sb.append(",?");
        }
        return sb.toString().substring(1);
    }

    public static String joinStrings(Collection<String> set, String separator, String append){
        StringBuilder sb = new StringBuilder();
        for(Iterator<String> iter = set.iterator(); iter.hasNext();){
            sb.append(separator).append(iter.next()).append(append);
        }
        return sb.toString().substring(separator.length());
    }

    public static String joinStrings(Collection<String> set, String separator){
        return joinStrings(set, separator, "");
    }

    public static String appendParamsAfterUrl(String url, Map map){
        if(null == map || map.isEmpty()){
            return url;
        }
        StringBuilder sb = new StringBuilder(url);
        sb = url.contains("?") ? sb.append("&") : sb.append("?");
        Set<Map.Entry> set = map.entrySet();
        for(Map.Entry entry : set){
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        url = sb.toString();
        return url.substring(0, url.length() - 1);
    }

    public static String joinArray(String[] array, String separator){
        if(null == array || array.length <= 0){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for(String s : array){
            sb.append(separator).append(s);
        }
        return sb.toString().substring(separator.length());
    }


}
