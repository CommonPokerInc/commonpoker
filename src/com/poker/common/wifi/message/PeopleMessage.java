
package com.poker.common.wifi.message;

import java.util.ArrayList;

import com.poker.common.entity.ClientPlayer;
import com.poker.common.entity.Poker;

/*
 * author FrankChan
 * description 游戏未开始前的信息载体，人数确认
 * time 2014-8-16
 *
 */
public class PeopleMessage extends BaseMessage {
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	
	//游戏开始的标记
	private boolean start;
	
	//目前包含的所有玩家
	private ArrayList<ClientPlayer>playerList;
	
	//当且仅当游戏开始时用到，为初始化时的发牌序列
	private ArrayList<Poker>pokerList;

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public ArrayList<ClientPlayer> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(ArrayList<ClientPlayer> playerList) {
		this.playerList = playerList;
	}

	public ArrayList<Poker> getPokerList() {
		return pokerList;
	}

	public void setPokerList(ArrayList<Poker> pokerList) {
		this.pokerList = pokerList;
	}
}


