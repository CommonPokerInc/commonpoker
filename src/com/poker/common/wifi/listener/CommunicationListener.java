
package com.poker.common.wifi.listener;

/*
 * author FrankChan
 * description 游戏通信回调接口，消息未解析
 * time 2014-8-16
 *
 */
public interface CommunicationListener {
	public void onStringReceive(String strInfo);
	public void onSendSuccess();
	public void onSendFailure(String errInfo);
}


