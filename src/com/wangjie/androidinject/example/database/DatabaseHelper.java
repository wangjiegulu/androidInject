package com.wangjie.androidinject.example.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import com.wangjie.androidinject.annotation.core.orm.AIDatabaseHelper;
import com.wangjie.androidinject.annotation.util.AIDbUtil;

public class DatabaseHelper extends AIDatabaseHelper {
    private static final String TAG = DatabaseHelper.class.getSimpleName();

    // 在SQLiteOpenHelper子类中必须调用该构造函数
    public DatabaseHelper(Context context, String name, CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }


    // 该函数是在第一次创建数据库时执行，实际上是在第一次得到SQLiteOpenHelper对象的时候才会调用这个方法
    @Override
    public void onCreate(SQLiteDatabase db) {
        AIDbUtil.createTableIfNotExist(db,
                "create table user(uid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "username varchar(20), " +
                        "password varchar(20), " +
                        "createmillis long, " +
                        "height float, " +
                        "weight double)",
                "user");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }



}
