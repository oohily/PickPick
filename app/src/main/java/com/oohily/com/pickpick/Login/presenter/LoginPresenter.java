package com.oohily.com.pickpick.Login.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.oohily.com.pickpick.Entity.ListEntity;
import com.oohily.com.pickpick.Entity.PersonalEntity;
import com.oohily.com.pickpick.Entity.UserEntity;
import com.oohily.com.pickpick.Login.model.IUser;
import com.oohily.com.pickpick.Login.model.UserModel;
import com.oohily.com.pickpick.Login.view.ILoginView;
import com.oohily.com.pickpick.MyApplication;
import com.oohily.com.pickpick.configs.StringConstant;
import com.oohily.com.pickpick.gen.ListEntityDao;
import com.oohily.com.pickpick.gen.PersonalEntityDao;
import com.oohily.com.pickpick.gen.UserEntityDao;
import com.oohily.com.pickpick.utils.DaoUtil;

/**
 * Created by Zhongsz on 2017/7/17.
 */

public class LoginPresenter implements ILoginPresenter {
    ILoginView iLoginView;
    IUser user;
    private static final String TAG = "LoginPresenter";
    public LoginPresenter(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
    }
    @Override
    public void doLogin(String name, String password){
        user = new UserModel(name,password);
        if (user.checkUserValidity()){
            SharedPreferences preferences = MyApplication.getApplication().getSharedPreferences(StringConstant.USER_PREFERENCE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(StringConstant.USER_NAME,name);
            editor.apply();
            iLoginView.onLoginResult(true);
        }else {
            Log.d(TAG, "doLogin: login fail");
            UserEntityDao userEntityDao = MyApplication.getApplication().getDaoSession().getUserEntityDao();
            userEntityDao.insert(new UserEntity(null,name,null));
            initUser(name,password);
            iLoginView.onLoginResult(false);
        }
    }
    private void initUser(String name,String password){
        SharedPreferences preferences = MyApplication.getApplication().getSharedPreferences(StringConstant.USER_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(StringConstant.USER_NAME,name);
        editor.apply();
        PersonalEntityDao personalEntityDao = MyApplication.getApplication().getDaoSession().getPersonalEntityDao();
        personalEntityDao.insert(new PersonalEntity(null,name,name+"_default"));
        ListEntityDao listEntityDao = MyApplication.getApplication().getDaoSession().getListEntityDao();
        listEntityDao.insert(new ListEntity(null,name+"_default","水林间",0,0));
        listEntityDao.insert(new ListEntity(null,name+"_default","食其家",0,0));
    }
}
