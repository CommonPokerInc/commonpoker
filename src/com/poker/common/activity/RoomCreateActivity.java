package com.poker.common.activity;

import java.util.List;

import com.poker.common.BaseApplication;
import com.poker.common.Constant;
import com.poker.common.R;
import com.poker.common.entity.Room;
import com.poker.common.entity.ServerPlayer;
import com.poker.common.entity.UserInfo;
import com.poker.common.util.SystemUtil;
import com.poker.common.wifi.Global;
import com.poker.common.wifi.SocketServer;
import com.poker.common.wifi.SocketServer.SocketCreateListener;
import com.poker.common.wifi.SocketServer.WifiCreateListener;
import com.poker.common.wifi.WifiHotManager;
import com.poker.common.wifi.WifiHotManager.OpretionsType;
import com.poker.common.wifi.WifiHotManager.WifiBroadCastOperations;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

public class RoomCreateActivity extends Activity implements OnClickListener,WifiCreateListener, WifiBroadCastOperations{
	
	private BaseApplication app;
	
	private Room room;
	
	private String mSSID;
	
	private boolean allowCreate = true;
	
	private final static int MSG_CREATE_SERVER_SOCKET = 1;
	private final static int MSG_SHOW_CREATE_HOT_ERROR =2;
	private final static int MSG_SHOW_CREATE_SOCKET_ERROR =3;
	private final static int MSG_SUCCESS_JUMP_GAME =4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_room_create);
		app = (BaseApplication) getApplication();
		findViewById(R.id.btn_create_rank).setOnClickListener(this);
		findViewById(R.id.btn_create_limit).setOnClickListener(this);
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what){
			case MSG_CREATE_SERVER_SOCKET: 
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Log.i("frankchan", "开始创建服务器套接字");
						SocketServer server = SocketServer.newInstance(Global.WIFI_PORT);
						server.createServerSocket(new SocketListener());
					}
				}).start();
				break;
			case MSG_SHOW_CREATE_SOCKET_ERROR:
			case MSG_SHOW_CREATE_HOT_ERROR:
				allowCreate = true;
				Toast.makeText(RoomCreateActivity.this, "创建房间失败", Toast.LENGTH_SHORT).show();
				break;
			case MSG_SUCCESS_JUMP_GAME:
				Toast.makeText(RoomCreateActivity.this, "创建房间成功", Toast.LENGTH_SHORT).show();
				gotoGameActivity();
				break;
			}
		}
		
	};
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(app.wm==null){
			app.wm = WifiHotManager.getInstance(app, this);
		}
		if(app.isConnected){
			if(app.isServer())
				app.getServer().clearServer();
			else
				app.getClient().clearClient();
		}

		if(!allowCreate){
			Toast.makeText(RoomCreateActivity.this, "创建房间中，请勿重复创建", Toast.LENGTH_SHORT).show();
			return;
		}
		switch(v.getId()){
			case R.id.btn_create_limit:
				allowCreate =false;
				room = new Room();
				room.setType(Room.TYPE_LIMIT);
				room.setCount(6);
				room.setInnings(10);
				room.setMinStake(100);
				room.setName("Godlike");
				room.setBasicChips(10000);
				mSSID = "Godlike"+Constant.WIFI_SUFFIX;
				app.wm.startAWifiHot(mSSID,this);
				break;
			case R.id.btn_create_rank:
				allowCreate =false;
				room = new Room();
				room.setType(Room.TYPE_RANK);
				room.setCount(6);
				//room.setInnings(10);
				room.setMinStake(100);
				room.setName("Godlike");
				room.setBasicChips(10000);
				mSSID = "Godlike"+Constant.WIFI_SUFFIX;
				app.wm.startAWifiHot(mSSID,this);
				break;
		}
	}

	@Override
	public void onCreateSuccess() {
		// TODO Auto-generated method stub
		Log.i("frankchan", "热点创建成功");
		handler.sendEmptyMessage(MSG_CREATE_SERVER_SOCKET);
		
	}

	@Override
	public void OnCreateFailure(String strError) {
		// TODO Auto-generated method stub
		Log.e("frankchan", "热点创建失败原因："+strError);
		handler.sendEmptyMessage(MSG_SHOW_CREATE_HOT_ERROR);
	}

	private void gotoGameActivity(){
		app.setServer(SocketServer.newInstance());
		UserInfo info = new UserInfo();
		info.setName("Server");
		app.sp = new ServerPlayer(info, app.getServer());
		info.setId(SystemUtil.getID(getApplicationContext()));
		Intent intent = new Intent(this,GameActivity.class);
		intent.putExtra("Room", room);
		intent.putExtra("SSID", mSSID);
		startActivity(intent);
	}
	
	class SocketListener implements SocketCreateListener{

		@Override
		public void onSuccess() {
			// TODO Auto-generated method stub
			Log.i("frankchan", "创建服务器套接字成功");
			app.setServer(SocketServer.newInstance());
			handler.sendEmptyMessage(MSG_SUCCESS_JUMP_GAME);
		}

		@Override
		public void onFailure() {
			// TODO Auto-generated method stub
			Log.i("frankchan", "创建服务器套接字失败");
			handler.sendEmptyMessage(MSG_SHOW_CREATE_SOCKET_ERROR);
		}
		
	}
	
	@Override
	public void disPlayWifiScanResult(List<ScanResult> wifiList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean disPlayWifiConResult(boolean result, WifiInfo wifiInfo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void operationByType(OpretionsType type, String SSID) {
		// TODO Auto-generated method stub
		
	}

	
	
}
