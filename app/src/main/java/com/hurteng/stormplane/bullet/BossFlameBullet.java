package com.hurteng.stormplane.bullet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.hurteng.stormplane.myplane.R;
import com.hurteng.stormplane.object.GameObject;

import java.util.Random;


/**
 * BOSS的火焰弹
 */
public class BossFlameBullet extends EnemyBullet {
    private Bitmap bullet;

    public BossFlameBullet(Resources resources) {
        super(resources);
    }

    @Override
    public void initial(int arg0, float arg1, float arg2) {
        isAlive = true;
        Random random = new Random();
        speed = random.nextInt(20) + 20;
        object_x = arg1 - object_width / 2;
        object_y = arg2 - object_height;

        //obj.initial(0, object_x + object_width / 2,object_height);
    }

    @Override
    public void initBitmap() {
        bullet = BitmapFactory.decodeResource(resources, R.drawable.boss_bullet_default);
        object_width = bullet.getWidth();
        object_height = bullet.getHeight();
    }


    /**
     * 绘制子弹
     * @param canvas
     */
    @Override
    public void drawSelf(Canvas canvas) {
        if (isAlive) {
            canvas.save();
            canvas.clipRect(object_x, object_y, object_x + object_width,
                    object_y + object_height);
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
        if (object_y <= screen_height) {
            object_y += speed;
//			object_x += 100*(Math.tan(object_y) + 0.4);
        } else {
            isAlive = false;
        }
    }

    // 碰撞检测
    @Override
    public boolean isCollide(GameObject obj) {
        return super.isCollide(obj);
    }
}