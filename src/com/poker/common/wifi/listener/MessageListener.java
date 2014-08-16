
package com.poker.common.wifi.listener;

import com.poker.common.wifi.message.GameMessage;
import com.poker.common.wifi.message.PeopleMessage;

/*
 * author FrankChan
 * description 根据实际情况解析通信载体的回调接口
 * time 2014-8-16
 *
 */
public interface MessageListener {
	
	//作为服务器收到玩家信息
	public void onServerReceive(PeopleMessage msg);
	
	//作为服务器收到游戏信息
	public void onServerReceive(GameMessage msg);
	
	//作为客户端收到玩家信息
	public void onClientReceive(PeopleMessage msg);
	
	//作为客户端收到游戏信息
	public void onClientReceive(GameMessage msg);
	
	//服务器发送消息成功
	public void onServerSendFailure();
	
	//服务器发送消息失败
	public void onServerSendSuccess();
	
	//客户端发送消息成功
	public void onClientSendFailure();
	
	//客户端发送消息失败
	public void onClientSendSuccess();
	
}


