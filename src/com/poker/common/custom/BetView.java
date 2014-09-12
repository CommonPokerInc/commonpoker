package com.poker.common.custom;

import com.poker.common.R;

import android.app.Fragment;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class BetView extends FrameLayout{
	
	private Context mContext;
	private TextView bet_txt;

	public BetView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		View view = LayoutInflater.from(context).inflate(R.layout.bet_view, this);
		bet_txt = (TextView)view.findViewById(R.id.bet_txt);
	}

}
