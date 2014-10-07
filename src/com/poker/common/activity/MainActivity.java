
package com.poker.common.activity;

import com.poker.common.BaseApplication;
import com.poker.common.R;
import com.poker.common.entity.SoundPlayer;
import com.poker.common.util.AnimationProvider;
import com.poker.common.util.SettingHelper;
import com.poker.common.util.SystemUtil;
import com.poker.common.util.UserUtil;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

/**
 * ��˵��
 * 
 * @author RinfonChen:
 * @Day 2014��8��4��
 * @Time ����2:44:40
 * @Declaration :
 */
public class MainActivity extends AbsBaseActivity implements OnClickListener {

	private View mView,titleView,girlView;
	
    private ImageButton sendGameBtn;

    private ImageButton settingBtn, meBtn;
    
    private Button createRoomBtn,joinGameBtn;

    private BaseApplication app;

    private View settingView,aboutView,helpView;
    
    private ScrollView playScrollView,allocateScrollView,introduceScrollView,raidersScrollView;

    private View meView,firstView;

    private PopupWindow settingWin, meWin,firstWin;

    private ImageView setting_bg,me_bg,first_bg;

    private RelativeLayout voice_item_layout, shock_item_layout, help_item_layout,
            about_item_layout;
    
    private LinearLayout setting_items_layout;

    private ImageView confirm_edit_btn;

    private ImageView voice_switch, shock_switch,first_head_img;

    private ImageButton firstLeft,firstRight,me_right, me_left;
    
    private SettingHelper settingHelper;

    private EditText edtFirstName,nickname_edt;
    
    private TextView nickname_txt,help_play_txt,help_allocate_txt,help_introduce_txt,help_raiders_txt;
    
    private Button btnGo;
    
    private boolean voice_switch_state = false, shock_switch_state = false;//

    private boolean editable = false;

    private TextView edit_or_confirm;

    private View formerActionView;

    private ImageView me_head_img;
    
    private RelativeLayout setting_dialog_layout, me_dialog_layout,first_dialog_layout;

	private int resId =-1;
	
	private float x,y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        SoundPlayer.init(getApplicationContext()); 
        setContentView(R.layout.activity_main);
        app = (BaseApplication) getApplication();
        init();
        if(Environment.getExternalStorageState()
        		.equals(android.os.Environment.MEDIA_MOUNTED)){
        	new Thread(copyRunnable).start();
        }
        startAnimation();
//        SystemUtil.initSounds(getApplicationContext());
        
    }
    
    private void startAnimation(){
    	handler.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mView.startAnimation(AnimationProvider.
						getAlphaAnimation(AnimationProvider.TYPE_INTERPLATOR_ACCELERATE_DECELERATE, 1000));
			}
		});
    	handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				meBtn.startAnimation(AnimationProvider.
						getTranslateAnimation(AnimationProvider.TYPE_ANIMATION_BOTTOM, 
								1,AnimationProvider.TYPE_INTERPLATOR_ACCELERATE_DECELERATE , 200, true));
				settingBtn.startAnimation(AnimationProvider.
						getTranslateAnimation(AnimationProvider.TYPE_ANIMATION_BOTTOM, 
								1,AnimationProvider.TYPE_INTERPLATOR_ACCELERATE_DECELERATE , 200, true));
				girlView.startAnimation(AnimationProvider.
						getTranslateAnimation(AnimationProvider.TYPE_ANIMATION_RIGHT, 
								1,AnimationProvider.TYPE_INTERPLATOR_ACCELERATE_DECELERATE , 800, true));
			}
		}, 1000);
    	handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				sendGameBtn.startAnimation(AnimationProvider.
						getAlphaAnimation(AnimationProvider.TYPE_INTERPLATOR_ACCELERATE_DECELERATE, 1000));
				titleView.startAnimation(AnimationProvider.
						getAlphaAnimation(AnimationProvider.TYPE_INTERPLATOR_ACCELERATE_DECELERATE, 1000));
				createRoomBtn.startAnimation(AnimationProvider.
						getAlphaAnimation(AnimationProvider.TYPE_INTERPLATOR_ACCELERATE_DECELERATE, 1000));
				joinGameBtn.startAnimation(AnimationProvider.
						getAlphaAnimation(AnimationProvider.TYPE_INTERPLATOR_ACCELERATE_DECELERATE, 1000));
			}
		}, 1800);
    }
    
    private Handler handler =new Handler();
    
    public void init() {
    	mView = findViewById(R.id.game_layout);
    	titleView = findViewById(R.id.mainpage_me_title);
    	girlView = findViewById(R.id.mainpage_me_girl);
        sendGameBtn = (ImageButton) findViewById(R.id.send_game_btn);
        joinGameBtn = (Button) findViewById(R.id.join_home_btn);
        createRoomBtn = (Button) findViewById(R.id.create_home_btn);
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
    
    private boolean checkAndShowFirstView(View view,boolean shouldSave){
    	if(userExists()){
    		showFirstDialog();
    		if(shouldSave){
    			formerActionView = view;
    		}
    		return true;
    	}else{
    		return false;
    	}
    }
    
	@Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
    	SoundPlayer.playSound(R.raw.backgroudmusic);
        if (v.getId() == R.id.send_game_btn) {
            Intent it = new Intent(MainActivity.this, SendGameActivity.class);
            startActivity(it);
        } else if (v.getId() == R.id.join_home_btn) {
        	if(checkAndShowFirstView(v, true))return;
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
        	if(checkAndShowFirstView(v, true))return;
            Intent it = new Intent(MainActivity.this, RoomCreateActivity.class);
            startActivity(it);
        } else if (v.getId() == R.id.mainpage_setting_btn) {
            showSettingDialog();
        } else if (v.getId() == R.id.mainpage_me_btn) {
        	if(checkAndShowFirstView(v, true))return;
            showMeDialog();
        } else if (v.getId() == R.id.voice_item_layout) {
//            Intent intent = new Intent(this, RankActivity.class);
//            startActivity(intent);
            voice_switch_state = !settingHelper.getVoiceStatus();
            voice_switch.setImageResource(voice_switch_state ? R.drawable.setting_switch_on
                    : R.drawable.setting_switch_off);
            String message = voice_switch_state ? "开启音效" : "关闭音效";
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            settingHelper.setVoiceStatus(voice_switch_state);

        } else if (v.getId() == R.id.shock_item_layout) {

//            Intent intent = new Intent(this, GoodCardsActivity.class);
//            startActivity(intent);
            shock_switch_state = !settingHelper.getVibrationStatus();
            shock_switch.setImageResource(shock_switch_state ? R.drawable.setting_switch_on
                    : R.drawable.setting_switch_off);
            String message = shock_switch_state ? "开启震动" : "关闭震动";
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            settingHelper.setVibrationStatus(shock_switch_state);

        } else if (v.getId() == R.id.help_item_layout) {
            helpView.setVisibility(View.VISIBLE);
            setScrollVisibility(true,false,false,false);
            setting_items_layout.setVisibility(View.INVISIBLE);
//            Intent intent = new Intent(this, PunishActivity.class);
//            startActivity(intent);

        } else if (v.getId() == R.id.about_item_layout) {
        	
            aboutView.setVisibility(View.VISIBLE);
            setting_items_layout.setVisibility(View.INVISIBLE);
//            Intent intent = new Intent(this,WaitingActivity.class);
//            startActivity(intent);
        } else if (v.getId() == R.id.confirm_edit_btn) {
        	if(nickname_edt.getText().toString().trim().equals("")){
            	Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
            	return;
        	}
            editable = !editable;
            if(!editable){
            	me_right.setVisibility(View.INVISIBLE);
                me_left.setVisibility(View.INVISIBLE);
            	Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
            	settingHelper.setAvatarNumber(resId);
            	settingHelper.setNickname(nickname_edt.getText().toString());
            	confirm_edit_btn.setImageResource(R.drawable.img_edit_btn);
            	edit_or_confirm.setText(R.string.edit);
            	nickname_txt.setText(nickname_edt.getText().toString().trim());
            	nickname_edt.setVisibility(View.GONE);
            	nickname_txt.setVisibility(View.VISIBLE);
            }else{
            	me_right.setVisibility(View.VISIBLE);
                me_left.setVisibility(View.VISIBLE);
            	confirm_edit_btn.setImageResource(R.drawable.img_confirm_btn);
                edit_or_confirm.setText(R.string.confirm);
                nickname_txt.setVisibility(View.GONE);
                nickname_edt.setVisibility(View.VISIBLE);
            }
            nickname_edt.setEnabled(editable);
            
        } else if (v.getId() == R.id.left) {
            resId = (resId-1)%UserUtil.head_img.length;
            me_head_img.setImageResource(UserUtil.head_img[Math.abs(resId)]);
        } else if (v.getId() == R.id.right) {
        	resId = (resId+1)%UserUtil.head_img.length;
        	me_head_img.setImageResource(UserUtil.head_img[Math.abs(resId)]);
        }else if(v.getId()==R.id.btn_change_left){
			resId = (resId-1)%UserUtil.head_img.length;
			first_head_img.setImageResource(UserUtil.head_img[Math.abs(resId)]);
		}else if(v.getId()==R.id.btn_change_right){
			resId = (resId+1)%UserUtil.head_img.length;
			first_head_img.setImageResource(UserUtil.head_img[Math.abs(resId)]);
		}else if(v.getId()==R.id.btn_go){
			String strName = edtFirstName.getText().toString();
			if(strName.equals("")){
				Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
				return;
			}else{
				settingHelper.setNickname(strName);
				settingHelper.setAvatarNumber(Math.abs(resId));
				Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
				if(null!=formerActionView){
					onClick(formerActionView);
					formerActionView = null;
				}
				firstWin.dismiss();
			}
		}else if(v.getId() == R.id.help_play_txt){
		    setScrollVisibility(true,false,false,false);
		}else if(v.getId() == R.id.help_allocate_txt){
		    setScrollVisibility(false,true,false,false);
		}else if(v.getId() == R.id.help_introduce_txt){
		    setScrollVisibility(false,false,true,false);
		}else if(v.getId() == R.id.help_raiders_txt){
		    setScrollVisibility(false,false,false,true);
		}
        
    }

    @SuppressLint("ResourceAsColor")
    private void setScrollVisibility(boolean play, boolean allocate, boolean introduce, boolean raiders) {
        // TODO Auto-generated method stub
        playScrollView.setVisibility(play?View.VISIBLE:View.GONE);
        help_play_txt.setTextColor(play?this.getResources().getColor(R.color.help_color_clicked):this.getResources().getColor(R.color.about_color));
        allocateScrollView.setVisibility(allocate?View.VISIBLE:View.GONE);
        help_allocate_txt.setTextColor(allocate?this.getResources().getColor(R.color.help_color_clicked):this.getResources().getColor(R.color.about_color));
        raidersScrollView.setVisibility(raiders?View.VISIBLE:View.GONE);
        help_raiders_txt.setTextColor(raiders?this.getResources().getColor(R.color.help_color_clicked):this.getResources().getColor(R.color.about_color));
        introduceScrollView.setVisibility(introduce?View.VISIBLE:View.GONE);
        help_introduce_txt.setTextColor(introduce?this.getResources().getColor(R.color.help_color_clicked):this.getResources().getColor(R.color.about_color));
    }

    private void showMeDialog() {
        // TODO Auto-generated method stub
    	if(null==meWin){
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        meView = inflater.inflate(R.layout.activity_me, null);
        me_dialog_layout = (RelativeLayout) meView.findViewById(R.id.me_dialog_layout);
        confirm_edit_btn = (ImageView) meView.findViewById(R.id.confirm_edit_btn);
        edit_or_confirm = (TextView) meView.findViewById(R.id.confirm_edit_txt);
        me_right = (ImageButton) meView.findViewById(R.id.right);
        me_left = (ImageButton) meView.findViewById(R.id.left);
        me_head_img = (ImageView) meView.findViewById(R.id.head_img);
        nickname_edt = (EditText) meView.findViewById(R.id.nickname_edt);
        nickname_txt = (TextView) meView.findViewById(R.id.nickname_txt);
        me_bg = (ImageView)meView.findViewById(R.id.me_bg);
        confirm_edit_btn.setOnClickListener(this);
        me_left.setOnClickListener(this);
        me_right.setOnClickListener(this);
        me_dialog_layout.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					x = event.getX();
					y = event.getY();
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if(!(x <= me_bg.getRight() && x >= me_bg.getLeft()
	                        && y >= me_bg.getTop() && y <= me_bg.getBottom())){
						if(meView != null && meView.isShown())
							meWin.dismiss();
						editable =false;
					}
				}
				return true;
			}
		});

        meWin = new PopupWindow(meView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
        meWin.setBackgroundDrawable(new BitmapDrawable());
        meWin.setFocusable(true);
        meWin.setOutsideTouchable(true);
        meWin.setAnimationStyle(R.style.settingAnimation);
    	}
    	resId  = settingHelper.getAvatarNumber();
        me_head_img.setImageResource(UserUtil.head_img[settingHelper.getAvatarNumber()]);
        nickname_edt.setText(settingHelper.getNickname());
        nickname_txt.setText(settingHelper.getNickname());
        me_right.setVisibility(editable?View.VISIBLE:View.INVISIBLE);
        me_left.setVisibility(editable?View.VISIBLE:View.INVISIBLE);
        confirm_edit_btn.setImageResource(R.drawable.img_edit_btn);
        edit_or_confirm.setText(R.string.edit);
        nickname_edt.setVisibility(editable?View.VISIBLE:View.INVISIBLE);
        nickname_txt.setVisibility(!editable?View.VISIBLE:View.INVISIBLE);
        meWin.showAtLocation(MainActivity.this.meBtn, Gravity.BOTTOM, 0, 0);
        meWin.update();
    }

    private void showFirstDialog(){
    	if(null==firstWin){
    		LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
    		firstView = inflater.inflate(R.layout.activity_first, null);
    		first_bg = (ImageView) firstView.findViewById(R.id.first_bg);
    		firstLeft = (ImageButton) firstView.findViewById(R.id.btn_change_left);
    		firstRight = (ImageButton) firstView.findViewById(R.id.btn_change_right);
    		first_head_img = (ImageView) firstView.findViewById(R.id.img_avatar);
    		edtFirstName = (EditText) firstView.findViewById(R.id.edt_name);
    		btnGo = (Button) firstView.findViewById(R.id.btn_go);
    		first_dialog_layout = (RelativeLayout) firstView.findViewById(R.id.first_dialog_layout);
    		first_dialog_layout.setOnTouchListener(new OnTouchListener() {
    			
    			@Override
    			public boolean onTouch(View v, MotionEvent event) {
    				// TODO Auto-generated method stub
    				if(event.getAction() == MotionEvent.ACTION_DOWN){
    					x = event.getX();
    					y = event.getY();
    				}
    				if (event.getAction() == MotionEvent.ACTION_UP) {
    					if(!(x <= first_bg.getRight() && x >= first_bg.getLeft()
    	                        && y >= first_bg.getTop() && y <= first_bg.getBottom())){
    						if(firstView != null && firstView.isShown()){
    							firstWin.dismiss();
    						}
    							
    					}
    				}
    				return true;
    			}
    		});
    		firstLeft.setOnClickListener(this);
    		firstRight.setOnClickListener(this);
    		btnGo.setOnClickListener(this);
    		firstWin = new PopupWindow(firstView,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,true);
    		firstWin.setBackgroundDrawable(new BitmapDrawable());
    		firstWin.setFocusable(true);
    		firstWin.setOutsideTouchable(true);
    		firstWin.setAnimationStyle(R.style.settingAnimation);
    	}
		resId  = settingHelper.getAvatarNumber();
		first_head_img.setImageResource(UserUtil.head_img[resId]);
    	firstWin.showAtLocation(meBtn,Gravity.BOTTOM,0,0);
		firstWin.update();
    }
    
    
	private void showSettingDialog() {
		// TODO Auto-generated method stub
		if(null==settingWin){
    	  LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
          settingView = inflater.inflate(R.layout.activity_setting, null);
          setting_dialog_layout = (RelativeLayout)settingView.findViewById(R.id.setting_dialog_layout);
          setting_items_layout = (LinearLayout)settingView.findViewById(R.id.setting_items_layout);
          voice_item_layout = (RelativeLayout)settingView.findViewById(R.id.voice_item_layout);
          shock_item_layout = (RelativeLayout)settingView.findViewById(R.id.shock_item_layout);
          help_item_layout = (RelativeLayout)settingView.findViewById(R.id.help_item_layout);
          about_item_layout = (RelativeLayout)settingView.findViewById(R.id.about_item_layout);
          setting_bg = (ImageView)settingView.findViewById(R.id.setting_bg);
          aboutView = (View)settingView.findViewById(R.id.about_view);
          helpView = (View)settingView.findViewById(R.id.help_view);
          playScrollView = (ScrollView)helpView.findViewById(R.id.help_play_scroll);
          allocateScrollView = (ScrollView)helpView.findViewById(R.id.help_allocate_scroll);
          introduceScrollView = (ScrollView)helpView.findViewById(R.id.help_introduce_scroll);
          raidersScrollView = (ScrollView)helpView.findViewById(R.id.help_raiders_scroll);
          
          help_play_txt = (TextView) helpView.findViewById(R.id.help_play_txt);
          help_allocate_txt = (TextView) helpView.findViewById(R.id.help_allocate_txt);
          help_introduce_txt = (TextView) helpView.findViewById(R.id.help_introduce_txt);
          help_raiders_txt = (TextView) helpView.findViewById(R.id.help_raiders_txt);
          help_play_txt.setOnClickListener(this);
          help_allocate_txt.setOnClickListener(this);
          help_introduce_txt.setOnClickListener(this);
          help_raiders_txt.setOnClickListener(this);
          
          voice_switch = (ImageView)settingView.findViewById(R.id.voice_switch_img);
          shock_switch = (ImageView)settingView.findViewById(R.id.shock_switch_img);
          voice_item_layout.setOnClickListener(this);
          shock_item_layout.setOnClickListener(this);
          help_item_layout.setOnClickListener(this);
          about_item_layout.setOnClickListener(this);
          setting_dialog_layout.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					x = event.getX();
					y = event.getY();
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if(!(x <= setting_bg.getRight() && x >= setting_bg.getLeft()
	                        && y >= setting_bg.getTop() && y <= setting_bg.getBottom())){
						if(settingView != null && settingView.isShown()){
							settingWin.dismiss();
							if(aboutView != null){
							    aboutView.setVisibility(View.GONE);
							}
							if(helpView != null){
							    helpView.setVisibility(View.GONE);
							}
							
						}
							
					}
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
		}
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

        setting_items_layout.setVisibility(View.VISIBLE);
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


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		editable =false;
		
	}
  
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		if(event.getAction()==KeyEvent.ACTION_DOWN && 
				event.getKeyCode()==KeyEvent.KEYCODE_BACK){
			new AlertDialog.Builder(this).setMessage("是否退出应用").setTitle("德州面对面").setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					MainActivity.this.finish();
				}
			}).setNegativeButton("取消", null).create().show();
			return true;
			
		}
		return super.dispatchKeyEvent(event);
	}
    
    
}
