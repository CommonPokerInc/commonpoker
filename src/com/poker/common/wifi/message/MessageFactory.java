
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
	public static GameMessage newGameMessage(){
		GameMessage msg = new GameMessage();
		msg.setSource(BaseMessage.MESSAGE_SOURCE);
		return msg;
	}
	
	//���������Ϣ
	public static PeopleMessage newPeopleMessage(boolean start,
			ArrayList<ClientPlayer>clientList,ArrayList<Poker>pokerList){
		PeopleMessage msg = new PeopleMessage();
		msg.setSource(BaseMessage.MESSAGE_SOURCE);
		msg.setPlayerList(clientList);
		msg.setPokerList(pokerList);
		msg.setStart(start);
		return msg;
	}
}


