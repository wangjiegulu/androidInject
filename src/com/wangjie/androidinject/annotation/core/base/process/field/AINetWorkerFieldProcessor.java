package com.wangjie.androidinject.annotation.core.base.process.field;

import com.wangjie.androidinject.annotation.core.base.process.AIAnnotationProcessor;
import com.wangjie.androidinject.annotation.core.net.NetInvoHandler;
import com.wangjie.androidinject.annotation.present.AIPresent;

import java.lang.reflect.Field;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2/4/15.
 */
public class AINetWorkerFieldProcessor implements AIAnnotationProcessor<Field> {
    @Override
    public void process(AIPresent present, Field field) throws Exception {
        field.setAccessible(true);
        field.set(present, NetInvoHandler.getWorker(field.getType()));
    }
}
