﻿
package com.poker.common.activity;

import com.poker.common.R;
import com.poker.common.customcontrols.VerticalSeekBar;
import com.poker.common.entity.AbsPlayer;
import com.poker.common.entity.ClientPlayer;
import com.poker.common.entity.Poker;
import com.poker.common.entity.Room;
import com.poker.common.util.Util;
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
import com.poker.common.customcontrols.VerticalSeekBar;
import com.poker.common.entity.AbsPlayer;
import com.poker.common.entity.ClientPlayer;
import com.poker.common.entity.Poker;
import com.poker.common.entity.Room;
import com.poker.common.util.Util;
import com.poker.common.wifi.listener.MessageListener;
import com.poker.common.wifi.message.GameMessage;
import com.poker.common.wifi.message.MessageFactory;
import com.poker.common.wifi.message.PeopleMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

@SuppressLint("NewApi")
public class GameActivity extends AbsGameActivity implements OnClickListener, MessageListener {
    private ImageButton reback;

    private Button follow, add, quit, tips, autopass, autopq, autofollow,startGame;

    private TextView current_rank, chips,desk_tips_text;
    
    private TextView roomName,dealText,roomRound;

    private ImageView img_card_tip, autofollow_check, autopass_check, autopq_check, allin;

    private RelativeLayout sidepool_layout1, sidepool_layout2, sidepool_layout3, sidepool_layout4,
            sidepool_layout5,mainpool_layout;

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

    private View CardTypeView = null;// 

    private PopupWindow CardTypeWin = null; // 牌型弹窗

    private VerticalSeekBar seekbar;

    private RelativeLayout allin_layout;

    private String max = "";
    
    private ClientPlayer currentPlay;
    
    private WorkHandler wHandler = null;
    
    private RelativeLayout img_card_tip_layout;

    private boolean isInOrOut = false;
    
    private int bigBlindIndex = -1;
    
    private int currentOptionPerson = -1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamelayout);

        initMessageListener(this);
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
        
        current_rank = (TextView) findViewById(R.id.player_current_rank);
        seat_one = (LeftSeatView) findViewById(R.id.seat_one);
        seat_one.setPokerStyle(0);
        seat_two = (RightSeatView) findViewById(R.id.seat_two);
        seat_three = (RightSeatView) findViewById(R.id.seat_three);
        seat_four = (RightSeatView) findViewById(R.id.seat_four);
        seat_five = (LeftSeatView) findViewById(R.id.seat_five);
        seat_six = (LeftSeatView) findViewById(R.id.seat_six);
        
        sidepool_layout1 = (RelativeLayout)findViewById(R.id.sidepool_layout1);
        sidepool_layout2 = (RelativeLayout)findViewById(R.id.sidepool_layout2);
        sidepool_layout3 = (RelativeLayout)findViewById(R.id.sidepool_layout3);
        sidepool_layout4 = (RelativeLayout)findViewById(R.id.sidepool_layout4);
        sidepool_layout5 = (RelativeLayout)findViewById(R.id.sidepool_layout5);
        mainpool_layout = (RelativeLayout)findViewById(R.id.mainpool_layout);
        
        desk_tips = (LinearLayout)findViewById(R.id.desk_tips);
        roomName = (TextView)findViewById(R.id.room_name);
        dealText = (TextView)findViewById(R.id.deal_text);
        roomRound = (TextView)findViewById(R.id.room_round_text);
        desk_tips_text = (TextView)findViewById(R.id.desk_tips_text);
        
        roomName.setVisibility(View.INVISIBLE);
        dealText.setVisibility(View.INVISIBLE);
        roomRound.setVisibility(View.INVISIBLE);
        sidepool_layout1.setVisibility(View.INVISIBLE);
        sidepool_layout2.setVisibility(View.INVISIBLE);
        sidepool_layout3.setVisibility(View.INVISIBLE);
        sidepool_layout4.setVisibility(View.INVISIBLE);
        sidepool_layout5.setVisibility(View.INVISIBLE);
        mainpool_layout.setVisibility(View.INVISIBLE);
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
//        autofollow_check.setOnClickListener(this);
//        autopass_check.setOnClickListener(this);
//        autopq_check.setOnClickListener(this);
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
            startGame.setVisibility(View.GONE);
        } else {
            currentPlay = app.sp;
            updateRoom(room);
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
        if (public_poker1.getVisibility() != View.VISIBLE) {
            public_poker1.setVisibility(View.VISIBLE);
            public_poker2.setVisibility(View.VISIBLE);
            public_poker3.setVisibility(View.VISIBLE);
            public_poker1.setAnimation(public_poker_anim);
            public_poker2.setAnimation(public_poker_anim);
            public_poker3.setAnimation(public_poker_anim);
            return;
        } else if (public_poker4.getVisibility() != View.VISIBLE) {
            public_poker4.setVisibility(View.VISIBLE);
            public_poker4.startAnimation(public_poker_anim);
            return;
        } else if (public_poker5.getVisibility() != View.VISIBLE) {
            public_poker5.setVisibility(View.VISIBLE);
            public_poker5.startAnimation(public_poker_anim);
            return;
        } else {
            return;
        }
    }
    
    public void startGame(){
        desk_tips.setVisibility(View.GONE);
        if(app.isServer()){
            if(isInOrOut||bigBlindIndex == -1){
    //            重新生成大盲
                newBigBlind();
            }else {
                bigBlindIndex++;
            }
            
            if(bigBlindIndex>=playerList.size()){
                bigBlindIndex = 0;
            }
    //      每次生成大盲之后都会发底牌
            sendMessage(MessageFactory.newGameMessage(false, GameMessage.ACTION_SEND_BOOL, -1, String.valueOf(bigBlindIndex)));
            wHandler.removeMessages(WorkHandler.MSG_SEND_BOOL);
            wHandler.sendEmptyMessage(WorkHandler.MSG_SEND_BOOL);
//            sendMessage(MessageFactory.newGameMessage(false, GameMessage.ACTION_CURRENT_PERSON, -1, String.valueOf(bigBlindIndex)));
        }
    }
    
    public void newBigBlind(){
        Random r = new Random();
        bigBlindIndex = r.nextInt(playerList.size());
    }

    // 发底牌
    public void bottomDeal() {
    	int index = findIndexWithIPinList(this.playerList);
    	setBigBlind(bigBlindIndex);
    	this.currentOptionPerson = bigBlindIndex;
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
            optionChoice(true);
        }else{
            optionChoice(false);;
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
    
    public void setBigBlind(int index){
        if(bigBlindIndex == Integer.parseInt(seat_one.getTag().toString()))
            seat_one.setBigBlindvisibility(View.VISIBLE);
        else
            seat_one.setBigBlindvisibility(View.INVISIBLE);
        if(bigBlindIndex == Integer.parseInt(seat_two.getTag().toString()))
            seat_two.setBigBlindvisibility(View.VISIBLE);
        else
            seat_two.setBigBlindvisibility(View.INVISIBLE);
        if(bigBlindIndex == Integer.parseInt(seat_three.getTag().toString()))
            seat_three.setBigBlindvisibility(View.VISIBLE);
        else
            seat_three.setBigBlindvisibility(View.INVISIBLE);
        if(bigBlindIndex == Integer.parseInt(seat_four.getTag().toString()))
            seat_four.setBigBlindvisibility(View.VISIBLE);
        else
            seat_four.setBigBlindvisibility(View.INVISIBLE);
        if(bigBlindIndex == Integer.parseInt(seat_five.getTag().toString()))
            seat_five.setBigBlindvisibility(View.VISIBLE);
        else
            seat_five.setBigBlindvisibility(View.INVISIBLE);
        if(bigBlindIndex == Integer.parseInt(seat_six.getTag().toString()))
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
                showPublicPoker();
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
                resetAllPlayStatus();
                break;
            case R.id.quit:
                closeCardTip();
                showPublicPoker();
                break;
            case R.id.tips:
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
                if(this.playerList.size()>=2){
                    desk_tips.setVisibility(View.GONE);
                    All_poker = Util.getPokers(playerList.size());
                    sendMessage(MessageFactory.newPeopleMessage(true, false, playerList, All_poker,null,null));
                    wHandler.removeMessages(WorkHandler.MSG_START_GAME);
                    wHandler.sendEmptyMessage(WorkHandler.MSG_START_GAME);
                }else{
                    Toast.makeText(getApplicationContext(), "还没人齐啊扑街", 1000).show();
                }
                break;
            case R.id.addnumber:
                allin_layout.setVisibility(View.INVISIBLE);
                add.setVisibility(View.VISIBLE);
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

    public void closeCardTip() {
    	
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
			playerList.add(msg.getPlayerList().get(0));
			msg.setPlayerList(playerList);
			msg.setRoom(room);
			sendMessage(msg);
//			chairUpdate(playerList);
            wHandler.removeMessages(WorkHandler.MSG_CHAIR_UPDATE);
            wHandler.sendEmptyMessage(WorkHandler.MSG_CHAIR_UPDATE);
		}
    }

    @Override
    public void onServerReceive(GameMessage msg) {
        // TODO Auto-generated method stub

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
    	if(GameMessage.ACTION_SEND_BOOL == cmd){
    	    if(msg.getExtra()!=null){
    	        this.bigBlindIndex = Integer.parseInt(msg.getExtra());
    	    }
    		wHandler.removeMessages(WorkHandler.MSG_SEND_BOOL);
            wHandler.sendEmptyMessage(WorkHandler.MSG_SEND_BOOL);
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
	
	private class WorkHandler extends Handler {
        
	    private static final int MSG_CHAIR_UPDATE = 1;
	    private static final int MSG_SEND_BOOL = 2;
	    private static final int MSG_START_GAME = 3;
	    private static final int MSG_ROOM_UPDATE = 4;
	    
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
                case MSG_ROOM_UPDATE:
                    updateRoom(room);
                default:
                    break;
            }
        }
	}
}
