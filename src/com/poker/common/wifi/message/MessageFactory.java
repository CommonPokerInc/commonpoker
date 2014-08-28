
package com.poker.common.wifi.message;

import java.util.ArrayList;

import com.poker.common.entity.ClientPlayer;
import com.poker.common.entity.Poker;
import com.poker.common.entity.Room;

/*
 * author FrankChan
 * description 信息载体生产类
 * time 2014-8-16
 *
 */
public class MessageFactory {
	
	//产生游戏行为信息
	public static GameMessage newGameMessage(boolean exit,int type,int money,String extra){
		GameMessage msg = new GameMessage();
		msg.setSource(BaseMessage.MESSAGE_SOURCE);
		msg.setExit(exit);
		msg.setAmount(money);
		msg.setExtra(extra);
		return msg;
	}
	
	//产生玩家信息
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


