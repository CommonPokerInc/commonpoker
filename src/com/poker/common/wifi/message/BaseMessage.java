
package com.poker.common.wifi.message;

import java.io.Serializable;

/*
 * author FrankChan
 * description ����ͨ��������
 * ���ó���������Ϣ��Դ�ж�
 * time 2014-8-16
 *
 */
public abstract class BaseMessage implements Serializable{
	/**
	 * ���л��汾��
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


