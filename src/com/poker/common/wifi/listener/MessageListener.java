
package com.poker.common.wifi.listener;
/*
 * author FrankChan
 * description 游戏通信回调接口
 * time 2014-8-16
 *
 */
public interface MessageListener {
	public void onServerReceive();
	public void onClientReceive();
	public void onSendFailure(String errInfo);
}


