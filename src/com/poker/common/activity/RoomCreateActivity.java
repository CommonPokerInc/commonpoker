package com.poker.common.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.poker.common.BaseApplication;
import com.poker.common.Constant;
import com.poker.common.R;
import com.poker.common.entity.Room;
import com.poker.common.entity.ServerPlayer;
import com.poker.common.entity.UserInfo;
import com.poker.common.util.SettingHelper;
import com.poker.common.util.SystemUtil;
import com.poker.common.util.WifiUtil;
import com.poker.common.wifi.Global;
import com.poker.common.wifi.SocketServer;
import com.poker.common.wifi.SocketServer.SocketCreateListener;
import com.poker.common.wifi.SocketServer.WifiCreateListener;
import com.poker.common.wifi.WifiHotManager;
import com.poker.common.wifi.WifiHotManager.OpretionsType;
import com.poker.common.wifi.WifiHotManager.WifiBroadCastOperations;

public class RoomCreateActivity extends AbsBaseActivity implements OnClickListener,WifiCreateListener, WifiBroadCastOperations{
	
	private BaseApplication app;
	
	private Room room;
	
	private EditText edtWifi;
	
	private RadioGroup rGroup;
	
	private SeekBar seekRounds;
	
	private TextView tipsView,txtRounds;
	
	private RadioButton rBtnLimit,rBtnRank;
	
	private String strWifi;
	
	private int[]bets = {800,8000,80000,800000};
	
	private int[]stakes ={10,80,500,4000};
	
	private int mBet = bets[0];
	
	private int mStakes = stakes[0];
	
	private int mRounds = 5;
	
	private int mType = Room.TYPE_LIMIT;
	
	private String mSSID;
	
	private boolean allowCreate = true;
	
	private SettingHelper helper;
	
	private final static int MAX_COUNT = 6;
	
	private final static String MOBILE_NAME = "=_="+Build.MODEL; 
	
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
		findViewById(R.id.btn_create_room).setOnClickListener(this);
		findViewById(R.id.reback).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RoomCreateActivity.this.finish();
			}
		});
		helper = new SettingHelper(this);
		rBtnLimit = (RadioButton) findViewById(R.id.radio_limit_rounds);
		rBtnRank = (RadioButton) findViewById(R.id.radio_rank_rounds);
		rBtnRank.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				rBtnLimit.setChecked(!arg1);
				if(arg1){
					tipsView.setVisibility(View.GONE);
				}
			}
		});
		rBtnLimit.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				rBtnRank.setChecked(!arg1);
				if(arg1&tipsView.getVisibility()==View.GONE){
					tipsView.setVisibility(View.VISIBLE);
				}
			}
		});
		rGroup = (RadioGroup) findViewById(R.id.radio_group);
		rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch(checkedId){
				case R.id.radio_diors:
					mBet = bets[0];
					mStakes = stakes[0];
					break;
				case R.id.radio_normal:
					mBet = bets[1];
					mStakes = stakes[1];
					break;
				case R.id.radio_rich:
					mBet = bets[2];
					mStakes = stakes[2];
					break;
				case R.id.radio_millionaire:
					mBet = bets[3];
					mStakes = stakes[3];
					break;
				}
			}
		});
		edtWifi = (EditText) findViewById(R.id.edit_room_name);
		seekRounds = (SeekBar) findViewById(R.id.seek_rounds);
		tipsView = (TextView) findViewById(R.id.txt_tips);
		txtRounds = (TextView)findViewById(R.id.txt_rank_rounds);

		txtRounds.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rBtnRank.setChecked(true);
			}
		});
		seekRounds.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				if(!rBtnLimit.isChecked()){
					rBtnLimit.setChecked(true);
					tipsView.setVisibility(View.VISIBLE);
				}
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				if(fromUser){
					tipsView.setText(String.valueOf(progress==0?1:progress));
				}
			}
		});
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
						Log.i("frankchan", "开始创建服务器socket");
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
			Toast.makeText(RoomCreateActivity.this, "正在创建房间，请勿重复点击", Toast.LENGTH_SHORT).show();
			return;
		}
		switch(v.getId()){
			case R.id.btn_create_limit:
				allowCreate =false;
				room = new Room();
				room.setType(Room.TYPE_LIMIT);
				room.setCount(MAX_COUNT);
				room.setInnings(1);
				room.setMinStake(100);
				room.setName("Godlike");
				room.setBasicChips(10000);
				mSSID = "Godlike"+MOBILE_NAME+Constant.WIFI_SUFFIX;
				app.wm.startAWifiHot(mSSID,this);
				break;
			case R.id.btn_create_rank:
				allowCreate =false;
				room = new Room();
				room.setType(Room.TYPE_RANK);
				room.setCount(MAX_COUNT);
				room.setInnings(-1);
				room.setMinStake(100);
				room.setName("Godlike");
				room.setBasicChips(10000);
				mSSID = "Godlike"+MOBILE_NAME+Constant.WIFI_SUFFIX;
				app.wm.startAWifiHot(mSSID,this);
				break;
			case R.id.btn_create_room:
				if(edtWifi.getText().toString().trim().equals("")){
					Toast.makeText(this, "Wifi名不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				if(seekRounds.getProgress()==0){
					mRounds = 1;
				}
				allowCreate = false;
				room = new Room();
				room.setType(mType);
				room.setCount(MAX_COUNT);
				room.setInnings(mType==Room.TYPE_RANK?-1:mRounds);
				room.setMinStake(mStakes);
				room.setBasicChips(mBet);
				room.setName(edtWifi.getText().toString().trim());
				strWifi = edtWifi.getText().toString();
				mSSID = strWifi+MOBILE_NAME+Constant.WIFI_SUFFIX;
				app.wm.startAWifiHot(mSSID,this);
				break;
		}
	}

	@Override
	public void onCreateSuccess() {
		// TODO Auto-generated method stub
		Log.i("frankchan", "创建热点成功");
		handler.sendEmptyMessage(MSG_CREATE_SERVER_SOCKET);
		
	}

	@Override
	public void OnCreateFailure(String strError) {
		// TODO Auto-generated method stub
		Log.e("frankchan", "创建热点失败："+strError);
		handler.sendEmptyMessage(MSG_SHOW_CREATE_HOT_ERROR);
	}

	private void gotoGameActivity(){
		app.setServer(SocketServer.newInstance());
		UserInfo info = new UserInfo();
		info.setAvatar(helper.getAvatarNumber());
		info.setName(helper.getNickname());
		info.setIp("192.168.43.1");
		info.setId(SystemUtil.getIMEI(getApplicationContext()));
		app.sp = new ServerPlayer(info, app.getServer());
		Intent intent = new Intent(this,NewGameActivity.class);
		intent.putExtra("Room", room);
		intent.putExtra("SSID", mSSID);
		startActivity(intent);
	}
	
	class SocketListener implements SocketCreateListener{

		@Override
		public void onSuccess() {
			// TODO Auto-generated method stub
			Log.i("frankchan", "创建Socket成功");
			app.setServer(SocketServer.newInstance());
			handler.sendEmptyMessage(MSG_SUCCESS_JUMP_GAME);
		}

		@Override
		public void onFailure() {
			// TODO Auto-generated method stub
			Log.i("frankchan", "创建socket失败");
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
