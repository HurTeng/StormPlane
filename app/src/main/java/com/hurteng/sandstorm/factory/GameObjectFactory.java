package com.hurteng.sandstorm.factory;

import android.content.res.Resources;

import com.hurteng.sandstorm.plane.BigPlane;
import com.hurteng.sandstorm.bullet.BigPlaneBullet;
import com.hurteng.sandstorm.bullet.BossFlameBullet;
import com.hurteng.sandstorm.bullet.BossSunBullet;
import com.hurteng.sandstorm.bullet.BossTriangleBullet;
import com.hurteng.sandstorm.bullet.BossGThunderBullet;
import com.hurteng.sandstorm.bullet.BossYHellfireBullet;
import com.hurteng.sandstorm.bullet.BossRHellfireBullet;
import com.hurteng.sandstorm.bullet.BossDefaultBullet;
import com.hurteng.sandstorm.plane.BossPlane;
import com.hurteng.sandstorm.object.PurpleBulletGoods;
import com.hurteng.sandstorm.object.RedBulletGoods;
import com.hurteng.sandstorm.object.GameObject;
import com.hurteng.sandstorm.object.LifeGoods;
import com.hurteng.sandstorm.plane.MiddlePlane;
import com.hurteng.sandstorm.object.MissileGoods;
import com.hurteng.sandstorm.bullet.MyBlueBullet;
import com.hurteng.sandstorm.bullet.MyPurpleBullet;
import com.hurteng.sandstorm.bullet.MyRedBullet;
import com.hurteng.sandstorm.plane.MyPlane;
import com.hurteng.sandstorm.plane.SmallPlane;


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

    /**
     * 生产我方机体
     * @param resources
     * @return
     */
    public GameObject createMyPlane(Resources resources) {
        return new MyPlane(resources);
    }

    /**
     * 生产我方的子弹
     * @param resources
     * @return
     */
    public GameObject createMyBlueBullet(Resources resources) {
        return new MyBlueBullet(resources);
    }
    public GameObject createMyPurpleBullet(Resources resources) {
        return new MyPurpleBullet(resources);
    }
    public GameObject createMyRedBullet(Resources resources) {
        return new MyRedBullet(resources);
    }

    /**
     * 生产BOSS的子弹
     * @param resources
     * @return
     */
    public GameObject createBossFlameBullet(Resources resources) {
        return new BossFlameBullet(resources);
    }

    public GameObject createBossSunBullet(Resources resources) {
        return new BossSunBullet(resources);
    }

    public GameObject createBossTriangleBullet(Resources resources) {
        return new BossTriangleBullet(resources);
    }

    public GameObject createBossGThunderBullet(Resources resources) {
        return new BossGThunderBullet(resources);
    }

    public GameObject createBossYHellfireBullet(Resources resources) {
        return new BossYHellfireBullet(resources);
    }

    public GameObject createBossRHellfireBullet(Resources resources) {
        return new BossRHellfireBullet(resources);
    }

    public GameObject createBossBulletDefault(Resources resources) {
        return new BossDefaultBullet(resources);
    }

    /**
     * 生产BigPlane的子弹
     * @param resources
     * @return
     */
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


    /**
     * 生产子弹物品
     * @param resources
     * @return
     */
    public GameObject createPurpleBulletGoods(Resources resources) {
        return new PurpleBulletGoods(resources);
    }

    public GameObject createRedBulletGoods(Resources resources) {
        return new RedBulletGoods(resources);
    }
}
