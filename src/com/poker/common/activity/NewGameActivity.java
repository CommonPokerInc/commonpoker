package com.poker.common.activity;

import com.poker.common.BaseApplication;
import com.poker.common.R;
import com.poker.common.custom.MeView;
import com.poker.common.entity.ClientPlayer;
import com.poker.common.entity.Poker;
import com.poker.common.entity.Room;
import com.poker.common.entity.ToastView;
import com.poker.common.util.PokerUtil;
import com.poker.common.wifi.message.GameMessage;
import com.poker.common.wifi.message.MessageFactory;
import com.poker.common.wifi.message.PeopleMessage;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
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
//    private int aroundIndex = -1;
  //玩家toast控件的元素
    private ImageView player,bet_fullImg,bet_nullImg;
    private TextView name,othersAction,myAction;
    private Button confirmBtn;
    private int mMaxAddBetCan,mPreAddBet = 0,mCurAddBet;
    private float percent;
    private FrameLayout betLayout;
    private boolean verticalSlide = false,horizontalSlide = false,isFollow=false,isQuit = false;
    private Sensor sensor;
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
//    private RelativeLayout game_maincool_layout,game_sidecool_layout1,game_sidecool_layout2,game_sidecool_layout3
//    ,game_sidecool_layout4,game_sidecool_layout5;
//    
    private ArrayList<Poker> All_poker;
    
    private Button button1;
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
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_view);
        
        getScreenSize();
        registerListener();
        initView();
    }
    
    public void initView(){
        betLayout = (FrameLayout)findViewById(R.id.new_bet_layout);
        betLayout.setVisibility(View.INVISIBLE);
        //增加赌注的滑动条初始化控件
        bet_fullImg = (ImageView)findViewById(R.id.new_bet_full);
        bet_nullImg = (ImageView)findViewById(R.id.new_bet_null);
        public_poker1 = (ImageView)findViewById(R.id.game_pokers_img1);
        public_poker2 = (ImageView)findViewById(R.id.game_pokers_img2);
        public_poker3 = (ImageView)findViewById(R.id.game_pokers_img3);
        public_poker4 = (ImageView)findViewById(R.id.game_pokers_img4);
        public_poker5 = (ImageView)findViewById(R.id.game_pokers_img5);
        game_mypoker_img1 = (ImageView)findViewById(R.id.game_mypoker_img1);
        game_mypoker_img2 = (ImageView)findViewById(R.id.game_mypoker_img2);
        game_mypoker_type_txt = (TextView)findViewById(R.id.game_mypoker_type_txt);
//        game_maincool_layout = (RelativeLayout)findViewById(R.id.game_maincool_layout);
//        game_sidecool_layout1 = (RelativeLayout)findViewById(R.id.game_sidecool_layout1);
//        game_sidecool_layout2 = (RelativeLayout)findViewById(R.id.game_sidecool_layout2);
//        game_sidecool_layout3 = (RelativeLayout)findViewById(R.id.game_sidecool_layout3);
//        game_sidecool_layout4 = (RelativeLayout)findViewById(R.id.game_sidecool_layout4);
//        game_sidecool_layout5 = (RelativeLayout)findViewById(R.id.game_sidecool_layout5);
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
        
        button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(this);
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
        SensorEventListener lsn = new SensorEventListener() {       
            public void onSensorChanged(SensorEvent e) {       
                float x = e.values[SensorManager.DATA_X];       
                float y = e.values[SensorManager.DATA_Y];       
                float z = e.values[SensorManager.DATA_Z];       
//                setTitle("x=" + (int) x + "," + "y=" + (int) y + "," + "z="+ (int) z);  
//                Log.v("zkzhou1",""+x+","+""+y+","+""+z);
            }       
            public void onAccuracyChanged(Sensor s, int accuracy) {       
            }       
        };       
        
        // 注册listener，第三个参数是检测的精确度       
        sm.registerListener(lsn, sensor, SensorManager.SENSOR_DELAY_GAME);  
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
    
    
    private void showMoneyBarSlide(float y) {
        // TODO Auto-generated method stub
        mMaxAddBetCan = currentPlay.getInfo().getBaseMoney();
        percent = y/height;
        mCurAddBet= (int)(percent * mMaxAddBetCan) + mPreAddBet;
        mPreAddBet = mCurAddBet/4;
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
        isFollow = false;
        isQuit = false;
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
                showMyToast("弃牌");
                isQuit = true;
            } else if (arg1.getX() - arg0.getX() > verticalMinDistance
                    && Math.abs(arg2) > minVelocity) {
                showMyToast("跟注");
                isFollow = true;
            }
        }

        if (horizontalSlide) {
            showMoneyBarSlide(arg0.getY() - (int) arg1.getRawY());
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
            if(horizontalSlide || isFollow||isQuit){
                endGesture();
            }
            break;
        }
        
       return super.onTouchEvent(event);
    }

    private void endGesture() {
        // TODO Auto-generated method stub
        confirmBtn.setVisibility(View.VISIBLE);
        if(isFollow){
            followAction();
        }else if(isQuit){
            doQuit();
        }
    }
    
    public void playerAbandom(int playerIndex){
        playerList.get(playerIndex).getInfo().setQuit(true);
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
        HashMap<String,ArrayList<ClientPlayer>> winSet = PokerUtil.getWinner(playerList, All_poker);
        for(int i = 0;i<winSet.size();i++){
            for(int j = 0;j<winSet.get(String.valueOf(i)).size();j++){
               int sum = PokerUtil.getWinMoney(winSet.get(String.valueOf(i)).get(0).getInfo().getId(), playerList);
               for(int n = 0;n<winSet.get(String.valueOf(i)).size();n++){
                   setBaseMoney(winSet.get(String.valueOf(i)).get(n), (sum/winSet.get(String.valueOf(i)).size())
                           +winSet.get(String.valueOf(i)).get(n).getInfo().getBaseMoney());
               }
            }
        }
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

    public void doQuit(){
        Log.i("Rinfon", currentOptionPerson+"");
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
        int money = 0;
          if(currentPlay.getInfo().getAroundChip()<playerList.get(maxChipIndex).getInfo().getAroundChip()){
               if(currentPlay.getInfo().getBaseMoney()<(playerList.get(maxChipIndex).getInfo().getAroundChip()
                       -currentPlay.getInfo().getAroundChip())){
                   money = currentPlay.getInfo().getBaseMoney();
               }else{
                   money = playerList.get(maxChipIndex).getInfo().getAroundChip();
               }
               sendMessage(MessageFactory.newGameMessage(false, GameMessage.ACTION_UPDATE_MONEY, 
                       money, String.valueOf(currentOptionPerson)));
           }
           if(isEnd){
               sendMessage(MessageFactory.newGameMessage(false, GameMessage.ACTION_SHOW_PUBLIC_POKER,
                       -1,  String.valueOf(DIndex)));
               if(app.isServer()){
                   
                    if(money != 0){
                        Message message = new Message();
                        message.what = WorkHandler.MSG_ADD_BET;
                        message.arg1 = currentOptionPerson;
                        message.arg2 = money;
                        wHandler.sendMessage(message);
                   }
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
        case R.id.button1:
            if(this.playerList.size()>=2){
                All_poker = PokerUtil.getPokers(playerList.size());
                sendMessage(MessageFactory.newPeopleMessage(true, false, playerList, All_poker,null,null));
                wHandler.removeMessages(WorkHandler.MSG_START_GAME);
                wHandler.sendEmptyMessage(WorkHandler.MSG_START_GAME);
            }else{
                Toast.makeText(getApplicationContext(), "还没人齐啊扑街", 1000).show();
            }
            break;
        default:
            break;
        }
    }
    
 // 发底牌
    public void bottomDeal() {
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
        if(isMe){
            isMeImageView.setVisibility(View.VISIBLE);
        }else{
            isMeImageView.setVisibility(View.INVISIBLE);
        }
        
    }
    
    public void setChips(int playerIndex,int money,int isShow){
        if(money == 0){
            playerList.get(playerIndex).getInfo().setAroundChip(0);
        }else{
            playerList.get(playerIndex).getInfo().setBaseMoney(playerList.get(playerIndex).getInfo().getBaseMoney()
                    +playerList.get(playerIndex).getInfo().getAroundChip() - money);
            playerList.get(playerIndex).getInfo().setAroundChip(money);
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
    
    
    public void startGame(){
//        aroundIndex = room.getInnings();
        currentPlayIndex  = findIndexWithIPinList(playerList);
        app.isGameStarted = true;
        currentPlay.setInfo(playerList.get(currentPlayIndex).getInfo());
        currentPlay.getInfo().setBaseMoney(room.getBasicChips());
        currentPlay.getInfo().setQuit(false);
        setPersonView(currentPlay);
        if(app.isServer()){
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
                playerList.remove(exitIndex);
//                wHandler.removeMessages(WorkHandler.MSG_CHAIR_UPDATE);
//                wHandler.sendEmptyMessage(WorkHandler.MSG_CHAIR_UPDATE);
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
//            wHandler.removeMessages(WorkHandler.MSG_CHAIR_UPDATE);
//            wHandler.sendEmptyMessage(WorkHandler.MSG_CHAIR_UPDATE);
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
                Toast.makeText(getApplicationContext(), "Server exit", 1000).show();
                finish();
            }
            playerList.remove(msg.getPlayerList().get(0));
//            wHandler.removeMessages(WorkHandler.MSG_CHAIR_UPDATE);
//            wHandler.sendEmptyMessage(WorkHandler.MSG_CHAIR_UPDATE);
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
//            wHandler.removeMessages(WorkHandler.MSG_SET_PERSONVIEW);
//            wHandler.sendEmptyMessage(WorkHandler.MSG_SET_PERSONVIEW);
//                wHandler.removeMessages(WorkHandler.MSG_ROOM_UPDATE);
//                wHandler.sendEmptyMessage(WorkHandler.MSG_ROOM_UPDATE);
            }   
//            wHandler.removeMessages(WorkHandler.MSG_CHAIR_UPDATE);
//            wHandler.sendEmptyMessage(WorkHandler.MSG_CHAIR_UPDATE);
        }
    }

    @Override
    public void onClientReceive(GameMessage msg) {
        // TODO Auto-generated method stub
        int cmd = msg.getAction();
        switch(msg.getAction()){
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
        
    }

    @Override
    public void disconnectFromServer(int sec) {
        // TODO Auto-generated method stub
        
    }
    
private class WorkHandler extends Handler {
        
        private static final int MSG_CHAIR_UPDATE = 1;
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
        
        public WorkHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_CHAIR_UPDATE:
//                    chairUpdate(playerList);
                    break;
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
                default:
                    break;
            }
        }
    }
}
