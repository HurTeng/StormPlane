package com.hurteng.sandstorm.factory;

import android.content.res.Resources;

import com.hurteng.sandstorm.object.BigPlane;
import com.hurteng.sandstorm.object.BigPlaneBullet;
import com.hurteng.sandstorm.object.BossBullet;
import com.hurteng.sandstorm.object.BossBullet1;
import com.hurteng.sandstorm.object.BossBullet2;
import com.hurteng.sandstorm.object.BossBullet3;
import com.hurteng.sandstorm.object.BossBullet4;
import com.hurteng.sandstorm.object.BossBullet5;
import com.hurteng.sandstorm.object.BossBulletDefault;
import com.hurteng.sandstorm.object.BossPlane;
import com.hurteng.sandstorm.object.BulletGoods1;
import com.hurteng.sandstorm.object.BulletGoods2;
import com.hurteng.sandstorm.object.GameObject;
import com.hurteng.sandstorm.object.LifeGoods;
import com.hurteng.sandstorm.object.MiddlePlane;
import com.hurteng.sandstorm.object.MissileGoods;
import com.hurteng.sandstorm.object.MyBullet;
import com.hurteng.sandstorm.object.MyBullet1;
import com.hurteng.sandstorm.object.MyBullet2;
import com.hurteng.sandstorm.object.MyPlane;
import com.hurteng.sandstorm.object.SmallPlane;


/**
 * 物品构建的工厂
 */
public class GameObjectFactory {
    /**
     * 小型机
     * @param resources
     * @return
     */
    public GameObject createSmallPlane(Resources resources) {
        return new SmallPlane(resources);
    }

    /**
     * 生产中型机
     * @param resources
     * @return
     */
    public GameObject createMiddlePlane(Resources resources) {
        return new MiddlePlane(resources);
    }

    /**
     * 生产大型机
     * @param resources
     * @return
     */
    public GameObject createBigPlane(Resources resources) {
        return new BigPlane(resources);
    }

    /**
     * 生产boss机体
     * @param resources
     * @return
     */
    public GameObject createBossPlane(Resources resources) {
        return new BossPlane(resources);
    }

    // 我方机体
    public GameObject createMyPlane(Resources resources) {
        return new MyPlane(resources);
    }

    // 我方子弹
    public GameObject createMyBullet(Resources resources) {
        return new MyBullet(resources);
    }
    public GameObject createMyBullet1(Resources resources) {
        return new MyBullet1(resources);
    }
    public GameObject createMyBullet2(Resources resources) {
        return new MyBullet2(resources);
    }

    //Boss子弹
    public GameObject createBossBullet(Resources resources) {
        return new BossBullet(resources);
    }

    //Boss子弹1
    public GameObject createBossBullet1(Resources resources) {
        return new BossBullet1(resources);
    }

    //Boss子弹2
    public GameObject createBossBullet2(Resources resources) {
        return new BossBullet2(resources);
    }

    //Boss子弹3
    public GameObject createBossBullet3(Resources resources) {
        return new BossBullet3(resources);
    }

    //Boss子弹4
    public GameObject createBossBullet4(Resources resources) {
        return new BossBullet4(resources);
    }

    //Boss子弹5
    public GameObject createBossBullet5(Resources resources) {
        return new BossBullet5(resources);
    }

    //Boss子弹Default
    public GameObject createBossBulletDefault(Resources resources) {
        return new BossBulletDefault(resources);
    }

    // BigPlane子弹
    public GameObject createBigPlaneBullet(Resources resources) {
        return new BigPlaneBullet(resources);
    }

    /**
     * 生产导弹物品
     * @param resources
     * @return
     */
    public GameObject createMissileGoods(Resources resources) {
        return new MissileGoods(resources);
    }

    /**
     * 生产生命物品
     * @param resources
     * @return
     */
    public GameObject createLifeGoods(Resources resources) {
        return new LifeGoods(resources);
    }


    // 生产子弹物品
    public GameObject createBulletGoods1(Resources resources) {
        return new BulletGoods1(resources);
    }

    public GameObject createBulletGoods2(Resources resources) {
        return new BulletGoods2(resources);
    }
}
