package com.poker.common;

import com.poker.common.entity.ClientPlayer;
import com.poker.common.entity.ServerPlayer;
import com.poker.common.wifi.WifiHotManager;

import android.app.Application;

public class BaseApplication extends Application{
	public ServerPlayer sp;
	public ClientPlayer cp;
	public WifiHotManager wm;
	public boolean isServer =false; 
	public boolean isConnected = false;
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
	}
	
	
}
