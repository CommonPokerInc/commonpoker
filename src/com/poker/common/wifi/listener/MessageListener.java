
package com.poker.common.wifi.listener;
/*
 * author FrankChan
 * description ��Ϸͨ�Żص��ӿ�
 * time 2014-8-16
 *
 */
public interface MessageListener {
	public void onServerReceive();
	public void onClientReceive();
	public void onSendFailure(String errInfo);
}


