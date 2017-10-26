package com.oohily.com.pickpick.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.oohily.com.pickpick.gen.DaoMaster;
import com.oohily.com.pickpick.gen.DaoSession;

/**
 * Created by Zhongsz on 2017/7/24.
 */

public class DaoUtil {
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private String name;

    public DaoUtil(String name) {
        this.name = name;
    }

    /**
     * 设置greenDao
     */
    public void setDatabase(Context context) {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(context, name, null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
//        mDaoSession.clear();
    }
    public DaoSession getDaoSession() {
        return mDaoSession;
    }
    public SQLiteDatabase getDb() {
        return db;
    }
    /**
     * 关闭DaoSession
     */
    public void closeDaoSession() {
        if (mDaoSession != null) {
            mDaoSession.clear();
            mDaoSession = null;
        }
    }

    /**
     * 关闭Helper
     */
    public void closeHelper() {
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
    }

    /**
     * 关闭所有的操作
     */
    public void closeConnection() {
        closeHelper();
        closeDaoSession();
    }
}
