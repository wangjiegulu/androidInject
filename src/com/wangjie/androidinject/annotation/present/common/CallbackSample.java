package com.wangjie.androidinject.annotation.present.common;

import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email: tiantian.china.2@gmail.com
 * Date: 13-12-17
 * Time: 下午3:36
 */
public interface CallbackSample {
    public void onClickCallbackSample(View view);
    public void onLongClickCallbackSample(View view);
    public void onItemClickCallbackSample(AdapterView<?> adapterView, View view, int i, long l);
    public void onItemLongClickCallbackSample(AdapterView<?> adapterView, View view, int i, long l);
    public void onCheckedChangedCallbackSample(CompoundButton buttonView, boolean isChecked);

}
