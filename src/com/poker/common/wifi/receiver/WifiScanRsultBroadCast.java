package com.poker.common.wifi.receiver;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.poker.common.wifi.WifiHotManager.WifiBroadCastOperations;


/**
 * 
 * @author frankchan
 * �ȵ�ɨ�������
 */

public class WifiScanRsultBroadCast extends BroadcastReceiver {

	private WifiBroadCastOperations operations;

	private WifiManager mWifimanager;

	// ɨ�����������б�
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
