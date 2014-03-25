package com.wangjie.androidinject.annotation.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:wangjie@cyyun.com
 * Date: 14-3-25
 * Time: 上午9:34
 */
public class AIDbUtil {
    public final static String TAG = AIDbUtil.class.getSimpleName();
    /**
     * 创建表，如果不存在
     * @param db
     * @param sql
     * @param tableName
     */
    public static void createTableIfNotExist(SQLiteDatabase db, String sql, String tableName){
        if(!tableIsExist(db, tableName)){
//            Log.d(TAG, "onUpgrade database, sql: " + sql);
            db.execSQL(sql);
        }
    }

    /**
     * 删除表，如果存在
     * @param db
     * @param sql
     * @param tableName
     */
    public static void deleteTableIfExist(SQLiteDatabase db, String sql, String tableName){
        if(tableIsExist(db, tableName)){
//            Log.d(TAG, "onUpgrade database, sql: " + sql);
            db.execSQL(sql);
        }
    }

    /**
     * 判断某张表是否存在
     * @param tableName 表名
     * @return
     */
    public static boolean tableIsExist(SQLiteDatabase db, String tableName){
        boolean result = false;
        if(tableName == null){
            return false;
        }
        Cursor cursor = null;
        try {
//            db = this.getReadableDatabase();
            String sql = "select count(*) as c from Sqlite_master where type ='table' and name ='"+tableName.trim()+"' ";
            cursor = db.rawQuery(sql, null);
            if(cursor.moveToNext()){
                int count = cursor.getInt(0);
                if(count>0){
                    result = true;
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "[tableIsExist method]error, e: ", e);
        }
        return result;
    }

    /**
     * 检查某表列是否存在
     * @param db
     * @param tableName 表名
     * @param columnName 列名
     * @return
     */
    public static boolean columnExist(SQLiteDatabase db, String tableName
            , String columnName) {
        boolean result = false ;
        Cursor cursor = null ;
        try{
            //查询一行
            cursor = db.rawQuery( "SELECT * FROM " + tableName + " LIMIT 0" , null );
            result = cursor != null && cursor.getColumnIndex(columnName) != -1 ;
        }catch (Exception e){
            Log.e(TAG, "[columnExist method]error, e: ", e);
        }finally{
            if(null != cursor && !cursor.isClosed()){
                cursor.close() ;
            }
        }

        return result ;
    }


}
