package com.wangjie.androidinject.annotation.util;

import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:wangjie@cyyun.com
 * Date: 14-2-8
 * Time: 上午10:55
 */
public class StringUtil {
    public static String appendParamsAfterUrl(String url, Map map){
        if(null == map || map.isEmpty()){
            return url;
        }
        StringBuilder sb = new StringBuilder(url);
        sb = url.endsWith("?") ? sb : sb.append("?");
        Set<Map.Entry> set = map.entrySet();
        for(Map.Entry entry : set){
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        url = sb.toString();
        return url.substring(0, url.length() - 1);
    }

}
