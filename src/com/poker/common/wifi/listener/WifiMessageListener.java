package com.poker.common.wifi.listener;

/**
 * 
 * @author frankchan
 * 信息发送结果反馈监听器
 */
public interface WifiMessageListener {
	void sendFailure(String msg);
	void sendSuccess(String msg);
}
