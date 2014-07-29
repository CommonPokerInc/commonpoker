
package com.poker.common.entity;
/*
 * author FrankChan
 * description 玩家属性抽象基类
 * time 2014-7-29
 *
 */
public abstract class AbsPlayer {
	
	private String name;
	
	//头像编号,0对应默认头像
	private int avatarNumber = 0;
	
	private int number;
	
	//玩家角色：0普通，1小蛮，2大蛮，3庄家
	private int character = 0;
	
	private int restMoney;
	
	private int betMoney;
	
	private boolean isOut;
	
}
