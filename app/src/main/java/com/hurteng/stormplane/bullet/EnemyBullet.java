package com.hurteng.stormplane.bullet;

import android.content.res.Resources;
import android.graphics.Canvas;

import com.hurteng.stormplane.object.GameObject;

/**
 * 敌方子弹类
 * 
 * @author Administrator
 * 
 */
public class EnemyBullet extends Bullet {

	public EnemyBullet(Resources resources) {
		super(resources);
		this.harm = 1;
	}

	// 初始化数据
	@Override
	public void initial(int arg0, float arg1, float arg2) {
		
	}

	// 初始化图片资源
	@Override
	public void initBitmap() {

	}

	// 绘图方法
	@Override
	public void drawSelf(Canvas canvas) {
		
	}

	// 释放资源的方法
	@Override
	public void release() {
	}

	// 子弹运动逻辑，默认向下
	@Override
	public void logic() {
		if (object_y >= 0) {
			object_y -= speed;
		} else {
			isAlive = false;
		}
	}

	// 碰撞检测
	@Override
	public boolean isCollide(GameObject obj) {
		// 矩形1位于矩形2的左侧
		if (object_x <= obj.getObject_x()
				&& object_x + object_width <= obj.getObject_x()) {
			return false;
		}
		// 矩形1位于矩形2的右侧
		else if (obj.getObject_x() <= object_x
				&& obj.getObject_x() + obj.getObject_width() <= object_x) {
			return false;
		}
		// 矩形1位于矩形2的上方
		else if (object_y <= obj.getObject_y()
				&& object_y + object_height <= obj.getObject_y()) {
			return false;
		}
		// 矩形1位于矩形2的下方
		else if (obj.getObject_y() <= object_y
				&& obj.getObject_y() + obj.getObject_height() <= object_y) {
			return false;
		}
		isAlive = false;
		return true;
	}
}
