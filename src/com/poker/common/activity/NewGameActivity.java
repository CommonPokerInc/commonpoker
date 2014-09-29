package com.poker.common.activity;

import com.poker.common.BaseApplication;
import com.poker.common.R;
import com.poker.common.custom.MeView;
import com.poker.common.entity.ClientPlayer;
import com.poker.common.entity.Poker;
import com.poker.common.entity.Room;
import com.poker.common.entity.ToastView;
import com.poker.common.util.PokerUtil;
import com.poker.common.util.SystemUtil;
import com.poker.common.util.UserUtil;
import com.poker.common.wifi.message.GameMessage;
import com.poker.common.wifi.message.MessageFactory;
import com.poker.common.wifi.message.PeopleMessage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class NewGameActivity extends AbsGameActivity implements OnGestureListener, OnClickListener{

    private int width,height;
    private LayoutInflater inflater;
    private View othersView,myView;
    private ToastView othersToast,myToast;
    private GestureDetector mGestureDetector; 
    private int verticalMinDistance = 5,horizontalMinDistance = 5;
    private int minVelocity = 0;
    private int aroundIndex = -1;
  //玩家toast控件的元素
    private ImageView player,bet_fullImg,bet_nullImg;
    private TextView name,othersAction,myAction,bet_txt;
    private Button confirmBtn;
    private int mMaxAddBetCan,mPreAddBet = 0,mCurAddBet;
    private float percent;
    private FrameLayout betLayout;
    private boolean verticalSlide = false,horizontalSlide = false,isFollow=false;
    private Sensor sensor;
    private Sensor lightSensor;
    private SensorManager sm ;
    
    private MeView meView;
    private String mSSID ;
  //客户端用户的IP地址
    private String mClientIpAddress ="127.0.0.1";
    private long mReceiveTime;
    private ClientPlayer currentPlay;
    private ArrayList<ClientPlayer> playerList;
 // 房间信息
    private Room room;
    private ImageView public_poker1,public_poker2,public_poker3,public_poker4,public_poker5;
    private ImageView game_mypoker_img1,game_mypoker_img2;
    private ImageView isMeImageView;
    private TextView game_mypoker_type_txt;

    private ImageView waitingImg[];
    
    private TextView waitingText[];
    
    private RelativeLayout waiting_roomerLayout[];
    private ArrayList<Poker> All_poker;
    
    private WorkHandler wHandler = null;
    
    private boolean isInOrOut = false;
    
    private boolean isEnd = false;
    
    private int DIndex = -1;
    
    private int bigBlindIndex = -1;
    
    private int maxChipIndex = -1;
    
    private int currentPlayIndex = -1;
    
    private int currentOptionPerson = -1;
    
    private ArrayList chipList;
    
    private RelativeLayout pools[];
    
    private TextView poolsText[];
    
    private ImageView helpImg,helpPic;
    
    private boolean isMe = false;
    
    private boolean isHasDoOption = true;
    
    private int z = 10;
    
    private int lux = 10;
    
    private ImageView game_back_img;

    private Vibrator vibrator;  
    
    private boolean isHelpLongPressed =false;
    
    private View waitView,winView;
    
    private boolean gameReady = false;
    
    private RelativeLayout game_view_layout;
    
    private Button wait_begin_btn;
    
    private TextView waiting_tips_txt;
    
    private ImageView game_win_tips_img,game_winner_img,game_sheng_img;
    
    private TextView game_winner_name_txt,game_winner_card_type_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_view);
		getScreenSize();
		registerListener();
		initView();
		waitingOthers();
        

    }
    
    private void waitingOthers() {
		// TODO Auto-generated method stub
        setViewVisibility(View.GONE,View.VISIBLE,View.GONE,View.GONE);
        waitingImg = new ImageView[6];
        waitingImg[0] = (ImageView)waitView.findViewById(R.id.waiting_roomer_img);
        waitingImg[1] = (ImageView)waitView.findViewById(R.id.waiting_player1_img);
        waitingImg[2] = (ImageView)waitView.findViewById(R.id.waiting_player2_img);
        waitingImg[3] = (ImageView)waitView.findViewById(R.id.waiting_player3_img);
        waitingImg[4] = (ImageView)waitView.findViewById(R.id.waiting_player4_img);
        waitingImg[5] = (ImageView)waitView.findViewById(R.id.waiting_player5_img);
        
        waitingText = new TextView[6];
        waitingText[0] = (TextView)waitView.findViewById(R.id.waiting_roomer_name);
        waitingText[1] = (TextView)waitView.findViewById(R.id.waiting_player1_name);
        waitingText[2] = (TextView)waitView.findViewById(R.id.waiting_player2_name);
        waitingText[3] = (TextView)waitView.findViewById(R.id.waiting_player3_name);
        waitingText[4] = (TextView)waitView.findViewById(R.id.waiting_player4_name);
        waitingText[5] = (TextView)waitView.findViewById(R.id.waiting_player5_name);
        
        waiting_roomerLayout = new RelativeLayout[6];
        waiting_roomerLayout[0] = (RelativeLayout)waitView.findViewById(R.id.waiting_roomer_layout);
        waiting_roomerLayout[1] = (RelativeLayout)waitView.findViewById(R.id.waiting_player1);
        waiting_roomerLayout[2] = (RelativeLayout)waitView.findViewById(R.id.waiting_player2);
        waiting_roomerLayout[3] = (RelativeLayout)waitView.findViewById(R.id.waiting_player3);
        waiting_roomerLayout[4] = (RelativeLayout)waitView.findViewById(R.id.waiting_player4);
        waiting_roomerLayout[5] = (RelativeLayout)waitView.findViewById(R.id.waiting_player5);
        
        waiting_roomerLayout[0].setVisibility(View.INVISIBLE);
        waiting_roomerLayout[1].setVisibility(View.INVISIBLE);
        waiting_roomerLayout[2].setVisibility(View.INVISIBLE);
        waiting_roomerLayout[3].setVisibility(View.INVISIBLE);
        waiting_roomerLayout[4].setVisibility(View.INVISIBLE);
        waiting_roomerLayout[5].setVisibility(View.INVISIBLE);
        
    	wait_begin_btn.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                    setViewVisibility(View.VISIBLE, View.GONE, View.GONE, View.GONE);
                    if(playerList.size()>=2){
                        All_poker = PokerUtil.getPokers(playerList.size());
                        sendMessage(MessageFactory.newPeopleMessage(true, false, playerList, All_poker,null,null));
                        wHandler.removeMessages(WorkHandler.MSG_START_GAME);
                        wHandler.sendEmptyMessage(WorkHandler.MSG_START_GAME);
                    }else{
                        Toast.makeText(getApplicationContext(), "还没人齐啊扑街", 1000).show();
                    } 
                }
        });
    	
    	 if (!app.isServer()) {
    	     wait_begin_btn.setVisibility(View.INVISIBLE);
    	     waiting_tips_txt.setText("等房主发号师令");
    	 }else{
    	     wait_begin_btn.setVisibility(View.VISIBLE);
    	 }
	}
    
    private void setWaitingPersonView(){
        int index = 0;
        for(int i = 0;i<playerList.size();i++){
            waitingImg[i].setImageResource(UserUtil.head_img[playerList.get(i).getInfo().getAvatar()]);
            waitingText[i].setText(playerList.get(i).getInfo().getName());
            waiting_roomerLayout[i].setVisibility(View.VISIBLE);
            index++;
        }
        for(int i = index;i<6;i++){
            waiting_roomerLayout[i].setVisibility(View.INVISIBLE);
        }
    }

    //用于设置几个大模块的layout可不可见
	private void setViewVisibility(int arg1,int arg2,int arg3,int arg4) {
		// TODO Auto-generated method stub
    	game_view_layout.setVisibility(arg1);
    	waitView.setVisibility(arg2);
    	isMeImageView.setVisibility(arg3);
    	helpPic.setVisibility(arg4);
	}

	public void initView(){
		game_view_layout = (RelativeLayout)findViewById(R.id.game_view_layout);
		waitView = (View)findViewById(R.id.game_waiting_layout);
		wait_begin_btn = (Button)waitView.findViewById(R.id.waiting_operation_btn);
		waiting_tips_txt = (TextView)waitView.findViewById(R.id.waiting_tips_txt);
		winView = (View)findViewById(R.id.game_win_layout);
		game_win_tips_img =(ImageView)winView.findViewById(R.id.game_win_tips_img);
		game_winner_img = (ImageView)winView.findViewById(R.id.game_winner_img);
		game_sheng_img = (ImageView)winView.findViewById(R.id.game_sheng_img);
		game_winner_name_txt = (TextView)winView.findViewById(R.id.game_winner_name_txt);
		game_winner_card_type_txt = (TextView)winView.findViewById(R.id.game_winner_card_type_txt);
        betLayout = (FrameLayout)findViewById(R.id.new_bet_layout);
        betLayout.setVisibility(View.INVISIBLE);
        //增加赌注的滑动条初始化控件
        bet_fullImg = (ImageView)findViewById(R.id.new_bet_full);
        bet_nullImg = (ImageView)findViewById(R.id.new_bet_null);
        helpImg = (ImageView)findViewById(R.id.game_help_img);
//        helpImg.setOnClickListener(this);
        helpImg.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				helpPic.setVisibility(View.VISIBLE);
				return false;
			}
		});
        helpImg.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_DOWN){
//					helpPic.setVisibility(View.VISIBLE);
				} 
				if(event.getAction() == MotionEvent.ACTION_UP){
					helpPic.setVisibility(View.INVISIBLE);
				}
				return false;
			}
		});
        helpPic = (ImageView)findViewById(R.id.help_img);
//        helpPic.setOnClickListener(this);
        public_poker1 = (ImageView)findViewById(R.id.game_pokers_img1);
        public_poker2 = (ImageView)findViewById(R.id.game_pokers_img2);
        public_poker3 = (ImageView)findViewById(R.id.game_pokers_img3);
        public_poker4 = (ImageView)findViewById(R.id.game_pokers_img4);
        public_poker5 = (ImageView)findViewById(R.id.game_pokers_img5);
        game_mypoker_img1 = (ImageView)findViewById(R.id.game_mypoker_img1);
        game_mypoker_img2 = (ImageView)findViewById(R.id.game_mypoker_img2);
        game_mypoker_type_txt = (TextView)findViewById(R.id.game_mypoker_type_txt);
        isMeImageView = (ImageView)findViewById(R.id.isMeImageView);
        pools = new RelativeLayout[6];
        pools[1] = (RelativeLayout)findViewById(R.id.game_sidecool_layout1);
        pools[2] = (RelativeLayout)findViewById(R.id.game_sidecool_layout2);
        pools[3] = (RelativeLayout)findViewById(R.id.game_sidecool_layout3);
        pools[4] = (RelativeLayout)findViewById(R.id.game_sidecool_layout4);
        pools[5] = (RelativeLayout)findViewById(R.id.game_sidecool_layout5);
        pools[0] = (RelativeLayout)findViewById(R.id.game_maincool_layout);
        
        poolsText = new TextView[6];
        poolsText[0] = (TextView)findViewById(R.id.game_maincool_chip_txt);
        poolsText[1] = (TextView)findViewById(R.id.game_sidecool_chip_txt1);
        poolsText[2] = (TextView)findViewById(R.id.game_sidecool_chip_txt2);
        poolsText[3] = (TextView)findViewById(R.id.game_sidecool_chip_txt3);
        poolsText[4] = (TextView)findViewById(R.id.game_sidecool_chip_txt4);
        poolsText[5] = (TextView)findViewById(R.id.game_sidecool_chip_txt5);
        game_back_img = (ImageView)findViewById(R.id.game_back_img);
        game_back_img.setOnClickListener(this);
        chipList = new ArrayList();
//        mMaxAddBetCan = 300;
        wHandler = new WorkHandler(getMainLooper());
        mGestureDetector = new GestureDetector(this);
        isTurnPhone();
        
        meView = (MeView)findViewById(R.id.game_me_view);
        
        reset();
        room = getIntent().getParcelableExtra("Room");
        playerList = new ArrayList<ClientPlayer>();
        if (!app.isServer()) {
            currentPlay = app.cp;
            playerList.add(currentPlay);
            sendMessage(MessageFactory.newPeopleMessage(false, false, playerList, null,null,null));
        } else {
            currentPlay = app.sp;
            currentPlay.getInfo().setBaseMoney(room.getBasicChips());
            currentPlay.getInfo().setAroundChip(0);
            playerList.add(currentPlay); 
            setPersonView(currentPlay);
            wHandler.removeMessages(WorkHandler.MSG_UPDATE_CHAIR);
            wHandler.sendEmptyMessage(WorkHandler.MSG_UPDATE_CHAIR);
        }
        
    }
    
    public void setPersonView(ClientPlayer player){
        meView.setPerson(player);
        meView.setActionViewVisiable(View.INVISIBLE);
        meView.setBetVisiable(View.INVISIBLE);
    }
    
    public void reset(){
        resetAllPoker();
        game_mypoker_type_txt.setVisibility(View.INVISIBLE);
        betLayout.setVisibility(View.INVISIBLE);
        pools[1].setVisibility(View.INVISIBLE);
        pools[2].setVisibility(View.INVISIBLE);
        pools[3].setVisibility(View.INVISIBLE);
        pools[4].setVisibility(View.INVISIBLE);
        pools[5].setVisibility(View.INVISIBLE);
        pools[0].setVisibility(View.INVISIBLE);
        isMeImageView.setVisibility(View.INVISIBLE);
    }
    
    public void resetAllPoker(){
        public_poker1.setAlpha(255);
        public_poker2.setAlpha(255);
        public_poker3.setAlpha(255);
        public_poker4.setAlpha(255);
        public_poker5.setAlpha(255);
        game_mypoker_img1.setAlpha(255);
        game_mypoker_img2.setAlpha(255);
        public_poker1.setVisibility(View.INVISIBLE);
        public_poker2.setVisibility(View.INVISIBLE);
        public_poker3.setVisibility(View.INVISIBLE);
        public_poker4.setVisibility(View.INVISIBLE);
        public_poker5.setVisibility(View.INVISIBLE);
        game_mypoker_img1.setVisibility(View.INVISIBLE);
        game_mypoker_img2.setVisibility(View.INVISIBLE);
    }
    
    protected void registerListener(){
        app = (BaseApplication) getApplication();
        mSSID = getIntent().getStringExtra("SSID");
        mClientIpAddress = getIntent().getStringExtra("IpAddress");
        if(app.isServer()){
            app.getServer().setListener(this);
            app.getServer().beginListen(this);
//          initTimeMap();
        }else{
            app.getClient().beganAcceptMessage(this);
            mReceiveTime = System.currentTimeMillis();
        }
//      initBackHandler();
    }
    
    private void isTurnPhone() {
        // TODO Auto-generated method stub
        //从系统服务中获得传感器管理器       
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);       
        lightSensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        SensorEventListener sn = new SensorEventListener() {       
            public void onSensorChanged(SensorEvent e) {            
                z =(int)e.values[SensorManager.DATA_Z];
            }       
            public void onAccuracyChanged(Sensor s, int accuracy) {       

            }       
        };       
        SensorEventListener lsn = new SensorEventListener(){

            @Override
            public void onSensorChanged(SensorEvent event) {
                // TODO Auto-generated method stub
                //获取光线强度  
                lux = (int)event.values[0];  
                if(checkisQuit(z,lux)&&isMe&&!isHasDoOption){
                    showMyToast("弃牌");
                    doQuit();
                    isHasDoOption = true;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // TODO Auto-generated method stub
                
            }
            
        };
        
        // 注册listener，第三个参数是检测的精确度       
        sm.registerListener(lsn, lightSensor, SensorManager.SENSOR_DELAY_GAME);  
        sm.registerListener(sn, sensor, SensorManager.SENSOR_DELAY_GAME); 
    }
    
    public boolean checkisQuit(int z,int lux){
        if(lux<=3&&z<=-7)
            return true;
        return false;
    }
    
    private void getScreenSize(){
        WindowManager wm = this.getWindowManager();
        this.width = wm.getDefaultDisplay().getWidth();
        this.height = wm.getDefaultDisplay().getHeight();
        Log.v("zkzhou","屏幕高度："+""+height+";"+"屏幕宽度："+""+width);
        
   }
    
    private void showMyToast(String str) {
        // TODO Auto-generated method stub
        if(inflater == null){
            inflater = LayoutInflater.from(this);
        }
        if(myView == null){
            myView = inflater.inflate(R.layout.me_action_view, null);
            myAction = (TextView)myView.findViewById(R.id.me_action_txt);
            confirmBtn = (Button)myView.findViewById(R.id.me_action_confirm_btn);
        }
        if(myToast == null){
            myToast = new ToastView(this,myView,this.height/4,Toast.LENGTH_SHORT);
        }
        if(str != null){
            myAction.setText(str);
        }
        confirmBtn.setVisibility(View.INVISIBLE);
        confirmBtn.setOnClickListener(this);
        myToast.show();
    }
    
    private void showOtherAction(int playerIndex,String str){
        if(inflater == null){
            inflater = LayoutInflater.from(this);
        }
        if(othersView == null){
            othersView = inflater.inflate(R.layout.player_action_view, null);
            player = (ImageView)othersView.findViewById(R.id.player_img);
            name = (TextView)othersView.findViewById(R.id.player_name_txt);
            othersAction = (TextView)othersView.findViewById(R.id.player_action_txt);
            bet_txt = (TextView)othersView.findViewById(R.id.bet_txt);
        }
        if(othersToast == null){
            othersToast = new ToastView(this,othersView,this.height/4,Toast.LENGTH_LONG);
        }
        player.setImageResource(UserUtil.head_img[playerList.get(playerIndex).getInfo().getAvatar()]);
        name.setText(playerList.get(playerIndex).getInfo().getName());
        othersAction.setText(str);
        bet_txt.setText(String.valueOf(playerList.get(playerIndex).getInfo().getBaseMoney()));
        othersToast.show();
    }
    
    
    private void showMoneyBarSlide(float y) {
        // TODO Auto-generated method stub
        mMaxAddBetCan = currentPlay.getInfo().getBaseMoney();
        percent = y/height;
        mCurAddBet= (int)(percent * mMaxAddBetCan) + mPreAddBet;
        mPreAddBet = mCurAddBet/8;
        if(mCurAddBet > mMaxAddBetCan){
            mCurAddBet = mMaxAddBetCan;
        }else if(mCurAddBet < 0){
            mCurAddBet = 0;
        }
        showMyToast("加注"+""+mCurAddBet);
        //更新进度条
        ViewGroup.LayoutParams lp = bet_fullImg.getLayoutParams();
        lp.height = bet_nullImg.getLayoutParams().height * mCurAddBet/mMaxAddBetCan;
        bet_fullImg.setLayoutParams(lp);
    }

    @Override
    public boolean onDown(MotionEvent arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {
        // TODO Auto-generated method stub
        
        return false;
    }

    @Override
    public void onLongPress(MotionEvent arg0) {
        // TODO Auto-generated method stub
    	
    }

    @Override
    public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
            float arg3) {
        // TODO Auto-generated method stub
        if(isMe){
            isFollow = false;
            float disXSlide = Math.abs(arg1.getX() - arg0.getX());
            float disYSlide = Math.abs(arg1.getY() - arg0.getY());
            if (disXSlide > 0 || disYSlide > 0) {
                if (disXSlide > 0 && disYSlide > 0) {
                    if ((float) disYSlide / disXSlide > 1f) {
                        verticalSlide = false;
                        horizontalSlide = true;
                        betLayout.setVisibility(View.VISIBLE);
                    } else {
                        verticalSlide = true;
                        horizontalSlide = false;
                    }
                } else if (disXSlide > 0) {
                    verticalSlide = true;
                    horizontalSlide = false;
                } else if (disYSlide > 0) {
                    verticalSlide = false;
                    horizontalSlide = true;
                    betLayout.setVisibility(View.VISIBLE);
                }
            }
    
            if (verticalSlide) {
                if (arg0.getX() - arg1.getX() > verticalMinDistance
                        && Math.abs(arg2) > minVelocity) {
                	isFollow = false;
    //                showMyToast("弃牌");
                } else if (arg1.getX() - arg0.getX() > verticalMinDistance
                        && Math.abs(arg2) > minVelocity) {
                    showMyToast("跟注");
                    isFollow = true;
                }
            }
    
            if (horizontalSlide) {
                showMoneyBarSlide(arg0.getY() - (int) arg1.getRawY());
            }
        }
        
        return false;
    }

    @Override
    public void onShowPress(MotionEvent arg0) {
        // TODO Auto-generated method stub
    	
    }

    @Override
    public boolean onSingleTapUp(MotionEvent arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if (mGestureDetector.onTouchEvent(event))
            return true;
        // 处理手势结束
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
        case MotionEvent.ACTION_UP:
            if(horizontalSlide || isFollow){
                endGesture();
            }
//            if(isHelpLongPressed){
//            	helpPic.setVisibility(View.GONE);
//            	isHelpLongPressed =false;
//            }
            break;
        }
        
       return super.onTouchEvent(event);
    }

    private void endGesture() {
        // TODO Auto-generated method stub
        confirmBtn.setVisibility(View.VISIBLE);
        if(isMe){
            if(isFollow){
                followAction();
            }
        }
    }
    
    public void playerAbandom(int playerIndex){
        playerList.get(playerIndex).getInfo().setQuit(true);
        showOtherAction(playerIndex,"弃牌");
        if(playerList.get(playerIndex).getInfo().getId() == currentPlay.getInfo().getId()){
            meView.setActionText("弃牌");
            meView.setActionViewVisiable(View.VISIBLE);
        }
    }
    
    public void countMoney(){
        ArrayList callBack = new ArrayList();
        chipList.clear();
        for(int i = playerList.size()-1;i>=0;i--){
            chipList.add(playerList.get(i).getInfo().getAroundSumChip());
            
        }
        PokerUtil.CountChips(chipList, callBack);
        setChipPool(callBack);
    }
    
    public void shareMoney(){
        optionChoice(false);
        HashMap<String,ArrayList<ClientPlayer>> winSet = PokerUtil.getWinner(playerList, All_poker);
        if(winSet.get("0").contains(currentPlay)){
            winView.setVisibility(View.VISIBLE);
            game_win_tips_img.setVisibility(View.VISIBLE);
        }else{
            setWinView(winSet.get("0").get(0));
        }
        for(int i = 0;i<winSet.size();i++){
            for(int j = 0;j<winSet.get(String.valueOf(i)).size();j++){
               int sum = PokerUtil.getWinMoney(winSet.get(String.valueOf(i)).get(0).getInfo().getId(), playerList);
               for(int n = 0;n<winSet.get(String.valueOf(i)).size();n++){
                   setBaseMoney(winSet.get(String.valueOf(i)).get(n), (sum/winSet.get(String.valueOf(i)).size())
                           +winSet.get(String.valueOf(i)).get(n).getInfo().getBaseMoney());
               }
            }
        }
        showAroundMessage(); 
    }
    
    public void setWinView(ClientPlayer p){
        game_winner_img.setImageResource(UserUtil.head_img[p.getInfo().getAvatar()]);
        game_sheng_img.setVisibility(View.VISIBLE);
        game_winner_name_txt.setText(p.getInfo().getName());
        game_winner_card_type_txt.setText(PokerUtil.getCardTypeString(p.getInfo().getCardType()));
        winView.setVisibility(View.VISIBLE);
    }
    
    public void setPokerAlpha(ArrayList<Poker> pokers,int index){
        if(!pokers.contains(All_poker.get(All_poker.size()-5))){
            public_poker1.setAlpha(60);
        }
        if(!pokers.contains(All_poker.get(All_poker.size()-4))){
            public_poker2.setAlpha(60);
        }
        if(!pokers.contains(All_poker.get(All_poker.size()-3))){
            public_poker3.setAlpha(60);
        }
        if(!pokers.contains(All_poker.get(All_poker.size()-2))){
            public_poker4.setAlpha(60);
        }
        if(!pokers.contains(All_poker.get(All_poker.size()-1))){
            public_poker5.setAlpha(60);
        }
        if(!pokers.contains(All_poker.get(index*2))){
            game_mypoker_img1.setAlpha(60);
        }
        if(!pokers.contains(All_poker.get(index*2+1))){
            game_mypoker_img2.setAlpha(60);
        }
        
        
    }
    
    public void showAroundMessage(){
        int index = findIndexWithIPinList(playerList);
        game_mypoker_type_txt.setText(PokerUtil.getCardTypeString(playerList.get(index).getInfo().getCardType()));
        game_mypoker_type_txt.setVisibility(View.VISIBLE);
        setPokerAlpha(playerList.get(index).getInfo().getPokerBack(),index);

        new Handler().postDelayed(new Runnable(){    
            public void run() {    
                if(app.isServer()){
                    if(aroundIndex<=room.getInnings()){
                        if(room.getInnings()!=-1)
                            aroundIndex++;
                        if(isInOrOut||DIndex == -1){
                            newDIndex();
                        }else {
                            DIndex++;
                        }
                        
                        if(DIndex>=playerList.size()){
                            DIndex = DIndex%playerList.size();
                        }
                        All_poker = PokerUtil.getPokers(playerList.size());
                        sendMessage(MessageFactory.newGameMessage(false, GameMessage.ACTION_RESET_ROUND, -1, String.valueOf(DIndex), All_poker));
                        wHandler.removeMessages(WorkHandler.MSG_RESET_ROUND);
                        wHandler.sendEmptyMessage(WorkHandler.MSG_RESET_ROUND);
                    }else{
                        sendMessage(MessageFactory.newGameMessage(false, GameMessage.ACTION_GAME_OVER, -1, null));
                        wHandler.removeMessages(WorkHandler.MSG_GAME_OVER);
                        wHandler.sendEmptyMessage(WorkHandler.MSG_GAME_OVER);
                    }
                }
            }    
         }, 3000);  

    }
    
    public void setBaseMoney(ClientPlayer player,int money){
        int playerIndex = findPlayer(player);
        playerList.get(playerIndex).getInfo().setBaseMoney(money);
        if(playerList.get(playerIndex).getInfo().getId() == currentPlay.getInfo().getId()){
            currentPlay.setInfo(playerList.get(playerIndex).getInfo());
            meView.setBaseMoneyText(String.valueOf(currentPlay.getInfo().getBaseMoney()));
        }
   }
    
    public void setChipPool(ArrayList chips){
        for(int i = 0;i<chips.size();i++){
            pools[i].setVisibility(View.VISIBLE);
            poolsText[i].setText(chips.get(i).toString());
            poolsText[i].setVisibility(View.VISIBLE);
        }
    }
    
    public void resetChipPool(){
        for(int i = 0;i<pools.length;i++){
            pools[i].setVisibility(View.INVISIBLE);
//            poolsText[i].setText(chips.get(i).toString());
//            poolsText[i].setVisibility(View.VISIBLE);
        }
    }
    
    public void setActionText(String actionName,int v){
        meView.setActionViewVisiable(v);
        meView.setActionText(actionName);
    }

    public void doQuit(){
        Log.i("Rinfon", currentOptionPerson+"");
        SystemUtil.Vibrate(this, 500);
        sendMessage(MessageFactory.newGameMessage(false, GameMessage.ACTION_ABANDOM, -1, String.valueOf(currentOptionPerson)));
        if(app.isServer()){
            Message message = new Message();
            message.what = WorkHandler.MSG_ACTION_ABANDOM;
            message.arg1 = findPlayer(currentPlay);
            wHandler.sendMessage(message);
            
//          弃牌后继续判断下一家
            currentOptionPerson = (currentOptionPerson+1)%playerList.size();
            sendMessage(MessageFactory.newGameMessage(false, GameMessage.ACTION_CURRENT_PERSON,
                  -1, String.valueOf(currentOptionPerson)));
            wHandler.removeMessages(WorkHandler.MSG_CHECKISME);
            wHandler.sendEmptyMessage(WorkHandler.MSG_CHECKISME);
        }
    }
    
    public void addChipAction(){
        int money = mCurAddBet;
        if(money == 0){
            followAction();
        }else{
            money += playerList.get(maxChipIndex).getInfo().getAroundChip();
            if(playerList.get(currentOptionPerson).getInfo().getBaseMoney()<=money){
                money = playerList.get(maxChipIndex).getInfo().getAroundChip() + playerList.get(currentOptionPerson).getInfo().getBaseMoney();
            }else{
                maxChipIndex = currentOptionPerson;
                isEnd = false;
            }
            setActionText("加注"+money,View.VISIBLE);
            sendMessage(MessageFactory.newGameMessage(false, GameMessage.ACTION_ADD_BET, money, String.valueOf(maxChipIndex)));
            if(app.isServer()){
                Message message = new Message();
                message.what = WorkHandler.MSG_ADD_BET;
                message.arg1 = maxChipIndex;
                message.arg2 = money;
                wHandler.sendMessage(message);
                currentOptionPerson = (currentOptionPerson+1)%playerList.size();
                wHandler.removeMessages(WorkHandler.MSG_CHECKISME);
                wHandler.sendEmptyMessage(WorkHandler.MSG_CHECKISME);
            }
        }
    }
    
    
    
    public void followAction(){
        int money = -1;
          if(currentPlay.getInfo().getAroundChip()<playerList.get(maxChipIndex).getInfo().getAroundChip()){
               if(currentPlay.getInfo().getBaseMoney()<(playerList.get(maxChipIndex).getInfo().getAroundChip()
                       -currentPlay.getInfo().getAroundChip())){
                   money = currentPlay.getInfo().getBaseMoney();
               }else{
                   money = playerList.get(maxChipIndex).getInfo().getAroundChip();
               }
               setActionText("跟注"+money,View.VISIBLE);
              
           }
          sendMessage(MessageFactory.newGameMessage(false, GameMessage.ACTION_UPDATE_MONEY, 
                  money, String.valueOf(currentOptionPerson)));
           if(isEnd){
               sendMessage(MessageFactory.newGameMessage(false, GameMessage.ACTION_SHOW_PUBLIC_POKER,
                       -1,  String.valueOf(DIndex)));
               if(app.isServer()){
                   Message message = new Message();
                   message.what = WorkHandler.MSG_ADD_BET;
                   message.arg1 = currentOptionPerson;
                   message.arg2 = money;
                   wHandler.sendMessage(message);
                   currentOptionPerson = (DIndex+1)%playerList.size();
                   int i = DIndex;
                   while(playerList.get(i%playerList.size()).getInfo().isQuit()){
                       i = Math.abs(i-1);
                   }
                   maxChipIndex = i;
                   wHandler.removeMessages(WorkHandler.MSG_SHOW_PUBLIC_POKER);
                   wHandler.sendEmptyMessage(WorkHandler.MSG_SHOW_PUBLIC_POKER);
                   wHandler.removeMessages(WorkHandler.MSG_CHECKISME);
                   wHandler.sendEmptyMessage(WorkHandler.MSG_CHECKISME);
               }
           }else{
               sendMessage(MessageFactory.newGameMessage(false, GameMessage.ACTION_FINISH_OPTIOIN,
                       -1, null));
               if(app.isServer()){
                   Message message2 = new Message();
                    message2.what = WorkHandler.MSG_ADD_BET;
                    message2.arg1 = currentOptionPerson;
                    message2.arg2 = money;
                    wHandler.sendMessage(message2);
                   currentOptionPerson = (currentOptionPerson+1)%playerList.size(); 
                   wHandler.removeMessages(WorkHandler.MSG_CHECKISME);
                   wHandler.sendEmptyMessage(WorkHandler.MSG_CHECKISME);
               }
           }
   }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
        case R.id.me_action_confirm_btn:
            betLayout.setVisibility(View.GONE);
            if(myToast != null){
                myToast.cancel();
            }
            addChipAction();
            break;
//        case R.id.wait_begin_btn:
//            setViewVisibility(View.VISIBLE, View.GONE, View.GONE, View.GONE);
//            if(this.playerList.size()>=2){
//                All_poker = PokerUtil.getPokers(playerList.size());
//                sendMessage(MessageFactory.newPeopleMessage(true, false, playerList, All_poker,null,null));
//                wHandler.removeMessages(WorkHandler.MSG_START_GAME);
//                wHandler.sendEmptyMessage(WorkHandler.MSG_START_GAME);
//            }else{
//                Toast.makeText(getApplicationContext(), "还没人齐啊扑街", 1000).show();
//            }
//            break;
        case R.id.game_back_img:
        	playerList.clear();
            playerList.add(currentPlay);
            sendMessage(MessageFactory.newPeopleMessage(false, true, playerList, null,null,"server exit"));
            finish();
            break;
        default:
            break;
        }
    }
    
 // 发底牌
    public void bottomDeal() {
        game_mypoker_type_txt.setVisibility(View.INVISIBLE);
        int index = findIndexWithIPinList(this.playerList);
        bigBlindIndex = (DIndex+2)%playerList.size();
        maxChipIndex = bigBlindIndex;
        setChips(bigBlindIndex,room.getMinStake(),View.VISIBLE);
        setChips((DIndex+1)%playerList.size(),room.getMinStake()/2,View.VISIBLE);
        currentOptionPerson = (maxChipIndex+1)%playerList.size();
        setMePokers(All_poker.get(index*2).getPokerImageId(), All_poker.get(index*2+1).getPokerImageId(),View.VISIBLE);
        checkIsMeOption();
    }
    
 // 发公共牌
    public void showPublicPoker() {
        meView.setActionViewVisiable(View.INVISIBLE);
        if(public_poker5.getVisibility() == View.VISIBLE){
            if(app.isServer()){
//            一轮结束，进行下一轮
                sendMessage(MessageFactory.newGameMessage(false, GameMessage.ACTION_NEXT_ROUND, -1, null));
                wHandler.removeMessages(WorkHandler.MSG_NEXT_ROUND);
                wHandler.sendEmptyMessage(WorkHandler.MSG_NEXT_ROUND);
            }else{
                return;
            }
        }
        if (public_poker1.getVisibility() != View.VISIBLE) {
            public_poker1.setImageResource(All_poker.get(All_poker.size()-5).getPokerImageId());
            public_poker2.setImageResource(All_poker.get(All_poker.size()-4).getPokerImageId());
            public_poker3.setImageResource(All_poker.get(All_poker.size()-3).getPokerImageId());
            public_poker1.setVisibility(View.VISIBLE);
            public_poker2.setVisibility(View.VISIBLE);
            public_poker3.setVisibility(View.VISIBLE);
            wHandler.removeMessages(WorkHandler.MSG_COUNT_POOL);
            wHandler.sendEmptyMessage(WorkHandler.MSG_COUNT_POOL);
            return;
        } else if (public_poker4.getVisibility() != View.VISIBLE) {
            public_poker4.setImageResource(All_poker.get(All_poker.size()-2).getPokerImageId());
            public_poker4.setVisibility(View.VISIBLE);
            wHandler.removeMessages(WorkHandler.MSG_COUNT_POOL);
            wHandler.sendEmptyMessage(WorkHandler.MSG_COUNT_POOL);
            return;
        } else if (public_poker5.getVisibility() != View.VISIBLE) {
            public_poker5.setImageResource(All_poker.get(All_poker.size()-1).getPokerImageId());
            public_poker5.setVisibility(View.VISIBLE);
            wHandler.removeMessages(WorkHandler.MSG_COUNT_POOL);
            wHandler.sendEmptyMessage(WorkHandler.MSG_COUNT_POOL);
            return;
        } else {
            return;
        }
    }
    
    public void checkIsMeOption(){
        Toast.makeText(getApplicationContext(), "currentOptionPerson:"+currentOptionPerson , 1000).show();
        if(playerList.get(currentOptionPerson).getInfo().getId().equals(currentPlay.getInfo().getId())){
            
            currentPlay.getInfo().setQuit(playerList.get(currentOptionPerson).getInfo().isQuit());
            if(playerList.get(currentOptionPerson).getInfo().isQuit()){
                sendMessage(MessageFactory.newGameMessage(false, GameMessage.ACTION_FINISH_OPTIOIN,
                        -1, null));
                if(app.isServer()){
                    currentOptionPerson = (currentOptionPerson+1)%playerList.size(); 
                    wHandler.removeMessages(WorkHandler.MSG_CHECKISME);
                    wHandler.sendEmptyMessage(WorkHandler.MSG_CHECKISME);
                }
                optionChoice(false);
                return ;
           }
            
           if(currentOptionPerson == maxChipIndex){
               isEnd = true;
           }else{
               isEnd = false;
           }
           if(isEnd&&currentPlayCount() == 1){
//             所有玩家都弃牌，只剩一个玩家，进行分钱
//               showToast("一轮游戏结束");
               return;
           }else{
               optionChoice(true);
           }
       }else{
           optionChoice(false);
       }
   }
    
    public void gameOver(){
        Intent intent = new Intent();
        
        //用intent.putExtra(String name, String value);来传递参数。
        int index = findIndexWithIPinList(playerList);
        Bundle bundle = new Bundle();  
        bundle.putSerializable("arrayList", playerList);  
        intent.putExtras(bundle);
        intent.putExtra("index", index);
        intent.setClass(NewGameActivity.this, RankActivity.class);
        startActivity(intent);
    }
    
    public int currentPlayCount(){
        int j = 0;
        for(int i = playerList.size()-1;i>=0;i--){
            if(playerList.get(i).getInfo().isQuit()){
                j++;
            }
        }
        return playerList.size() - j;
    }
    
    public void optionChoice(boolean isMe){
        this.isMe = isMe;
        if(isMe){
            isHasDoOption = false;
            SystemUtil.Vibrate(this, 500);
            isMeImageView.setVisibility(View.VISIBLE);
        }else{
            isMeImageView.setVisibility(View.INVISIBLE);
        }
        
    }
    
    public void setChips(int playerIndex,int money,int isShow){
        
        if(money == -1){
//            showOtherAction(playerIndex,"让牌");
        }else if(money == 0){
            playerList.get(playerIndex).getInfo().setAroundChip(0);
        }else{
            playerList.get(playerIndex).getInfo().setBaseMoney(playerList.get(playerIndex).getInfo().getBaseMoney()
                    +playerList.get(playerIndex).getInfo().getAroundChip() - money);
            playerList.get(playerIndex).getInfo().setAroundChip(money);
            if(playerList.get(playerIndex).getInfo().getId() != currentPlay.getInfo().getId()){
//                showOtherAction(playerIndex,"跟注"+money);
            }  
        }
        if(playerList.get(playerIndex).getInfo().getId() == currentPlay.getInfo().getId()){
            currentPlay.setInfo(playerList.get(playerIndex).getInfo());
            meView.setBaseMoneyText(String.valueOf(playerList.get(playerIndex).getInfo().getBaseMoney()));
            meView.setBetMoneyText(String.valueOf(playerList.get(playerIndex).getInfo().getAroundChip()));
            meView.setBetVisiable(isShow);
        }
    }
    
    public void setMePokers(int pokerid1,int pokerid2,int isShow){
        game_mypoker_img1.setImageResource(pokerid1);
        game_mypoker_img2.setImageResource(pokerid2);
        game_mypoker_img1.setVisibility(isShow);
        game_mypoker_img2.setVisibility(isShow);
    }
    
    public int findPlayer(ClientPlayer player){
        for(int i = playerList.size()-1;i>=0;i--){
            if(player.getInfo().getId().equals(playerList.get(i).getInfo().getId())){
                return i;
            }
        }
        return -1;
    }
    
    public int findIndexWithIPinList(ArrayList<ClientPlayer> playerList){
        for(int i = playerList.size()-1;i>=0;i--){
            if(currentPlay.getInfo().getId().equals(playerList.get(i).getInfo().getId())){
                return i;
            }
        }
        return -1;
    }
    
    //手机震动
    public void phoneShock(){
    	vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
    	long [] pattern = {100,400,100,400};   // 停止 开启 停止 开启 
    	vibrator.vibrate(pattern,-1);
    }
    
    
    public void startGame(){
        setViewVisibility(View.VISIBLE, View.GONE, View.GONE, View.GONE);
        if(room.getInnings() == -1){
            aroundIndex = -1;
        }else{
            aroundIndex = 0;
        }
        
        currentPlayIndex  = findIndexWithIPinList(playerList);
        app.isGameStarted = true;
        currentPlay.setInfo(playerList.get(currentPlayIndex).getInfo());
        currentPlay.getInfo().setBaseMoney(room.getBasicChips());
        currentPlay.getInfo().setQuit(false);
        setPersonView(currentPlay);
        if(app.isServer()){
            forbidJoin();
            if(isInOrOut||DIndex == -1){
                newDIndex();
            }else {
                DIndex++;
            }
            
            if(DIndex>=playerList.size()){
                DIndex = DIndex%playerList.size();
            }
            sendMessage(MessageFactory.newGameMessage(false, GameMessage.ACTION_SEND_BOOL, -1, String.valueOf(DIndex)));
            wHandler.removeMessages(WorkHandler.MSG_SEND_BOOL);
            wHandler.sendEmptyMessage(WorkHandler.MSG_SEND_BOOL);
        }
    }
    
    public void newDIndex(){
        Random r = new Random();
        DIndex = r.nextInt(playerList.size());
    }
    
    public void resetAllPlayerAroundChaip(){
        for(int i = 0;i<playerList.size();i++){
            setChips(i, 0, View.INVISIBLE);
        }
    }

    @Override
    public void onServerReceive(PeopleMessage msg) {
        // TODO Auto-generated method stub
        if(msg.isExit()){
            int exitIndex = findPlayer(msg.getPlayerList().get(0));
            if(exitIndex!=-1){
            	sendMessage(msg);
                playerList.remove(exitIndex);
                wHandler.removeMessages(WorkHandler.MSG_UPDATE_CHAIR);
                wHandler.sendEmptyMessage(WorkHandler.MSG_UPDATE_CHAIR);
            }
        }else if (msg.getPlayerList() != null && msg.getPlayerList().get(0) != null) {
            msg.getPlayerList().get(0).getInfo().setBaseMoney(room.getBasicChips());
            msg.getPlayerList().get(0).getInfo().setAroundChip(0);
            msg.getPlayerList().get(0).getInfo().setAroundSumChip(0);
            msg.getPlayerList().get(0).getInfo().setQuit(false);
            playerList.add(msg.getPlayerList().get(0));
            msg.setPlayerList(playerList);
            msg.setRoom(room);
            sendMessage(msg);
            wHandler.removeMessages(WorkHandler.MSG_UPDATE_CHAIR);
            wHandler.sendEmptyMessage(WorkHandler.MSG_UPDATE_CHAIR);
        }
    }

    @Override
    public void onServerReceive(GameMessage msg) {
        // TODO Auto-generated method stub
        switch(msg.getAction()){
            case GameMessage.ACTION_UPDATE_MONEY:
                sendMessage(msg);
                
                Message message2 = new Message();
                message2.what = WorkHandler.MSG_ADD_BET;
                message2.arg1 = Integer.parseInt(msg.getExtra());
                message2.arg2 = msg.getAmount();
                wHandler.sendMessage(message2);
                break;
            case GameMessage.ACTION_FINISH_OPTIOIN:
                currentOptionPerson = (currentOptionPerson+1)%playerList.size();
                sendMessage(MessageFactory.newGameMessage(false, GameMessage.ACTION_CURRENT_PERSON,
                        -1, String.valueOf(currentOptionPerson)));
                wHandler.removeMessages(WorkHandler.MSG_CHECKISME);
                wHandler.sendEmptyMessage(WorkHandler.MSG_CHECKISME);
                break;
            case GameMessage.ACTION_SHOW_PUBLIC_POKER:
                sendMessage(msg);
                DIndex = Integer.parseInt(msg.getExtra().toString());
                currentOptionPerson = (DIndex+1)%playerList.size();
                int i = DIndex;
                while(playerList.get(i%playerList.size()).getInfo().isQuit()){
                    i = Math.abs(i-1);
                }
                maxChipIndex = i;
                wHandler.removeMessages(WorkHandler.MSG_SHOW_PUBLIC_POKER);
                wHandler.sendEmptyMessage(WorkHandler.MSG_SHOW_PUBLIC_POKER);
                wHandler.removeMessages(WorkHandler.MSG_CHECKISME);
                wHandler.sendEmptyMessage(WorkHandler.MSG_CHECKISME);
                break;
            case GameMessage.ACTION_ADD_BET:
                maxChipIndex = Integer.parseInt(msg.getExtra());
                sendMessage(msg);
                Message message = new Message();
                message.what = WorkHandler.MSG_ADD_BET;
                message.arg1 = maxChipIndex;
                message.arg2 = msg.getAmount();
                wHandler.sendMessage(message);
                
//                加注后继续判断下一家
                currentOptionPerson = (currentOptionPerson+1)%playerList.size();
                sendMessage(MessageFactory.newGameMessage(false, GameMessage.ACTION_CURRENT_PERSON,
                        -1, String.valueOf(currentOptionPerson)));
                wHandler.removeMessages(WorkHandler.MSG_CHECKISME);
                wHandler.sendEmptyMessage(WorkHandler.MSG_CHECKISME);
                break;
            case GameMessage.ACTION_ABANDOM:
                sendMessage(msg);
                Message message3 = new Message();
                message3.what = WorkHandler.MSG_ACTION_ABANDOM;
                message3.arg1 = Integer.parseInt(msg.getExtra());
                wHandler.sendMessage(message3);
//              弃牌后继续判断下一家
                currentOptionPerson = (currentOptionPerson+1)%playerList.size();
                sendMessage(MessageFactory.newGameMessage(false, GameMessage.ACTION_CURRENT_PERSON,
                      -1, String.valueOf(currentOptionPerson)));
                wHandler.removeMessages(WorkHandler.MSG_CHECKISME);
                wHandler.sendEmptyMessage(WorkHandler.MSG_CHECKISME);
                break;
        }
    }

    @Override
    public void onClientReceive(PeopleMessage msg) {
        // TODO Auto-generated method stub
        if (msg.isExit()) {
            if("server exit".equals(msg.getExtra())){
//                Toast.makeText(getApplicationContext(), "Server exit", 1000).show();
                finish();
            }
            playerList.remove(msg.getPlayerList().get(0));
            wHandler.removeMessages(WorkHandler.MSG_UPDATE_CHAIR);
            wHandler.sendEmptyMessage(WorkHandler.MSG_UPDATE_CHAIR);
        }
        if (msg.isStart()) {
//          游戏开始
            app.isGameStarted = true;
            this.All_poker = msg.getPokerList();
            wHandler.removeMessages(WorkHandler.MSG_START_GAME);
            wHandler.sendEmptyMessage(WorkHandler.MSG_START_GAME);
        } else {
            this.playerList.clear();
            this.playerList.addAll(msg.getPlayerList());
            if(this.room == null){
            	this.room = msg.getRoom();
            }   
            wHandler.removeMessages(WorkHandler.MSG_UPDATE_CHAIR);
            wHandler.sendEmptyMessage(WorkHandler.MSG_UPDATE_CHAIR);
        }
    }

    @Override
    public void onClientReceive(GameMessage msg) {
        // TODO Auto-generated method stub
        int cmd = msg.getAction();
        switch(cmd){
        case GameMessage.ACTION_SEND_BOOL:
             if(msg.getExtra()!=null){
                this.DIndex = Integer.parseInt(msg.getExtra());
            }
            wHandler.removeMessages(WorkHandler.MSG_SEND_BOOL);
            wHandler.sendEmptyMessage(WorkHandler.MSG_SEND_BOOL);
            break;
        case GameMessage.ACTION_CURRENT_PERSON:
            currentOptionPerson = Integer.parseInt(msg.getExtra().toString());
            wHandler.removeMessages(WorkHandler.MSG_CHECKISME);
            wHandler.sendEmptyMessage(WorkHandler.MSG_CHECKISME);
            break;
        case GameMessage.ACTION_UPDATE_MONEY:
            Message message2 = new Message();
            message2.what = WorkHandler.MSG_ADD_BET;
            message2.arg1 = Integer.parseInt(msg.getExtra());
            message2.arg2 = msg.getAmount();
            wHandler.sendMessage(message2);
            break;
        case GameMessage.ACTION_FINISH_OPTIOIN:
            currentOptionPerson = (currentOptionPerson+1)%playerList.size();
            wHandler.removeMessages(WorkHandler.MSG_CHECKISME);
            wHandler.sendEmptyMessage(WorkHandler.MSG_CHECKISME);
            break;
        case GameMessage.ACTION_SHOW_PUBLIC_POKER:
            DIndex = Integer.parseInt(msg.getExtra().toString());
            currentOptionPerson = (DIndex+1)%playerList.size();
            int i = DIndex;
            while(playerList.get(i%playerList.size()).getInfo().isQuit()){
                i = Math.abs(i-1);
            }
            maxChipIndex = i;
            wHandler.removeMessages(WorkHandler.MSG_SHOW_PUBLIC_POKER);
            wHandler.sendEmptyMessage(WorkHandler.MSG_SHOW_PUBLIC_POKER);
            wHandler.removeMessages(WorkHandler.MSG_CHECKISME);
            wHandler.sendEmptyMessage(WorkHandler.MSG_CHECKISME);
            break;
        case GameMessage.ACTION_NEXT_ROUND:
//          showToast("游戏结束");
          wHandler.removeMessages(WorkHandler.MSG_NEXT_ROUND);
          wHandler.sendEmptyMessage(WorkHandler.MSG_NEXT_ROUND);
          break;
        case GameMessage.ACTION_ADD_BET:
            maxChipIndex = Integer.parseInt(msg.getExtra());
            Message message = new Message();
            message.what = WorkHandler.MSG_ADD_BET;
            message.arg1 = maxChipIndex;
            message.arg2 = msg.getAmount();
            wHandler.sendMessage(message);
            
            currentOptionPerson = (currentOptionPerson+1)%playerList.size();
            wHandler.removeMessages(WorkHandler.MSG_CHECKISME);
            wHandler.sendEmptyMessage(WorkHandler.MSG_CHECKISME);
            break;
        case GameMessage.ACTION_ABANDOM:
            Message message3 = new Message();
            message3.what = WorkHandler.MSG_ACTION_ABANDOM;
            message3.arg1 = Integer.parseInt(msg.getExtra());
            wHandler.sendMessage(message3);
            
//          弃牌后继续判断下一家
            currentOptionPerson = (currentOptionPerson+1)%playerList.size();
            sendMessage(MessageFactory.newGameMessage(false, GameMessage.ACTION_CURRENT_PERSON,
                  -1, String.valueOf(currentOptionPerson)));
            wHandler.removeMessages(WorkHandler.MSG_CHECKISME);
            wHandler.sendEmptyMessage(WorkHandler.MSG_CHECKISME);
            break;
        case GameMessage.ACTION_RESET_ROUND:
            this.All_poker = msg.getPokerList();
            DIndex = Integer.parseInt(msg.getExtra());
            wHandler.removeMessages(WorkHandler.MSG_RESET_ROUND);
            wHandler.sendEmptyMessage(WorkHandler.MSG_RESET_ROUND);
            break;
        case GameMessage.ACTION_GAME_OVER:
        	wHandler.removeMessages(WorkHandler.MSG_GAME_OVER);
            wHandler.sendEmptyMessage(WorkHandler.MSG_GAME_OVER);
        	break;
        }
    }

    @Override
    public void onServerSendFailure() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onServerSendSuccess() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onClientSendFailure() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onClientSendSuccess() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void clientDecrease(String clientName) {
        // TODO Auto-generated method stub
        ArrayList<ClientPlayer> box = new ArrayList<ClientPlayer>();
        for(int i=0;i<playerList.size();i++){
            if(playerList.get(i).getInfo().getIp().equals(clientName)){
                box.add(playerList.get(i));
                break;
            }
        }
        sendMessage(MessageFactory.newPeopleMessage(false, true, box, null, room, "client exit"));
        
    }

    @Override
    public void disconnectFromServer(int sec) {
        // TODO Auto-generated method stub
        
    }
    
private class WorkHandler extends Handler {
        
//        private static final int MSG_CHAIR_UPDATE = 1;
        private static final int MSG_SEND_BOOL = 2;
        private static final int MSG_START_GAME = 3;
        private static final int MSG_ROOM_UPDATE = 4;
        private static final int MSG_CHECKISME = 5;
        private static final int MSG_SHOW_PUBLIC_POKER = 6;
        private static final int MSG_ADD_BET =7;
//      private static final int MSG_UPDATE_MONEY =8;
        private static final int MSG_ACTION_ABANDOM = 8;
        private static final int MSG_NEXT_ROUND = 9;
        private static final int MSG_COUNT_POOL = 10;
        private static final int MSG_UPDATE_CHAIR = 11;
        private static final int MSG_RESET_ROUND = 12;
        private static final int MSG_GAME_OVER = 13;
        
        
        public WorkHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
//                case MSG_CHAIR_UPDATE:
////                    chairUpdate(playerList);
//                    break;
                case MSG_SEND_BOOL:
                    bottomDeal();
                    break;
                case MSG_START_GAME:
                    startGame();
                    break;
                case MSG_ROOM_UPDATE:
//                    updateRoom(room);
                    break;
                case MSG_CHECKISME:
                    checkIsMeOption();
                    break;
                case MSG_SHOW_PUBLIC_POKER:
                    for(int i = 0;i<playerList.size();i++){
                        int ar = playerList.get(i).getInfo().getAroundChip();
                        int asr = playerList.get(i).getInfo().getAroundSumChip();
                        playerList.get(i).getInfo().setAroundSumChip(asr+ar);
                    }
                    resetAllPlayerAroundChaip();
                    showPublicPoker();
                    break;
                case MSG_ADD_BET:
                    setChips(msg.arg1, msg.arg2, View.VISIBLE);
                    break;
                case MSG_ACTION_ABANDOM:
                    playerAbandom(msg.arg1);
                    break;
                case MSG_NEXT_ROUND:
                    shareMoney();
                    break;
                case MSG_COUNT_POOL:
                    countMoney();
                    break;
                case MSG_UPDATE_CHAIR:
                    setWaitingPersonView();
                    break;
                case MSG_RESET_ROUND:
                    resetAllPlayerAroundChaip();
                    resetAllPoker();
                    resetChipPool();
                    bottomDeal();
                    break;
                case MSG_GAME_OVER:
                	Log.i("Rinfon", "Game over");
                    gameOver();
                    break;
                default:
                    break;
            }
        }
    }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK )  
        {  
		    Dialog dialog = new AlertDialog.Builder(this).setMessage("是否退出游戏？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    playerList.clear();
                    playerList.add(currentPlay);
                    if(app.isServer())
                        sendMessage(MessageFactory.newPeopleMessage(false, true, playerList, null,null,"server exit"));
                    else{
                        sendMessage(MessageFactory.newPeopleMessage(false, true, playerList, null,null,"client exit"));
                    }
                    finish();
                }
            }).setNegativeButton("取消", null).create();
		    dialog.show();
			
        }  
          
        return false;  
	}

}
