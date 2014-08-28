package com.poker.common.adapter;

import java.util.ArrayList;
import java.util.List;

import com.poker.common.Constant;
import com.poker.common.R;


import android.content.Context;
import android.net.wifi.ScanResult;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RoomAdapter extends BaseAdapter {

	public ArrayList<ScanResult> mResults;

	private Context mContext;

	private OnItemListener listener; 
	
	public RoomAdapter(ArrayList<ScanResult> results, Context mContext,OnItemListener listener) {

		this.mResults = results;
		this.mContext = mContext;
		this.listener = listener;
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
		final String strSSID = mResults.get(position).SSID;
		nameTxt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(null!=listener){
					listener.onItemClick(strSSID);
				}
			}
		});
		Log.i("frankchan", "筛选房间名："+strSSID);
		String result = strSSID.substring(0, strSSID.lastIndexOf(Constant.WIFI_SUFFIX));
		nameTxt.setText(result);
		
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
	
	public interface OnItemListener{
		void onItemClick(String SSID);
	}
}
