
package com.poker.common.entity;
/*
 * author FrankChan
 * description ������Գ������
 * time 2014-7-29
 *
 */
public abstract class AbsPlayer {
	
	private String name;
	
	//ͷ����,0��ӦĬ��ͷ��
	private int avatarNumber = 0;
	
	private int number;
	
	//��ҽ�ɫ��0��ͨ��1С����2������3ׯ��
	private int character = 0;
	
	private int restMoney;
	
	private int betMoney;
	
	private boolean isOut;
	
}
