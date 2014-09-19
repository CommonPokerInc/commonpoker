package com.poker.common.activity;

import com.poker.common.BaseApplication;
import com.poker.common.R;
import com.poker.common.custom.MeView;
import com.poker.common.entity.ClientPlayer;
import com.poker.common.entity.Room;
import com.poker.common.entity.ToastView;
import com.poker.common.wifi.message.GameMessage;
import com.poker.common.wifi.message.MessageFactory;
import com.poker.common.wifi.message.PeopleMessage;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NewGameActivity extends AbsGameActivity implements OnGestureListener, OnClickListener{

    private int width,height;
    private LayoutInflater inflater;
    private View othersView,myView;
    private ToastView othersToast,myToast;
    private GestureDetector mGestureDetector; 
    private int verticalMinDistance = 5,horizontalMinDistance = 5;
    private int minVelocity = 0;
  //玩家toast控件的元素
    private ImageView player,bet_fullImg,bet_nullImg;
    private TextView name,othersAction,myAction;
    private Button confirmBtn;
    private int mMaxAddBetCan,mPreAddBet = 0,mCurAddBet;
    private float percent;
    private FrameLayout betLayout;
    private boolean verticalSlide = false,horizontalSlide = false,isFollow=false;
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
        //增加赌注的滑动条初始化控件
        bet_fullImg = (ImageView)findViewById(R.id.new_bet_full);
        bet_nullImg = (ImageView)findViewById(R.id.new_bet_null);
        mMaxAddBetCan = 300;
        mGestureDetector = new GestureDetector(this);
        isTurnPhone();
        
        meView = (MeView)findViewById(R.id.game_me_view);
        
        
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
            myToast = new ToastView(this,myView,this.height/4,Toast.LENGTH_LONG);
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
        float disXSlide = Math.abs(arg1.getX() - arg0.getX());
        float disYSlide = Math.abs(arg1.getY() - arg0.getY());
        if (disXSlide > 0 || disYSlide > 0) {
            if (disXSlide > 0 && disYSlide > 0) {
                if ((float) disYSlide / disXSlide > 1f) {
                    verticalSlide = false;
                    horizontalSlide = true;
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
            }
        }

        if (verticalSlide) {
            if (arg0.getX() - arg1.getX() > verticalMinDistance
                    && Math.abs(arg2) > minVelocity) {
                showMyToast("弃牌");
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
            if(horizontalSlide || isFollow){
                endGesture();
            }
            break;
        }
        
       return super.onTouchEvent(event);
    }

    private void endGesture() {
        // TODO Auto-generated method stub
        confirmBtn.setVisibility(View.VISIBLE);
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
            break;

        default:
            break;
        }
    }

    @Override
    public void onServerReceive(PeopleMessage msg) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onServerReceive(GameMessage msg) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onClientReceive(PeopleMessage msg) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onClientReceive(GameMessage msg) {
        // TODO Auto-generated method stub
        
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
}
