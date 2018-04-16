package com.hurteng.stormplane.view;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.hurteng.stormplane.MainActivity;
import com.hurteng.stormplane.sounds.GameSoundPool;

@SuppressLint("ViewConstructor")
public class BaseView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    protected int currentFrame;
    protected float scalex;
    protected float scaley;
    protected float screen_width;
    protected float screen_height;
    protected boolean threadFlag;
    protected Paint paint;
    protected Canvas canvas;
    protected Thread thread;
    protected SurfaceHolder sfh;
    protected GameSoundPool sounds;
    protected MainActivity mainActivity;

    /**
     * 构造函数
     * @param context
     * @param sounds
     */
    public BaseView(Context context, GameSoundPool sounds) {
        super(context);
        this.sounds = sounds;
        this.mainActivity = (MainActivity) context;
        sfh = this.getHolder();
        sfh.addCallback(this);
        paint = new Paint();
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        screen_width = this.getWidth();
        screen_height = this.getHeight();
        threadFlag = true;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        threadFlag = false;
    }

    public void initBitmap() {
    }

    /**
     * 释放资源
     */
    public void release() {
    }

    /**
     * 绘制
     */
    public void drawSelf() {
    }

    @Override
    public void run() {

    }

    public void setThreadFlag(boolean threadFlag) {
        this.threadFlag = threadFlag;
    }


}
