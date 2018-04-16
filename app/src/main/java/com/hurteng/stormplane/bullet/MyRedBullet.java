package com.hurteng.stormplane.bullet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.hurteng.stormplane.constant.GameConstant;
import com.hurteng.stormplane.myplane.R;
import com.hurteng.stormplane.object.GameObject;

/**
 * 我方机体的红色追踪战斧冲击波
 */
public class MyRedBullet extends Bullet {
	private Bitmap bullet; 		 // 子弹的图片
	private boolean attack;		//标记子弹是否击中
	
	public MyRedBullet(Resources resources) {
		super(resources);
		this.harm = GameConstant.MYBULLET2_HARM;
	}
	// 初始化数据
	@Override
	public void initial(int arg0,float arg1,float arg2){
		isAlive = true;

		speed = GameConstant.MYBULLET2_SPEED;	
		object_x = arg1 - object_width / 2;
		object_y = arg2 - object_height;

	}
	// 初始化图片资源的
	@Override
	public void initBitmap() {
		bullet = BitmapFactory.decodeResource(resources, R.drawable.my_bullet_red);
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

		logic();
	}
	// 释放资源的方法
	@Override
	public void release() {
		if(!bullet.isRecycled()){
			bullet.recycle();
		}
	}
	// 对象的逻辑函数
	@Override
	public void logic() {
		if (object_y >= 0) {
			object_y -= speed;
			object_x += 100*(Math.sin(System.currentTimeMillis()));
		}
		else {
			isAlive = false;
		}

	}
	// 检测碰撞的方法
	@Override
	public boolean isCollide(GameObject obj) {
		return super.isCollide(obj);
	}
	
	//判断子弹是否存在
	@Override
	public boolean isAlive() {
		return isAlive;
	}
	
}

