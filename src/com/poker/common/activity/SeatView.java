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
import android.widget.ImageView;
import android.widget.TextView;

public class SeatView extends FrameLayout {

	private ImageView left_up,right_up,left_middle,right_middle;
	private TextView left,right;
	public SeatView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		View view = LayoutInflater.from(context).inflate(R.layout.seat_view, this);
	}
	
}
