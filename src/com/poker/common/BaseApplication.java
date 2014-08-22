package com.poker.common;


import com.poker.common.entity.ClientPlayer;
import com.poker.common.entity.ServerPlayer;
import com.poker.common.wifi.SocketClient;
import com.poker.common.wifi.SocketServer;
import com.poker.common.wifi.WifiHotManager;

import android.app.Application;

public class BaseApplication extends Application{
	public ServerPlayer sp;
	public ClientPlayer cp;
	private SocketServer server;
	private SocketClient client;
	public WifiHotManager wm;
	private boolean isServer =false; 
	public boolean isConnected = false;
	public boolean isGameStarted =false;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		wm.unRegisterWifiConnectBroadCast();
		wm.unRegisterWifiScanBroadCast();
		wm.unRegisterWifiStateBroadCast();
		if(server!=null){
			server.clearServer();
		}
		if(client!=null){
			client.clearClient();
		}
	}
	public SocketServer getServer(){
		return server;
	}
	public void setServer(SocketServer server){
		this.server = server;
		this.isConnected = true;
		setServer(true);
	}
	
	public SocketClient getClient(){
		return client;
	}
	
	public void setClient(SocketClient client){
		this.client = client;
		this.isConnected = true;
		setServer(false);
	}

	public boolean isServer() {
		return isServer;
	}

	private void setServer(boolean isServer) {
		this.isServer = isServer;
	}
}
