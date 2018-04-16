package com.hurteng.stormplane.plane;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.hurteng.stormplane.bullet.BossFlameBullet;
import com.hurteng.stormplane.bullet.BossSunBullet;
import com.hurteng.stormplane.bullet.BossTriangleBullet;
import com.hurteng.stormplane.bullet.BossGThunderBullet;
import com.hurteng.stormplane.bullet.BossYHellfireBullet;
import com.hurteng.stormplane.bullet.BossRHellfireBullet;
import com.hurteng.stormplane.bullet.BossDefaultBullet;
import com.hurteng.stormplane.bullet.Bullet;
import com.hurteng.stormplane.constant.ConstantUtil;
import com.hurteng.stormplane.constant.GameConstant;
import com.hurteng.stormplane.factory.GameObjectFactory;
import com.hurteng.stormplane.myplane.R;
import com.hurteng.stormplane.object.GameObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * BOSS机体
 */
public class BossPlane extends EnemyPlane {
    private static int currentCount = 0; // 总数量
    private static int sumCount = GameConstant.BOSSPLANE_COUNT;
    private Bitmap boosPlane;
    private Bitmap boosPlaneBomb;
    private Bitmap bossPlane_crazy;
    private int direction; // 移动方向
    private int interval; // 射击间隔
    private float leftBorder; // 移动的左边界
    private float rightBorder; // 移动的右边界
    private float upBorder; // 移动的上行边界
    private float downBorder; // 移动的下行边界
    private boolean isFire; // 开火状态
    private boolean isAnger;// 愤怒状态
    private boolean isCrazy; // 疯狂状态
    private boolean isLimit;// 极限状态
    private List<Bullet> bullets; // 子弹列表
    private MyPlane myplane;

    private int bulletType;

    private GameObjectFactory factory;

    private static final int STATE_NORMAL = 0; // 普通状态
    private static final int STATE_ANGER = 1; // 愤怒状态
    private static final int STATE_CRAZY = 2; // 疯狂状态
    private static final int STATE_LIMIT = 3; // 极限状态


    private long bossappear_interval;//Boss出现的间隔时间

    public BossPlane(Resources resources) {
        super(resources);

        this.score = GameConstant.BOSSPLANE_SCORE;
        interval = 1;

        bullets = new ArrayList<Bullet>();
        factory = new GameObjectFactory();

        // bulletType = ConstantUtil.BOSSBULLET_DEFAULT;
        changeBullet(bulletType);

    }

    public void setMyPlane(MyPlane myplane) {
        this.myplane = myplane;
    }

    @Override
    public void setScreenWH(float screen_width, float screen_height) {
        super.setScreenWH(screen_width, screen_height);

        for (Bullet obj : bullets) {
            obj.setScreenWH(screen_width, screen_height);
        }

        leftBorder = -object_width / 2;
        rightBorder = screen_width - object_width / 2;
        upBorder = 0;
        downBorder = screen_height * 2 / 3;
    }

    /**
     * 初始化相关数据
     *
     * @param arg0
     * @param arg1
     * @param arg2
     */
    @Override
    public void initial(int arg0, float arg1, float arg2) {
        super.initial(arg0, arg1, arg2);

        isAlive = true;
        isVisible = true;
        isAnger = false;
        isCrazy = false;
        isLimit = false;
        isFire = false;

        speed = 15;
        bloodVolume = GameConstant.BOSSPLANE_BLOOD;
        blood = bloodVolume;
        direction = ConstantUtil.DIR_RIGHT;

        Random ran = new Random();
        object_x = ran.nextInt((int) (screen_width - object_width));
        object_y = -object_height * (arg0 * 2 + 1);

        currentCount++;
        if (currentCount >= sumCount) {
            currentCount = 0;
        }

    }

    /**
     * 初始化图片
     */
    @Override
    public void initBitmap() {
        boosPlane = BitmapFactory.decodeResource(resources,
                R.drawable.boosplane);
        boosPlaneBomb = BitmapFactory.decodeResource(resources,
                R.drawable.bossplane_bomb);
        bossPlane_crazy = BitmapFactory.decodeResource(resources,
                R.drawable.bossplane_crazy);
        object_width = boosPlane.getWidth(); // 宽度
        object_height = boosPlane.getHeight() / 2; // 高度
    }

    /**
     * 初始化子弹
     */
    public void initBullet() {
        if (!isFire) return;

        if (interval == 1) {
            for (GameObject obj : bullets) {
                if (!obj.isAlive()) {
                    obj.initial(0, object_x + object_width / 2,
                            object_y + object_height);
                    break;
                }
            }
        }

        interval++;
        if (bulletType == ConstantUtil.BOSSBULLET_DEFAULT) {
            if (interval >= 2) {
                interval = 1;
            }
        } else {
            if (interval >= 30 / speedTime + 5) {
                interval = 1;
            }
        }

    }


    /**
     * 绘制BOSS机体
     *
     * @param canvas
     */
    @Override
    public void drawSelf(Canvas canvas) {
        if (!isAlive) return;

        if (isExplosion) {
            drawExplosion(canvas);
        } else {
            drawBoss(canvas);
        }
    }

    /**
     * 绘制Boss爆炸的状态
     *
     * @param canvas
     */
    private void drawExplosion(Canvas canvas) {
        // 绘制爆炸时的图片
        int y = (int) (currentFrame * object_height);
        canvas.save();
        canvas.clipRect(object_x, object_y, object_x + object_width,
                object_y + object_height);
        canvas.drawBitmap(boosPlaneBomb, object_x, object_y - y, paint);
        canvas.restore();

        // 绘制帧动画
        currentFrame++;
        if (currentFrame >= 5) {
            currentFrame = 0;
            isExplosion = false;
            isAlive = false;
            if (bulletType != ConstantUtil.BOSSBULLET_DEFAULT) {
                changeBullet(ConstantUtil.BOSSBULLET_DEFAULT);
            }
        }
    }

    /**
     * 绘制Boss机体
     *
     * @param canvas
     */
    private void drawBoss(Canvas canvas) {
        // 极限状态
        if (isLimit) {
            int y = (int) (currentFrame * object_height);
            canvas.save();
            canvas.clipRect(object_x, object_y,
                    object_x + object_width, object_y + object_height);
            canvas.drawBitmap(bossPlane_crazy, object_x, object_y - y,
                    paint);
            canvas.restore();
            currentFrame++;
            if (currentFrame >= 2) {
                currentFrame = 0;
            }

        }

        // 疯狂状态
        else if (isCrazy) {
            canvas.save();
            canvas.clipRect(object_x, object_y,
                    object_x + object_width, object_y + object_height);
            canvas.drawBitmap(bossPlane_crazy, object_x, object_y
                    - object_height, paint);
            canvas.restore();
        }

        // 愤怒状态
        else if (isAnger) {
            canvas.save();
            canvas.clipRect(object_x, object_y,
                    object_x + object_width, object_y + object_height);
            canvas.drawBitmap(boosPlane, object_x, object_y
                    - object_height, paint);
            canvas.restore();

        }

        // 普通状态
        else {
            canvas.save();
            canvas.clipRect(object_x, object_y,
                    object_x + object_width, object_y + object_height);
            canvas.drawBitmap(boosPlane, object_x, object_y, paint);
            canvas.restore();
        }

        logic();
        shoot(canvas); // 射击
    }

    /**
     * 射击逻辑
     *
     * @param canvas
     * @return
     */
    public boolean shoot(Canvas canvas) {
        // 如果我方引爆了导弹，敌方当前子弹消失，并且不能继续射击
        if (isFire && !myplane.getMissileState()) {
            for (Bullet obj : bullets) {
                if (obj.isAlive()) {
                    obj.drawSelf(canvas);// 绘制子弹
                    // 我方处于无敌模式时，敌方可以继续射击，但无法对我方机体造成伤害
                    if (obj.isCollide(myplane) && !myplane.isInvincible()) {
                        myplane.setAlive(false);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 释放资源
     */
    @Override
    public void release() {
        for (Bullet obj : bullets) {
            obj.release();
        }
        if (!boosPlane.isRecycled()) {
            boosPlane.recycle();
        }
        if (!boosPlaneBomb.isRecycled()) {
            boosPlaneBomb.recycle();
        }
        if (!bossPlane_crazy.isRecycled()) {
            bossPlane_crazy.recycle();
        }
    }

    @Override
    public boolean isCollide(GameObject obj) {
        return super.isCollide(obj);
    }

    /**
     * BOSS逻辑
     */
    @Override
    public void logic() {

        if (object_y < 0) {
            object_y += speed;
        } else {

            if (!isFire) {
                isFire = true;
            }

            // 愤怒状态
            if (blood <= GameConstant.BOSSPLANE_ANGER_BLOOD
                    && blood > GameConstant.BOSSPLANE_CRAZY_BLOOD) {
                if (!isAnger) {
                    isAnger = true;
                    if (bulletType != ConstantUtil.BOSSBULLET_ANGER) {
                        changeBullet(ConstantUtil.BOSSBULLET_ANGER);
                    }
                }
            }

            // 疯狂状态
            if (blood <= GameConstant.BOSSPLANE_CRAZY_BLOOD
                    && blood > GameConstant.BOSSPLANE_LIMIT_BLOOD) {
                if (isAnger) {
                    isAnger = false;
                }

                if (!isCrazy) {
                    isCrazy = true;
                    speed = 20 + 3 * speedTime;
                    if (bulletType != ConstantUtil.BOSSBULLET_CRAZY) {
                        changeBullet(ConstantUtil.BOSSBULLET_CRAZY);
                    }
                }
            }

            // 极限状态
            if (blood <= GameConstant.BOSSPLANE_LIMIT_BLOOD) {
                if (isAnger) {
                    isAnger = false;
                }

                if (isCrazy) {
                    isCrazy = false;
                }

                if (!isLimit) {
                    isLimit = true;
                    speed = 30 + 5 * speedTime;
                    if (bulletType != ConstantUtil.BOSSBULLET_LIMIT) {
                        changeBullet(ConstantUtil.BOSSBULLET_LIMIT);
                    }
                }

            }

            moveLogic();

        }
    }

    /**
     * BOSS移动逻辑
     */
    public void moveLogic() {
        if (isCrazy || isLimit) {
            if (direction == ConstantUtil.DIR_RIGHT) {
                direction = ConstantUtil.DIR_LEFT;
            }
            // boss疯狂状态时的移动(右下>左>右上>左>右下``)
            if (object_x < rightBorder && object_y < downBorder
                    && direction == ConstantUtil.DIR_RIGHT_DOWN) {
                object_x += speed;
                object_y += speed;
                if (object_x >= rightBorder || object_y >= downBorder) {
                    direction = ConstantUtil.DIR_LEFT;
                }
            }

            if (object_x > leftBorder && direction == ConstantUtil.DIR_LEFT) {
                object_x -= speed;
                if (object_x <= leftBorder) {
                    direction = ConstantUtil.DIR_RIGHT_UP;
                }
            }
            if (object_x < rightBorder && object_y > upBorder
                    && direction == ConstantUtil.DIR_RIGHT_UP) {
                object_x += speed;
                object_y -= speed;
                if (object_x >= rightBorder || object_y <= upBorder) {
                    direction = ConstantUtil.DIR_TEMP;
                }
            }

            if (object_x > leftBorder && direction == ConstantUtil.DIR_TEMP) {
                object_x -= speed;
                if (object_x <= leftBorder) {
                    direction = ConstantUtil.DIR_RIGHT_DOWN;
                }
            }
        } else if (isAnger) {

            // boss愤怒状态的移动(下方左右移动)
            if (object_y < downBorder) {
                object_y += speed;
                if (object_y >= downBorder) {
                    direction = ConstantUtil.DIR_RIGHT;
                }
            }

            if (object_x < rightBorder && direction == ConstantUtil.DIR_RIGHT) {
                object_x += speed;
                if (object_x >= rightBorder) {
                    direction = ConstantUtil.DIR_LEFT;
                }
            }

            if (object_x > leftBorder && direction == ConstantUtil.DIR_LEFT) {
                object_x -= speed;
                if (object_x <= leftBorder) {
                    direction = ConstantUtil.DIR_RIGHT;
                }
            }
        } else {
            // boss普通状态的移动(上方左右移动)
            if (object_x < rightBorder && direction == ConstantUtil.DIR_RIGHT) {
                object_x += speed;
                if (object_x >= rightBorder) {
                    direction = ConstantUtil.DIR_LEFT;
                }
            }

            if (object_x > leftBorder && direction == ConstantUtil.DIR_LEFT) {
                object_x -= speed;
                if (object_x <= leftBorder) {
                    direction = ConstantUtil.DIR_RIGHT;
                }
            }

        }
    }

    /**
     * 更换子弹类型
     * @param type
     */
    public void changeBullet(int type) {
        bulletType = type;

        // 清理原先的子弹
        bullets.clear();


        if (bulletType == ConstantUtil.BOSSBULLET_DEFAULT) { // 普通状态
            normalShooting();
        } else if (bulletType == ConstantUtil.BOSSBULLET_ANGER) { // 愤怒状态
            angerShooting();
        } else if (bulletType == ConstantUtil.BOSSBULLET_CRAZY) { // 疯狂状态
            crazyShooting();
        } else if (bulletType == ConstantUtil.BOSSBULLET_LIMIT) { // 极限状态
            limitShooting();
        } else { // 其他情况
            for (int i = 0; i < 5; i++) {
                // 生产普通子弹
                BossFlameBullet bullet = (BossFlameBullet) factory
                        .createBossFlameBullet(resources);
                bullets.add(bullet);

            }
        }

    }

    /**
     * 极限射击模式
     */
    private void limitShooting() {
        // 弹夹数
        int clip = speedTime + 5;
        for (int i = 0; i < clip; i++) {
            if (speedTime == 1) {
                // 生产子弹5
                BossRHellfireBullet bullet5 = (BossRHellfireBullet) factory
                        .createBossRHellfireBullet(resources);
                bullets.add(bullet5);
            } else if (speedTime == 2) {
                // 生产子弹4
                BossYHellfireBullet bullet4 = (BossYHellfireBullet) factory
                        .createBossYHellfireBullet(resources);
                bullets.add(bullet4);

                // 生产子弹5
                BossRHellfireBullet bullet5 = (BossRHellfireBullet) factory
                        .createBossRHellfireBullet(resources);
                bullets.add(bullet5);
            } else if (speedTime == 3) {
                // 生产子弹1
                BossSunBullet bullet1 = (BossSunBullet) factory
                        .createBossSunBullet(resources);
                bullets.add(bullet1);
                // 生产子弹4
                BossYHellfireBullet bullet4 = (BossYHellfireBullet) factory
                        .createBossYHellfireBullet(resources);
                bullets.add(bullet4);
                // 生产子弹5
                BossRHellfireBullet bullet5 = (BossRHellfireBullet) factory
                        .createBossRHellfireBullet(resources);
                bullets.add(bullet5);
            } else if (speedTime == 4) {
                // 生产子弹1
                BossSunBullet bullet1 = (BossSunBullet) factory
                        .createBossSunBullet(resources);
                bullets.add(bullet1);

                // 生产子弹3
                BossGThunderBullet bullet3 = (BossGThunderBullet) factory
                        .createBossGThunderBullet(resources);
                bullets.add(bullet3);

                // 生产子弹5
                BossRHellfireBullet bullet5 = (BossRHellfireBullet) factory
                        .createBossRHellfireBullet(resources);
                bullets.add(bullet5);
            } else {

                // 生产子弹1
                BossSunBullet bullet1 = (BossSunBullet) factory
                        .createBossSunBullet(resources);
                bullets.add(bullet1);

                // 生产子弹5
                BossRHellfireBullet bullet5 = (BossRHellfireBullet) factory
                        .createBossRHellfireBullet(resources);
                bullets.add(bullet5);

                // 生产子弹Default
                BossDefaultBullet bullet_default = (BossDefaultBullet) factory
                        .createBossBulletDefault(resources);
                bullets.add(bullet_default);

            }

        }
    }

    /**
     * 疯狂射击模式
     */
    private void crazyShooting() {
        // 弹夹数
        int clip = speedTime + 4;
        for (int i = 0; i < clip; i++) {
            if (speedTime == 1) {
                // 生产子弹4
                BossYHellfireBullet bullet4 = (BossYHellfireBullet) factory
                        .createBossYHellfireBullet(resources);
                bullets.add(bullet4);
            } else if (speedTime == 2) {
                // 生产子弹5
                BossRHellfireBullet bullet5 = (BossRHellfireBullet) factory
                        .createBossRHellfireBullet(resources);
                bullets.add(bullet5);
            } else if (speedTime == 3) {
                // 生产子弹4
                BossYHellfireBullet bullet4 = (BossYHellfireBullet) factory
                        .createBossYHellfireBullet(resources);
                bullets.add(bullet4);
                // 生产子弹5
                BossRHellfireBullet bullet5 = (BossRHellfireBullet) factory
                        .createBossRHellfireBullet(resources);
                bullets.add(bullet5);
            } else {
                // 生产子弹3
                BossGThunderBullet bullet3 = (BossGThunderBullet) factory
                        .createBossGThunderBullet(resources);
                bullets.add(bullet3);

                // 生产子弹4
                BossYHellfireBullet bullet4 = (BossYHellfireBullet) factory
                        .createBossYHellfireBullet(resources);
                bullets.add(bullet4);

                // 生产子弹5
                BossRHellfireBullet bullet5 = (BossRHellfireBullet) factory
                        .createBossRHellfireBullet(resources);
                bullets.add(bullet5);

            }

        }
    }

    /**
     * 愤怒射击模式
     */
    private void angerShooting() {
        for (int i = 0; i < 8; i++) {
            if (speedTime <= 2) {
                // 生产子弹1
                BossSunBullet bullet1 = (BossSunBullet) factory
                        .createBossSunBullet(resources);
                bullets.add(bullet1);

                // 生产子弹2
                BossTriangleBullet bullet2 = (BossTriangleBullet) factory
                        .createBossTriangleBullet(resources);
                bullets.add(bullet2);
            } else if (speedTime <= 4) {
                // 生产子弹1
                BossSunBullet bullet1 = (BossSunBullet) factory
                        .createBossSunBullet(resources);
                bullets.add(bullet1);

                // 生产子弹3
                BossGThunderBullet bullet3 = (BossGThunderBullet) factory
                        .createBossGThunderBullet(resources);
                bullets.add(bullet3);
            } else {
                // 生产子弹1
                BossSunBullet bullet1 = (BossSunBullet) factory
                        .createBossSunBullet(resources);
                bullets.add(bullet1);

                // 生产子弹2
                BossTriangleBullet bullet2 = (BossTriangleBullet) factory
                        .createBossTriangleBullet(resources);
                bullets.add(bullet2);

                // 生产子弹3
                BossGThunderBullet bullet3 = (BossGThunderBullet) factory
                        .createBossGThunderBullet(resources);
                bullets.add(bullet3);
            }

        }
    }

    /**
     * 普通射击模式
     */
    private void normalShooting() {
        for (int i = 0; i < 100; i++) {
            // 生产子弹Default
            BossDefaultBullet bullet_default = (BossDefaultBullet) factory
                    .createBossBulletDefault(resources);
            bullets.add(bullet_default);

            if (speedTime >= 3) {
                if (speedTime == 3) {
                    // 生产子弹4
                    BossYHellfireBullet bullet4 = (BossYHellfireBullet) factory
                            .createBossYHellfireBullet(resources);
                    bullets.add(bullet4);
                } else if (speedTime == 4) {
                    // 生产子弹5
                    BossRHellfireBullet bullet5 = (BossRHellfireBullet) factory
                            .createBossRHellfireBullet(resources);
                    bullets.add(bullet5);
                } else {
                    // 生产子弹4
                    BossYHellfireBullet bullet4 = (BossYHellfireBullet) factory
                            .createBossYHellfireBullet(resources);
                    bullets.add(bullet4);

                    // 生产子弹5
                    BossRHellfireBullet bullet5 = (BossRHellfireBullet) factory
                            .createBossRHellfireBullet(resources);
                    bullets.add(bullet5);
                }
            }

        }
    }

}
