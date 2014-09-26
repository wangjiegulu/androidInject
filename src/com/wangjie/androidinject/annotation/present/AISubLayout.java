package com.wangjie.androidinject.annotation.present;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import com.wangjie.androidbucket.customviews.sublayout.SubLayout;
import com.wangjie.androidinject.annotation.core.base.AnnotationManager;
import com.wangjie.androidinject.annotation.present.common.CallbackSample;

/**
 * Created by wangjie on 14-5-4.
 */
public class AISubLayout extends SubLayout implements AIPresent, CallbackSample {
    private static final String TAG = AISubLayout.class.getSimpleName();

    public AISubLayout(Context context) {
        super(context);
        long start = System.currentTimeMillis();
        new AnnotationManager(this).initAnnotations();
        Log.d(TAG, "[" + ((Object) this).getClass().getSimpleName() + "]AISubLayout(parser annotations) takes: " + (System.currentTimeMillis() - start) + "ms");
    }

    @Override
    public void initLayout() {
        super.initLayout();

    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public Object getFindViewView() {
        return layout;
    }


    @Override
    public void onClickCallbackSample(View view) {
    }

    @Override
    public void onLongClickCallbackSample(View view) {
    }

    @Override
    public void onItemClickCallbackSample(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onItemLongClickCallbackSample(AdapterView<?> adapterView, View view, int i, long l) {
    }


}
