package com.hurteng.stormplane.object;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

import com.hurteng.stormplane.myplane.R;

/**
 * 紫色弹药物品
 */
public class PurpleBulletGoods extends GameGoods{
	public PurpleBulletGoods(Resources resources) {
		super(resources);
	}
	@Override
	protected void initBitmap() {
		bmp = BitmapFactory.decodeResource(resources, R.drawable.bullet_goods1);
		object_width = bmp.getWidth();			
		object_height = bmp.getHeight();	
	}
}

