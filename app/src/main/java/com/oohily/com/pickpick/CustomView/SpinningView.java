package com.oohily.com.pickpick.CustomView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Service;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by Zhongsz on 2017/8/11.
 */

public class SpinningView extends View implements View.OnClickListener{
    //转盘paint
    private Paint mPaint;
    //指针paint
    private Paint iPaint;
    //360/items
    private float averageSweep;
    //转到的角度
    private float currentAng;
    //半径
    private float mRadius;
    //转动速度
    private float spinSpeed;
    //块数
    private int blockCount;
    //屏幕中心点
    private float screenX,screenY;
    //转动动画
    private ValueAnimator spin;
    //停止动画
    private ValueAnimator stop;
    //是否在转动
    private boolean isSpinning;
    //是否可以点击
    private boolean touchable;
    public SpinningView(Context context) {
        super(context);
    }

    public SpinningView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initWin();
        init();
        startSpin();
        setOnClickListener(this);
    }

    private void init(){
        mPaint = new Paint();
        iPaint = new Paint();
        mPaint.setAntiAlias(true);
        iPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#FFFF0000"));
        iPaint.setColor(Color.parseColor("#40FF0000"));
        mPaint.setStyle(Paint.Style.STROKE);
        blockCount = 12;
        averageSweep = 360/blockCount;
        currentAng = -90;
        spinSpeed = 25;
        mRadius = 250;
        isSpinning = false;
        touchable = true;
    }
    private void initWin(){
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Service.WINDOW_SERVICE);
        Point p = new Point();
        windowManager.getDefaultDisplay().getSize(p);
        screenX = p.x/2;
        screenY = p.y/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (currentAng >= 360)currentAng -= 360;
        for (int i = 0; i < blockCount; i++) {
            if (currentAng >= averageSweep*i && currentAng < averageSweep*(i+1)){
                canvas.drawArc(screenX - mRadius, screenY - mRadius, screenX + mRadius,
                        screenY + mRadius, averageSweep * i - 90, averageSweep, true, iPaint);
            }
            canvas.drawArc(screenX - mRadius, screenY - mRadius, screenX + mRadius,
                    screenY + mRadius, averageSweep * i - 90, averageSweep, true, mPaint);
        }
    }

    private void startSpin(){
        isSpinning = true;
        spin = ValueAnimator.ofFloat(0f,1f);
        spin.setDuration(1000);
        spin.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                currentAng += spinSpeed;
                invalidate();
            }
        });
        spin.setRepeatMode(ValueAnimator.RESTART);
        spin.setRepeatCount(ValueAnimator.INFINITE);
        spin.setInterpolator(new LinearInterpolator());
        spin.start();
    }
    private void stopSpin(){
        spin.cancel();
        stop = ValueAnimator.ofFloat(spinSpeed,0f);
        stop.setDuration(5000);
        stop.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                currentAng += (float)valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        stop.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                touchable = true;
            }
        });
        stop.setInterpolator(new DecelerateInterpolator());
        stop.start();

    }

    @Override
    public void onClick(View view) {
        if (touchable) {
            if (isSpinning) {
                touchable = false;
                isSpinning = false;
                stopSpin();
            } else {
                startSpin();
            }
        }
    }
}
