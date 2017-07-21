package com.hurteng.sandstorm.object;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

import com.hurteng.sandstorm.myplane.R;

/**
 * 子弹物品2
 */
public class BulletGoods2 extends GameGoods {

	public BulletGoods2(Resources resources) {
		super(resources);
	}

	protected void initBitmap() {
		bmp = BitmapFactory.decodeResource(resources, R.drawable.bullet_goods2);
		object_width = bmp.getWidth();			
		object_height = bmp.getHeight();	
	}
}
