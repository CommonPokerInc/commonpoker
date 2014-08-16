
package com.poker.common.wifi.message;

import java.io.Serializable;

/*
 * author FrankChan
 * description 基础通信载体类
 * 内置常量进行信息来源判断
 * time 2014-8-16
 *
 */
public abstract class BaseMessage implements Serializable{
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;

	public final static String MESSAGE_SOURCE = "commonpoker";
	
	private String source;
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}


