package com.hurteng.stormplane.bullet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.hurteng.stormplane.myplane.R;
import com.hurteng.stormplane.object.GameObject;

/**
 * 大型机的烈焰飘雪弹
 */
public class BigPlaneBullet extends EnemyBullet {

	private Bitmap bullet;

	public BigPlaneBullet(Resources resources) {
		super(resources);
	}

	// 初始化数据
	@Override
	public void initial(int arg0, float arg1, float arg2) {
		isAlive = true;
		speed = 50;
		object_x = arg1 - object_width / 2;
		object_y = arg2 - object_height;
	}

	// 初始化图片的资源
	@Override
	public void initBitmap() {
		bullet = BitmapFactory.decodeResource(resources, R.drawable.bigplane_bullet);
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
		if (object_y <= screen_height) {
			object_y += speed;
			object_x += 30*Math.sin(System.currentTimeMillis()/1000);
		} else {
			isAlive = false;
		}
	}

	@Override
	public boolean isCollide(GameObject obj) {
		return super.isCollide(obj);
	}

}
