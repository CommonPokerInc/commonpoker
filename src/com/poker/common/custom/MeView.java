package com.poker.common.custom;

import com.poker.common.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

public class MeView extends FrameLayout{

	public MeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		View view = LayoutInflater.from(context).inflate(R.layout.me_view, this);
	}

}
