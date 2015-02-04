package com.wangjie.androidinject.annotation.present.common;

import com.wangjie.androidinject.annotation.annotations.base.*;
import com.wangjie.androidinject.annotation.annotations.dimens.AIScreenSize;
import com.wangjie.androidinject.annotation.annotations.mvp.AIPresenter;
import com.wangjie.androidinject.annotation.annotations.net.AINetWorker;
import com.wangjie.androidinject.annotation.core.base.process.AIAnnotationProcessor;
import com.wangjie.androidinject.annotation.core.base.process.field.*;
import com.wangjie.androidinject.annotation.core.base.process.method.*;
import com.wangjie.androidinject.annotation.core.base.process.type.AIFullScreenTypeProcessor;
import com.wangjie.androidinject.annotation.core.base.process.type.AILayoutTypeProcessor;
import com.wangjie.androidinject.annotation.core.base.process.type.AINoTitleTypeProcessor;

import java.lang.annotation.Annotation;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2/4/15.
 */
public enum AnnoProcessorAlias {
    /**
     * ************************ Field Annotations *****************************
     */
    /**
     * 控件注解
     */
    AIVIEW(AIView.class, AIViewFieldProcessor.class),
    /**
     * MVP注解
     */
    AIPresenter(AIPresenter.class, AIPresenterFieldProcessor.class),
    /**
     * 网络请求注解
     */
    AINetWorker(AINetWorker.class, AINetWorkerFieldProcessor.class),
    /**
     * java bean注解
     */
    AIBean(AIBean.class, AIBeanFieldProcessor.class),
    /**
     * 屏幕尺寸注解
     */
    AIScreenSize(AIScreenSize.class, AIScreenSizeFieldProcessor.class),
    /**
     * 系统服务注解
     */
    AISystemService(AISystemService.class, AISystemServiceFieldProcessor.class),

    /**
     * ************************ Method Annotations *****************************
     */
    /**
     * 点击事件注解
     */
    AIClick(AIClick.class, AIClickMethodProcessor.class),
    /**
     * 长按事件注解
     */
    AILongClick(AILongClick.class, AILongClickMethodProcessor.class),
    /**
     * item点击事件注解
     */
    AIItemClick(AIItemClick.class, AIItemClickMethodProcessor.class),
    /**
     * item长按事件注解
     */
    AIItemLongClick(AIItemLongClick.class, AIItemLongClickMethodProcessor.class),
    /**
     * 选中事件注解
     */
    AIChecked(AIChecked.class, AICheckedMethodProcessor.class),

    /**
     * ************************ Type Annotations *****************************
     */
    /**
     * 布局注解
     */
    AILayout(AILayout.class, AILayoutTypeProcessor.class),
    /**
     * 全部注解
     */
    AIFullScreen(AIFullScreen.class, AIFullScreenTypeProcessor.class),
    /**
     * 隐藏Title注解
     */
    AINoTitle(AINoTitle.class, AINoTitleTypeProcessor.class);

    /**
     * 注解类型
     */
    private Class<? extends Annotation> annotationClazz;
    /**
     * 对应的注解解析器的类型
     */
    private Class<? extends AIAnnotationProcessor> processorClazz;

    AnnoProcessorAlias(Class<? extends Annotation> annotationClazz, Class<? extends AIAnnotationProcessor> processorClazz) {
        this.annotationClazz = annotationClazz;
        this.processorClazz = processorClazz;
    }

    public Class<? extends Annotation> getAnnotationClazz() {
        return annotationClazz;
    }

    public Class<? extends AIAnnotationProcessor> getProcessorClazz() {
        return processorClazz;
    }

    /**
     * 根据注解类型，返回对应的注解解析器类型
     *
     * @param annotationClazz
     * @return
     */
    public static Class<? extends AIAnnotationProcessor> getAnnotationProcessor(Class<? extends Annotation> annotationClazz) {
        AnnoProcessorAlias[] aliases = AnnoProcessorAlias.values();
        for (AnnoProcessorAlias alias : aliases) {
            if (alias.annotationClazz.equals(annotationClazz)) {
                return alias.processorClazz;
            }
        }
        return null;
    }
}
