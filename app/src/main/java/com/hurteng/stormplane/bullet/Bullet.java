package com.hurteng.stormplane.bullet;

import android.content.res.Resources;
import android.graphics.Canvas;

import com.hurteng.stormplane.object.GameObject;
import com.hurteng.stormplane.plane.SmallPlane;

/**
 * 子弹类
 */
public class Bullet extends GameObject {
    protected int harm;

    public Bullet(Resources resources) {
        super(resources);
        initBitmap();
    }

    @Override
    protected void initBitmap() {

    }

    @Override
    public void drawSelf(Canvas canvas) {

    }

    @Override
    public void release() {

    }

    /**
     * 碰撞检测
     *
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
        // 位于物体上边
        else if (object_y <= obj.getObject_y()
                && object_y + object_height <= obj.getObject_y()) {
            return false;
        }
        // 位于物体下边
        else if (obj.getObject_y() <= object_y
                && obj.getObject_y() + obj.getObject_height() <= object_y) {
            if (obj instanceof SmallPlane) {
                if (object_y - speed < obj.getObject_y()) {
                    isAlive = false;
                    return true;
                }
            } else
                return false;
        }
        isAlive = false;
        return true;
    }

    /**
     * 获取子弹的伤害值
     * @return
     */
    public int getHarm() {
        return harm;
    }

    public void setHarm(int harm) {
        this.harm = harm;
    }
}
