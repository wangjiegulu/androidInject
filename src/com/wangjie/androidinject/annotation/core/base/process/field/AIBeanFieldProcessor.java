package com.wangjie.androidinject.annotation.core.base.process.field;

import com.wangjie.androidinject.annotation.core.base.process.AIAnnotationProcessor;
import com.wangjie.androidinject.annotation.present.AIPresent;

import java.lang.reflect.Field;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2/4/15.
 */
public class AIBeanFieldProcessor implements AIAnnotationProcessor<Field> {

    @Override
    public void process(AIPresent present, Field field) throws Exception {
        try {
            field.getType().getConstructor();
        } catch (NoSuchMethodException e) {
            throw new Exception(field.getType() + " must has a default constructor (a no-args constructor)! " + e.getMessage());
        }
        field.setAccessible(true);
        try {
            field.set(present, field.getType().newInstance());
        } catch (Exception e) {
            throw new Exception("Bean newInstance() failed! " + e.getMessage());
        }
    }
}
