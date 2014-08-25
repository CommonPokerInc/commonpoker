
package com.poker.common.entity;

import com.poker.common.wifi.SocketClient;
import com.poker.common.wifi.SocketServer;

/*
 * author FrankChan
 * description ·þÎñÆ÷Íæ¼Ò
 * time 2014-7-29
 *
 */
public class ServerPlayer extends ClientPlayer{

	private UserInfo info;
	
	public UserInfo getInfo() {
		return info;
	}

	public void setInfo(UserInfo info) {
		this.info = info;
	}

	public SocketServer getServer() {
		return server;
	}

	public void setServer(SocketServer server) {
		this.server = server;
	}

	private SocketServer server;
	
	//forbidden
	public ServerPlayer(UserInfo info, SocketClient socket) {
		super(info, socket);
		// TODO Auto-generated constructor stub
	}
	
	public ServerPlayer(UserInfo info, SocketServer server) {
		this.info = info;
		this.server = server;
		// TODO Auto-generated constructor stub
	}
	

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

}


