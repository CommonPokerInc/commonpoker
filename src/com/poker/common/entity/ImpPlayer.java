package com.poker.common.entity;
/*
 * author FrankChan
 * description 游戏行为接口
 * time 2014-7-29
 *
 */
public interface ImpPlayer {
	//弃牌行为
	public void abandon();
	//让牌行为
	public void givein();
	//跟注行为
	public void follow();
	//加注行为
	public void addbet(int money);
}


