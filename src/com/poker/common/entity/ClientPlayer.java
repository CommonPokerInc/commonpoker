
package com.poker.common.entity;

import com.poker.common.wifi.SocketClient;

/*
 * author FrankChan
 * description ¿Í»§¶ËÍæ¼Ò
 * time 2014-7-29
 *
 */
public class ClientPlayer extends AbsPlayer implements ImpPlayer {

	private UserInfo info;
	
	private SocketClient socket;
	
	public ClientPlayer(UserInfo info,SocketClient socket){
		this.setInfo(info);
		this.setSocket(socket);
	}
	
	public ClientPlayer(){}
	@Override
	public void abandon() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void givein() {
		// TODO Auto-generated method stub

	}

	@Override
	public void follow() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addbet(int money) {
		// TODO Auto-generated method stub

	}
	public UserInfo getInfo() {
		return info;
	}
	public void setInfo(UserInfo info) {
		this.info = info;
	}
	public SocketClient getSocket() {
		return socket;
	}
	public void setSocket(SocketClient socket) {
		this.socket = socket;
	}

}


