package com.poker.common.activity;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.poker.common.R;

public class PersonView extends FrameLayout{

	private TextView title;
	
	private ImageButton personImg;
	
	private TextView personMoney;
	
	public PersonView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		 View v = LayoutInflater.from(context).inflate(R.layout.person_view, this);
		 title = (TextView) v.findViewById(R.id.person_title);
		 personMoney = (TextView)v.findViewById(R.id.person_current_money);
		 personImg = (ImageButton) v.findViewById(R.id.person_image);
		 personImg.setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                Log.i("Rinfon", "person Image Click");
	            }
	        });
	}
	
	public void setPersonImgClickListen(OnClickListener l){
		personImg.setOnClickListener(l);
	}

}
