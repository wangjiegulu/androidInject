package com.wangjie.androidinject.annotation.present.common;

import com.wangjie.androidinject.annotation.annotations.base.*;
import com.wangjie.androidinject.annotation.annotations.dimens.AIScreenSize;
import com.wangjie.androidinject.annotation.annotations.mvp.AIPresenter;
import com.wangjie.androidinject.annotation.annotations.net.AINetWorker;
import com.wangjie.androidinject.annotation.cache.common.CommonCache;
import com.wangjie.androidinject.annotation.cache.common.generator.CachedAnnotationProcessorGenerator;
import com.wangjie.androidinject.annotation.core.base.process.AIAnnotationProcessor;
import com.wangjie.androidinject.annotation.core.base.process.field.*;
import com.wangjie.androidinject.annotation.core.base.process.method.*;
import com.wangjie.androidinject.annotation.core.base.process.type.AIFullScreenTypeProcessor;
import com.wangjie.androidinject.annotation.core.base.process.type.AILayoutTypeProcessor;
import com.wangjie.androidinject.annotation.core.base.process.type.AINoTitleTypeProcessor;

import java.lang.annotation.Annotation;
import java.util.HashMap;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 2/4/15.
 */
public enum AnnoProcessorAlias {
    /**
     * ************************ Field Annotations BEGIN *****************************
     */
    /**
     * 控件注解
     */
    AI_VIEW(AIView.class, AIViewFieldProcessor.class),
    /**
     * MVP注解
     */
    AI_PRESENTER(AIPresenter.class, AIPresenterFieldProcessor.class),
    /**
     * 网络请求注解
     */
    AI_NET_WORKER(AINetWorker.class, AINetWorkerFieldProcessor.class),
    /**
     * java bean注解
     */
    AI_BEAN(AIBean.class, AIBeanFieldProcessor.class),
    /**
     * 屏幕尺寸注解
     */
    AI_SCREEN_SIZE(AIScreenSize.class, AIScreenSizeFieldProcessor.class),
    /**
     * 系统服务注解
     */
    AI_SYSTEM_SERVICE(AISystemService.class, AISystemServiceFieldProcessor.class),
    /**
     * ************************ Field Annotations END *****************************
     */


    /**
     * ************************ Method Annotations BEGIN *****************************
     */
    /**
     * 点击事件注解
     */
    AI_CLICK(AIClick.class, AIClickMethodProcessor.class),
    /**
     * 长按事件注解
     */
    AI_LONG_CLICK(AILongClick.class, AILongClickMethodProcessor.class),
    /**
     * item点击事件注解
     */
    AI_ITEM_CLICK(AIItemClick.class, AIItemClickMethodProcessor.class),
    /**
     * item长按事件注解
     */
    AI_ITEM_LONG_CLICK(AIItemLongClick.class, AIItemLongClickMethodProcessor.class),
    /**
     * 选中事件注解
     */
    AI_CHECKED(AIChecked.class, AICheckedMethodProcessor.class),
    /**
     * ************************ Method Annotations END *****************************
     */


    /**
     * ************************ Type Annotations BEGIN *****************************
     */
    /**
     * 布局注解
     */
    AI_LAYOUT(AILayout.class, AILayoutTypeProcessor.class),
    /**
     * 全部注解
     */
    AI_FULL_SCREEN(AIFullScreen.class, AIFullScreenTypeProcessor.class),
    /**
     * 隐藏Title注解
     */
    AI_NO_TITLE(AINoTitle.class, AINoTitleTypeProcessor.class);

    /**
     * ************************ Type Annotations END *****************************
     */

    private static final String TAG = AnnoProcessorAlias.class.getSimpleName();

    /**
     * 缓存annotation类型和对应的解析器
     */
    private static HashMap<Class<? extends Annotation>, AnnoProcessorAlias> annotationMapper;

    static {
        annotationMapper = new HashMap<>();
        for (AnnoProcessorAlias alias : AnnoProcessorAlias.values()) {
            annotationMapper.put(alias.annotationClazz, alias);
        }
    }

    /**
     * 注解处理器的缓存生成器
     */
    static CachedAnnotationProcessorGenerator cachedAnnotationProcessorGenerator = new CachedAnnotationProcessorGenerator();

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
    public static AnnoProcessorAlias getAnnotationProcessorAlias(Class<? extends Annotation> annotationClazz) {
        return annotationMapper.get(annotationClazz);
    }

    public static AIAnnotationProcessor getCachedAnnotationProcessor(final Class<? extends Annotation> annotationClazz) {
        cachedAnnotationProcessorGenerator.setAnnotationClazz(annotationClazz);
        return CommonCache.getInstance().getCache(AIAnnotationProcessor.class, TAG, annotationClazz, cachedAnnotationProcessorGenerator);
    }

}
