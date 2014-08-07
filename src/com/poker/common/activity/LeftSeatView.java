package com.poker.common.activity;

import com.poker.common.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class LeftSeatView extends FrameLayout {

	private ImageView left_up,left_middle;
	private TextView left;
	private PersonView personView;
	public LeftSeatView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		View view = LayoutInflater.from(context).inflate(R.layout.left_seat_view, this);
		initView(view);
	}
	private void initView(View view) {
		// TODO Auto-generated method stub
		left_up = (ImageView)view.findViewById(R.id.left_up);
		left_middle = (ImageView)view.findViewById(R.id.left_middle);
		left = (TextView)view.findViewById(R.id.left_middle_text);

		
	}
	
}
