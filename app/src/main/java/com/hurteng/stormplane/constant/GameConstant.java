package com.hurteng.stormplane.constant;

/**
 * 游戏数据常量
 * 
 * @author Administrator
 * 
 */
public interface GameConstant {

	//初始相关
	int LIFEAMOUNT = 5;// 初始生命值
	int MISSILECOUNT = 5;// 初始导弹数
	int LIFE_MAXCOUNT = 9;// 生命最大值
	int MISSILE_MAXCOUNT = 9;// 导弹最大存有量
	int GAMESPEED = 1;// 游戏初始速度倍率
	int MAXGRADE = 6;// 游戏最高等级/速度倍率(游戏速度跟游戏等级相关，等级越高，速度越快)
	int LEVELUP_SCORE = 50000;// 升级所需的积分


	//敌机总数量
	int SMALLPLANE_COUNT = 10;// 小型机
	int MIDDLEPLANE_COUNT = 8;// 中型机
	int BIGPLANE_COUNT = 10;// 大型机
	int BOSSPLANE_COUNT = 1;// Boss

	//敌机血量
	int SMALLPLANE_BLOOD = 1;// 小型机
	int MIDDLEPLANE_BLOOD = 40;// 中型机
	int BIGPLANE_BLOOD = 120;// 大型机
	int BOSSPLANE_BLOOD = 1000;// Boss总血量
	int BOSSPLANE_ANGER_BLOOD = 700;// Boss进入愤怒状态的血量值（小于Boss总血量）
	int BOSSPLANE_CRAZY_BLOOD = 500;// Boss进入疯狂状态的血量值（小于Boss愤怒状态的血量）
	int BOSSPLANE_LIMIT_BLOOD = 150;// Boss进入极限状态的血量值（小于Boss疯狂状态的血量）

	//敌机分数
	int SMALLPLANE_SCORE = 100;// 小型机
	int MIDDLEPLANE_SCORE = 300;// 中型机
	int BIGPLANE_SCORE = 800;// 大型机
	int BOSSPLANE_SCORE = 2000;// Boss

	//物品出现所需的积分值
	int MIDDLEPLANE_APPEARSCORE = 2000;// 中型机
	int BIGPLANE_APPEARSCORE = 8000;// 大型机
	int BOSSPLANE_APPEARSCORE = 30000;// Boss
	int MISSILE_APPEARSCORE = 5000;// 导弹
	int LIFE_APPEARSCORE = 10000;// 生命
	int BULLET1_APPEARSCORE = 3000;// 子弹1
	int BULLET2_APPEARSCORE = 7000;// 子弹2

	//伤害值
	int MISSILE_HARM = 80;// 导弹
	int MYBULLET_HARM = 1;// 我方初始子弹(默认值为1)
	int MYBULLET1_HARM = 4;// 我方子弹1
	int MYBULLET2_HARM = 5;// 我方子弹2

	//我军速度
	int MYBULLET_SPEED = 80;// 原始子弹速度
	int MYBULLET1_SPEED = 100;// 子弹1速度
	int MYBULLET2_SPEED = 120;// 子弹2速度
	int MYPLANE_SPEED = 30;// 机体速度
	
	//敌机速度
	int BIGPLANE_SPEED = 3;// 大机体初始速度(默认速度为3，摆冲移动)
	
	
	//持续显示时间
	long BOOM_TIME = 2000;// 我方飞机炸毁
	long INVINCIBLE_TIME = 5000;// 我方飞机无敌模式
	long MISSILEBOOM_TIME = 500;// 我方导弹爆炸
	long MYSPECIALBULLET_DURATION = 15000;// 我方特殊子弹

}
