package com.hurteng.stormplane.plane;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.hurteng.stormplane.bullet.BigPlaneBullet;
import com.hurteng.stormplane.bullet.Bullet;
import com.hurteng.stormplane.constant.GameConstant;
import com.hurteng.stormplane.factory.GameObjectFactory;
import com.hurteng.stormplane.myplane.R;
import com.hurteng.stormplane.object.GameObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 大型机
 */
public class BigPlane extends EnemyPlane {
	private static int currentCount = 0; // 对象当前的数量
	public static int sumCount = GameConstant.BIGPLANE_COUNT; // 总数量
	private Bitmap bigPlane; // 大型机图片
	private int direction;
	private boolean isFire; // 是否允许射击
	private List<Bullet> bullets; // 子弹类
	private MyPlane myplane;
	private int interval; // 发射子弹的间隔

	public BigPlane(Resources resources) {
		super(resources);
		this.score = GameConstant.BIGPLANE_SCORE; // 大型机的分数

		interval = 1;
		bullets = new ArrayList<Bullet>();
		// 创建机体
		GameObjectFactory factory = new GameObjectFactory();
		for (int i = 0; i < 3; i++) {
			BigPlaneBullet bullet = (BigPlaneBullet) factory
					.createBigPlaneBullet(resources);
			bullets.add(bullet);
		}
	}

	@Override
	public void initial(int arg0, float arg1, float arg2) {
		super.initial(arg0, arg1, arg2);
		
		speed = GameConstant.BIGPLANE_SPEED;
		bloodVolume = GameConstant.BIGPLANE_BLOOD;
		blood = bloodVolume;
		isFire = false;
		isAlive = true;

		Random ran = new Random();
		object_x = ran.nextInt((int) (screen_width - object_width));
		object_y = -object_height;

		currentCount++;
		if (currentCount >= sumCount) {
			currentCount = 0;
		}
	}

	@Override
	public void initBitmap() {
		bigPlane = BitmapFactory.decodeResource(resources, R.drawable.big);
		object_width = bigPlane.getWidth();
		object_height = bigPlane.getHeight() / 5;
	}

	@Override
	public void drawSelf(Canvas canvas) {
		if (isAlive) {

			if (!isExplosion) {
				// 速度倍率大于3，则变红色状态
				if (speedTime > 3) {
					canvas.save();
					canvas.clipRect(object_x, object_y,
							object_x + object_width, object_y + object_height);
					canvas.drawBitmap(bigPlane, object_x, object_y
							- object_height, paint);
					canvas.restore();
				}

				else {
					canvas.save();
					canvas.clipRect(object_x, object_y,
							object_x + object_width, object_y + object_height);
					canvas.drawBitmap(bigPlane, object_x, object_y, paint);
					canvas.restore();
				}
				logic();
				shoot(canvas); // 射击
			} else {
				int y = (int) (currentFrame * object_height); // ��õ�ǰ֡�����λͼ��Y����
				canvas.save();
				canvas.clipRect(object_x, object_y, object_x + object_width,
						object_y + object_height);
				canvas.drawBitmap(bigPlane, object_x, object_y - y, paint);
				canvas.restore();
				currentFrame++;
				if (currentFrame >= 5) {
					currentFrame = 1;
					isExplosion = false;
					isAlive = false;
				}
			}
		}
	}

	//初始化子弹数据
		@Override
		public void setScreenWH(float screen_width,float screen_height){
			super.setScreenWH(screen_width, screen_height);
			for(Bullet obj:bullets){	
				obj.setScreenWH(screen_width, screen_height);
			}
		}
	
	// 初始化子弹对象
	public void initBullet() {
		
		if (isFire) {
			if (interval == 1) {
				for (GameObject obj : bullets) {
					if (!obj.isAlive()) {
						//给子弹设置发射初始坐标，重要！！
						obj.initial(0, object_x + object_width / 2,
								object_y + object_height*2/3);
						break;
					}
				}
			}

			interval++;
			if (interval >= 72/speedTime) {
				interval = 1;
			}
			
		}
		
	}

	// 发射子弹
	public boolean shoot(Canvas canvas) {
		//如果我方引爆了导弹，敌方当前子弹消失，并且不能继续射击
		if (isFire && !myplane.getMissileState()) {
			// 遍历子弹的对象
			for (Bullet obj : bullets) {
				if (obj.isAlive()) {
					obj.drawSelf(canvas);// 绘制子弹
					//我方处于无敌模式时，敌方可以继续射击，但无法对我方机体造成伤害
					if (obj.isCollide(myplane) && !myplane.isInvincible()) {
						myplane.setAlive(false);
						return true;
					}
				}
			}
		} 
		return false;
	}

	// 将我方机体设置进来
	public void setMyPlane(MyPlane myplane) {
		this.myplane = myplane;
	}

	@Override
	public void logic() {

		if (!isFire) {
			isFire = true;
		}

		if (object_y < screen_height) {

			if (speedTime < 4) {
				object_y += speed;
				object_x += Math.tan(object_y);
			} else {
				speed = 11;
				object_y += speed;
				object_x -= Math.tan(object_y);
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

	// 释放资源
	@Override
	public void release() {
		if (!bigPlane.isRecycled()) {
			bigPlane.recycle();
		}

		for (Bullet obj : bullets) {
			obj.release();
		}
	}

}
