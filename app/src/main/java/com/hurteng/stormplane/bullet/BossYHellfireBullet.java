package com.hurteng.stormplane.bullet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.hurteng.stormplane.myplane.R;
import com.hurteng.stormplane.object.GameObject;

import java.util.Random;

/**
 * BOSS的恶魔地狱火(黄)
 */
public class BossYHellfireBullet extends EnemyBullet {

	private Bitmap bullet;

	public BossYHellfireBullet(Resources resources) {
		super(resources);
	}

	// 初始化数据
	@Override
	public void initial(int arg0, float arg1, float arg2) {
		isAlive = true;
		Random random = new Random();
		speed = random.nextInt(5) + 5;
		object_x = arg1 - object_width / 2;
		object_y = arg1 + 2*object_height;
		
	}

	// 初始化图片的资源
	@Override
	public void initBitmap() {
		bullet = BitmapFactory.decodeResource(resources, R.drawable.boss_bullet_hellfire_yellow);
		object_width = bullet.getWidth();
		object_height = bullet.getHeight();
	}

	// 对象的绘图方法
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

	// 释放资源的方法
	@Override
	public void release() {
		if (!bullet.isRecycled()) {
			bullet.recycle();
		}
	}

	// 对象的逻辑函数
	@Override
	public void logic() {
		if (object_y >= 0) {
			object_y -= speed;
			object_x += Math.tan(System.currentTimeMillis()/1000);
			
		} else {
			isAlive = false;
		}
	}

	@Override
	public boolean isCollide(GameObject obj) {
		return super.isCollide(obj);
	}

}
