package com.oohily.com.pickpick.SettingView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.oohily.com.pickpick.MainView.MainActivity;
import com.oohily.com.pickpick.R;
import com.oohily.com.pickpick.SettingView.view.ISettingView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.microedition.khronos.opengles.GL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity implements ISettingView {
    private static final String TAG = "SettingActivity";
    private float winX, winY;
    private float touchX, touchY;
    private float moveX, moveY;
    @BindView(R.id.test_pic)
    ImageView testPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        WindowManager wm = getWindowManager();
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        winX = size.x;
        winY = size.y;

    }

    @OnClick(R.id.setting_back)
    public void back() {
        if (ContextCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SettingActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            openAlbum();
        }
    }

    private static final int CHOOSE_PHOTO = 2;

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ContentResolver resolver = getContentResolver();
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri uri = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(resolver, uri);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
                        byte[] bytes = baos.toByteArray();
                        Glide.with(this)
                                .load(bytes)
                                .into(testPic);
                        Log.d(TAG, "onActivityResult: " + uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
        }
    }

    public static View getContentView(Activity ac) {
        ViewGroup view = (ViewGroup) ac.getWindow().getDecorView();
        FrameLayout content = (FrameLayout) view.findViewById(android.R.id.content);
        return content.getChildAt(0);
    }
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        WindowManager windowManager = (WindowManager) getSystemService(Service.WINDOW_SERVICE);
//        View v = getContentView(this);
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                touchX = event.getX();
//                touchY = event.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
////                WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams)v.getLayoutParams();
////                layoutParams.x = (int) event.getRawX();
////                layoutParams.y = (int) event.getRawY();
////                windowManager.updateViewLayout(v,layoutParams);
//                break;
//            case MotionEvent.ACTION_UP:
//                moveX = event.getX() - touchX;
//                moveY = event.getY() - touchY;
//                if (moveX > winX / 2 || moveY > winY / 2) {
//                    finish();
//                    overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
//                }
//                break;
//        }
//        return true;
//    }
}
