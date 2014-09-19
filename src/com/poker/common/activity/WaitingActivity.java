package com.poker.common.activity;

import java.io.IOException;

import com.poker.common.R;
import com.poker.common.R.layout;
import com.poker.common.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WaitingActivity extends Activity implements OnClickListener, OnLongClickListener {

	private Button operationBtn;
	private ImageView playerImg5;
	private TextView nameTxt5;
	private boolean isRoomer = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_waiting);
		
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		operationBtn = (Button)findViewById(R.id.waiting_operation_btn);
		playerImg5 = (ImageView)findViewById(R.id.waiting_player5_img);
		nameTxt5 = (TextView)findViewById(R.id.waiting_player5_name);
		operationBtn.setOnClickListener(this);
		playerImg5.setOnLongClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.waiting, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.waiting_operation_btn:
			
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
        playerImg5.setImageDrawable(null);
        Toast.makeText(getApplicationContext(),"踢走了尼玛逗比",Toast.LENGTH_SHORT).show();
        nameTxt5.setText("");
		return false;
	}

}
