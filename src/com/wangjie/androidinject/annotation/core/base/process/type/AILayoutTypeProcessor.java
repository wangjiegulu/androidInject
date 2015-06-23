package com.wangjie.androidinject.annotation.core.base.process.type;

import android.support.v4.app.Fragment;
import android.util.Log;
import com.wangjie.androidinject.annotation.annotations.base.AILayout;
import com.wangjie.androidinject.annotation.core.base.process.AIAnnotationProcessor;
import com.wangjie.androidinject.annotation.present.AIPresent;

/**
 * 实现布局注解
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2/4/15.
 */
public class AILayoutTypeProcessor implements AIAnnotationProcessor<Class<?>> {
    private static final String TAG = AILayoutTypeProcessor.class.getSimpleName();

    @Override
    public void process(AIPresent present, Class<?> clazz) throws Exception {
        // 如果是fragment，忽略这里的layout processor处理
        if (Fragment.class.isAssignableFrom(clazz) || android.app.Fragment.class.isAssignableFrom(clazz)) {
            Log.d(TAG, present.getClass() + " layout bind ignore in layout processor.");
            return;
        }

        // 布局类注解setContentView
        AILayout cv = clazz.getAnnotation(AILayout.class);
        if (null == cv) {
            throw new Exception("Present[" + present + "]had not added @AILayout annotation!");
        }

        int layoutId = cv.value();
        if (layoutId < 0) {
            throw new Exception("Present[" + present + "] @AILayout value layoutId is invalidate!");
        }
        present.setContentView_(layoutId);
    }
}
