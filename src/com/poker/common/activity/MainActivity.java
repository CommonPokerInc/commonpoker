package com.poker.common.activity;

import com.poker.common.BaseApplication;
import com.poker.common.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;

/**
 *
 * ��˵��
 *
 * @author RinfonChen:
 * @Day 2014��8��4�� 
 * @Time ����2:44:40
 * @Declaration :
 *
 */
public class MainActivity extends Activity implements OnClickListener{

    private ImageButton sendGameBtn;
    
    private ImageButton joinGameBtn,createRoomBtn;
    private BaseApplication app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
        app = (BaseApplication) getApplication();
        init();
    }
    
    public void init(){
        sendGameBtn = (ImageButton)findViewById(R.id.send_game_btn);
        joinGameBtn = (ImageButton)findViewById(R.id.join_home_btn);
        createRoomBtn = (ImageButton)findViewById(R.id.create_home_btn);
        sendGameBtn.setOnClickListener(this);
        joinGameBtn.setOnClickListener(this);
        createRoomBtn.setOnClickListener(this);
    }

    
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if(v.getId() == R.id.send_game_btn){
            Intent it = new Intent(MainActivity.this,SendGameActivity.class);
            startActivity(it);
        }else if(v.getId() == R.id.join_home_btn){
        	if(app.isConnected&&app.isServer()){
        		app.getServer().clearServer();
        		app.isConnected = false;
        	}else if(app.isConnected&&!app.isServer()){
        		app.getClient().clearClient();
        		app.isConnected =false;
        	}
        	Intent it = new Intent(MainActivity.this,RoomActivity.class);
            startActivity(it);
        }else if(v.getId()==R.id.create_home_btn){
        	Intent it = new Intent(MainActivity.this,RoomCreateActivity.class);
            startActivity(it);
        }
        
    }
    
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
}
