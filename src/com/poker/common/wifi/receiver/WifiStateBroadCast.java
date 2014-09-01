package com.poker.common.wifi.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.poker.common.wifi.WifiHotManager.OpretionsType;
import com.poker.common.wifi.WifiHotManager.WifiBroadCastOperations;


/**
 * 
 * @author frankchan
 * 手机自身状态的监听器，与链接无关
 */
public class WifiStateBroadCast extends BroadcastReceiver {

	private WifiBroadCastOperations operations;

	private OpretionsType type;

	private String SSID;

	public WifiStateBroadCast(WifiBroadCastOperations operations, String SSID) {

		this.operations = operations;
		this.SSID = SSID;
	}

	public void setOpType(OpretionsType type) {
		this.type = type;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
			int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
			Log.i("WIFI状态", "wifiState" + wifiState);
			switch (wifiState) {
			case WifiManager.WIFI_STATE_DISABLED:
				break;
			case WifiManager.WIFI_STATE_DISABLING:
				break;
			case WifiManager.WIFI_STATE_ENABLED:
				if (type != null) {
					operations.operationByType(type, SSID);
				}
				break;
			}
		}

	}

}
