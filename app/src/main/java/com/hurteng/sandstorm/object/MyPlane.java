package com.hurteng.sandstorm.object;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.SystemClock;

import com.hurteng.sandstorm.constant.ConstantUtil;
import com.hurteng.sandstorm.constant.DebugConstant;
import com.hurteng.sandstorm.constant.GameConstant;
import com.hurteng.sandstorm.factory.GameObjectFactory;
import com.hurteng.sandstorm.interfaces.IMyPlane;
import com.hurteng.sandstorm.myplane.R;
import com.hurteng.sandstorm.view.MainView;

import java.util.ArrayList;
import java.util.List;

/**
 * 玩家的飞机
 */
public class MyPlane extends GameObject implements IMyPlane {
    private static final boolean Random = false;
    private float middle_x;
    private float middle_y;
    private long startTime; // 开始时间
    private long endTime; // 结束时间
    private boolean isChangeBullet; // 更换子弹类型
    private Bitmap myplane;
    private Bitmap myplane2;
    private List<Bullet> bullets; // 子弹列表
    private MainView mainView;
    private GameObjectFactory factory;
    private boolean isInvincible; // 是否无敌
    private boolean isDamaged; // 是否受损
    private int bulletType;//当前子弹类型
    private boolean isMissileBoom; //导弹是否被引爆

    public MyPlane(Resources resources) {
        super(resources);
        initBitmap();
        this.speed = GameConstant.MYPLANE_SPEED;
        isInvincible = false;
        isChangeBullet = false;
        isDamaged = false;
        isMissileBoom = false;

        factory = new GameObjectFactory();
        bullets = new ArrayList<Bullet>();
        changeBullet(ConstantUtil.MYBULLET);
        bulletType = ConstantUtil.MYBULLET;
    }

    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void setScreenWH(float screen_width, float screen_height) {
        super.setScreenWH(screen_width, screen_height);
        object_x = screen_width / 2 - object_width / 2;
        object_y = screen_height - object_height;
        middle_x = object_x + object_width / 2;
        middle_y = object_y + object_height / 2;
    }

    @Override
    public void initBitmap() {
        myplane = BitmapFactory.decodeResource(resources, R.drawable.myplane);
        myplane2 = BitmapFactory.decodeResource(resources,
                R.drawable.myplaneexplosion);

        object_width = myplane.getWidth() / 3;
        object_height = myplane.getHeight();
    }

    @Override
    public void drawSelf(Canvas canvas) {

        if (isInvincible && !isDamaged) {
            int x = (int) (currentFrame * object_width);
            canvas.save();
            canvas.clipRect(object_x, object_y, object_x + object_width,
                    object_y + object_height);
            canvas.drawBitmap(myplane, object_x - x, object_y, paint);
            canvas.restore();

            currentFrame++;
            if (currentFrame >= 3) {
                currentFrame = 0;
            }
        } else if (isAlive && !isDamaged) {
            int x = (int) (currentFrame * object_width);
            canvas.save();
            canvas.clipRect(object_x, object_y, object_x + object_width,
                    object_y + object_height);
            canvas.drawBitmap(myplane, object_x - x, object_y, paint);
            canvas.restore();
            if (bulletType == ConstantUtil.MYBULLET) {
                currentFrame = 0;
            } else if (bulletType == ConstantUtil.MYBULLET1) {
                currentFrame = 1;
            } else if (bulletType == ConstantUtil.MYBULLET2) {
                currentFrame = 2;
            }
        } else {
            int x = (int) (currentFrame * object_width);
            canvas.save();
            canvas.clipRect(object_x, object_y, object_x + object_width,
                    object_y + object_height);
            canvas.drawBitmap(myplane2, object_x - x, object_y, paint);
            canvas.restore();

            if (bulletType == ConstantUtil.MYBULLET) {
                currentFrame++;
                if (currentFrame >= 2) {
                    currentFrame = 0;
                }
            } else if (bulletType == ConstantUtil.MYBULLET1) {
                currentFrame++;
                if (currentFrame >= 4) {
                    currentFrame = 2;
                }
            } else if (bulletType == ConstantUtil.MYBULLET2) {
                currentFrame++;
                if (currentFrame >= 6) {
                    currentFrame = 4;
                }
            }
        }
    }

    @Override
    public void release() {
        for (Bullet obj : bullets) {
            obj.release();
        }
        if (!myplane.isRecycled()) {
            myplane.recycle();
        }
        if (!myplane2.isRecycled()) {
            myplane2.recycle();
        }

    }

    // TODO: 2017/6/13  玩家飞机的射击逻辑
    /**
     * 射击逻辑
     * @param canvas
     * @param planes
     */
    @Override
    public void shoot(Canvas canvas, List<EnemyPlane> planes) {
        for (Bullet obj : bullets) {
            if (obj.isAlive()) {
                for (EnemyPlane pobj : planes) {
                    if (pobj.isCanCollide()) {
                        if (obj.isCollide((GameObject) pobj)) {
                            pobj.attacked(obj.getHarm());
                            if (pobj.isExplosion()) {
                                mainView.addGameScore(pobj.getScore());
                                if (pobj instanceof SmallPlane) {
                                    mainView.playSound(2);
                                } else if (pobj instanceof MiddlePlane) {
                                    mainView.playSound(3);
                                } else if (pobj instanceof BigPlane) {
                                    mainView.playSound(4);
                                } else {
                                    mainView.playSound(5);
                                }
                            }
                            break;
                        }
                    }
                }
                obj.drawSelf(canvas);
            }
        }
    }

    /**
     * 初始化子弹
     */
    @Override
    public void initBullet() {
        // TODO Auto-generated method stub
        for (Bullet obj : bullets) {
            if (!obj.isAlive()) {
                obj.initial(0, middle_x, middle_y);
                break;
            }
        }
    }

    /**
     * 更换子弹
     * @param type
     */
    @Override
    public void changeBullet(int type) {
        bulletType = type;
        bullets.clear();
        if (isChangeBullet) {
            if (type == ConstantUtil.MYBULLET1) {
                for (int i = 0; i < 6; i++) {
                    MyBullet1 bullet1 = (MyBullet1) factory
                            .createMyBullet1(resources);
                    bullets.add(bullet1);
                }
            } else if (type == ConstantUtil.MYBULLET2) {
                for (int i = 0; i < 4; i++) {
                    MyBullet2 bullet2 = (MyBullet2) factory
                            .createMyBullet2(resources);
                    bullets.add(bullet2);
                }
            }

        } else {
            for (int i = 0; i < 4; i++) {
                MyBullet bullet = (MyBullet) factory.createMyBullet(resources);
                bullets.add(bullet);
            }
        }
    }

    /**
     * 判断特殊子弹是否超时
     */
    public void isBulletOverTime() {
        if (isChangeBullet) {
            endTime = System.currentTimeMillis();
            if (endTime - startTime > GameConstant.MYSPECIALBULLET_DURATION) {
                isChangeBullet = false;
                startTime = 0;
                endTime = 0;
                changeBullet(ConstantUtil.MYBULLET);
            }
        }
    }

    /**
     * 设置飞机的无敌时间
     * @param time
     */
    public void setInvincibleTime(long time) {
        if (DebugConstant.INVINCIBLE) {
            isInvincible = true;
            SystemClock.sleep(time);
            isInvincible = false;
        }
    }

    /**
     * 检测是否为无敌状态
     * @return
     */
    public boolean isInvincible() {
        return isInvincible;
    }

    /**
     * 设置导弹状态
     * @param isBoom
     */
    public void setMissileState(boolean isBoom) {
        isMissileBoom = isBoom;
    }

    /**
     * 检测导弹状态（是否已经引爆）
     * @return
     */
    public boolean getMissileState() {
        return isMissileBoom;
    }

    /**
     * 设置是否为受损状态
     * @param arg
     */
    public void setDamaged(boolean arg) {
        isDamaged = arg;
    }

    /**
     * 检测是否为受损状态
     * @return
     */
    public boolean getDamaged() {
        return isDamaged;

    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean isChangeBullet() {
        return isChangeBullet;
    }

    @Override
    public void setChangeBullet(boolean isChangeBullet) {
        this.isChangeBullet = isChangeBullet;
    }

    @Override
    public float getMiddle_x() {
        return middle_x;
    }

    @Override
    public void setMiddle_x(float middle_x) {
        this.middle_x = middle_x;
        this.object_x = middle_x - object_width / 2;
    }

    @Override
    public float getMiddle_y() {
        return middle_y;
    }

    @Override
    public void setMiddle_y(float middle_y) {
        this.middle_y = middle_y;
        this.object_y = middle_y - object_height / 2;
    }
}
