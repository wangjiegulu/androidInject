package com.wangjie.androidinject.annotation.annotations.mvp;

import com.wangjie.androidbucket.mvp.ABBasePresenter;
import com.wangjie.androidbucket.mvp.ABInteractor;
import com.wangjie.androidbucket.mvp.ABNoneInteractorImpl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email: tiantian.china.2@gmail.com
 * Date: 13-12-2
 * Time: 上午10:29
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AIPresenter {
    Class<? extends ABBasePresenter> presenter();

    Class<? extends ABInteractor> interactor() default ABNoneInteractorImpl.class;
}
