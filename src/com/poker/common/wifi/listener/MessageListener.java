
package com.poker.common.wifi.listener;

import com.poker.common.wifi.message.GameMessage;
import com.poker.common.wifi.message.PeopleMessage;

/*
 * author FrankChan
 * description ����ʵ���������ͨ������Ļص��ӿ�
 * time 2014-8-16
 *
 */
public interface MessageListener {
	
	//��Ϊ�������յ������Ϣ
	public void onServerReceive(PeopleMessage msg);
	
	//��Ϊ�������յ���Ϸ��Ϣ
	public void onServerReceive(GameMessage msg);
	
	//��Ϊ�ͻ����յ������Ϣ
	public void onClientReceive(PeopleMessage msg);
	
	//��Ϊ�ͻ����յ���Ϸ��Ϣ
	public void onClientReceive(GameMessage msg);
	
	//������������Ϣ�ɹ�
	public void onServerSendFailure();
	
	//������������Ϣʧ��
	public void onServerSendSuccess();
	
	//�ͻ��˷�����Ϣ�ɹ�
	public void onClientSendFailure();
	
	//�ͻ��˷�����Ϣʧ��
	public void onClientSendSuccess();
	
}


