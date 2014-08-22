
package com.poker.common.wifi.message;

import java.util.ArrayList;

import com.poker.common.entity.ClientPlayer;
import com.poker.common.entity.Poker;

/*
 * author FrankChan
 * description ��Ϣ����������
 * time 2014-8-16
 *
 */
public class MessageFactory {
	
	//������Ϸ��Ϊ��Ϣ
	public static GameMessage newGameMessage(boolean exit){
		GameMessage msg = new GameMessage();
		msg.setSource(BaseMessage.MESSAGE_SOURCE);
		msg.setExit(exit);
		return msg;
	}
	
	//���������Ϣ
	public static PeopleMessage newPeopleMessage(boolean start,boolean exit,
			ArrayList<ClientPlayer>clientList,ArrayList<Poker>pokerList){
		PeopleMessage msg = new PeopleMessage();
		msg.setSource(BaseMessage.MESSAGE_SOURCE);
		msg.setPlayerList(clientList);
		msg.setPokerList(pokerList);
		msg.setStart(start);
		msg.setExit(exit);
		return msg;
	}
}


