package com.wangjie.androidinject.annotation.cache.common.generator;

import com.wangjie.androidinject.annotation.cache.common.cached.CachedField;
import com.wangjie.androidinject.annotation.cache.common.cached.CachedPresentFields;
import com.wangjie.androidinject.annotation.present.AIPresent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/26/15.
 */
public class CachedPresentFieldsGenerator implements CachedGenerator<CachedPresentFields>{
    private Class<? extends AIPresent> clazz;

    public void setClazz(Class<? extends AIPresent> clazz) {
        this.clazz = clazz;
    }

    @Override
    public CachedPresentFields generate() throws Exception {
        CachedPresentFields cs = new CachedPresentFields();
        List<CachedField> cachedFields = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            CachedField cachedField = new CachedField();
            cachedField.setField(field);
            cachedField.setAnnotations(field.getAnnotations());
            cachedFields.add(cachedField);
        }
        cs.setCachedFields(cachedFields);
        return cs;
    }
}
