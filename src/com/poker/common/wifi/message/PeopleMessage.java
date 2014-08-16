
package com.poker.common.wifi.message;

import java.util.ArrayList;

import com.poker.common.entity.ClientPlayer;
import com.poker.common.entity.Poker;

/*
 * author FrankChan
 * description ��Ϸδ��ʼǰ����Ϣ���壬����ȷ��
 * time 2014-8-16
 *
 */
public class PeopleMessage extends BaseMessage {
	/**
	 * ���л��汾��
	 */
	private static final long serialVersionUID = 1L;
	
	//��Ϸ��ʼ�ı��
	private boolean start;
	
	//Ŀǰ�������������
	private ArrayList<ClientPlayer>playerList;
	
	//���ҽ�����Ϸ��ʼʱ�õ���Ϊ��ʼ��ʱ�ķ�������
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


