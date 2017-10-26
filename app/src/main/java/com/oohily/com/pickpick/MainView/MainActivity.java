package com.oohily.com.pickpick.MainView;

import com.bumptech.glide.Glide;
import com.google.gson.internal.LinkedTreeMap;
import com.oohily.com.pickpick.CustomView.GaChaView;
import com.oohily.com.pickpick.Login.LoginActivity;
import com.oohily.com.pickpick.MainView.adapter.ListAdapet;
import com.oohily.com.pickpick.MainView.adapter.MainAdapter;
import com.oohily.com.pickpick.MainView.model.Item;
import com.oohily.com.pickpick.MainView.model.ListItem;
import com.oohily.com.pickpick.MainView.presenter.IMainPresenter;
import com.oohily.com.pickpick.MainView.presenter.MainPresenter;
import com.oohily.com.pickpick.MainView.view.IMainView;
import com.oohily.com.pickpick.MyApplication;
import com.oohily.com.pickpick.R;
import com.oohily.com.pickpick.SettingView.SettingActivity;
import com.oohily.com.pickpick.configs.Constant;
import com.oohily.com.pickpick.configs.StringConstant;
import com.sandemarine.hsddialog.HSDDialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements IMainView, NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pick_btn)
    ImageButton pickBtn;
    @BindView(R.id.menu_layout_left)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.list_recycler)
    RecyclerView recyclerList;
    @BindView(R.id.gacha_hint)
    ImageView gachaHint;
    @BindView(R.id.gacha_view)
    GaChaView gaChaView;
    @BindView(R.id.weather_icon)
    ImageView weatherIcon;
    @BindView(R.id.weather_deg)
    TextView weatherDeg;

    private static final String TAG = "MainActivity";
    IMainPresenter iMainPresenter;
    MainAdapter adapter;
    ListAdapet listAdapet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fitWindow();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        iMainPresenter = new MainPresenter(this);
        navigationView.setNavigationItemSelectedListener(this);
        verifyUser();
        iMainPresenter.getWeather();
    }

    //去掉状态栏
    private void fitWindow() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    //右DRAWER初始化
    public void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerList.setLayoutManager(linearLayoutManager);
        iMainPresenter.loadList();
    }

    @Override
    public void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        iMainPresenter.loadData("default");
    }

    //获取用户
    @Override
    public void verifyUser() {
        if (!MyApplication.getApplication().getSharedPreferences(StringConstant.USER_PREFERENCE, Context.MODE_PRIVATE).getString(StringConstant.USER_NAME, "").equals("")) {
            MyApplication.getApplication().setLogin(true);
        }
        if (MyApplication.getApplication().isLogin()) {
            initView();
            initList();
        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivityForResult(intent, Constant.LOGIN_REQUEST);
        }
    }

    //Toolbar Setting
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    //主界面
    @Override
    public void onLoadData(List<Item> itemList) {
        adapter = new MainAdapter(itemList);
        adapter.register(iMainPresenter);
        recyclerView.setAdapter(adapter);
    }

    //右drawer
    @Override
    public void onLoadList(List<ListItem> listItemList) {
        listAdapet = new ListAdapet(listItemList);
        listAdapet.register(iMainPresenter);
        recyclerList.setAdapter(listAdapet);
    }

    //点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final EditText editText = new EditText(this);
        switch (item.getItemId()) {
            case R.id.add:
                new AlertDialog.Builder(this)
                        .setTitle("添加")
                        .setView(editText)
                        .setPositiveButton("吃！", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                iMainPresenter.addData(editText.getText().toString());
                            }
                        })
                        .setNegativeButton("滚！", null)
                        .show();
                break;
            default:
        }
        return true;
    }

    //左drawer菜单点击
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_setting:
                Toast.makeText(this, "设设设有什么好设的?", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_in,R.anim.slide_left_out);
                break;
            case R.id.menu_personal:
                Toast.makeText(this, "????????????????", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

    //主界面add回调
    @Override
    public void onAddData(int status, int position) {
        if (status == Constant.SUCCESS) {
            adapter.notifyItemRangeInserted(position, 1);
//            adapter.notifyDataSetChanged();
        } else if (status == Constant.FAIL) {
            Toast.makeText(this, "已存在", Toast.LENGTH_SHORT).show();
        }
    }

    //右drawer add回调
    @Override
    public void onAddList(int status, int position) {
        if (status == Constant.SUCCESS) {
            listAdapet.notifyItemRangeInserted(position, 1);
//            adapter.notifyDataSetChanged();
        } else if (status == Constant.FAIL) {
            Toast.makeText(this, "已存在", Toast.LENGTH_SHORT).show();
        }
    }

    //主界面delete回调
    @Override
    public void onDeleteData(int position) {
        adapter.notifyItemRangeRemoved(position, 1);
        adapter.notifyDataSetChanged();
    }

    //右drawer delete回调
    @Override
    public void onDeleteList(int position) {
        listAdapet.notifyItemRangeRemoved(position, 1);
        listAdapet.notifyDataSetChanged();
    }

    @OnClick(R.id.logout_btn)
    public void doLogout() {
        iMainPresenter.doLogOut();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Constant.LOGIN_RESULT && requestCode == Constant.LOGIN_REQUEST) {
            initView();
            initList();
        }
    }

    //右drawer add回调
    @OnClick(R.id.add_list)
    public void addList() {
        final EditText editText = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle("添加")
                .setView(editText)
                .setPositiveButton("吔！", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (editText.getText().toString().equals("")) {
                            Toast.makeText(MainActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                        } else {
                            iMainPresenter.addList(editText.getText().toString());
                        }
                    }
                })
                .setNegativeButton("No！", null)
                .show();
    }

    //关闭右drawer
    @Override
    public void dismissList() {
        drawerLayout.closeDrawer(Gravity.END);
    }

    //gacha
    @OnClick(R.id.pick_btn)
    public void pick() {
        pressanim();
        long seed = System.currentTimeMillis();
        gachaHint.setVisibility(View.VISIBLE);
        gachaanim();
        iMainPresenter.doPick(seed);
        gaChaView.setVisibility(View.VISIBLE);
        gaChaView.setStage(1);
//        gaChaView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                return false;
//            }
//        });
    }

    @Override
    public void onPickResult(String result) {
        final HSDDialog dialog = new HSDDialog.Builder()
                .setStyle(HSDDialog.DIALOG_STYLE_ALERT)
                .setPosition(HSDDialog.DIALOG_POSITION_CENTER)
                .setAnim(R.style.DialogAlpha)
                .setText(result)
                .create();
        dialog.show(getSupportFragmentManager(),"gacha");
    }

    //gacha按钮点击动画
    private void pressanim() {
        ObjectAnimator animX = ObjectAnimator.ofFloat(pickBtn, "scaleX", 1f, 0.95f, 1f);
        ObjectAnimator animY = ObjectAnimator.ofFloat(pickBtn, "scaleY", 1f, 0.95f, 1f);
        AnimatorSet set = new AnimatorSet();
        set.play(animX).with(animY);
        set.setDuration(500);
        set.start();
    }

    //gacha动画
    private void gachaanim() {
        ObjectAnimator animGacha = ObjectAnimator.ofFloat(gachaHint, "alpha", 1f, 0f);
        animGacha.setDuration(2000);
        animGacha.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                gachaHint.setVisibility(View.INVISIBLE);
            }
        });
        animGacha.start();
    }

    @Override
    public void onGetWeather(LinkedTreeMap result) {
        String text = ((LinkedTreeMap)result.get("now")).get("tmp").toString();
        weatherDeg.setText(text + "℃");
        String code = ((LinkedTreeMap)((LinkedTreeMap)result.get("now")).get("cond")).get("code").toString();
        String url = "https://cdn.heweather.com/cond_icon/" + code + ".png";
        Glide.with(this)
                .load(url)
                .into(weatherIcon);
    }
}
