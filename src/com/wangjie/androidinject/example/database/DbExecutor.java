package com.wangjie.androidinject.example.database;

import android.content.Context;
import com.wangjie.androidinject.annotation.core.orm.AIDatabaseHelper;
import com.wangjie.androidinject.annotation.core.orm.AIDbExecutor;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:wangjie@cyyun.com
 * Date: 14-3-25
 * Time: 上午9:56
 */
public class DbExecutor<T> extends AIDbExecutor<T>{
    private final static String TAG = DbExecutor.class.getSimpleName();
    public final static String DB_NAME = "androidinject_db";
    public final static int VERSION = 1;

    public DbExecutor(Context context) {
        super(context);
    }

    @Override
    public AIDatabaseHelper obtainDbHelper() {
        AIDatabaseHelper helper = new DatabaseHelper(context, DB_NAME, VERSION);
        return helper;
    }


}
