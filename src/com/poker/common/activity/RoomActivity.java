package com.poker.common.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.poker.common.BaseApplication;
import com.poker.common.Constant;
import com.poker.common.R;
import com.poker.common.adapter.RoomAdapter;
import com.poker.common.adapter.RoomAdapter.OnItemListener;
import com.poker.common.entity.ClientPlayer;
import com.poker.common.entity.UserInfo;
import com.poker.common.util.SettingHelper;
import com.poker.common.util.SystemUtil;
import com.poker.common.util.WifiUtil;
import com.poker.common.wifi.Global;
import com.poker.common.wifi.SocketClient;
import com.poker.common.wifi.SocketClient.ClientConnectListener;
import com.poker.common.wifi.WifiHotManager;
import com.poker.common.wifi.WifiHotManager.OpretionsType;
import com.poker.common.wifi.WifiHotManager.WifiBroadCastOperations;

public class RoomActivity extends AbsBaseActivity implements WifiBroadCastOperations{
	

	private final static String TAG = RoomActivity.class.getSimpleName();
	
	private ListView ltvRoom;
	
	private Button btnRefresh;
	
	private TextView txtNoRoom; 
	
	private ProgressDialog dialog,connectDialog;
	
	private List<ScanResult> wifiList;
	
	private RoomAdapter adapter;
	
	private SocketClient client;
	
	private BaseApplication app;

	private String mSSID;
	
	private final static int MSG_CONNECT_SUCCESS = 1;
	
	private final static int MSG_CONNECT_FAILURE = 2;
	
	private boolean allowEntry = true;
	
	private SettingHelper helper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_room);
		app = (BaseApplication)getApplication();
		helper = new SettingHelper(this);
		initView();
		beginScan();
	}
	
	private void initView(){
		dialog =new ProgressDialog(this);
		dialog.setMessage("正在为您扫描附近的房间");
		dialog.setTitle("德州面对面");
		ltvRoom = (ListView)findViewById(R.id.list_room);
		btnRefresh = (Button)findViewById(R.id.btn_refresh_room);
		txtNoRoom = (TextView)findViewById(R.id.txt_no_room);
		btnRefresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				beginScan();
			}
		});
		findViewById(R.id.reback).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RoomActivity.this.finish();
			}
		});
	}
	
	private class MyItemListener implements OnItemListener{

		@Override
		public void onItemClick(String SSID) {
			// TODO Auto-generated method stub
			if(null==connectDialog){
				connectDialog = new ProgressDialog(RoomActivity.this);
				connectDialog.setTitle("德州扑克");
				connectDialog.setMessage("正在为你连接该房间");
			}
			connectDialog.show();
			if(!allowEntry){
				if(mSSID.equals(SSID)){
					Toast.makeText(RoomActivity.this, "正在为您连接此房间", Toast.LENGTH_SHORT).show();
				}
				else{
					Toast.makeText(RoomActivity.this, "已经在连接房间", Toast.LENGTH_SHORT).show();
				}
				return;
			}
			if(app.isServer()){
				Toast.makeText(RoomActivity.this, "您已经创建了房间", Toast.LENGTH_SHORT).show();
				return;
			}
			mSSID = SSID;
			allowEntry = false;
			app.wm.connectToHotpot(SSID, wifiList, Global.PASSWORD);
		}
		
	} 
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what){
			case MSG_CONNECT_FAILURE:
				connectDialog.dismiss();
				Toast.makeText(RoomActivity.this, "加入房间失败,房间不存在或者已经开始", Toast.LENGTH_SHORT).show();
				app.wm.disconnectWifi(mSSID);
				allowEntry = true;
				break;
			case MSG_CONNECT_SUCCESS:
				connectDialog.dismiss();
				allowEntry =true;
				app.setClient(client);
				UserInfo info = new UserInfo();
				info.setAvatar(helper.getAvatarNumber());
				info.setName(helper.getNickname());
				info.setIp(client.getLocalAddress().toString().substring(1));
				info.setId(SystemUtil.getIMEI(getApplicationContext()));
				app.cp = new ClientPlayer(info,app.getClient());
				Toast.makeText(RoomActivity.this, "加入成功", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(RoomActivity.this,NewGameActivity.class);
				intent.putExtra("IpAddress", info.getIp());
				startActivity(intent);
				RoomActivity.this.finish();
				break;
			}
		}
		
	};
	
		// client ��ʼ��
	private void initClient(String IP) {
		client = SocketClient.newInstance("192.168.43.1", Global.WIFI_PORT);
		client.connectServer(new SocketListener());
	}
		
	@Override
	public void disPlayWifiScanResult(final List<ScanResult> wifiList) {
		// TODO Auto-generated method stub
		Log.e("frankchan", "扫描结果回调");
		refreshWifiList(filterResult(wifiList));
		app.wm.unRegisterWifiScanBroadCast();
		dialog.cancel();
	}
	@Override
	public boolean disPlayWifiConResult(boolean result, WifiInfo wifiInfo) {
		// TODO Auto-generated method stub
		Log.i("frankchan", "连接结果回调");
		app.wm.setConnectStatu(false);
		app.wm.unRegisterWifiStateBroadCast();
		app.wm.unRegisterWifiConnectBroadCast();
		initClient("");
		return false;
	}
	@Override
	public void operationByType(OpretionsType type, String SSID) {
		// TODO Auto-generated method stub
		Log.i(TAG, "into operationByType��type = " + type);
		if (type == OpretionsType.CONNECT) {
			app.wm.connectToHotpot(SSID, wifiList, Global.PASSWORD);
		} else if (type == OpretionsType.SCAN) {
			app.wm.scanWifiHot();
		}
	}

	private void beginScan(){
		if(app.wm==null){
			app.wm = WifiHotManager.getInstance(app,RoomActivity.this);
		}else{
			app.wm.changeWifiOperation(this);
		}
		app.wm.scanWifiHot();
		dialog.show();
	}
	
	class SocketListener implements ClientConnectListener{

		@Override
		public void onSuccess() {
			// TODO Auto-generated method stub
			handler.sendEmptyMessage(MSG_CONNECT_SUCCESS);
		}

		@Override
		public void onFailure(String errorInfo) {
			// TODO Auto-generated method stub
			Log.e("frankchan", "连接socket失败");
			handler.sendEmptyMessage(MSG_CONNECT_FAILURE);
		}
		
	}
	
	private void refreshWifiList(ArrayList<ScanResult> results) {
		wifiList = results;
		if(results.size()==0){
			txtNoRoom.setVisibility(View.VISIBLE);
			ltvRoom.setVisibility(View.INVISIBLE);
		}else{
			txtNoRoom.setVisibility(View.GONE);
			ltvRoom.setVisibility(View.VISIBLE);
		}
		if (null == adapter) {
			adapter = new RoomAdapter(results, this,new MyItemListener());
			ltvRoom.setAdapter(adapter);
		} else {
			adapter.clearData();
			adapter.refreshData(results);
		}
	}
	
	//过滤非本应用的WIFI
	private ArrayList<ScanResult> filterResult(List<ScanResult> target){
		ArrayList<ScanResult>result = new ArrayList<ScanResult>();
		for(ScanResult sc:target){
			if(sc.SSID.endsWith(Constant.WIFI_SUFFIX)){
				result.add(sc);
			}
		}
		return result;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Log.i(TAG, "into onDestroy() ");
		if (adapter != null) {
			adapter.clearData();
			adapter = null;
		}
		Log.i(TAG, "out onDestroy() ");
		dialog.cancel();
		super.onDestroy();
	}
}
