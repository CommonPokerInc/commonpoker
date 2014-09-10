package com.poker.common.activity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
/**
 * 
 * @author Administrator
 * 原始基类，屏幕常亮横屏不可切换
 */
public abstract class AbsBaseActivity extends Activity {

	private PowerManager powerManager = null;
    private WakeLock wakeLock = null;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		powerManager = (PowerManager)this.getSystemService(Context.POWER_SERVICE);
        wakeLock = this.powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "My Lock");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		wakeLock.release();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		 wakeLock.acquire();
	}


    
    @Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		switch (newConfig.orientation)   
        {   
           case (Configuration.ORIENTATION_LANDSCAPE):  
                 break;   
           case (Configuration.ORIENTATION_PORTRAIT):   
                 break;
        }
	}
	
}
