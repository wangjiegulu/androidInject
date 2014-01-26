package com.wangjie.androidinject.annotation.present;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.wangjie.androidinject.annotation.core.AnnotationManager;
import com.wangjie.androidinject.annotation.core.ParticularAnnotation;
import com.wangjie.androidinject.annotation.present.common.CallbackSample;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email: tiantian.china.2@gmail.com
 * Date: 13-12-4
 * Time: 下午4:37
 */
public class AISupportFragment extends Fragment implements AIPresent, CallbackSample{
    private static String TAG = AISupportFragment.class.getSimpleName();
    public Context context;
    private Class<?> clazz;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getActivity();
        clazz = this.getClass();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return ParticularAnnotation.realizeLayoutAnnotation(this, inflater, container);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new AnnotationManager(this).initAnnotations();
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
    public Context getContext() {
        return context;
    }

    @Override
    public Object getFindViewView() {
        return getView();
    }


    @Override
    public void onClickCallbackSample(View view) {}
    @Override
    public void onLongClickCallbackSample(View view) {}
    @Override
    public void onItemClickCallbackSample(AdapterView<?> adapterView, View view, int i, long l) {}
    @Override
    public void onItemLongClickCallbackSample(AdapterView<?> adapterView, View view, int i, long l) {}


}
