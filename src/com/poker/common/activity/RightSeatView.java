package com.poker.common.activity;

import com.poker.common.R;
import com.poker.common.R.layout;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class RightSeatView extends FrameLayout {

	private ImageView right_up,right_middle;
	private TextView right;
	public RightSeatView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		View view = LayoutInflater.from(context).inflate(R.layout.right_seat_view, this);
		
		initView(view);
	}
	private void initView(View view) {
		// TODO Auto-generated method stub
		right_up = (ImageView)view.findViewById(R.id.right_up);
		right_middle = (ImageView)view.findViewById(R.id.right_middle);
		right = (TextView)view.findViewById(R.id.right_middle_text);
	}
	
}
