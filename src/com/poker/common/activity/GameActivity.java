
package com.poker.common.activity;

import java.text.Bidi;
import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.poker.common.R;
import com.poker.common.customcontrols.VerticalSeekBar;
import com.poker.common.entity.AbsPlayer;
import com.poker.common.entity.ClientPlayer;
import com.poker.common.entity.Poker;
import com.poker.common.entity.Room;
import com.poker.common.entity.ServerPlayer;
import com.poker.common.util.Util;
import com.poker.common.wifi.listener.MessageListener;
import com.poker.common.wifi.message.GameMessage;
import com.poker.common.wifi.message.MessageFactory;
import com.poker.common.wifi.message.PeopleMessage;

@SuppressLint("NewApi")
public class GameActivity extends AbsGameActivity implements OnClickListener, MessageListener {
    private ImageButton reback;

    private Button follow, add, quit, tips, autopass, autopq, autofollow;

    private TextView current_rank, chips;
    
    private TextView roomName,dealText,roomRound;

    private ImageView img_card_tip, checked1, checked2, checked3, allin;

    private RelativeLayout sidepool_layout1, sidepool_layout2, sidepool_layout3, sidepool_layout4,
            sidepool_layout5,mainpool_layout;

    // 座位一永远都是自己
    private LeftSeatView seat_one, seat_two, seat_three;

    private RightSeatView seat_four, seat_five, seat_six;

    private ImageView public_poker1, public_poker2, public_poker3, public_poker4, public_poker5;

    private Animation public_poker_anim, tip_anim;

    private Boolean autopass_checked = false, autopq_checked = false, autofollow_checked = false;

    // 房间信息
    private Room room;

    // 公共牌
    private ArrayList<Poker> public_poker;

    // 玩家列表
    private ArrayList<ClientPlayer> playerList;

    // 玩家列表抽象，因为存在clientplayer和serverplayer，到时再继续详细的实例
    private HashMap<String, AbsPlayer> playList;

    private View popView = null;// 保存弹出窗口布局

    private PopupWindow popWin = null; // 弹出窗口

    private VerticalSeekBar seekbar;

    private RelativeLayout allin_layout;

    private String max = "";
    
    private ClientPlayer currentPlay;

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
        Log.v("zkzhou", max);
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
        autofollow = (Button) findViewById(R.id.autofollow);
        current_rank = (TextView) findViewById(R.id.player_current_rank);
        seat_one = (LeftSeatView) findViewById(R.id.seat_one);
        seat_one.setPokerStyle(0);
        seat_two = (LeftSeatView) findViewById(R.id.seat_two);
        seat_three = (LeftSeatView) findViewById(R.id.seat_three);
        seat_four = (RightSeatView) findViewById(R.id.seat_four);
        seat_five = (RightSeatView) findViewById(R.id.seat_five);
        seat_six = (RightSeatView) findViewById(R.id.seat_six);
        sidepool_layout1 = (RelativeLayout)findViewById(R.id.sidepool_layout1);
        sidepool_layout2 = (RelativeLayout)findViewById(R.id.sidepool_layout2);
        sidepool_layout3 = (RelativeLayout)findViewById(R.id.sidepool_layout3);
        sidepool_layout4 = (RelativeLayout)findViewById(R.id.sidepool_layout4);
        sidepool_layout5 = (RelativeLayout)findViewById(R.id.sidepool_layout5);
        mainpool_layout = (RelativeLayout)findViewById(R.id.mainpool_layout);
        roomName = (TextView)findViewById(R.id.room_name);
        dealText = (TextView)findViewById(R.id.deal_text);
        roomRound = (TextView)findViewById(R.id.room_round_text);
        roomName.setVisibility(View.INVISIBLE);
        dealText.setVisibility(View.INVISIBLE);
        roomRound.setVisibility(View.INVISIBLE);
        sidepool_layout1.setVisibility(View.INVISIBLE);
        sidepool_layout2.setVisibility(View.INVISIBLE);
        sidepool_layout3.setVisibility(View.INVISIBLE);
        sidepool_layout4.setVisibility(View.INVISIBLE);
        sidepool_layout5.setVisibility(View.INVISIBLE);
        mainpool_layout.setVisibility(View.INVISIBLE);
//        seat_two.setVisibility(View.INVISIBLE);
//        seat_three.setVisibility(View.INVISIBLE);
//        seat_four.setVisibility(View.INVISIBLE);
//        seat_five.setVisibility(View.INVISIBLE);
//        seat_six.setVisibility(View.INVISIBLE);
        public_poker1 = (ImageView) findViewById(R.id.poker1);
        public_poker2 = (ImageView) findViewById(R.id.poker2);
        public_poker3 = (ImageView) findViewById(R.id.poker3);
        public_poker4 = (ImageView) findViewById(R.id.poker4);
        public_poker5 = (ImageView) findViewById(R.id.poker5);
        checked1 = (ImageView) findViewById(R.id.checked1);
        checked2 = (ImageView) findViewById(R.id.checked2);
        checked3 = (ImageView) findViewById(R.id.checked3);
        reback.setOnClickListener(this);
        follow.setOnClickListener(this);
        add.setOnClickListener(this);
        quit.setOnClickListener(this);
        tips.setOnClickListener(this);
        autopass.setOnClickListener(this);
        autopq.setOnClickListener(this);
        autofollow.setOnClickListener(this);
        checked1.setOnClickListener(this);
        checked2.setOnClickListener(this);
        checked3.setOnClickListener(this);
        setPublicPokerVisibility(View.INVISIBLE);

        seekbar = (VerticalSeekBar) findViewById(R.id.ChipSeekbar);
        allin_layout = (RelativeLayout) findViewById(R.id.allin_layout);
        allin_layout.setVisibility(View.INVISIBLE);
        chips = (TextView) findViewById(R.id.chips);
        allin = (ImageView) findViewById(R.id.allin);
        seekbar.setOnSeekBarChangeListener(mSeekbarListener);// 添加事件监听

        room = getIntent().getParcelableExtra("Room");

        playerList = new ArrayList<ClientPlayer>();
        if (!app.isServer()) {
            currentPlay = app.cp;
            sendMessage(MessageFactory.newPeopleMessage(false, false, playerList, null,null));
        } else {
            currentPlay = app.sp;
            initRoom(room);
        }
        playerList.add(currentPlay);
        
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
    
    public void initRoom(Room room) {
        if (room != null) {
            roomName.setText(room.getName().trim());
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

    // 发底牌
    public void bottomDeal() {
        seat_one.ownPokerAnim();
        seat_two.ownPokerAnim();
        seat_three.ownPokerAnim();
        seat_four.ownPokerAnim();
        seat_five.ownPokerAnim();
        seat_six.ownPokerAnim();
    }
    
    // 重置状态
    public void resetAllPlayStatus() {
        seat_one.getLeft_seat_poker1().setVisibility(View.INVISIBLE);
        seat_one.getLeft_seat_poker2().setVisibility(View.INVISIBLE);
        seat_two.getLeft_seat_poker1().setVisibility(View.INVISIBLE);
        seat_two.getLeft_seat_poker2().setVisibility(View.INVISIBLE);
        seat_three.getLeft_seat_poker1().setVisibility(View.INVISIBLE);
        seat_three.getLeft_seat_poker2().setVisibility(View.INVISIBLE);
        seat_four.getRight_seat_poker1().setVisibility(View.INVISIBLE);
        seat_four.getRight_seat_poker2().setVisibility(View.INVISIBLE);
        seat_five.getRight_seat_poker1().setVisibility(View.INVISIBLE);
        seat_five.getRight_seat_poker2().setVisibility(View.INVISIBLE);
        seat_six.getRight_seat_poker1().setVisibility(View.INVISIBLE);
        seat_six.getRight_seat_poker2().setVisibility(View.INVISIBLE);
        setPublicPokerVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.add:
                // closeCardTip();
//                bottomDeal();
                showPublicPoker();
                allin_layout.setVisibility(View.VISIBLE);
                add.setVisibility(View.INVISIBLE);
                break;
            case R.id.reback:
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
            case R.id.checked1:
                autopass_checked = !autopass_checked;
                if (autopass_checked) {
                    autopq_checked = autofollow_checked = false;
                }
                setAutoChecked(autopass_checked, autopq_checked, autofollow_checked);
                break;
            case R.id.checked2:
                autopq_checked = !autopq_checked;
                if (autopq_checked) {
                    autopass_checked = autofollow_checked = false;
                }
                setAutoChecked(autopass_checked, autopq_checked, autofollow_checked);
                break;
            case R.id.checked3:
                autofollow_checked = !autofollow_checked;
                if (autofollow_checked) {
                    autopq_checked = autopass_checked = false;
                }
                setAutoChecked(autopass_checked, autopq_checked, autofollow_checked);
                break;
            default:
                break;
        }
    }

    private void setAutoChecked(boolean a, boolean b, boolean c) {
        // TODO Auto-generated method stub
        if (a) {
            checked1.setImageResource(R.drawable.img_auto_checked);
        } else {
            checked1.setImageResource(R.drawable.img_auto_uncheck);
        }
        if (b) {
            checked2.setImageResource(R.drawable.img_auto_checked);
        } else {
            checked2.setImageResource(R.drawable.img_auto_uncheck);
        }
        if (c) {
            checked3.setImageResource(R.drawable.img_auto_checked);
        } else {
            checked3.setImageResource(R.drawable.img_auto_uncheck);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        // if(img_card_tip.getVisibility() == View.VISIBLE){
        // setCardTip(View.INVISIBLE);
        // }
        // if(popWin.isShowing()){
        // popWin.dismiss();
        // popWin = null;
        // }
        // return true;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                allin_layout.setVisibility(View.INVISIBLE);
                add.setVisibility(View.VISIBLE);
                break;

            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setCardTip(int i) {
        // img_card_tip.setVisibility(i);
        // img_card_tip.setAnimation(tip_anim);
        // tip_anim.setFillAfter(true);
        showImageCard();
    }

    private void showImageCard() {
        // TODO Auto-generated method stub
        LayoutInflater inflater = LayoutInflater.from(GameActivity.this);
        popView = inflater.inflate(R.layout.card_tip, null); // 读取布局管理器
        popWin = new PopupWindow(popView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
                true); // 实例化
        popWin.setBackgroundDrawable(new BitmapDrawable());
        popWin.setOutsideTouchable(true);
        popWin.setFocusable(true);
        popWin.setAnimationStyle(R.style.popupAnimation);
        popWin.showAtLocation(GameActivity.this.tips, Gravity.LEFT, 0, 0); // 显示弹出窗口
        popWin.update();
    }

    public void closeCardTip() {
        if (img_card_tip.getVisibility() == View.VISIBLE) {
            img_card_tip.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onSendSuccess() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onServerReceive(PeopleMessage msg) {
        // TODO Auto-generated method stub
        playerList.addAll(msg.getPlayerList());
        msg.setPlayerList(playerList);
        sendMessage(msg);
    }

    @Override
    public void onServerReceive(GameMessage msg) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onClientReceive(PeopleMessage msg) {
        // TODO Auto-generated method stub
        if (msg.isExit()) {

        }
        if (msg.isStart()) {
            // 开始游戏

        } else {
            this.playerList.clear();
            this.playerList.addAll(msg.getPlayerList());
        }
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
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
