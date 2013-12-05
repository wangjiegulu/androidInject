package com.wangjie.androidinject.annotation.present;

import android.content.Context;

/**
 * Created with IntelliJ IDEA.
 * User: wangjie
 * Date: 13-11-30
 * Time: 下午7:40
 * To change this template use File | Settings | File Templates.
 */
public interface AIPresent {
    public Context getContext();

    public Class<?> getFindViewClazz();
    public Object getFindViewView();

}
