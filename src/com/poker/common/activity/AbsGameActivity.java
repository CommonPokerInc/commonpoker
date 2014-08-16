
package com.poker.common.activity;

import com.poker.common.BaseApplication;
import com.poker.common.wifi.listener.MessageListener;

import android.app.Activity;

/*
 * author FrankChan
 * description 游戏通信抽象类
 * time 2014-8-16
 *
 */
public abstract class AbsGameActivity extends Activity {
	private BaseApplication app = (BaseApplication) getApplication();
	private void initMessageListener(MessageListener listener){
		if(app.isServer()){
			
		}else{
			
		}
	}
}


