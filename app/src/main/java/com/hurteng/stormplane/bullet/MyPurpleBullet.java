package com.hurteng.stormplane.bullet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.hurteng.stormplane.constant.GameConstant;
import com.hurteng.stormplane.myplane.R;
import com.hurteng.stormplane.object.GameObject;
import com.hurteng.stormplane.plane.SmallPlane;

/**
 * 我方机体的紫色双螺旋粒子炮
 */
public class MyPurpleBullet extends Bullet {
    private Bitmap bullet;
    private float object_x2;
    private float object_y2;
    private boolean isAlive2;
    private boolean attack;            // 命中状态
    private boolean attack2;

    public MyPurpleBullet(Resources resources) {
        super(resources);
        this.harm = GameConstant.MYBULLET1_HARM;
    }

    @Override
    public void initial(int arg0, float arg1, float arg2) {
        isAlive = true;
        isAlive2 = true;
        speed = GameConstant.MYBULLET1_SPEED;
        object_x = arg1 - 2 * object_width;
        object_y = arg2 - object_height;
        object_x2 = arg1 + object_width;
        object_y2 = object_y;
    }

    @Override
    public void initBitmap() {
        bullet = BitmapFactory.decodeResource(resources, R.drawable.my_bullet_purple);
        object_width = bullet.getWidth();
        object_height = bullet.getHeight();
    }

    @Override
    public void drawSelf(Canvas canvas) {
        if (isAlive) {
            canvas.save();
            canvas.clipRect(object_x, object_y, object_x + object_width, object_y + object_height);
            canvas.drawBitmap(bullet, object_x, object_y, paint);
            canvas.restore();
        }
        if (isAlive2) {
            canvas.save();
            canvas.clipRect(object_x2, object_y2, object_x2 + object_width, object_y2 + object_height);
            canvas.drawBitmap(bullet, object_x2, object_y2, paint);
            canvas.restore();
        }
        logic();
    }

    @Override
    public void release() {
        if (!bullet.isRecycled()) {
            bullet.recycle();
        }
    }

    @Override
    public void logic() {
        if (object_y >= 0) {
            object_y -= speed;
            object_x += 30 * (Math.sin(object_y));
        } else {
            isAlive = false;
        }
        if (object_y2 >= 0) {
            object_y2 -= speed;
            object_x2 += -30 * (Math.sin(object_y2));
        } else {
            isAlive2 = false;
        }
    }

    /**
     * 碰撞检测
     * @param obj
     * @return
     */
    @Override
    public boolean isCollide(GameObject obj) {
        attack = false;
        attack2 = false;
        //
        if (isAlive) {
            if (object_x <= obj.getObject_x()
                    && object_x + object_width <= obj.getObject_x()) {
            }
            else if (obj.getObject_x() <= object_x
                    && obj.getObject_x() + obj.getObject_width() <= object_x) {
            }
            else if (object_y <= obj.getObject_y()
                    && object_y + object_height + 30 <= obj.getObject_y()) {
            }
            else if (obj.getObject_y() <= object_y
                    && obj.getObject_y() + obj.getObject_height() + 30 <= object_y) {
                if (obj instanceof SmallPlane) {
                    if (object_y - speed < obj.getObject_y()) {
                        isAlive = false;
                        attack = true;
                    }
                }
            } else {
                isAlive = false;
                attack = true;
            }
        }

        if (isAlive2) {
            if (object_x2 <= obj.getObject_x()
                    && object_x2 + object_width <= obj.getObject_x()) {
            }
            else if (obj.getObject_x() <= object_x2
                    && obj.getObject_x() + obj.getObject_width() <= object_x2) {
            }
            else if (object_y2 <= obj.getObject_y()
                    && object_y2 + object_height + 30 <= obj.getObject_y()) {
            }
            else if (obj.getObject_y() <= object_y2
                    && obj.getObject_y() + obj.getObject_height() + 30 <= object_y2) {
                if (obj instanceof SmallPlane) {
                    if (object_y - speed < obj.getObject_y()) {
                        isAlive2 = false;
                        attack2 = true;
                    }
                }
            } else {
                isAlive2 = false;
                attack2 = true;
            }
        }
        if (attack && attack2)
            harm = 2;
        return attack || attack2;
    }

    /**
     * 子弹存活状态
     * @return
     */
    @Override
    public boolean isAlive() {
        return isAlive || isAlive2;
    }
}

