package com.poker.common.activity;

import com.poker.common.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class GameActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gamelayout);
	}	

}
