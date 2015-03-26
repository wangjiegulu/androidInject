package com.wangjie.androidinject.annotation.core.base.process.field;

import com.wangjie.androidbucket.mvp.ABBasePresenter;
import com.wangjie.androidbucket.mvp.ABInteractor;
import com.wangjie.androidbucket.mvp.ABNoneInteractorImpl;
import com.wangjie.androidbucket.mvp.ABNonePresenterImpl;
import com.wangjie.androidinject.annotation.annotations.mvp.AIPresenter;
import com.wangjie.androidinject.annotation.core.base.process.AIAnnotationProcessor;
import com.wangjie.androidinject.annotation.present.AIPresent;

import java.lang.reflect.Field;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2/4/15.
 */
public class AIPresenterFieldProcessor implements AIAnnotationProcessor<Field> {
    @Override
    public void process(AIPresent present, Field field) throws Exception {
        field.setAccessible(true);

        AIPresenter aiPresenter = field.getAnnotation(AIPresenter.class);
        Class prClass = aiPresenter.presenter();
        // Viewer层（Activity）中注入presenter
        ABBasePresenter presenter;
        if (ABNonePresenterImpl.class.equals(prClass)) {
            Class fieldType = field.getType();
            if (ABBasePresenter.class.isAssignableFrom(fieldType)) {
                presenter = (ABBasePresenter) fieldType.newInstance();
            } else {
                throw new Exception("presenter inject error!");
            }
        } else {
            String presenterClazzName = prClass.getName();
            presenter = (ABBasePresenter) Class.forName(presenterClazzName).newInstance();
        }

        field.set(present, presenter);

        Class<?> superPresenterClazz = presenter.getClass();
        while (!ABBasePresenter.class.equals(superPresenterClazz)) {
            superPresenterClazz = superPresenterClazz.getSuperclass();
        }

        /**
         * 在presenter中注入viewer和interactor（presenter中需要有viewer和interactor的引用）
         */
        // 把viewer注入到presenter中
        Field viewerField = superPresenterClazz.getDeclaredField("viewer");
        viewerField.setAccessible(true);
        viewerField.set(presenter, present);


        String interactorClazzName = aiPresenter.interactor().getName();
        if (!ABNoneInteractorImpl.class.equals(aiPresenter.interactor())) {
            // 把interactor注入到presenter中
            ABInteractor interactor = (ABInteractor) Class.forName(interactorClazzName).newInstance();
            Field interactorField = superPresenterClazz.getDeclaredField("interactor");
            interactorField.setAccessible(true);
            interactorField.set(presenter, interactor);
        }

        present.registerPresenter(presenter);
    }
}
