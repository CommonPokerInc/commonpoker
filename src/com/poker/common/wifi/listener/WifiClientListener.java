package com.poker.common.wifi.listener;

/**
 * 
 * @author frankchan
 *	玩家数量变化监听器
 */
public interface WifiClientListener {
	void clientIncrease(String clientName);
	void clientDecrease(String clientName);
}
