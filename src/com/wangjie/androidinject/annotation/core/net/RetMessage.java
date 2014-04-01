package com.wangjie.androidinject.annotation.core.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * Json响应结果包装类
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 14-2-7
 * Time: 下午4:25
 */
public class RetMessage<T>
{

    Gson gb = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss:SSS").create();

    private int resultCode; // 结果码，必须包含

    private List<T> list; // 返回的数据

    private T obj; // 返回的数据

    private Integer size; // 返回数据长度

    private String errorMessage; // 返回错误信息

    public String toJson(){
        return gb.toJson(this);
    }

    public int getResultCode()
    {
        return resultCode;
    }

    public RetMessage<T> setResultCode(int resultCode)
    {
        this.resultCode = resultCode;
        return this;
    }

    public List<T> getList()
    {
        return list;
    }

    public RetMessage<T> setList(List<T> list)
    {
        this.list = list;
        if(null != this.list){
            this.size = this.list.size();
        }
        return this;
    }

    public T getObj()
    {
        return obj;
    }

    public RetMessage<T> setObj(T obj)
    {
        this.obj = obj;
        return this;
    }

    public Integer getSize()
    {
        return size;
    }

    public RetMessage<T> setSize(Integer size)
    {
        this.size = size;
        return this;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public RetMessage<T> setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
        return this;
    }



}

