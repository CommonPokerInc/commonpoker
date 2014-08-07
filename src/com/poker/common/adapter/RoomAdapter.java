package com.poker.common.adapter;

import java.util.ArrayList;
import java.util.List;

import com.poker.common.R;


import android.content.Context;
import android.net.wifi.ScanResult;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RoomAdapter extends BaseAdapter {

	public ArrayList<ScanResult> mResults;

	private Context mContext;

	public RoomAdapter(ArrayList<ScanResult> results, Context mContext) {

		this.mResults = results;
		this.mContext = mContext;
		System.out.println("into  WifiHotAdapter results =" + this.mResults);

	}

	@Override
	public int getCount() {
		System.out.println("into  WifiHotAdapter getCount() results size=" + mResults.size());
		return mResults.size();
	}

	@Override
	public Object getItem(int position) {
		System.out.println("into  WifiHotAdapter getItem  Object=" + mResults.get(position));
		return mResults.get(position);
	}

	@Override
	public long getItemId(int position) {
		System.out.println("into  WifiHotAdapter getItemId  position=" + position);
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		System.out.println("into getView()");
		TextView nameTxt = null;
		TextView levelTxt = null;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.adapter_room_item, null);
		}
		nameTxt = (TextView) convertView.findViewById(R.id.hotName);
		levelTxt = (TextView) convertView.findViewById(R.id.hotLevel);
		String strSSID = mResults.get(position).SSID;
		Log.i("frankchan", "筛选房间名："+strSSID);
		nameTxt.setText(strSSID);
		levelTxt.setText("Level :" + mResults.get(position).level);
		
		return convertView;
	}

	public void refreshData(ArrayList<ScanResult> results) {
		this.mResults = results;
		this.notifyDataSetChanged();
	}

	public void clearData() {

		if (mResults != null && mResults.size() > 0) {
			mResults.clear();
			mResults = null;
		}
	}
	
}
