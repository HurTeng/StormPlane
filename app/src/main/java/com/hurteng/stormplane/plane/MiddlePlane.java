package com.hurteng.stormplane.plane;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.hurteng.stormplane.constant.GameConstant;
import com.hurteng.stormplane.myplane.R;

import java.util.Random;

/**
 * 中型机
 */
public class MiddlePlane extends EnemyPlane {
    private static int currentCount = 0;
    private Bitmap middlePlane;
    public static int sumCount = GameConstant.MIDDLEPLANE_COUNT;             //	中型机的总数
    private int direction;

    public MiddlePlane(Resources resources) {
        super(resources);
        this.score = GameConstant.MIDDLEPLANE_SCORE;
    }

    @Override
    public void initial(int arg0, float arg1, float arg2) {
        super.initial(arg0, arg1, arg2);
        isAlive = true;
        bloodVolume = GameConstant.MIDDLEPLANE_BLOOD;
        blood = bloodVolume;
        Random ran = new Random();
        speed = 19;
        object_x = ran.nextInt((int) (screen_width - object_width));
        object_y = -object_height * (currentCount * 2 + 1);
        currentCount++;
        if (currentCount >= sumCount) {
            currentCount = 0;
        }
    }

    @Override
    public void initBitmap() {
        middlePlane = BitmapFactory.decodeResource(resources, R.drawable.middle);
        object_width = middlePlane.getWidth();
        object_height = middlePlane.getHeight() / 4;
    }

    @Override
    public void drawSelf(Canvas canvas) {
        if (isAlive) {
            if (!isExplosion) {
                if (isVisible) {
                    canvas.save();
                    canvas.clipRect(object_x, object_y, object_x + object_width, object_y + object_height);
                    canvas.drawBitmap(middlePlane, object_x, object_y, paint);
                    canvas.restore();
                }
                logic();
            } else {
                int y = (int) (currentFrame * object_height);
                canvas.save();
                canvas.clipRect(object_x, object_y, object_x + object_width, object_y + object_height);
                canvas.drawBitmap(middlePlane, object_x, object_y - y, paint);
                canvas.restore();
                currentFrame++;
                if (currentFrame >= 4) {
                    currentFrame = 0;
                    isExplosion = false;
                    isAlive = false;
                }
            }
        }
    }

    @Override
    public void release() {
        if (!middlePlane.isRecycled()) {
            middlePlane.recycle();
        }
    }

    @Override
    public void logic() {
        if (object_y < screen_height) {
//			object_x +=50*Math.sin(object_y);
            speed -= 1;
            object_y += speed;

            if (speed <= 0) {
                Random random = new Random();
                if (speedTime >= 3) {
                    speed = 20 * speedTime;
                } else {
                    speed = random.nextInt(40);
                }
            }
        } else {
            isAlive = false;
        }

        if (object_y + object_height > 0) {
            isVisible = true;
        } else {
            isVisible = false;
        }
    }
}
