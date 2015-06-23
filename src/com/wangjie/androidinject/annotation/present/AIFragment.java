package com.wangjie.androidinject.annotation.present;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import com.wangjie.androidbucket.log.Logger;
import com.wangjie.androidbucket.present.ABFragment;
import com.wangjie.androidinject.annotation.core.base.AnnotationManager;
import com.wangjie.androidinject.annotation.core.base.ParticularAnnotation;
import com.wangjie.androidinject.annotation.present.common.CallbackSample;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email: tiantian.china.2@gmail.com
 * Date: 13-12-4
 * Time: 下午4:37
 */
@Deprecated
public class AIFragment extends ABFragment implements AIPresent, CallbackSample{
    private static String TAG = AIFragment.class.getSimpleName();
    public Context context;
    public Class<?> clazz;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getActivity();
        clazz = ((Object)this).getClass();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return ParticularAnnotation.realizeLayoutAnnotation(this, inflater, container);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        long start = System.currentTimeMillis();
        super.onActivityCreated(savedInstanceState);
        try {
            new AnnotationManager(this).initAnnotations();
        } catch (Exception e) {
            onInjectFailed(e);
        }
        Log.d(TAG, "[" + clazz.getSimpleName() + "]onActivityCreated supper(parser annotations) takes: " + (System.currentTimeMillis() - start) + "ms");
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//    }

    @Override
    public void onInjectFailed(Exception exception) {
        Logger.e(TAG, "inject failed!!: ", exception);
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public final void setContentView_(int layoutResID) {
        // Fragment不使用次方法进行设置layout，而是在onCreateView中设置，无需实现此方法
    }

    @Override
    public View findViewById_(int id) {
        View view = getView();
        return null == view ? null : view.findViewById(id);
    }

    @Override
    public void parserTypeAnnotations(Class clazz) throws Exception{}
    @Override
    public void parserMethodAnnotations(Method method) throws Exception{}
    @Override
    public void parserFieldAnnotations(Field field) throws Exception {}

    @Override
    public void onClickCallbackSample(View view) {}
    @Override
    public void onLongClickCallbackSample(View view) {}
    @Override
    public void onItemClickCallbackSample(AdapterView<?> parent, View view, int position, long id) {}
    @Override
    public void onItemLongClickCallbackSample(AdapterView<?> parent, View view, int position, long id) {}

    @Override
    public void onCheckedChangedCallbackSample(CompoundButton buttonView, boolean isChecked) {}


}
