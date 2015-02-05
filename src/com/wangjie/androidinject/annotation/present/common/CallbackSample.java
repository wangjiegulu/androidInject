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
    void onClickCallbackSample(View view);
    void onLongClickCallbackSample(View view);
    void onItemClickCallbackSample(AdapterView<?> parent, View view, int position, long id);
    void onItemLongClickCallbackSample(AdapterView<?> parent, View view, int position, long id);
    void onCheckedChangedCallbackSample(CompoundButton buttonView, boolean isChecked);

}
