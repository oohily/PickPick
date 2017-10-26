package com.oohily.com.pickpick.Login;

import com.oohily.com.pickpick.Login.presenter.LoginPresenter;
import com.oohily.com.pickpick.Login.view.ILoginView;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.oohily.com.pickpick.MyApplication;
import com.oohily.com.pickpick.R;
import com.oohily.com.pickpick.configs.Constant;
import com.oohily.com.pickpick.configs.StringConstant;

import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements ILoginView {
    @BindView(R.id.login_username)
    EditText username;
    LoginPresenter loginPresenter;
    private static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenter(this);
    }

    @Override
    public void onLoginResult(Boolean result) {
        if (result){
            Toast.makeText(this,"来ROLL啊!",Toast.LENGTH_SHORT).show();
            MyApplication.getApplication().setLogin(true);
            setResult(Constant.LOGIN_RESULT);
            finish();
        }else {
            Log.d(TAG, "onLoginResult: login result fail");
            Toast.makeText(this,"欢迎来ROLL!",Toast.LENGTH_SHORT).show();
            MyApplication.getApplication().setLogin(true);
            setResult(Constant.LOGIN_RESULT);
            finish();
        }
    }
    @OnClick(R.id.login_btn)
    public void doLogin(){
        String name = username.getText().toString();
        loginPresenter.doLogin(name,null);
    }
}
