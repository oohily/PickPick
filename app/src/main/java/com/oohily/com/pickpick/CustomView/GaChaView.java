package com.oohily.com.pickpick.CustomView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Zhongsz on 2017/7/29.
 */

public class GaChaView extends View implements View.OnClickListener {
    private static final String TAG = "GaChaView";
    private final static int STAGE_CLOSE = -1;
    private final static int STAGE_N = 0;
    private final static int STAGE_R = 1;
    private final static int STAGE_SR = 2;
    private final static int STAGE_SSR = 3;
    Paint mPaint;
    float positionX;
    float positionY;
    int stage;
    int stageColor;
    boolean clickAble;

    public GaChaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        stageColor = Color.WHITE;
        clickAble = false;
//        stage = STAGE_N;
//        mPaint.setAlpha(1);
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    @Override
    public void onClick(View view) {
        if (clickAble) {
            clickAble = false;
            switch (stage) {
                case STAGE_N:
                    stageColor = Color.GRAY;
                    invalidate();
                    break;
                case STAGE_R:
                    stageColor = Color.YELLOW;
                    invalidate();
                    break;
                case STAGE_SR:
                    stageColor = Color.GREEN;
                    invalidate();
                    break;
                case STAGE_SSR:
                    stageColor = Color.RED;
                    invalidate();
                    break;
                case STAGE_CLOSE:
                    this.setVisibility(INVISIBLE);
                    break;
                default:
                    this.setVisibility(INVISIBLE);
                    break;

            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        mPaint.setColor(stageColor);
        canvas.drawColor(stageColor);
        startAnimation();
    }

    private void startAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "alpha", 1f, 0f);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                clickAble = true;
                super.onAnimationEnd(animation);
                if (stage == STAGE_SSR) {
                    stage = STAGE_CLOSE;
                    performClick();
                } else if (stage < STAGE_SSR) {
                    stage++;
                    performClick();
                }
            }
        });
        animator.setDuration(1500);
        animator.start();
    }
}
