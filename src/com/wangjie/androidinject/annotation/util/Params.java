package com.wangjie.androidinject.annotation.util;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 14-2-8
 * Time: 上午10:41
 */
public class Params {
    private List<NameValuePair> nameValuePairs = new ArrayList<>();

    public Params add(String key, Object value) {
        if (null == key || null == value) {
            return this;
        }
        nameValuePairs.add(new BasicNameValuePair(key, String.valueOf(value)));
        return this;
    }

    public Params put(String key, Object value) {
        return add(key, value);
    }

    public Params putAll(Params params) {
        if (null == params) {
            return this;
        }
        this.nameValuePairs.addAll(params.getNameValuePairs());
        return this;
    }

    public List<NameValuePair> getNameValuePairs() {
        return nameValuePairs;
    }

    public void clear() {
        nameValuePairs.clear();
    }

}
