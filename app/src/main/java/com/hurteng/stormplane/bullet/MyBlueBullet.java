package com.hurteng.stormplane.bullet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.hurteng.stormplane.constant.GameConstant;
import com.hurteng.stormplane.myplane.R;
import com.hurteng.stormplane.object.GameObject;

/**
 * 我方机体的蓝色激光粒子弹
 */
public class MyBlueBullet extends Bullet {
    private Bitmap bullet;         // 子弹图片

    public MyBlueBullet(Resources resources) {
        super(resources);
        this.harm = GameConstant.MYBULLET_HARM;
    }

    @Override
    public void initial(int arg0, float arg1, float arg2) {
        isAlive = true;
        speed = GameConstant.MYBULLET_SPEED;
        object_x = arg1 - object_width / 2;
        object_y = arg2 - object_height;
    }

    @Override
    public void initBitmap() {
        bullet = BitmapFactory.decodeResource(resources, R.drawable.my_bullet_blue);
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
            logic();
        }
    }

    @Override
    public void release() {
        if (!bullet.isRecycled()) {
            bullet.recycle();
        }
    }

    /**
     * 子弹移动逻辑
     */
    @Override
    public void logic() {
        if (object_y >= 0) {
            object_y -= speed;
        } else {
            isAlive = false;
        }
    }

    @Override
    public boolean isCollide(GameObject obj) {
        return super.isCollide(obj);
    }
}
