﻿
package com.poker.common.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.poker.common.BaseApplication;
import com.poker.common.R;
import com.poker.common.util.SettingHelper;

/**
 * ��˵��
 * 
 * @author RinfonChen:
 * @Day 2014��8��4��
 * @Time ����2:44:40
 * @Declaration :
 */
public class MainActivity extends AbsBaseActivity implements OnClickListener {

    private ImageButton sendGameBtn;

    private ImageButton joinGameBtn, createRoomBtn, settingBtn, meBtn;

    private BaseApplication app;

    private View settingView;

    private View meView,firstView;

    private PopupWindow settingWin, meWin,firstWin;

    private ImageView setting_close, me_close;

    private RelativeLayout voice_item_layout, shock_item_layout, help_item_layout,
            about_item_layout;

    private ImageView confirm_edit_btn;

    private ImageView voice_switch, shock_switch,first_head_img;

    private ImageButton firstLeft,firstRight;
    
    private SettingHelper settingHelper;

    private EditText edtFirstName;
    
    private Button btnGo;
    
    private boolean voice_switch_state = false, shock_switch_state = false;//

    private boolean editable = false;

    private TextView edit_or_confirm;

    private int[] head_img = {R.drawable.img_player_picture1,
    		R.drawable.img_player_picture2,R.drawable.img_player_picture3,
    		R.drawable.img_player_picture4,R.drawable.img_player_picture5,
    		R.drawable.img_player_picture6,R.drawable.img_player_picture7,
    		R.drawable.img_player_picture8};

    private ImageView me_right, me_left, me_head_img;

    private int whichImg;

    private RelativeLayout setting_dialog_layout, me_dialog_layout;

	private int resId =-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        app = (BaseApplication) getApplication();
        init();
        if(Environment.getExternalStorageState()
        		.equals(android.os.Environment.MEDIA_MOUNTED)){
        	new Thread(copyRunnable).start();
        }
    }
    

    public void init() {
        sendGameBtn = (ImageButton) findViewById(R.id.send_game_btn);
        joinGameBtn = (ImageButton) findViewById(R.id.join_home_btn);
        createRoomBtn = (ImageButton) findViewById(R.id.create_home_btn);
        settingBtn = (ImageButton) findViewById(R.id.mainpage_setting_btn);
        meBtn = (ImageButton) findViewById(R.id.mainpage_me_btn);
        sendGameBtn.setOnClickListener(this);
        joinGameBtn.setOnClickListener(this);
        createRoomBtn.setOnClickListener(this);
        settingBtn.setOnClickListener(this);
        meBtn.setOnClickListener(this);

        settingHelper = new SettingHelper(getApplicationContext());
    }

    private boolean userExists(){
    	return null == settingHelper.getNickname();
    }
    
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.getId() == R.id.send_game_btn) {
            Intent it = new Intent(MainActivity.this, SendGameActivity.class);
            startActivity(it);
        } else if (v.getId() == R.id.join_home_btn) {
        	if(userExists()){
        		showFirstDialog();
        		return;
        	}
            if (app.isConnected && app.isServer()) {
                app.getServer().clearServer();
                app.isConnected = false;
            } else if (app.isConnected && !app.isServer()) {
                app.getClient().clearClient();
                app.isConnected = false;
            }
            Intent it = new Intent(MainActivity.this, RoomActivity.class);
            startActivity(it);
        } else if (v.getId() == R.id.create_home_btn) {
        	if(userExists()){
        		showFirstDialog();
        		return;
        	}
            Intent it = new Intent(MainActivity.this, RoomCreateActivity.class);
            startActivity(it);
        } else if (v.getId() == R.id.mainpage_setting_btn) {
            showSettingDialog();
        } else if (v.getId() == R.id.mainpage_me_btn) {
        	if(userExists()){
        		showFirstDialog();
        		return;
        	}
            showMeDialog();
        } else if (v.getId() == R.id.setting_close) {
            if (settingView != null && settingView.isShown())
                settingWin.dismiss();
        } else if (v.getId() == R.id.voice_item_layout) {
            Intent intent = new Intent(this, RankActivity.class);
            startActivity(intent);

        } else if (v.getId() == R.id.shock_item_layout) {

            Intent intent = new Intent(this, GoodCardsActivity.class);
            startActivity(intent);

        } else if (v.getId() == R.id.help_item_layout) {
            
            Intent intent = new Intent(this, PunishActivity.class);
            startActivity(intent);

        } else if (v.getId() == R.id.about_item_layout) {

        } else if (v.getId() == R.id.shock_switch_img) {
            shock_switch_state = !settingHelper.getVibrationStatus();
            shock_switch.setImageResource(shock_switch_state ? R.drawable.setting_switch_on
                    : R.drawable.setting_switch_off);
            String message = shock_switch_state ? "开启震动" : "关闭震动";
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            settingHelper.setVibrationStatus(shock_switch_state);
        } else if (v.getId() == R.id.voice_switch_img) {
            voice_switch_state = !settingHelper.getVoiceStatus();
            voice_switch.setImageResource(voice_switch_state ? R.drawable.setting_switch_on
                    : R.drawable.setting_switch_off);
            String message = voice_switch_state ? "开启音效" : "关闭音效";
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            settingHelper.setVoiceStatus(voice_switch_state);
        } else if (v.getId() == R.id.me_close) {
            if (meView != null && meView.isShown())
                meWin.dismiss();
        } else if (v.getId() == R.id.confirm_edit_btn) {
            editable = !editable;
            confirm_edit_btn.setImageResource(editable ? R.drawable.img_confirm_btn
                    : R.drawable.img_edit_btn);
            edit_or_confirm.setText(editable ? R.string.confirm : R.string.edit);

        } else if (v.getId() == R.id.left) {
            whichImg = settingHelper.getAvatarNumber();
            if (whichImg <= 0) {
                me_head_img.setImageResource(head_img[whichImg]);
            } else {
                whichImg--;
                me_head_img.setImageResource(head_img[whichImg]);
            }
            settingHelper.setAvatarNumber(whichImg);
        } else if (v.getId() == R.id.right) {
            whichImg = settingHelper.getAvatarNumber();
            if (whichImg >= 7) {
                me_head_img.setImageResource(head_img[whichImg]);
            } else {
                whichImg++;
                me_head_img.setImageResource(head_img[whichImg]);
            }
            settingHelper.setAvatarNumber(whichImg);
        }else if(v.getId()==R.id.btn_change_left){
			resId = (resId-1)%head_img.length;
			first_head_img.setImageResource(head_img[Math.abs(resId)]);
		}else if(v.getId()==R.id.btn_change_right){
			resId = (resId+1)%head_img.length;
			first_head_img.setImageResource(head_img[resId]);
		}else if(v.getId()==R.id.btn_go){
			String strName = edtFirstName.getText().toString();
			if(strName.equals("")){
				Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
				return;
			}else{
				settingHelper.setNickname(strName);
				settingHelper.setAvatarNumber(Math.abs(resId));
				Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
				firstWin.dismiss();
			}
		}
        
    }

    private void showMeDialog() {
        // TODO Auto-generated method stub
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        meView = inflater.inflate(R.layout.activity_me, null);
        me_dialog_layout = (RelativeLayout) meView.findViewById(R.id.me_dialog_layout);
        me_close = (ImageView) meView.findViewById(R.id.me_close);
        confirm_edit_btn = (ImageView) meView.findViewById(R.id.confirm_edit_btn);
        edit_or_confirm = (TextView) meView.findViewById(R.id.confirm_edit_txt);
        me_right = (ImageView) meView.findViewById(R.id.right);
        me_left = (ImageView) meView.findViewById(R.id.left);
        me_head_img = (ImageView) meView.findViewById(R.id.head_img);
        me_close.setOnClickListener(this);
        confirm_edit_btn.setOnClickListener(this);
        me_left.setOnClickListener(this);
        me_right.setOnClickListener(this);
        me_dialog_layout.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (meView != null && meView.isShown())
                        meWin.dismiss();
                }
                return true;
            }
        });

        meWin = new PopupWindow(meView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
        meWin.setBackgroundDrawable(new BitmapDrawable());
        meWin.setFocusable(true);
        meWin.setOutsideTouchable(true);
        meWin.setAnimationStyle(R.style.settingAnimation);
        meWin.showAtLocation(MainActivity.this.meBtn, Gravity.BOTTOM, 0, 0);
        meWin.update();
        me_head_img.setImageResource(head_img[settingHelper.getAvatarNumber()]);
    }

    private void showFirstDialog(){
    	if(settingHelper.getNickname()!=null){
    		return;
    	}
    	if(null==firstWin){
    		LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
    		firstView = inflater.inflate(R.layout.activity_first, null);
    		firstLeft = (ImageButton) firstView.findViewById(R.id.btn_change_left);
    		firstRight = (ImageButton) firstView.findViewById(R.id.btn_change_right);
    		first_head_img = (ImageView) firstView.findViewById(R.id.img_avatar);
    		edtFirstName = (EditText) firstView.findViewById(R.id.edt_name);
    		btnGo = (Button) firstView.findViewById(R.id.btn_go);
    		firstLeft.setOnClickListener(this);
    		firstRight.setOnClickListener(this);
    		btnGo.setOnClickListener(this);
    		firstWin = new PopupWindow(firstView,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,true);
    		firstWin.setBackgroundDrawable(new BitmapDrawable());
    		firstWin.setFocusable(true);
    		firstWin.setOutsideTouchable(false);
    		firstWin.setAnimationStyle(R.style.settingAnimation);
    	}
		resId  = settingHelper.getAvatarNumber();
		first_head_img.setImageResource(head_img[resId]);
    	firstWin.showAtLocation(meBtn,Gravity.BOTTOM,0,0);
		firstWin.update();
    }
    
    
	private void showSettingDialog() {
		// TODO Auto-generated method stub
    	  LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
          settingView = inflater.inflate(R.layout.activity_setting, null);
          setting_dialog_layout = (RelativeLayout)settingView.findViewById(R.id.setting_dialog_layout);
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
          setting_dialog_layout.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if(settingView != null && settingView.isShown())
						settingWin.dismiss();
				}
				return true;
			}
		});
          settingWin = new PopupWindow(settingView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
                  true); 
          settingWin.setBackgroundDrawable(new BitmapDrawable());
          settingWin.setOutsideTouchable(true);
          settingWin.setFocusable(true);
          settingWin.setAnimationStyle(R.style.settingAnimation);
          settingWin.showAtLocation(MainActivity.this.settingBtn, Gravity.BOTTOM, 0, 0); 
          settingWin.update();
          
          initSetting();
	}
	private Runnable copyRunnable = new Runnable() {
		
		@Override
		public void run() {
			String strDir = Environment.getExternalStorageDirectory().getPath()
					+"/.poker";
			String strFile = strDir +"/commonpoker.apk";
			File target =new File(strFile);
			
			if(settingHelper.isFirstStart()||(!settingHelper.isFirstStart()&&!target.exists())){
				settingHelper.setFirstStart(false);
				List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
				String path = "";
				for(PackageInfo info:packs){
					if(info.applicationInfo.packageName.contains("com.poker.common")){
						path = info.applicationInfo.publicSourceDir;
						break;
					}
				}
				File file = new File(path);
				new File(strDir).mkdirs();
				if(file.exists()&&!target.exists()){
					copyFile(file.getPath(), target.getPath());
				}
			}
		}
	};
	
	 public void copyFile(String oldPath, String newPath) { 
	       try { 
	           int bytesum = 0; 
	           int byteread = 0; 
	           File oldfile = new File(oldPath); 
	           if (oldfile.exists()) { //�ļ�����ʱ 
	               InputStream inStream = new FileInputStream(oldPath); //����ԭ�ļ� 
	               FileOutputStream fs = new FileOutputStream(newPath); 
	               byte[] buffer = new byte[1444]; 
	               while ( (byteread = inStream.read(buffer)) != -1) { 
	                   bytesum += byteread; //�ֽ��� �ļ���С 
	                   System.out.println(bytesum); 
	                   fs.write(buffer, 0, byteread); 
	               } 
	               inStream.close(); 
	           } 
	           settingHelper.setCopied(true);
	       } 
	       catch (Exception e) { 
	           System.out.println("copy single file faliure"); 
	           e.printStackTrace(); 
	           settingHelper.setCopied(false);
	       } 

	   } 

    private void initSetting() {
        // TODO Auto-generated method stub

        voice_switch.setImageResource(settingHelper.getVoiceStatus() ? R.drawable.setting_switch_on
                : R.drawable.setting_switch_off);
        shock_switch
                .setImageResource(settingHelper.getVibrationStatus() ? R.drawable.setting_switch_on
                        : R.drawable.setting_switch_off);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        System.exit(0);
        super.onDestroy();
    }
}
