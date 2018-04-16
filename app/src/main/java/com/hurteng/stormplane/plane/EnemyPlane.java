package com.hurteng.stormplane.plane;

import android.content.res.Resources;
import android.graphics.Canvas;

import com.hurteng.stormplane.object.GameObject;

/**
 * 敌机类
 */
public class EnemyPlane extends GameObject {
    protected int score;                         // 分数
    protected int blood;                         // 当前血量
    protected int bloodVolume;                     // 总血量
    protected boolean isExplosion;             // 爆炸状态
    protected boolean isVisible;                 //	 可见状态
    public int speedTime; // 游戏速度的倍数

    public EnemyPlane(Resources resources) {
        super(resources);
        initBitmap(); // 初始化图片
    }

    /**
     * 初始化
     * @param arg0
     * @param arg1
     * @param arg2
     */
    @Override
    public void initial(int arg0, float arg1, float arg2) {
        speedTime = arg0;
    }

    /**
     * 初始化图片
     */
    @Override
    public void initBitmap() {

    }

    /**
     * 绘制
     * @param canvas
     */
    @Override
    public void drawSelf(Canvas canvas) {
        // 由子类实现
    }

    /**
     * 释放资源
     */
    @Override
    public void release() {

    }

    /**
     * 敌机逻辑
     */
    @Override
    public void logic() {
        if (object_y < screen_height) {
            object_y += speed;
        } else {
            isAlive = false;
        }
        if (object_y + object_height > 0) {
            isVisible = true;
        } else {
            isVisible = false;
        }
    }

    /**
     * 被攻击时的逻辑
     * @param harm
     */
    public void attacked(int harm) {
        blood -= harm;
        if (blood <= 0) {
            isExplosion = true;
        }
    }

    /**
     * 碰撞逻辑
     * @param obj
     * @return
     */
    @Override
    public boolean isCollide(GameObject obj) {
        // 位于物体左边
        if (object_x <= obj.getObject_x()
                && object_x + object_width <= obj.getObject_x()) {
            return false;
        }
        // 位于物体右边
        else if (obj.getObject_x() <= object_x
                && obj.getObject_x() + obj.getObject_width() <= object_x) {
            return false;
        }
        // 位于物体上方
        else if (object_y <= obj.getObject_y()
                && object_y + object_height <= obj.getObject_y()) {
            return false;
        }
        // 位于物体下方
        else if (obj.getObject_y() <= object_y
                && obj.getObject_y() + obj.getObject_height() <= object_y) {
            return false;
        }
        // 若不满足上述条件,则判断为相碰撞
        return true;
    }

    /**
     * 是否能碰撞
     * @return
     */
    public boolean isCanCollide() {
        return isAlive && !isExplosion && isVisible;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }

    public int getBloodVolume() {
        return bloodVolume;
    }

    public void setBloodVolume(int bloodVolume) {
        this.bloodVolume = bloodVolume;
    }

    public boolean isExplosion() {
        return isExplosion;
    }
}

