
package com.poker.common.activity;

import java.net.ServerSocket;

import com.google.gson.Gson;
import com.poker.common.BaseApplication;
import com.poker.common.wifi.Global;
import com.poker.common.wifi.SocketServer;
import com.poker.common.wifi.listener.CommunicationListener;
import com.poker.common.wifi.listener.MessageListener;
import com.poker.common.wifi.message.BaseMessage;
import com.poker.common.wifi.message.GameMessage;
import com.poker.common.wifi.message.PeopleMessage;

import android.app.Activity;
import android.util.Log;

/*
 * author FrankChan
 * description 游戏通信抽象类
 * time 2014-8-16
 *
 */
public abstract class AbsGameActivity extends Activity implements CommunicationListener{
	
	private BaseApplication app;
	
	private MessageListener listener;
	
	public void initMessageListener(MessageListener listener){
		app = (BaseApplication) getApplication();
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
			Log.e("frankchan", "游戏环境变量未配置完成");
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
			Log.e("frankchan", "游戏环境变量未配置完成");
			return;
		}
		if(app.isServer()){
			listener.onServerSendFailure();
		}else{
			listener.onClientSendFailure();
		}
	}
	
	
	public boolean checkEnvError(){
		return null==listener||null==app||app.isConnected==false;
	}
	
	public void sendMessage(BaseMessage message){
		String strMessage = null;
		if(message instanceof PeopleMessage){
			strMessage = new Gson().toJson(message,PeopleMessage.class);
		}else if(message instanceof GameMessage){
			strMessage = new Gson().toJson(message,GameMessage.class);
		}else{
			strMessage = "";
		}
		
		if(app.isServer()){
			app.getServer().sendMessageToAllClients(strMessage);
		}else{
			app.getClient().sendMessage(strMessage);
		}
	}
}


