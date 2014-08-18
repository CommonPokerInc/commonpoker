
package com.poker.common.activity;

import com.google.gson.Gson;
import com.poker.common.BaseApplication;
import com.poker.common.wifi.listener.CommunicationListener;
import com.poker.common.wifi.listener.MessageListener;
import com.poker.common.wifi.message.BaseMessage;
import com.poker.common.wifi.message.GameMessage;
import com.poker.common.wifi.message.PeopleMessage;

import android.app.Activity;
import android.util.Log;

/*
 * author FrankChan
 * description ��Ϸͨ�ų�����
 * time 2014-8-16
 *
 */
public abstract class AbsGameActivity extends Activity implements CommunicationListener{
	
	private BaseApplication app = (BaseApplication) getApplication();
	
	private MessageListener listener;
	
	public void initMessageListener(MessageListener listener){
		this.listener = listener;
		if(app.isServer()){
		}else{
			app.getClient().beganAcceptMessage(this);
		}
	}
	
	@Override
	public void onStringReceive(String strInfo) {
		// TODO Auto-generated method stub
		if(checkEnvError()){
			Log.e("frankchan", "��Ϸ��������δ�������");
			return;
		}
		
		if(app.isGameStarted){
			GameMessage gm = new Gson().fromJson(strInfo, GameMessage.class);
			if(gm.getSource()==BaseMessage.MESSAGE_SOURCE){
				if(app.isServer()){
					listener.onServerReceive(gm);
				}else{
					listener.onClientReceive(gm);
				}
			}
		}else{
			PeopleMessage pm = new Gson().fromJson(strInfo, PeopleMessage.class);
			if(pm.getSource()==BaseMessage.MESSAGE_SOURCE){
				if(app.isServer()){
					listener.onServerReceive(pm);
				}else{
					listener.onClientReceive(pm);
				}
			}
		}
	}
	@Override
	public void onSendFailure(String errInfo) {
		// TODO Auto-generated method stub
		if(checkEnvError()){
			Log.e("frankchan", "��Ϸ��������δ�������");
			return;
		}
		if(app.isServer()){
			listener.onServerSendFailure();
		}else{
			listener.onClientSendFailure();
		}
	}
	
	
	private boolean checkEnvError(){
		return null==listener||null==app||app.isConnected==false;
	}
}


