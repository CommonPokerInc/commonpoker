package com.poker.common.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.poker.common.R;

public class zzkTestActivity extends Activity{

	
	private Button testToastButton;
	private LayoutInflater inflater;
	private View view;
	private Toast toast;
	private int width,height;
	private RelativeLayout player_action_view;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        // TODO Auto-generated method stub
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.zzktest);
	        getScreenSize();
	        
	        testToastButton = (Button)findViewById(R.id.toast);
	        player_action_view = (RelativeLayout)findViewById(R.id.player_action_view);
	        testToastButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showToast();
				}
			});
	        
	 }
	 
	private void getScreenSize(){
		 WindowManager wm = this.getWindowManager();
	     this.width = wm.getDefaultDisplay().getWidth();
	     this.height = wm.getDefaultDisplay().getHeight();
	     Log.v("zkzhou","屏幕高度："+""+height+";"+"屏幕宽度："+""+width+""+"布局高度"+""+player_action_view.getHeight());
	}
	
	protected void showToast() {
		// TODO Auto-generated method stub
		inflater = LayoutInflater.from(this);
		view = inflater.inflate(R.layout.player_action_view, null);
		
		toast = new Toast(this);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setGravity(Gravity.BOTTOM, 0,(this.height-player_action_view.getHeight())/2);
		toast.setView(view);
		toast.show();
	}
}
