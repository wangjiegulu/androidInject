package com.wangjie.androidinject.annotation.core.orm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AIDatabaseHelper extends SQLiteOpenHelper {
//    private static final int VERSION = 1;

//    private static final String TAG = AIDatabaseHelper.class.getSimpleName();

    // 在SQLiteOpenHelper子类中必须调用该构造函数
    public AIDatabaseHelper(Context context, String name, CursorFactory factory,
                            int version) {
        super(context, name, factory, version);
    }

    public AIDatabaseHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }

//    public AIDatabaseHelper(Context context, String name) {
//        this(context, name, 1);
//    }

    // 该函数是在第一次创建数据库时执行，实际上是在第一次得到SQLiteOpenHelper对象的时候才会调用这个方法
    @Override
    public void onCreate(SQLiteDatabase db) {
//        Log.d(TAG, "create database...");
        // execSQL函数用于执行SQL语句

        // 预警、推荐阅读已读未读表
//        db.execSQL("create table warn_read_state_table(readid INTEGER PRIMARY KEY AUTOINCREMENT, aid long, said long)");
//        createTableIfNotExist(db, "create table warn_read_state_table(readid INTEGER PRIMARY KEY AUTOINCREMENT, aid long, warn_type int)", "warn_read_state_table");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//        Log.d(TAG, "upgrade a Database...");
        // 创建搜索历史表search_history
//        createTableIfNotExist(db, "create table search_history(historyid INTEGER PRIMARY KEY AUTOINCREMENT, word varchar(20))", "search_history");

    }



}
