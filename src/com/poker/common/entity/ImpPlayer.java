package com.poker.common.entity;
/*
 * author FrankChan
 * description ��Ϸ��Ϊ�ӿ�
 * time 2014-7-29
 *
 */
public interface ImpPlayer {
	//������Ϊ
	public void abandon();
	//������Ϊ
	public void givein();
	//��ע��Ϊ
	public void follow();
	//��ע��Ϊ
	public void addbet(int money);
}


