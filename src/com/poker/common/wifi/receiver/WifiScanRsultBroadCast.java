package com.poker.common.wifi.receiver;

import java.util.List;

import com.poker.common.wifi.WifiHotManager.WifiBroadCastOperations;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;


/**
 * 
 * @author frankchan
 * 热点扫描监听器
 */

public class WifiScanRsultBroadCast extends BroadcastReceiver {

	private WifiBroadCastOperations operations;

	private WifiManager mWifimanager;

	// 扫描网络连接列表
	private List<ScanResult> wifiList;

	public WifiScanRsultBroadCast(WifiBroadCastOperations operations) {

		this.operations = operations;

	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("WifiBroadCast", "into onReceive(Context context, Intent intent)");
		if (intent.getAction().equalsIgnoreCase(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
			mWifimanager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			wifiList = mWifimanager.getScanResults();
			operations.disPlayWifiScanResult(wifiList);
		}
	}
}
