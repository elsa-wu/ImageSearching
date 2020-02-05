package com.example.elsa.imagesearching.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.elsa.imagesearching.mvp.model.CommentBean;
import java.util.ArrayList;
import java.util.List;


public class DBDao {
    private static final String TABLE_NAME = "comment";  //table name

    private static final String ID = "id";
    private static final String IMAGEID = "imageId";   // image id
    private static final String COMMENT = "comment";   // comment

    private final DBHelper dbHelper;
    //create table
    static final String SQL_CREATE_TABLE = "create table " + TABLE_NAME + "(" +
            ID + " integer primary key autoincrement," +
            IMAGEID + " varchar(64)," +
            COMMENT + " varchar(64)" +
            ")";

    private DBDao(Context context) {
        dbHelper = new DBHelper(context);
    }
    private static DBDao dbDao = null;
    // DB  singleton
    public static DBDao getInstance(Context context) {
        if (dbDao == null) {
            dbDao = new DBDao(context);
        }
        return dbDao;
    }

    //insert data
    public synchronized <T> void insert(T bean) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            if (bean instanceof CommentBean) {
                CommentBean CommentBean = (CommentBean) bean;
                ContentValues cv = new ContentValues();
//                Log.i("dbdb", CommentBean.getWeight() + "-------" + CommentBean.getDate() + "-------" + CommentBean.getTime());
                cv.put(IMAGEID, CommentBean.getId());
                cv.put(COMMENT, CommentBean.getComment());
                db.insert(TABLE_NAME, null, cv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //select data
    public synchronized List<CommentBean> query(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<CommentBean> list = new ArrayList<>();
        String querySql = "select * from " + TABLE_NAME + " where " +  IMAGEID + "='" +  id + "'";   //sq language
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(querySql, null);
            while (cursor.moveToNext()) {
                CommentBean CommentBean = new CommentBean();
                CommentBean.setComment(cursor.getString(cursor.getColumnIndex(COMMENT)));
                CommentBean.setId(cursor.getString(cursor.getColumnIndex(IMAGEID)));
                list.add(CommentBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return list;
    }

    /**
     * Test use
     * clear db
     */
    public synchronized void clearAll() {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            String sql = "delete from " + TABLE_NAME;
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
