package com.poker.common.activity;

import java.util.ArrayList;
import java.util.List;

import com.poker.common.BaseApplication;
import com.poker.common.Constant;
import com.poker.common.R;
import com.poker.common.adapter.RoomAdapter;
import com.poker.common.adapter.RoomAdapter.OnItemListener;
import com.poker.common.entity.ClientPlayer;
import com.poker.common.entity.UserInfo;
import com.poker.common.util.SystemUtil;
import com.poker.common.wifi.Global;
import com.poker.common.wifi.SocketClient;
import com.poker.common.wifi.SocketClient.ClientConnectListener;
import com.poker.common.wifi.WifiHotManager;
import com.poker.common.wifi.WifiHotManager.OpretionsType;
import com.poker.common.wifi.WifiHotManager.WifiBroadCastOperations;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class RoomActivity extends Activity implements WifiBroadCastOperations{
	

	private final static String TAG = RoomActivity.class.getSimpleName();
	
	private ListView ltvRoom;
	
	private Button btnRefresh;
	
	private TextView txtNoRoom; 
	
	private ProgressDialog dialog;
	
	private List<ScanResult> wifiList;
	
	private RoomAdapter adapter;
	
	private SocketClient client;
	
	private BaseApplication app;

	private String mSSID;
	
	private final static int MSG_CONNECT_SUCCESS = 1;
	
	private final static int MSG_CONNECT_FAILURE = 2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_room);
		app = (BaseApplication)getApplication();
		initView();
		beginScan();
	}
	
	private void initView(){
		dialog =new ProgressDialog(this);
		dialog.setMessage("请稍后，正在为您检测附房间");
		dialog.setTitle("德州面对面");
		ltvRoom = (ListView)findViewById(R.id.list_room);
		btnRefresh = (Button)findViewById(R.id.btn_refresh_room);
		txtNoRoom = (TextView)findViewById(R.id.txt_no_room);
		/*ltvRoom.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//作为服务器不可加入其他wifi
				if(app.isServer()){
					Toast.makeText(RoomActivity.this, "您已经建立wifi请关闭后再连接其他热点", Toast.LENGTH_SHORT).show();
					return;
				}
				ScanResult result = wifiList.get(position);
				Log.i(TAG, "into  onItemClick() SSID= " + result.SSID);
				app.wm.connectToHotpot(result.SSID, wifiList, Global.PASSWORD);
				Log.i(TAG, "out  onItemClick() SSID= " + result.SSID);
			}
		});*/
		btnRefresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				beginScan();
			}
		});
	}
	
	private class MyItemListener implements OnItemListener{

		@Override
		public void onItemClick(String SSID) {
			// TODO Auto-generated method stub
			if(app.isServer()){
				Toast.makeText(RoomActivity.this, "您已经建立wifi请关闭后再连接其他热点", Toast.LENGTH_SHORT).show();
				return;
			}
			app.wm.connectToHotpot(SSID, wifiList, Global.PASSWORD);
		}
		
	} 
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what){
			case MSG_CONNECT_FAILURE:
				Toast.makeText(RoomActivity.this, "连接失败，房间满员或者关闭了", Toast.LENGTH_SHORT).show();
				break;
			case MSG_CONNECT_SUCCESS:
				app.setClient(client);
				UserInfo info = new UserInfo();
				info.setName("client1");
				info.setId(SystemUtil.getID(getApplicationContext()));
				app.cp = new ClientPlayer(info,app.getClient());
				Toast.makeText(RoomActivity.this, "连接服务器成功", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(RoomActivity.this,GameActivity.class);
				startActivity(intent);
				break;
			}
		}
		
	};
	
		// client 初始化
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
		Log.i("frankchan", "连接热点成功");
		app.wm.setConnectStatu(false);
		app.wm.unRegisterWifiStateBroadCast();
		app.wm.unRegisterWifiConnectBroadCast();
		initClient("");
		return false;
	}
	@Override
	public void operationByType(OpretionsType type, String SSID) {
		// TODO Auto-generated method stub
		Log.i(TAG, "into operationByType！type = " + type);
		if (type == OpretionsType.CONNECT) {
			app.wm.connectToHotpot(SSID, wifiList, Global.PASSWORD);
		} else if (type == OpretionsType.SCAN) {
			app.wm.scanWifiHot();
		}
	}

	private void beginScan(){
		if(app.wm==null){
			app.wm = WifiHotManager.getInstance(app,RoomActivity.this);
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
			Log.e("frankchan", "连接服务器套接字失败");
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
			Log.i(TAG, "into 刷新wifi热点列表 adapter is null！");
			adapter = new RoomAdapter(results, this,new MyItemListener());
			ltvRoom.setAdapter(adapter);
		} else {
			Log.i(TAG, "into 刷新wifi热点列表 adapter is not null！");
			adapter.refreshData(results);
		}
		Log.i(TAG, "out 刷新wifi热点列表");
	}
	
	//过滤非本应用建立的热点列表
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
