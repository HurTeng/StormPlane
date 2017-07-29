package com.hurteng.sandstorm.interfaces;


import android.graphics.Canvas;

import com.hurteng.sandstorm.object.EnemyPlane;

import java.util.List;

public interface IMyPlane {
    public float getMiddle_x();

    public void setMiddle_x(float middle_x);

    public float getMiddle_y();

    public void setMiddle_y(float middle_y);

    public boolean isChangeBullet();

    public void setChangeBullet(boolean isChangeBullet);

    /**
     * 射击
     *
     * @param canvas
     * @param planes
     */
    public void shoot(Canvas canvas, List<EnemyPlane> planes);

    /**
     * 初始化子弹
     */
    public void initBullet();

    /**
     * 改变子弹类型
     *
     * @param type
     */
    public void changeBullet(int type);
}
