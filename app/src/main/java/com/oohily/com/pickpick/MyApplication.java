package com.oohily.com.pickpick;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.oohily.com.pickpick.gen.DaoMaster;
import com.oohily.com.pickpick.gen.DaoSession;


/**
 * Created by Zhongsz on 2017/7/17.
 */

public class MyApplication extends Application {

    private static MyApplication application;
    private static Context mContext;
    private static boolean isLogin;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        setDatabase();
        mContext = getApplicationContext();
    }

    public static MyApplication getApplication() {
        return application;
    }

    public static Context getmContext() {
        return mContext;
    }

    public void setLogin(boolean status) {
        isLogin = status;
    }

    public boolean isLogin() {
        return isLogin;
    }

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    /**
     * 设置greenDao
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "pick-db", null);
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
}