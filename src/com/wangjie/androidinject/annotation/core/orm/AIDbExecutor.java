package com.wangjie.androidinject.annotation.core.orm;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.wangjie.androidinject.annotation.annotations.orm.AIColumn;
import com.wangjie.androidinject.annotation.annotations.orm.AIPrimaryKey;
import com.wangjie.androidinject.annotation.annotations.orm.AITable;
import com.wangjie.androidinject.annotation.util.AITextUtil;
import com.wangjie.androidinject.annotation.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.sql.Blob;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 14-3-24
 * Time: 下午4:44
 */
public abstract class AIDbExecutor<T> {
    private static final String TAG = AIDbExecutor.class.getSimpleName();
//    public SQLiteDatabase db;
    public AIDatabaseHelper dbHelper;

    public Context context;

    public AIDbExecutor(Context context) {
        this.context = context;
        this.dbHelper = obtainDbHelper();
    }

    public abstract AIDatabaseHelper obtainDbHelper();

    /**
     * 查询数据库表并自动装箱到clazz对象中
     * @param sql
     * @param clazz
     * @return
     * @throws Exception
     */
    public List<T> executeQuery(String sql, String[] selectionArgs, Class<?> clazz) throws Exception{
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try{
            sql = sql.trim();
            if(null == sql || !sql.toLowerCase().contains("select")){
                throw new Exception("paramter sql is not a SELECT statement!");
            }
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery(sql, selectionArgs);

            Log.d(TAG, "[executeQuery]sql ==> " + sql);
            Log.d(TAG, "[executeQuery]params ==> " + AITextUtil.joinArray(selectionArgs, ", "));
            List<T> list = null;
            while(cursor.moveToNext()){
                @SuppressWarnings("unchecked")
                final T obj = (T)clazz.newInstance();
                final Cursor cur = cursor;
                ReflectionUtils.doWithFields(clazz, new ReflectionUtils.FieldCallback() {

                    public void doWith(Field field) throws Exception {
                        String columnValue = getColumnValue(field);
                        if (null == columnValue) { // 如果该属性没有加column注解
                            return;
                        }
                        field.setAccessible(true);
                        int index = cur.getColumnIndex(columnValue);
                        Class<?> fieldType = field.getType();
                        String fieldTypeName = fieldType.getName();
                        if(String.class == fieldType){
                            field.set(obj, cur.getString(index));
                        }else if(Long.class == fieldType || fieldTypeName.equals("long")){
                            field.set(obj, cur.getLong(index));
                        }else if(Integer.class == fieldType || fieldTypeName.equals("int")){
                            field.set(obj, cur.getInt(index));
                        }else if(Short.class == fieldType || fieldTypeName.equals("short")){
                            field.set(obj, cur.getShort(index));
                        }else if(Double.class == fieldType || fieldTypeName.equals("double")){
                            field.set(obj, cur.getDouble(index));
                        }else if(Float.class == fieldType || fieldTypeName.equals("float")){
                            field.set(obj, cur.getFloat(index));
                        }else if(Blob.class == fieldType){
                            field.set(obj, cur.getBlob(index));
                        }else{
                            field.set(obj, null);
                        }
                    }

                });
                if(null == list){
                    list = new ArrayList<T>();
                }
                list.add(obj);

            }
            Log.d(TAG, "[executeQuery]result: " + list);
            return list;
        }catch(Exception ex){
            throw ex;
        }finally{
            closeCursor(cursor);
            closeDatabase(db);
        }

    }


    /**
     * 插入一条数据
     * @param obj
     * @return
     * @throws Exception
     */
    public int executeSave(final T obj) throws Exception{
        SQLiteDatabase db = null;
        try{
            final Map<String, Object> map = new HashMap<String, Object>();
            ReflectionUtils.doWithFields(obj.getClass(), new ReflectionUtils.FieldCallback() {

                public void doWith(Field field) throws Exception
                {
                    String columnValue = getColumnValue(field);
                    if(null == columnValue){ // 如果该属性没有加column注解，则不插入数据库
                        return;
                    }
                    AIPrimaryKey pk = field.getAnnotation(AIPrimaryKey.class);
                    if(null != pk && !pk.insertable()){ // 如果是主键，并且设置为不需插入，则不插入数据库
                        return;
                    }
                    field.setAccessible(true);
                    map.put(columnValue, field.get(obj));
                }
            });

            String tablename = getTableValue(obj.getClass()); // 获取表名

            String sql = "insert into " + tablename + "(" + AITextUtil.joinStrings(map.keySet(), ",") + ")" + " values(" + AITextUtil.generatePlaceholders(map.size()) + ")";

            db = dbHelper.getWritableDatabase();
            Log.d(TAG, "[executeSave]sql ==> " + sql);
            db.execSQL(sql, map.values().toArray());
            int result = 0;
            Log.d(TAG, "[executeSave]result ==> " + result);
            return result;
        }catch(Exception ex){
            throw ex;
        }finally{
            closeDatabase(db);
        }

    }


    /**
     * 根据主键更新数据
     * @param obj
     * @param includeParams 包含哪些字段需要更新数据（类的属性）
     * @param excludeParams 排除哪些字段不更新数据（类的属性）
     * @return
     * @throws Exception
     */
    public int executeUpdate(final T obj, final String[] includeParams, final String[] excludeParams) throws Exception{
        SQLiteDatabase db = null;
        try{
            final List<String> includeParamsList = null == includeParams ? null : Arrays.asList(includeParams);
            final List<String> excludeParamsList = null == excludeParams ? new ArrayList<String>() : Arrays.asList(excludeParams);

            final Map<String, Object> pkMap = new HashMap<String, Object>(); // 存储主键的属性和值（类中使用Primay Key注解的）
            final Map<String, Object> updateMap = new HashMap<String, Object>(); // 存储需要更新的属性和值
            ReflectionUtils.doWithFields(obj.getClass(), new ReflectionUtils.FieldCallback() {

                public void doWith(Field field) throws Exception
                {
                    String columnValue = getColumnValue(field);
                    if(null == columnValue){ // 如果该属性没有加column注解，则不插入数据库
                        return;
                    }
                    AIPrimaryKey primaryKey = field.getAnnotation(AIPrimaryKey.class);
                    if(null == primaryKey){ // 如果不是主键
                        if(shouldModifyField(field, includeParamsList, excludeParamsList)){ // 如果包含在includeParams中，并不包含在excludeParams中，则需要更新这个字段
                            field.setAccessible(true);
                            updateMap.put(columnValue, field.get(obj));
                        }
                        return;
                    }
                    //如果是主键
                    field.setAccessible(true);
                    pkMap.put(columnValue, field.get(obj));

                }
            });

            if(updateMap.size() <= 0 || pkMap.size() <= 0){
                Log.w(TAG, "[executeUpdate]更新数据失败，无需更新任何字段或未指定主键而无法更新数据");
                return -1;
            }

            String tablename = getTableValue(obj.getClass()); // 获取表名

            String updateStr = AITextUtil.joinStrings(updateMap.keySet(), ",", "=?");
            String pkStr = AITextUtil.joinStrings(pkMap.keySet(), ",", "=?");
            String sql = "update " + tablename + " set " + updateStr + " where " + pkStr;
            Log.d(TAG, "==> [executeUpdate]sql: " + sql);

            db = dbHelper.getWritableDatabase();

            List<Object> args = new ArrayList<Object>();
            args.addAll(updateMap.values());
            args.addAll(pkMap.values());
            db.execSQL(sql, args.toArray());
            Log.d(TAG, "[executeUpdate]result success ");
            return 0;
        }catch(Exception ex){
            throw ex;
        }finally{
            closeDatabase(db);
        }
    }



    /**
     * 根据主键删除数据
     * @param obj
     * @return
     * @throws Exception
     */
    public int executeDelete(final T obj) throws Exception{
        SQLiteDatabase db = null;
        try{
            final Map<String, Object> pkMap = new HashMap<String, Object>(); // 存储主键的属性和值（类中使用Primay Key注解的）
            ReflectionUtils.doWithFields(obj.getClass(), new ReflectionUtils.FieldCallback() {

                public void doWith(Field field) throws Exception
                {
                    String columnValue = getColumnValue(field);
                    if(null == columnValue){ // 如果该属性没有加column注解，则不插入数据库
                        return;
                    }
                    AIPrimaryKey primaryKey = field.getAnnotation(AIPrimaryKey.class);
                    if(null == primaryKey){ // 如果不是主键
                        return;
                    }
                    //如果是主键
                    field.setAccessible(true);
                    pkMap.put(columnValue, field.get(obj));

                }
            });

            String tablename = getTableValue(obj.getClass()); // 获取表名

            String pkStr = AITextUtil.joinStrings(pkMap.keySet(), ",", "=?");
            String sql = "delete from " + tablename + " where " + pkStr;
            Log.d(TAG, "==> [executeDelete]sql: " + sql);

            db = dbHelper.getWritableDatabase();

            db.execSQL(sql, pkMap.values().toArray());

            Log.d(TAG, "[executeDelete]result ==> ");
            return 0;
        }catch(Exception ex){
            throw ex;
        }finally{
            closeDatabase(db);
        }
    }


    /**
     * 执行一条sql语句
     * @param sql
     * @param selectionArgs
     * @throws Exception
     */
    public void executeSql(String sql, Object[] selectionArgs) throws Exception{
        SQLiteDatabase db = null;
        try{
            db = dbHelper.getWritableDatabase();
            db.execSQL(sql, selectionArgs);
        }catch(Exception ex){
            throw ex;
        }finally {
            closeDatabase(db);
        }
    }

    /**
     * 执行删除
     * @param clazz
     * @param whereClause
     * @param whereArgs
     * @return
     * @throws Exception
     */
    public int executeDelete(Class<?> clazz, String whereClause, String[] whereArgs) throws Exception{
        SQLiteDatabase db = null;
        try{
            db = dbHelper.getWritableDatabase();
            return db.delete(getTableValue(clazz), whereClause, whereArgs);
        }catch(Exception ex){
            throw ex;
        }finally {
            closeDatabase(db);
        }
    }

    /**
     * 使用事务执行多条sql
     * @param sqlCases
     * @throws Exception
     */
    public void executeSqlTransaction(AISqlCase... sqlCases) throws Exception{
        if(null == sqlCases || sqlCases.length <= 0){
            return;
        }
        SQLiteDatabase db = null;
        try{
            db = dbHelper.getWritableDatabase();
            db.beginTransaction();
            for(AISqlCase sc : sqlCases){
                db.execSQL(sc.getSql(), sc.getSelectionArgs());
            }
            db.setTransactionSuccessful();
        }catch(Exception ex){
            throw ex;
        }finally {
            if(null != db){
                db.endTransaction();
                closeDatabase(db);
            }
        }

    }


    public AIDatabaseHelper getDbHelper(){
        return dbHelper;
    }

    public SQLiteDatabase getReadableDatabase(){
        return dbHelper.getReadableDatabase();
    }
    public SQLiteDatabase getWritableDatabase(){
        return dbHelper.getWritableDatabase();
    }




    /**
     * 判断更新数据时是否需要修改某个字段
     * @param field
     * @param includeParamsList
     * @param excludeParamsList
     * @return
     */
    private boolean shouldModifyField(Field field, List<String> includeParamsList, List<String> excludeParamsList){
        String fieldname = field.getName();
        if(null == includeParamsList){
            return !excludeParamsList.contains(fieldname);
        }
        return includeParamsList.contains(fieldname) && !excludeParamsList.contains(fieldname);
    }


    /**
     * 获得Column对应的value值（表的列名）
     * @param field
     * @return 如果该属性没有加column注解，则返回null；如果该属性的column注解value为空，则使用属性名（全小写）作为列名；否则使用value值
     */
    private String getColumnValue(Field field){
        AIColumn column = field.getAnnotation(AIColumn.class);
        if(null == column){ // 如果该属性没有加column注解，则返回null
            return null;
        }
        String value = column.value();
        if("".equals(value)){ // 如果该属性的column注解value为空，则使用属性名
            return field.getName().toLowerCase();
        }
        return value;
    }
    /**
     * 获得Table对应的value值（表名）
     * @param clazz
     * @return 如果类中没有加Table注解，或者Table注解为空，那么直接使用类名（全小写）作为表名；否则使用value值
     */
    private String getTableValue(Class<?> clazz){
        AITable table = clazz.getAnnotation(AITable.class);
        if(null == table || "".equals(table.value())){ // 如果类中没有加Table注解，或者Table注解为空，那么直接使用类名作为表名
            return clazz.getSimpleName().toLowerCase();
        }
        return table.value();
    }


    public void closeCursor(Cursor cursor){
        if(null == cursor){
            return;
        }
        cursor.close();
    }

    public void closeDatabase(SQLiteDatabase db){
        if(null == db){
            return;
        }
        db.close();
    }


}
