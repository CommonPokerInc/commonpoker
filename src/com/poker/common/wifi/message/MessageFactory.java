
package com.poker.common.wifi.message;

import java.util.ArrayList;

import com.poker.common.entity.ClientPlayer;
import com.poker.common.entity.Poker;
import com.poker.common.entity.Room;

/*
 * author FrankChan
 * description ��Ϣ���������
 * time 2014-8-16
 *
 */
public class MessageFactory {
	
	//������Ϸ��Ϊ��Ϣ
	public static GameMessage newGameMessage(boolean exit,int type,int money,String extra){
//		GameMessage msg = new GameMessage();
//		msg.setSource(BaseMessage.MESSAGE_SOURCE);
//		msg.setAction(type);
//		msg.setExit(exit);
//		msg.setAmount(money);
//		msg.setExtra(extra);
//		return msg;
	    return newGameMessage(exit,type,money,extra,null);
	}
	
	public static GameMessage newGameMessage(boolean exit,int type,int money,String extra,ArrayList<Poker> pokerList){
        GameMessage msg = new GameMessage();
        msg.setSource(BaseMessage.MESSAGE_SOURCE);
        msg.setAction(type);
        msg.setExit(exit);
        msg.setAmount(money);
        msg.setExtra(extra);
        msg.setPokerList(pokerList);
        return msg;
    }
	
	//���������Ϣ
	public static PeopleMessage newPeopleMessage(boolean start,boolean exit,
			ArrayList<ClientPlayer>clientList,ArrayList<Poker>pokerList,Room room,String extra){
		PeopleMessage msg = new PeopleMessage();
		msg.setSource(BaseMessage.MESSAGE_SOURCE);
		msg.setPlayerList(clientList);
		msg.setPokerList(pokerList);
		msg.setStart(start);
		msg.setExit(exit);
		msg.setRoom(room);
		msg.setExtra(extra);
		return msg;
	}
}


