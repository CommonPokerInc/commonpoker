
package com.poker.common.activity;


import android.app.Activity;
import android.util.Log;
import java.util.HashMap;

import com.google.gson.Gson;
import com.poker.common.BaseApplication;
import com.poker.common.wifi.listener.CommunicationListener;
import com.poker.common.wifi.listener.MessageListener;
import com.poker.common.wifi.message.BaseMessage;
import com.poker.common.wifi.message.GameMessage;
import com.poker.common.wifi.message.PeopleMessage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/*
 * author FrankChan
 * description ��Ϸͨ�ų�����
 * time 2014-8-16
 *
 */
public abstract class AbsGameActivity extends Activity implements CommunicationListener{
	
	protected BaseApplication app;
	
	private MessageListener listener;
	
	private String mSSID ;
	
	private BackHandler mHandler;

	private HandlerThread mThread;
	
	private final static int MSG_SEND_ACK = 1;
	
	private final static int MSG_RECEIVE_ACK = 2;
	
	//ȷ����Ϣ���ͼ���
	private final static int INTERVAL_SEND_MSG = 5000;
	
	//ȷ����Ϣ�ɽ��ܵ�����ʱ��
	private final static int INTERVAL_MAX_ACK = 8000;
	
	//������Ϸ���浽��ʼ����ȷ�ϰ��ļ���
	private final static int INTERVAL_AFTER_START = 1000;
	
	private final static int INTERVAL_AFTER_SEND = 5000;
	
	private boolean allowSend = true;
	
	private boolean allowAck =true;
	
	private HashMap<String, Integer>timeMap = new HashMap<String,Integer>();
	
	public void initMessageListener(MessageListener listener){
		app = (BaseApplication) getApplication();
		mSSID = getIntent().getStringExtra("SSID");
		this.listener = listener;
		if(app.isServer()){
			app.getServer().setListener(this);
			app.getServer().beginListen(null);
			//initTimeMap();
		}else{
			app.getClient().beganAcceptMessage(this);
		}
		//initBackHandler();
	}
	
	@SuppressLint("HandlerLeak")
	class BackHandler extends Handler{
		
		public BackHandler(Looper looper) {
			// TODO Auto-generated constructor stub
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what){
			case MSG_RECEIVE_ACK:
				String mapKey = (String) msg.obj; 
				break;
			case MSG_SEND_ACK:
				break;
			}
		}
		
	}
	
	@Override
	public void onSendSuccess() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStringReceive(String strInfo) {
		// TODO Auto-generated method stub
		if(checkEnvError()){
			Log.e("frankchan", "��Ϸ�������δ��������");
			return;
		}
		//�����յ����ַ�������15λ����Ϊȷ����Ϣ
		if(strInfo.length()<=15){
			Message msg = mHandler.obtainMessage(MSG_RECEIVE_ACK, strInfo);
			mHandler.sendMessage(msg);
			return;
		}
		if(app.isGameStarted){
			GameMessage gm = new Gson().fromJson(strInfo, GameMessage.class);
			if(BaseMessage.MESSAGE_SOURCE.equals(gm.getSource())){
				if(app.isServer()){
					listener.onServerReceive(gm);
				}else{
					listener.onClientReceive(gm);
				}
			}
		}else{
			PeopleMessage pm = new Gson().fromJson(strInfo, PeopleMessage.class);
			if(BaseMessage.MESSAGE_SOURCE.equals(pm.getSource())){
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
			Log.e("frankchan", "��Ϸ�������δ��������");
			return;
		}
		if(app.isServer()){
			listener.onServerSendFailure();
		}else{
			listener.onClientSendFailure();
		}
	}
	
	
	protected boolean checkEnvError(){
		return null==listener||null==app||app.isConnected==false;
	}
	
	protected void sendMessage(BaseMessage message){
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
	
	private void initBackHandler(){
		mThread = new HandlerThread("BeatDetector"){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				//��ʱ����BackHandler�����̷߳�����Ϣ�ͼ���
				if(app.isServer()){
					mHandler.postDelayed(new SendRunnable(),INTERVAL_AFTER_START);
				}
				mHandler.postDelayed(new AckRunnable(), INTERVAL_AFTER_SEND);
			}
		};
		mThread.start();
		mHandler = new BackHandler(mThread.getLooper());
	}

	private void initTimeMap(){
		for(String key :app.getServer().socketMap.keySet()){
			timeMap.put(key, new Integer(INTERVAL_AFTER_START/1000));
		}
	
	}
	
	class SendRunnable implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class AckRunnable implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
		
	}
	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		stopCheckAck();
		stopSendAck();
		if(null!=mThread){
			mThread.quit();
		}
		if(app.isServer()){
			app.getServer().stopListner();
			app.getServer().clearServer();
			app.setServer(null);
			app.wm.disableWifiHot();
		}else{
			app.getClient().stopAcceptMessage();
			app.getClient().clearClient();
			app.setClient(null);
			if(null!=mSSID){
				app.wm.deleteMoreCon(mSSID);
			}
			app.wm.disconnectWifi(mSSID);
		}
		app.isConnected = false;
		app.isGameStarted =false;
		super.onDestroy();
	}
	
	//ֹͣ����ȷ�ϰ�
	protected void stopSendAck(){
		allowSend = false;
	}
	
	//ֹͣ����ʱ��
	protected void stopCheckAck(){
		allowAck = false;
	}
}


