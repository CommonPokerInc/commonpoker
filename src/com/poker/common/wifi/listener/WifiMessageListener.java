package com.poker.common.wifi.listener;

/**
 * 
 * @author frankchan
 * ��Ϣ���ͽ������������
 */
public interface WifiMessageListener {
	void sendFailure(String msg);
	void sendSuccess(String msg);
}
