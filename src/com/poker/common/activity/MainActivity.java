package com.poker.common.activity;

import com.poker.common.BaseApplication;
import com.poker.common.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

/**
 *
 * 类说明
 *
 * @author RinfonChen:
 * @Day 2014年8月4日 
 * @Time 下午2:44:40
 * @Declaration :
 *
 */
public class MainActivity extends Activity implements OnClickListener{

    private ImageButton sendGameBtn;
    
    private ImageButton joinGameBtn,createRoomBtn,settingBtn;
    private BaseApplication app;
    
    private View settingView;//设置页面视图
    private PopupWindow settingWin;
    private ImageView setting_close;
    private RelativeLayout voice_item_layout,shock_item_layout,help_item_layout,about_item_layout;//声音，震动，帮助，关于
    private ImageView voice_switch,shock_switch;
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
        settingBtn = (ImageButton)findViewById(R.id.mainpage_setting_btn);
        sendGameBtn.setOnClickListener(this);
        joinGameBtn.setOnClickListener(this);
        createRoomBtn.setOnClickListener(this);
        settingBtn.setOnClickListener(this);

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
        }else if(v.getId() == R.id.mainpage_setting_btn){
        	showSettingDialog();
        }else if(v.getId() == R.id.setting_close){
        	if(settingView != null && settingView.isShown())
        		settingWin.dismiss();
        }else if(v.getId() == R.id.voice_item_layout){
			
		}else if(v.getId() == R.id.shock_item_layout){
			
		}else if(v.getId() == R.id.help_item_layout){
			
		}else if(v.getId() == R.id.about_item_layout){
			
		}else if(v.getId() == R.id.shock_switch_img){
			
		}else if(v.getId() == R.id.voice_switch_img){
			
		}
        
    }
    
    private void showSettingDialog() {
		// TODO Auto-generated method stub
    	  LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
          settingView = inflater.inflate(R.layout.activity_setting, null); 
          setting_close = (ImageView)settingView.findViewById(R.id.setting_close);
          voice_item_layout = (RelativeLayout)settingView.findViewById(R.id.voice_item_layout);
          shock_item_layout = (RelativeLayout)settingView.findViewById(R.id.shock_item_layout);
          help_item_layout = (RelativeLayout)settingView.findViewById(R.id.help_item_layout);
          about_item_layout = (RelativeLayout)settingView.findViewById(R.id.about_item_layout);
          
          voice_switch = (ImageView)settingView.findViewById(R.id.voice_switch_img);
          shock_switch = (ImageView)settingView.findViewById(R.id.shock_switch_img);
          setting_close.setOnClickListener(MainActivity.this);
          voice_item_layout.setOnClickListener(this);
          shock_item_layout.setOnClickListener(this);
          help_item_layout.setOnClickListener(this);
          about_item_layout.setOnClickListener(this);
          voice_switch.setOnClickListener(this);
          shock_switch.setOnClickListener(this);
          settingWin = new PopupWindow(settingView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
                  true); 
          settingWin.setBackgroundDrawable(new BitmapDrawable());
          settingWin.setOutsideTouchable(true);
          settingWin.setFocusable(true);
          settingWin.setAnimationStyle(R.style.settingAnimation);
          settingWin.showAtLocation(MainActivity.this.settingBtn, Gravity.BOTTOM, 0, 0); 
          settingWin.update();
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
