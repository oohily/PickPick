package com.oohily.com.pickpick.Login.model;


import android.util.Log;

import com.oohily.com.pickpick.MyApplication;
import com.oohily.com.pickpick.gen.UserEntityDao;


/**
 * Created by Zhongsz on 2017/7/17.
 */

public class UserModel implements IUser {
    String name;
    String password;
    private static final String TAG = "UserModel";
    public UserModel(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPasswd() {
        return password;
    }

    @Override
    public boolean checkUserValidity() {
        if (password == null){
            Log.d(TAG, "checkUserValidity: password null");
            UserEntityDao userEntityDao = MyApplication.getApplication().getDaoSession().getUserEntityDao();
            if (userEntityDao.queryBuilder().where(UserEntityDao.Properties.Username.eq(name)).list().size() == 0){
                Log.d(TAG, "checkUserValidity: not found");
                return false;
            }
            return true;
        }else {
            UserEntityDao userEntityDao = MyApplication.getApplication().getDaoSession().getUserEntityDao();
            if (userEntityDao.queryBuilder().where(UserEntityDao.Properties.Username.eq(name),UserEntityDao.Properties.Password.eq(password)).list().size() == 0){
                return false;
            }
            return true;
        }
    }
}
