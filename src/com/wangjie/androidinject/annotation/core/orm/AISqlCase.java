package com.wangjie.androidinject.annotation.core.orm;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:wangjie@cyyun.com
 * Date: 14-3-25
 * Time: 下午3:15
 */
public class AISqlCase {
    private String sql;
    private Object[] selectionArgs;

    public AISqlCase() {
    }

    public AISqlCase(String sql, Object[] selectionArgs) {
        this.sql = sql;
        this.selectionArgs = selectionArgs;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Object[] getSelectionArgs() {
        return selectionArgs;
    }

    public void setSelectionArgs(Object[] selectionArgs) {
        this.selectionArgs = selectionArgs;
    }
}
