package com.hurteng.stormplane.object;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 游戏物品
 */
abstract public class GameObject {
    protected int currentFrame;    // 帧数
    protected int speed;            // 移动速度
    protected float object_x;        // 物品的x坐标
    protected float object_y;        // 物品的y坐标
    protected float object_width;    // 物体宽度
    protected float object_height;    // 物体高度
    protected float screen_width;    // 屏幕宽度
    protected float screen_height;  // 屏幕高度
    protected boolean isAlive;        // 存活状态
    protected Paint paint;            // 画笔
    protected Resources resources;  // 资源文件

    public GameObject(Resources resources) {
        this.resources = resources;
        this.paint = new Paint();
    }

    public void setScreenWH(float screen_width, float screen_height) {
        this.screen_width = screen_width;
        this.screen_height = screen_height;
    }

    public void initial(int arg0, float arg1, float arg2) {
    }

    protected abstract void initBitmap();

    public abstract void drawSelf(Canvas canvas);

    public abstract void release();

    public boolean isCollide(GameObject obj) {
        return true;
    }

    /**
     * 逻辑
     */
    public void logic() {
    }


    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public float getObject_x() {
        return object_x;
    }

    public void setObject_x(float object_x) {
        this.object_x = object_x;
    }

    public float getObject_y() {
        return object_y;
    }

    public void setObject_y(float object_y) {
        this.object_y = object_y;
    }

    public float getObject_width() {
        return object_width;
    }

    public void setObject_width(float object_width) {
        this.object_width = object_width;
    }

    public float getObject_height() {
        return object_height;
    }

    public void setObject_height(float object_height) {
        this.object_height = object_height;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
}
