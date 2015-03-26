package com.wangjie.androidinject.annotation.cache;

import com.wangjie.androidinject.annotation.present.AIPresent;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 3/23/15.
 */
@Deprecated
public class FieldCache {
    private static final String TAG = FieldCache.class.getSimpleName();
    @Deprecated
    public static class CachedField{
        private Field field;
        private Annotation[] annotations;

        public Annotation[] getAnnotations() {
            return annotations;
        }

        public void setAnnotations(Annotation[] annotations) {
            this.annotations = annotations;
        }

        public Field getField() {
            return field;
        }

        public void setField(Field field) {
            this.field = field;
        }
    }

    private static FieldCache instance;

    public synchronized static FieldCache getInstance() {
        if (null == instance) {
            instance = new FieldCache();
        }
        return instance;
    }

    private FieldCache() {
    }

    private ConcurrentHashMap<Class<? extends AIPresent>, List<CachedField>> cacheMapper = new ConcurrentHashMap<>();

    public List<CachedField> getCache(Class<? extends AIPresent> key) {
        List<CachedField> cachedFields = cacheMapper.get(key);
        if (null == cachedFields) {
            cachedFields = new ArrayList<>();

            Field[] fields = key.getDeclaredFields();
            for (Field field : fields) {
                CachedField cachedField = new CachedField();
                cachedField.setField(field);
                cachedField.setAnnotations(field.getAnnotations());
                cachedFields.add(cachedField);
            }
            cacheMapper.put(key, cachedFields);
//            Logger.i(TAG, key.getName() + " fields find with reflect..., fields: " + cachedFields);
        }
        return cachedFields;
    }

}
