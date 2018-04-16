package com.hurteng.stormplane.object;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

import com.hurteng.stormplane.myplane.R;

/**
 * 红色弹药物品
 */
public class RedBulletGoods extends GameGoods {

	public RedBulletGoods(Resources resources) {
		super(resources);
	}

	protected void initBitmap() {
		bmp = BitmapFactory.decodeResource(resources, R.drawable.bullet_goods2);
		object_width = bmp.getWidth();			
		object_height = bmp.getHeight();	
	}
}
