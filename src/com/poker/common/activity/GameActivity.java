package com.poker.common.activity;

import com.poker.common.R;

import android.app.Activity;
import android.os.Bundle;
import android.sax.TextElementListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

public class GameActivity extends Activity{

	private ImageButton reback,follow,add,quit;
	private TextView current_rank;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gamelayout);
		
		init();
	}
	private void init() {
		// TODO Auto-generated method stub
		reback = (ImageButton)findViewById(R.id.reback);
		follow = (ImageButton)findViewById(R.id.follow);
		add = (ImageButton)findViewById(R.id.add);
		quit = (ImageButton)findViewById(R.id.quit);
		current_rank = (TextView)findViewById(R.id.player_current_rank);
		
		reback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		follow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		quit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}	

}
