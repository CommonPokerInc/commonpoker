
package com.poker.common.wifi.message;
/*
 * author FrankChan
 * description ��Ϸ��ʼ�����Ϣ����
 * time 2014-8-16
 *
 */
public class GameMessage extends BaseMessage{

	/**
	 * ���л��汾��
	 */
	private static final long serialVersionUID = 1L;
	
	//����
	public final static int ACTION_ABANDOM = 1;
	//����
	public final static int ACTION_GIVE_IN = 2;
	//��ע
	public final static int ACTION_FOLLOW =3;
	//��ע
	public final static int ACTION_ADD_BET =4;
	//����
	public final static int ACTION_SEND_POKER =5;
	
	private int id;
	
	private int action;

	//��Ǯ
	private int amount;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
}


