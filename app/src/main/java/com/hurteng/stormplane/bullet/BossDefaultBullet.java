package com.hurteng.stormplane.bullet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.hurteng.stormplane.myplane.R;
import com.hurteng.stormplane.object.GameObject;
import com.hurteng.stormplane.plane.MyPlane;
import com.hurteng.stormplane.plane.SmallPlane;

/**
 * Boss默认的子弹
 */
public class BossDefaultBullet extends EnemyBullet {

	private Bitmap bullet;
	private boolean isAlive2;
	private float object_x2;
	private float object_y2;
	private boolean attack;
	private boolean attack2;

	public BossDefaultBullet(Resources resources) {
		super(resources);
	}

	// 初始化数据
	@Override
	public void initial(int arg0, float arg1, float arg2) {
		isAlive = true;
		isAlive2 = true;
		speed = 10;
		object_x = arg1 - 3*object_width;
		object_y = arg1 + 2*object_height;
		
		object_x2 = arg1 + 3*object_width;
		object_y2 = arg1 + 2*object_height;
		
	}

	// 初始化图片资源的
	@Override
	public void initBitmap() {
		bullet = BitmapFactory.decodeResource(resources, R.drawable.bossbullet_default);
		object_width = bullet.getWidth();
		object_height = bullet.getHeight();
	}

	// 对象的绘图方法
	@Override
	public void drawSelf(Canvas canvas) {
		if (isAlive) {
			canvas.save();
			canvas.clipRect(object_x, object_y, object_x + object_width,object_y + object_height);
			canvas.drawBitmap(bullet, object_x, object_y, paint);
			canvas.restore();
		}
		if (isAlive2) {
			canvas.save();
			canvas.clipRect(object_x2, object_y2, object_x2 + object_width,object_y2 + object_height);
			canvas.drawBitmap(bullet, object_x2, object_y2, paint);
			canvas.restore();
		}
		logic();
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
		//左边
		if (object_y >= 0) {
			object_y -= speed;
			object_x += 10*(Math.sin(object_y));
		}
		else {
			isAlive = false;
		}
		
		//右边
		if (object_y2 >= 0) {
			object_y2 -= speed;
			object_x2 += -10*(Math.sin(object_y2));
		} 
		else{
			isAlive2 = false;
		}
	}

	@Override
	public boolean isCollide(GameObject obj) {
		attack = false;
		attack2 = false;
		// 判断左边的子弹是否存活
		if (isAlive) {
			if (object_x <= obj.getObject_x()
					&& object_x + object_width <= obj.getObject_x()) {}
			// 矩形1位于矩形2的右侧
			else if (obj.getObject_x() <= object_x
					&& obj.getObject_x() + obj.getObject_width() <= object_x) {}
			// 矩形1位于矩形2的上方
			else if (object_y <= obj.getObject_y()
					&& object_y + object_height + 30 <= obj.getObject_y()) {}
			// 矩形1位于矩形2的下方
			else if (obj.getObject_y() <= object_y
					&& obj.getObject_y() + obj.getObject_height() + 30 <= object_y) 
			{
				if(obj instanceof SmallPlane){
					if(object_y - speed < obj.getObject_y()){
						isAlive = false;
						attack = true;
					}
				}
			} 
			else {
				isAlive = false;
				attack = true;
			}
		}
		if (isAlive2) {
			if (object_x2 <= obj.getObject_x()
					&& object_x2 + object_width <= obj.getObject_x()) {}
			// 矩形1位于矩形2的右侧
			else if (obj.getObject_x() <= object_x2
					&& obj.getObject_x() + obj.getObject_width() <= object_x2) {}
			// 矩形1位于矩形2的上方
			else if (object_y2 <= obj.getObject_y()
					&& object_y2 + object_height + 30 <= obj.getObject_y()) {}
			// 矩形1位于矩形2的下方
			else if (obj.getObject_y() <= object_y2
					&& obj.getObject_y() + obj.getObject_height() + 30 <= object_y2) 
			{
				if(obj instanceof MyPlane){
					if(object_y - speed < obj.getObject_y()){
						isAlive2 = false;
						attack2 = true;
					}
				}
			} 
			else {
				isAlive2 = false;
				attack2 = true;
			}
		}
		return attack || attack2;
	}

}
