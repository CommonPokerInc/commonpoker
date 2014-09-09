
package com.poker.common.wifi.message;
/*
 * author FrankChan
 * description 游戏开始后的信息载体
 * time 2014-8-16
 *
 */
public class GameMessage extends BaseMessage{

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	
	//弃牌
	public final static int ACTION_ABANDOM = 1;
	//让牌
	public final static int ACTION_GIVE_IN = 2;
	//跟注
	public final static int ACTION_FOLLOW =3;
	//加注
	public final static int ACTION_ADD_BET =4;
	//发公共牌
	public final static int ACTION_SHOW_PUBLIC_POKER =5;
//	发底牌
	public final static int ACTION_SEND_BOOL = 6;
//	发4张牌
	public final static int ACTION_SEND_FOUR = 7;
//	发5张牌
	public final static int ACTION_SEND_FIVE = 8;
//	当前操作的人
	public final static int ACTION_CURRENT_PERSON = 9;
//	当前操作的人完成操作，提示下一家进行操作
	public final static int ACTION_FINISH_OPTIOIN = 10;
//	更新指定下标的玩家的钱
	public final static int ACTION_UPDATE_MONEY = 11;
	
	public final static int ACTION_NEXT_ROUND = 12;
	
	private int id;
	
	private int action;

	//金钱
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


