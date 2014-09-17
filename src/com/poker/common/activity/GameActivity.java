
package com.poker.common.activity;

import com.poker.common.R;
import com.poker.common.custom.LeftSeatView;
import com.poker.common.custom.RightSeatView;
import com.poker.common.custom.VerticalSeekBar;
import com.poker.common.entity.AbsPlayer;
import com.poker.common.entity.ClientPlayer;
import com.poker.common.entity.Poker;
import com.poker.common.entity.Room;
import com.poker.common.util.PokerUtil;
import com.poker.common.wifi.listener.MessageListener;
import com.poker.common.wifi.message.GameMessage;
import com.poker.common.wifi.message.MessageFactory;
import com.poker.common.wifi.message.PeopleMessage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.poker.common.R;
import com.poker.common.entity.AbsPlayer;
import com.poker.common.entity.ClientPlayer;
import com.poker.common.entity.Poker;
import com.poker.common.entity.Room;
import com.poker.common.util.PokerUtil;
import com.poker.common.wifi.listener.MessageListener;
import com.poker.common.wifi.message.GameMessage;
import com.poker.common.wifi.message.MessageFactory;
import com.poker.common.wifi.message.PeopleMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

@SuppressLint("NewApi")
public class GameActivity extends AbsGameActivity implements OnClickListener{
    private ImageButton reback;

    private Button follow, add, quit, tips, autopass, autopq, autofollow,startGame;

    private TextView current_rank, chips,desk_tips_text;
    
    private TextView roomName,dealText,roomRound;

    private ImageView img_card_tip, autofollow_check, autopass_check, autopq_check, allin,addnumber;

//    private RelativeLayout sidepool_layout1, sidepool_layout2, sidepool_layout3, sidepool_layout4,
//            sidepool_layout5,mainpool_layout;
    
    private RelativeLayout pools[];
    
    private TextView poolsText[];
    
    private LinearLayout desk_tips;
    
    // 座位一永远都是自己
    private LeftSeatView seat_one, seat_five, seat_six;

    private RightSeatView seat_four, seat_two, seat_three;

    private ImageView public_poker1, public_poker2, public_poker3, public_poker4, public_poker5;

    private Animation public_poker_anim, tip_anim;

    private Boolean autopass_checked = false, autopq_checked = false, autofollow_checked = false;

    // 房间信息
    private Room room;

    // 公共牌
//    private ArrayList<Poker> public_poker;
    
    private ArrayList<Poker> All_poker;

    // 
    private ArrayList<ClientPlayer> playerList;

    // 玩家列表
    private HashMap<String, AbsPlayer> playList;
    
    private ArrayList chipList;

    private View CardTypeView = null;// 

    private PopupWindow CardTypeWin = null; // 牌型弹窗

    private VerticalSeekBar seekbar;

    private RelativeLayout allin_layout;

    private String max = "";
    
    private ClientPlayer currentPlay;
    
    private WorkHandler wHandler = null;
    
    private RelativeLayout img_card_tip_layout;

    private boolean isInOrOut = false;
    
    private int DIndex = -1;
    
    private int bigBlindIndex = -1;
    
    private int maxChipIndex = -1;
    
    private int currentOptionPerson = -1;
    
	private int currentPlayIndex = -1;
	
    private boolean isEnd = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);

        registerListener();
        init();
        initPokerAnim();
        initBar();
    }

    private void initBar() {
        // TODO Auto-generated method stub
        seekbar.setProgress(0);
        max = seat_one.getPersonView().getPersonMoney().getText().toString();
        seekbar.setMax(Integer.valueOf(max));
    }

    private void init() {
        // TODO Auto-generated method stub
        reback = (ImageButton) findViewById(R.id.reback);
        follow = (Button) findViewById(R.id.follow);
        add = (Button) findViewById(R.id.add);
        quit = (Button) findViewById(R.id.quit);
        tips = (Button) findViewById(R.id.tips);
        autopass = (Button) findViewById(R.id.autopass);
        autopq = (Button) findViewById(R.id.autopq);
        startGame = (Button) findViewById(R.id.desk_tips_start_game_btn);
        autofollow = (Button) findViewById(R.id.autofollow);
        addnumber = (ImageView)findViewById(R.id.addnumber);
        
        current_rank = (TextView) findViewById(R.id.player_current_rank);
        seat_one = (LeftSeatView) findViewById(R.id.seat_one);
        seat_one.setPokerStyle(0);
        seat_two = (RightSeatView) findViewById(R.id.seat_two);
        seat_three = (RightSeatView) findViewById(R.id.seat_three);
        seat_four = (RightSeatView) findViewById(R.id.seat_four);
        seat_five = (LeftSeatView) findViewById(R.id.seat_five);
        seat_six = (LeftSeatView) findViewById(R.id.seat_six);
        
        pools = new RelativeLayout[6];
        pools[1] = (RelativeLayout)findViewById(R.id.sidepool_layout1);
        pools[2] = (RelativeLayout)findViewById(R.id.sidepool_layout2);
        pools[3] = (RelativeLayout)findViewById(R.id.sidepool_layout3);
        pools[4] = (RelativeLayout)findViewById(R.id.sidepool_layout4);
        pools[5] = (RelativeLayout)findViewById(R.id.sidepool_layout5);
        pools[0] = (RelativeLayout)findViewById(R.id.mainpool_layout);
        
        poolsText = new TextView[6];
        poolsText[0] = (TextView)findViewById(R.id.mainpool_txt);
        poolsText[1] = (TextView)findViewById(R.id.sidepool1_txt);
        poolsText[2] = (TextView)findViewById(R.id.sidepool2_txt);
        poolsText[3] = (TextView)findViewById(R.id.sidepool3_txt);
        poolsText[4] = (TextView)findViewById(R.id.sidepool4_txt);
        poolsText[5] = (TextView)findViewById(R.id.sidepool5_txt);
        
        chipList = new ArrayList();
        desk_tips = (LinearLayout)findViewById(R.id.desk_tips);
        roomName = (TextView)findViewById(R.id.room_name);
        dealText = (TextView)findViewById(R.id.deal_text);
        roomRound = (TextView)findViewById(R.id.room_round_text);
        desk_tips_text = (TextView)findViewById(R.id.desk_tips_text);
        
        roomName.setVisibility(View.INVISIBLE);
        dealText.setVisibility(View.INVISIBLE);
        roomRound.setVisibility(View.INVISIBLE);
        pools[1].setVisibility(View.INVISIBLE);
        pools[2].setVisibility(View.INVISIBLE);
        pools[3].setVisibility(View.INVISIBLE);
        pools[4].setVisibility(View.INVISIBLE);
        pools[5].setVisibility(View.INVISIBLE);
        pools[0].setVisibility(View.INVISIBLE);
        seat_two.setVisibility(View.INVISIBLE);
        seat_three.setVisibility(View.INVISIBLE);
        seat_four.setVisibility(View.INVISIBLE);
        seat_five.setVisibility(View.INVISIBLE);
        seat_six.setVisibility(View.INVISIBLE);
        
        public_poker1 = (ImageView) findViewById(R.id.poker1);
        public_poker2 = (ImageView) findViewById(R.id.poker2);
        public_poker3 = (ImageView) findViewById(R.id.poker3);
        public_poker4 = (ImageView) findViewById(R.id.poker4);
        public_poker5 = (ImageView) findViewById(R.id.poker5);
        autofollow_check = (ImageView) findViewById(R.id.autofollow_checked);
        autopass_check = (ImageView) findViewById(R.id.autopass_checked);
        autopq_check = (ImageView) findViewById(R.id.autopq_checked);

        reback.setOnClickListener(this);
        follow.setOnClickListener(this);
        add.setOnClickListener(this);
        quit.setOnClickListener(this);
        tips.setOnClickListener(this);
        autopass.setOnClickListener(this);
        autopq.setOnClickListener(this);
        autofollow.setOnClickListener(this);
        addnumber.setOnClickListener(this);
        startGame.setOnClickListener(this);
        setPublicPokerVisibility(View.INVISIBLE);

        seekbar = (VerticalSeekBar) findViewById(R.id.ChipSeekbar);
        allin_layout = (RelativeLayout) findViewById(R.id.allin_layout);
        allin_layout.setVisibility(View.INVISIBLE);
        chips = (TextView) findViewById(R.id.chips);
        allin = (ImageView) findViewById(R.id.allin);
        seekbar.setOnSeekBarChangeListener(mSeekbarListener);

        room = getIntent().getParcelableExtra("Room");
        wHandler = new WorkHandler(getMainLooper());
        playerList = new ArrayList<ClientPlayer>();
        if (!app.isServer()) {
            currentPlay = app.cp;
            playerList.add(currentPlay);
            sendMessage(MessageFactory.newPeopleMessage(false, false, playerList, null,null,null));
            desk_tips_text.setText(R.string.throw_people);
            startGame.setVisibility(View.INVISIBLE);
        } else {
            currentPlay = app.sp;
            updateRoom(room);
            currentPlay.getInfo().setBaseMoney(room.getBasicChips());
            currentPlay.getInfo().setAroundChip(0);
            desk_tips_text.setText(R.string.waiting_people);
            playerList.add(currentPlay); 
        }
        updateChairByPlayer(0,currentPlay);
    }

    private OnSeekBarChangeListener mSeekbarListener = new OnSeekBarChangeListener() {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            chips.setText("" + progress);
            if (chips.getText().equals(max)) {
                allin.setVisibility(View.VISIBLE);
            } else {
                allin.setVisibility(View.INVISIBLE);
            }
        }
    };

    public void initPokerAnim() {
        public_poker_anim = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.poker_scale);
        tip_anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.tip_enter);
    }
    
    public void updateRoom(Room room) {
        if (room != null) {
            roomName.setText("房間 : "+room.getName().trim());
            dealText.setText((int)room.getMinStake()/2+"/"+(int)room.getMinStake());
            roomRound.setText(String.valueOf(room.getInnings()));
            roomName.setVisibility(View.VISIBLE);
            dealText.setVisibility(View.VISIBLE);
            roomRound.setVisibility(View.VISIBLE);
        }
    }

    public void setPublicPokerVisibility(int i) {
        public_poker1.setVisibility(i);
        public_poker2.setVisibility(i);
        public_poker3.setVisibility(i);
        public_poker4.setVisibility(i);
        public_poker5.setVisibility(i);
    }
    
    public void setPublicPoker(ArrayList<Poker> pokers){
        public_poker1.setImageResource(pokers.get(0).getPokerImageId());
        public_poker2.setImageResource(pokers.get(1).getPokerImageId());
        public_poker3.setImageResource(pokers.get(2).getPokerImageId());
        public_poker4.setImageResource(pokers.get(3).getPokerImageId());
        public_poker5.setImageResource(pokers.get(4).getPokerImageId());
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
            public_poker1.setAnimation(public_poker_anim);
            public_poker2.setAnimation(public_poker_anim);
            public_poker3.setAnimation(public_poker_anim);
            wHandler.removeMessages(WorkHandler.MSG_COUNT_POOL);
            wHandler.sendEmptyMessage(WorkHandler.MSG_COUNT_POOL);
            return;
        } else if (public_poker4.getVisibility() != View.VISIBLE) {
        	public_poker4.setImageResource(All_poker.get(All_poker.size()-2).getPokerImageId());
            public_poker4.setVisibility(View.VISIBLE);
            public_poker4.startAnimation(public_poker_anim);
            wHandler.removeMessages(WorkHandler.MSG_COUNT_POOL);
            wHandler.sendEmptyMessage(WorkHandler.MSG_COUNT_POOL);
            return;
        } else if (public_poker5.getVisibility() != View.VISIBLE) {
        	public_poker5.setImageResource(All_poker.get(All_poker.size()-1).getPokerImageId());
            public_poker5.setVisibility(View.VISIBLE);
            public_poker5.startAnimation(public_poker_anim);
            wHandler.removeMessages(WorkHandler.MSG_COUNT_POOL);
            wHandler.sendEmptyMessage(WorkHandler.MSG_COUNT_POOL);
            return;
        } else {
            return;
        }
    }
    
    public void startGame(){
        desk_tips.setVisibility(View.INVISIBLE);
        currentPlayIndex  = findIndexWithIPinList(playerList);
        app.isGameStarted = true;
        currentPlay.setInfo(playerList.get(currentPlayIndex).getInfo());
//        currentPlay.getInfo().setBaseMoney(room.getBasicChips());
//        currentPlay.getInfo().setQuit(false);
        if(app.isServer()){
            if(isInOrOut||DIndex == -1){
    //            重新生成D
            	newDIndex();
            }else {
                DIndex++;
            }
            
            if(DIndex>=playerList.size()){
                DIndex = DIndex%playerList.size();
            }
    //      每次生成D之后都会发底牌
            sendMessage(MessageFactory.newGameMessage(false, GameMessage.ACTION_SEND_BOOL, -1, String.valueOf(DIndex)));
            wHandler.removeMessages(WorkHandler.MSG_SEND_BOOL);
            wHandler.sendEmptyMessage(WorkHandler.MSG_SEND_BOOL);
        }
    }
    
    public void newDIndex(){
        Random r = new Random();
        DIndex = r.nextInt(playerList.size());
    }

    // 发底牌
    public void bottomDeal() {
    	int index = findIndexWithIPinList(this.playerList);
    	bigBlindIndex = (DIndex+2)%playerList.size();
    	maxChipIndex = bigBlindIndex;
    	setDIndex(DIndex);
    	setChairChip(bigBlindIndex,room.getMinStake(),View.VISIBLE);
    	setChairChip((DIndex+1)%playerList.size(),room.getMinStake()/2,View.VISIBLE);
    	this.currentOptionPerson = (maxChipIndex+1)%playerList.size();
    	seat_one.setPokerImage(All_poker.get(index*2).getPokerImageId(), All_poker.get(index*2+1).getPokerImageId());
        seat_one.ownPokerAnim();
        seat_two.ownPokerAnim();
        seat_three.ownPokerAnim();
        seat_four.ownPokerAnim();
        seat_five.ownPokerAnim();
        seat_six.ownPokerAnim();
        checkIsMeOption();
    }
    
    public void checkIsMeOption(){
         if(playerList.get(currentOptionPerson).getInfo().getId().equals(currentPlay.getInfo().getId())){
             
             showToast("currentOptionPerson: "+currentOptionPerson+" currentOptionPerson quit: "+
             playerList.get(currentOptionPerson).getInfo().isQuit()+" currentPlay: "+currentPlay.getInfo().isQuit());
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
//              所有玩家都弃牌，只剩一个玩家，进行分钱
                showToast("一轮游戏结束");
                return;
            }else{
                optionChoice(true);
            }
        }else{
            optionChoice(false);
        }
    }
    
    public void doOption(int cmd){
//    	if(currentOptionPerson == maxChipIndex){
//    		isEnd = true;
//    	}else{
//    		isEnd = false;
//    	}
//    	if(isEnd&&currentPlayCount() == 1){
////    	    所有玩家都弃牌，只剩一个玩家，进行分钱
//    	    showToast("一轮游戏结束");
//    	    return;
//    	}
    	if(!currentPlay.getInfo().isQuit()){
    		if(currentPlay.getInfo().getBaseMoney()>0){
    			if(cmd == R.id.follow){
    				followAction();
    			}else if(cmd == R.id.addnumber){
    				addChipAction();
    			}else if(cmd == R.id.quit){
    				doQuit();
    			}
    		}
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
    
    public void doQuit(){
        Log.i("Rinfon", currentOptionPerson+"");
        sendMessage(MessageFactory.newGameMessage(false, GameMessage.ACTION_ABANDOM, -1, String.valueOf(currentOptionPerson)));
        if(app.isServer()){
            Message message = new Message();
            message.what = WorkHandler.MSG_ACTION_ABANDOM;
            message.arg1 = findPlayer(currentPlay);
            wHandler.sendMessage(message);
            
            currentOptionPerson = (currentOptionPerson+1)%playerList.size();
            sendMessage(MessageFactory.newGameMessage(false, GameMessage.ACTION_CURRENT_PERSON,
                    -1, String.valueOf(currentOptionPerson)));
            wHandler.removeMessages(WorkHandler.MSG_CHECKISME);
            wHandler.sendEmptyMessage(WorkHandler.MSG_CHECKISME);
        }
    }
    
    public void addChipAction(){
    	int money = Integer.parseInt(chips.getText().toString());
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
//					if(playerList.get(DIndex).getInfo().isQuit()){
						int i = DIndex;
						while(playerList.get(i%playerList.size()).getInfo().isQuit()){
							i = Math.abs(i-1);
//							i++;
						}
						maxChipIndex = i;
//					}
					
//		    		maxChipIndex = DIndex;
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
				}
				wHandler.removeMessages(WorkHandler.MSG_CHECKISME);
	            wHandler.sendEmptyMessage(WorkHandler.MSG_CHECKISME);
			}
    }
    
    public void optionChoice(boolean choice){
        if(choice){
            follow.setVisibility(View.VISIBLE);
            add.setVisibility(View.VISIBLE);
            quit.setVisibility(View.VISIBLE);
            autofollow.setVisibility(View.INVISIBLE);
            autopass.setVisibility(View.INVISIBLE);
            autopq.setVisibility(View.INVISIBLE);
            autofollow_check.setVisibility(View.INVISIBLE);
            autopass_check.setVisibility(View.INVISIBLE);
            autopq_check.setVisibility(View.INVISIBLE);
        }else{
            follow.setVisibility(View.INVISIBLE);
            add.setVisibility(View.INVISIBLE);
            quit.setVisibility(View.INVISIBLE);
            autofollow.setVisibility(View.VISIBLE);
            autofollow_check.setVisibility(View.VISIBLE);
            autopass.setVisibility(View.VISIBLE);
            autopass_check.setVisibility(View.VISIBLE);
            autopq.setVisibility(View.VISIBLE);
            autopq_check.setVisibility(View.VISIBLE);
        }
    }
    
    public void setBaseMoney(ClientPlayer player,int money){
    	 int playerIndex = findPlayer(player);
    	 if(playerIndex == Integer.parseInt(seat_one.getTag().toString())){
             seat_one.getPersonView().setPersonMoney(String.valueOf(money));
         }
    	 if(playerIndex == Integer.parseInt(seat_two.getTag().toString())){
    		 seat_two.getPersonView().setPersonMoney(String.valueOf(money));
    	 }
    	 if(playerIndex == Integer.parseInt(seat_three.getTag().toString())){
    		 seat_three.getPersonView().setPersonMoney(String.valueOf(money));
    	 }
    	 if(playerIndex == Integer.parseInt(seat_four.getTag().toString())){
    		 seat_four.getPersonView().setPersonMoney(String.valueOf(money));
    	 }
    	 if(playerIndex == Integer.parseInt(seat_five.getTag().toString())){
    		 seat_five.getPersonView().setPersonMoney(String.valueOf(money));
    	 }
    	 if(playerIndex == Integer.parseInt(seat_six.getTag().toString())){
    	     seat_six.getPersonView().setPersonMoney(String.valueOf(money));
    	 }
    	 playerList.get(playerIndex).getInfo().setBaseMoney(money);
    }
    
    public void setChairChip(int playerIndex,int money,int isShow){
        if(money == -1){
//            如果是-1，则不需要设置
            return ;
        }
    	if(money == 0){
    		playerList.get(playerIndex).getInfo().setAroundChip(0);
    	}else{
	        playerList.get(playerIndex).getInfo().setBaseMoney(playerList.get(playerIndex).getInfo().getBaseMoney()
	                +playerList.get(playerIndex).getInfo().getAroundChip() - money);
	        playerList.get(playerIndex).getInfo().setAroundChip(money);
    	}
    	 if(playerIndex == Integer.parseInt(seat_one.getTag().toString())){
             seat_one.setCurrentChip(money, isShow);
             seat_one.getPersonView().setPersonMoney(String.valueOf(playerList.get(playerIndex).getInfo().getBaseMoney()));
         }
    	 if(playerIndex == Integer.parseInt(seat_two.getTag().toString())){
    		 seat_two.setCurrentChip(money, isShow);
    		 seat_two.getPersonView().setPersonMoney(String.valueOf(playerList.get(playerIndex).getInfo().getBaseMoney()));
    	 }
    	 if(playerIndex == Integer.parseInt(seat_three.getTag().toString())){
    		 seat_three.setCurrentChip(money, isShow);
    		 seat_three.getPersonView().setPersonMoney(String.valueOf(playerList.get(playerIndex).getInfo().getBaseMoney()));
    	 }
    	 if(playerIndex == Integer.parseInt(seat_four.getTag().toString())){
    		 seat_four.setCurrentChip(money, isShow);
    		 seat_four.getPersonView().setPersonMoney(String.valueOf(playerList.get(playerIndex).getInfo().getBaseMoney()));
    	 }
    	 if(playerIndex == Integer.parseInt(seat_five.getTag().toString())){
    		 seat_five.setCurrentChip(money, isShow);
    		 seat_five.getPersonView().setPersonMoney(String.valueOf(playerList.get(playerIndex).getInfo().getBaseMoney()));
    	 }
    	 if(playerIndex == Integer.parseInt(seat_six.getTag().toString())){
    	     seat_six.setCurrentChip(money, isShow);
    	     seat_six.getPersonView().setPersonMoney(String.valueOf(playerList.get(playerIndex).getInfo().getBaseMoney()));
    	 }
    	 if(playerIndex == currentPlayIndex){
    		 currentPlay.setInfo(playerList.get(playerIndex).getInfo());
    	 }
    }
    
    public void setDIndex(int index){
        if(DIndex == Integer.parseInt(seat_one.getTag().toString()))
            seat_one.setBigBlindvisibility(View.VISIBLE);
        else
            seat_one.setBigBlindvisibility(View.INVISIBLE);
        if(DIndex == Integer.parseInt(seat_two.getTag().toString()))
            seat_two.setBigBlindvisibility(View.VISIBLE);
        else
            seat_two.setBigBlindvisibility(View.INVISIBLE);
        if(DIndex == Integer.parseInt(seat_three.getTag().toString()))
            seat_three.setBigBlindvisibility(View.VISIBLE);
        else
            seat_three.setBigBlindvisibility(View.INVISIBLE);
        if(DIndex == Integer.parseInt(seat_four.getTag().toString()))
            seat_four.setBigBlindvisibility(View.VISIBLE);
        else
            seat_four.setBigBlindvisibility(View.INVISIBLE);
        if(DIndex == Integer.parseInt(seat_five.getTag().toString()))
            seat_five.setBigBlindvisibility(View.VISIBLE);
        else
            seat_five.setBigBlindvisibility(View.INVISIBLE);
        if(DIndex == Integer.parseInt(seat_six.getTag().toString()))
            seat_six.setBigBlindvisibility(View.VISIBLE);
        else
            seat_six.setBigBlindvisibility(View.INVISIBLE);
    }
   
//    重置所有玩家
    public void resetAllPlayStatus() {
        seat_one.getLeft_seat_poker1().setVisibility(View.INVISIBLE);
        seat_one.getLeft_seat_poker2().setVisibility(View.INVISIBLE);
        seat_two.getRight_seat_poker1().setVisibility(View.INVISIBLE);
        seat_two.getRight_seat_poker2().setVisibility(View.INVISIBLE);
        seat_three.getRight_seat_poker1().setVisibility(View.INVISIBLE);
        seat_three.getRight_seat_poker2().setVisibility(View.INVISIBLE);
        seat_four.getRight_seat_poker1().setVisibility(View.INVISIBLE);
        seat_four.getRight_seat_poker2().setVisibility(View.INVISIBLE);
        seat_five.getLeft_seat_poker1().setVisibility(View.INVISIBLE);
        seat_five.getLeft_seat_poker2().setVisibility(View.INVISIBLE);
        seat_six.getLeft_seat_poker1().setVisibility(View.INVISIBLE);
        seat_six.getLeft_seat_poker2().setVisibility(View.INVISIBLE);
        setPublicPokerVisibility(View.INVISIBLE);
    }
    
    public void resetAllPlayerAroundChaip(){
        for(int i = 0;i<playerList.size();i++){
            setChairChip(i, 0, View.INVISIBLE);
        }
    }
    
    public void chairUpdate(ArrayList<ClientPlayer> playerList){
        int index = findIndexWithIPinList(playerList);
        int chairIndex = 0;
        if(index!=-1){
            updateChairByPlayer(chairIndex++,playerList.get(index));
//            更新自己后面的玩家桌位
            for(int i = index+1;i<playerList.size();i++){
                updateChairByPlayer(chairIndex++,playerList.get(i));
            }
            
//            更新自己前面的玩家桌位
            for(int i = 0;i<index;i++){
                updateChairByPlayer(chairIndex++,playerList.get(i));
            }
            for(int i = chairIndex;i<6;i++){
                hideChairByPlayer(chairIndex++);
            }
        }
        
    }
    
    public void updateChairByPlayer(int index,ClientPlayer play){
        switch (index) {
           case 0:
               seat_one.setPersonViewTitle(play.getInfo().getName());
               seat_one.setTag(findPlayer(play));
               if(seat_one.getVisibility()!=View.VISIBLE)
                   seat_one.setVisibility(View.VISIBLE);
               break;
           case 1:
               seat_two.setPersonViewTitle(play.getInfo().getName());
               seat_two.setTag(findPlayer(play));
               if(seat_two.getVisibility()!=View.VISIBLE)
                   seat_two.setVisibility(View.VISIBLE);
               break;
           case 2:
               seat_three.setPersonViewTitle(play.getInfo().getName());
               seat_three.setTag(findPlayer(play));
               if(seat_three.getVisibility()!=View.VISIBLE)
                   seat_three.setVisibility(View.VISIBLE);
               break;
           case 3:
               seat_four.setPersonViewTitle(play.getInfo().getName());
               seat_four.setTag(findPlayer(play));
               if(seat_four.getVisibility()!=View.VISIBLE)
                   seat_four.setVisibility(View.VISIBLE);
               break;
           case 4:
               seat_five.setPersonViewTitle(play.getInfo().getName());
               seat_five.setTag(findPlayer(play));
               if(seat_five.getVisibility()!=View.VISIBLE)
                   seat_five.setVisibility(View.VISIBLE);
               break;
           case 5:
               seat_six.setPersonViewTitle(play.getInfo().getName());
               seat_six.setTag(findPlayer(play));
               if(seat_six.getVisibility()!=View.VISIBLE)
                   seat_six.setVisibility(View.VISIBLE);
               break;
        }
    }
    
    public void hideChairByPlayer(int index){
        switch (index) {
           case 0:
               seat_one.setVisibility(View.INVISIBLE);
               seat_one.setTag(-1);
               break;
           case 1:
               seat_two.setVisibility(View.INVISIBLE);
               seat_two.setTag(-1);
               break;
           case 2:
               seat_three.setVisibility(View.INVISIBLE);
               seat_three.setTag(-1);
               break;
           case 3:
               seat_four.setVisibility(View.INVISIBLE);
               seat_four.setTag(-1);
               break;
           case 4:
               seat_five.setVisibility(View.INVISIBLE);
               seat_five.setTag(-1);
               break;
           case 5:
               seat_six.setVisibility(View.INVISIBLE);
               seat_six.setTag(-1);
               break;
        }
    }
    
    public int findIndexWithIPinList(ArrayList<ClientPlayer> playerList){
        for(int i = playerList.size()-1;i>=0;i--){
            if(currentPlay.getInfo().getId().equals(playerList.get(i).getInfo().getId())){
                return i;
            }
        }
        return -1;
    }
    
    public int findPlayer(ClientPlayer player){
        for(int i = playerList.size()-1;i>=0;i--){
            if(player.getInfo().getId().equals(playerList.get(i).getInfo().getId())){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.add:
                allin_layout.setVisibility(View.VISIBLE);
                add.setVisibility(View.INVISIBLE);
                break;
            case R.id.reback:
                playerList.clear();
                playerList.add(currentPlay);
                sendMessage(MessageFactory.newPeopleMessage(false, true, playerList, null,null,"server exit"));
                Intent i = new Intent(GameActivity.this, MainActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.follow:
                closeCardTip();
                setAddSeekBar(false);
                doOption(R.id.follow);
                break;
            case R.id.quit:
                closeCardTip();
                setAddSeekBar(false);
                doOption(R.id.quit);
                break;
            case R.id.tips:
                setAddSeekBar(false);
                setCardTip(View.VISIBLE);
                break;
            case R.id.autopass:
                autopass_checked = !autopass_checked;
                if (autopass_checked) {
                    autopq_checked = autofollow_checked = false;
                }
                setAutoChecked(autopass_checked, autopq_checked, autofollow_checked);
                break;
            case R.id.autopq:
                autopq_checked = !autopq_checked;
                if (autopq_checked) {
                    autopass_checked = autofollow_checked = false;
                }
                setAutoChecked(autopass_checked, autopq_checked, autofollow_checked);
                break;
            case R.id.autofollow:
                autofollow_checked = !autofollow_checked;
                if (autofollow_checked) {
                    autopq_checked = autopass_checked = false;
                }
                setAutoChecked(autopass_checked, autopq_checked, autofollow_checked);
                break;
            case R.id.desk_tips_start_game_btn:
                setAddSeekBar(false);
                if(this.playerList.size()>=2){
                    desk_tips.setVisibility(View.INVISIBLE);
                    All_poker = PokerUtil.getPokers(playerList.size());
                    sendMessage(MessageFactory.newPeopleMessage(true, false, playerList, All_poker,null,null));
                    wHandler.removeMessages(WorkHandler.MSG_START_GAME);
                    wHandler.sendEmptyMessage(WorkHandler.MSG_START_GAME);
                }else{
                    Toast.makeText(getApplicationContext(), "还没人齐啊扑街", 1000).show();
                }
                break;
            case R.id.addnumber:
                setAddSeekBar(false);
                add.setVisibility(View.VISIBLE);
                doOption(R.id.addnumber);
            default:
                break;
        }
    }

    private void setAutoChecked(boolean a, boolean b, boolean c) {
        // TODO Auto-generated method stub
        if (a) {
            autopass_check.setImageResource(R.drawable.img_auto_checked);
        } else {
            autopass_check.setImageResource(R.drawable.img_auto_uncheck);
        }
        if (b) {
            autopq_check.setImageResource(R.drawable.img_auto_checked);
        } else {
            autopq_check.setImageResource(R.drawable.img_auto_uncheck);
        }
        if (c) {
            autofollow_check.setImageResource(R.drawable.img_auto_checked);
        } else {
            autofollow_check.setImageResource(R.drawable.img_auto_uncheck);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

    public void setCardTip(int i) {
        showImageCard();
    }

    private void showImageCard() {
        // TODO Auto-generated method stub
        LayoutInflater inflater = LayoutInflater.from(GameActivity.this);
        
        CardTypeView = inflater.inflate(R.layout.card_tip, null); 
        img_card_tip_layout = (RelativeLayout)CardTypeView.findViewById(R.id.img_card_tip_layout);
        img_card_tip_layout.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if(CardTypeView != null && CardTypeView.isShown())
						CardTypeWin.dismiss();
				}
				return true;
			}
		});
        CardTypeWin = new PopupWindow(CardTypeView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
                true); 
        CardTypeWin.setBackgroundDrawable(new BitmapDrawable());
        CardTypeWin.setOutsideTouchable(true);
        CardTypeWin.setFocusable(true);
        CardTypeWin.setAnimationStyle(R.style.cardTypeAnimation);
        CardTypeWin.showAtLocation(GameActivity.this.tips, Gravity.LEFT, 0, 0); 
        CardTypeWin.update();
    }
    
    public void playerAbandom(int playerIndex){
        playerList.get(playerIndex).getInfo().setQuit(true);
        if(playerIndex == Integer.parseInt(seat_one.getTag().toString())){
            seat_one.setPersonViewTitle("弃牌");
            seat_one.setPokerVisibility(false);
        }
        else if(playerIndex == Integer.parseInt(seat_two.getTag().toString())){
            seat_two.setPersonViewTitle("弃牌");
            seat_two.setPokerVisibility(false);
        }
        else if(playerIndex == Integer.parseInt(seat_three.getTag().toString())){
            seat_three.setPersonViewTitle("弃牌");
            seat_three.setPokerVisibility(false);
        }
        else if(playerIndex == Integer.parseInt(seat_four.getTag().toString())){
            seat_four.setPersonViewTitle("弃牌");
            seat_four.setPokerVisibility(false);
        }
        else if(playerIndex == Integer.parseInt(seat_five.getTag().toString())){
            seat_five.setPersonViewTitle("弃牌");
            seat_five.setPokerVisibility(false);
        }
        else if(playerIndex == Integer.parseInt(seat_six.getTag().toString())){
            seat_six.setPersonViewTitle("弃牌");
            seat_six.setPokerVisibility(false);
        }
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
    
    public void countMoney(){
        ArrayList callBack = new ArrayList();
        chipList.clear();
        for(int i = playerList.size()-1;i>=0;i--){
            chipList.add(playerList.get(i).getInfo().getAroundSumChip());
            
        }
        PokerUtil.CountChips(chipList, callBack);
        setChipPool(callBack);
        
    }
    
    public void setChipPool(ArrayList chips){
        for(int i = 0;i<chips.size();i++){
            pools[i].setVisibility(View.VISIBLE);
            poolsText[i].setText(chips.get(i).toString());
            poolsText[i].setVisibility(View.VISIBLE);
        }
    }

    public void closeCardTip() {
    	
    }
    
    public void setAddSeekBar(boolean isShow){
        if(isShow){
            allin_layout.setVisibility(View.VISIBLE);
            return ;
        }
        allin_layout.setVisibility(View.INVISIBLE);
        return ;
    }
    

    @Override
    public void onSendSuccess() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onServerReceive(PeopleMessage msg) {
        // TODO Auto-generated method stub
        if(msg.isExit()){
            int exitIndex = findPlayer(msg.getPlayerList().get(0));
            if(exitIndex!=-1){
                playerList.remove(exitIndex);
                wHandler.removeMessages(WorkHandler.MSG_CHAIR_UPDATE);
                wHandler.sendEmptyMessage(WorkHandler.MSG_CHAIR_UPDATE);
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
            wHandler.removeMessages(WorkHandler.MSG_CHAIR_UPDATE);
            wHandler.sendEmptyMessage(WorkHandler.MSG_CHAIR_UPDATE);
		}
    }

    @Override
    public void onServerReceive(GameMessage msg) {
        // TODO Auto-generated method stub
    	switch(msg.getAction()){
    	case GameMessage.ACTION_FINISH_OPTIOIN:
    		currentOptionPerson = (currentOptionPerson+1)%playerList.size();
    		sendMessage(MessageFactory.newGameMessage(false, GameMessage.ACTION_CURRENT_PERSON,
    				-1, String.valueOf(currentOptionPerson)));
    		wHandler.removeMessages(WorkHandler.MSG_CHECKISME);
            wHandler.sendEmptyMessage(WorkHandler.MSG_CHECKISME);
    		break;
    	case GameMessage.ACTION_SHOW_PUBLIC_POKER:
    		currentOptionPerson = (DIndex+1)%playerList.size();
    		maxChipIndex = DIndex;
    		sendMessage(MessageFactory.newGameMessage(false, GameMessage.ACTION_SHOW_PUBLIC_POKER,
    				-1, String.valueOf(DIndex)));
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
            
//            加注后继续判断下一家
            currentOptionPerson = (currentOptionPerson+1)%playerList.size();
            sendMessage(MessageFactory.newGameMessage(false, GameMessage.ACTION_CURRENT_PERSON,
                    -1, String.valueOf(currentOptionPerson)));
            wHandler.removeMessages(WorkHandler.MSG_CHECKISME);
            wHandler.sendEmptyMessage(WorkHandler.MSG_CHECKISME);
    	    break;
    	case GameMessage.ACTION_UPDATE_MONEY:
    	    sendMessage(msg);
    	    
    	    Message message2 = new Message();
    	    message2.what = WorkHandler.MSG_ADD_BET;
    	    message2.arg1 = Integer.parseInt(msg.getExtra());
    	    message2.arg2 = msg.getAmount();
            wHandler.sendMessage(message2);
            break;
        case GameMessage.ACTION_ABANDOM:
            sendMessage(msg);
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
    public void onClientReceive(PeopleMessage msg) {
        // TODO Auto-generated method stub
        if (msg.isExit()) {
        	if("server exit".equals(msg.getExtra())){
        		Toast.makeText(getApplicationContext(), "Server exit", 1000).show();
                finish();
        	}
            playerList.remove(msg.getPlayerList().get(0));
            wHandler.removeMessages(WorkHandler.MSG_CHAIR_UPDATE);
            wHandler.sendEmptyMessage(WorkHandler.MSG_CHAIR_UPDATE);
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
                wHandler.removeMessages(WorkHandler.MSG_ROOM_UPDATE);
                wHandler.sendEmptyMessage(WorkHandler.MSG_ROOM_UPDATE);
            }   
            wHandler.removeMessages(WorkHandler.MSG_CHAIR_UPDATE);
            wHandler.sendEmptyMessage(WorkHandler.MSG_CHAIR_UPDATE);
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
    	case GameMessage.ACTION_FINISH_OPTIOIN:
    		currentOptionPerson = (currentOptionPerson+1)%playerList.size();
    		wHandler.removeMessages(WorkHandler.MSG_CHECKISME);
            wHandler.sendEmptyMessage(WorkHandler.MSG_CHECKISME);
    		break;
    	case GameMessage.ACTION_SHOW_PUBLIC_POKER:
    		DIndex = Integer.parseInt(msg.getExtra().toString());
    		currentOptionPerson = (DIndex+1)%playerList.size();
			maxChipIndex = DIndex;
			wHandler.removeMessages(WorkHandler.MSG_SHOW_PUBLIC_POKER);
	        wHandler.sendEmptyMessage(WorkHandler.MSG_SHOW_PUBLIC_POKER);
			wHandler.removeMessages(WorkHandler.MSG_CHECKISME);
	        wHandler.sendEmptyMessage(WorkHandler.MSG_CHECKISME);
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
        case GameMessage.ACTION_UPDATE_MONEY:
            Message message2 = new Message();
            message2.what = WorkHandler.MSG_ADD_BET;
            message2.arg1 = Integer.parseInt(msg.getExtra());
            message2.arg2 = msg.getAmount();
            wHandler.sendMessage(message2);
            break;
        case GameMessage.ACTION_ABANDOM:
            Message message3 = new Message();
            message3.what = WorkHandler.MSG_ACTION_ABANDOM;
            message3.arg1 = Integer.parseInt(msg.getExtra());
            wHandler.sendMessage(message3);
            break;
        case GameMessage.ACTION_NEXT_ROUND:
//            showToast("游戏结束");
            wHandler.removeMessages(WorkHandler.MSG_NEXT_ROUND);
            wHandler.sendEmptyMessage(WorkHandler.MSG_NEXT_ROUND);
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
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	public void showToast(String str){
	    Toast.makeText(getApplicationContext(), str, 1000).show();;
	}
	
	private class WorkHandler extends Handler {
        
	    private static final int MSG_CHAIR_UPDATE = 1;
	    private static final int MSG_SEND_BOOL = 2;
	    private static final int MSG_START_GAME = 3;
	    private static final int MSG_ROOM_UPDATE = 4;
	    private static final int MSG_CHECKISME = 5;
	    private static final int MSG_SHOW_PUBLIC_POKER = 6;
	    private static final int MSG_ADD_BET =7;
//	    private static final int MSG_UPDATE_MONEY =8;
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
                    chairUpdate(playerList);
                    break;
                case MSG_SEND_BOOL:
                	bottomDeal();
                	break;
                case MSG_START_GAME:
                	startGame();
                	break;
                case MSG_ROOM_UPDATE:
                    updateRoom(room);
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
                    setChairChip(msg.arg1, msg.arg2, View.VISIBLE);
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

	@Override
	public void clientDecrease(String clientName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void disconnectFromServer(int sec) {
		// TODO Auto-generated method stub
		
	}
}
