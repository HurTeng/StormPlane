package com.hurteng.sandstorm.object;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

import com.hurteng.sandstorm.myplane.R;

/**
 * 子弹物品1
 */
public class BulletGoods1 extends GameGoods{
	public BulletGoods1(Resources resources) {
		super(resources);
	}
	@Override
	protected void initBitmap() {
		bmp = BitmapFactory.decodeResource(resources, R.drawable.bullet_goods1);
		object_width = bmp.getWidth();			
		object_height = bmp.getHeight();	
	}
}

