package com.poker.common.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.poker.common.R;
import com.poker.common.entity.Room;

@SuppressLint("NewApi")
public class GameActivity extends Activity implements OnClickListener{
	private ImageButton reback,follow,add,quit,tips;
	private TextView current_rank;
//	座位一永远都是自己
	private LeftSeatView seat_two,seat_four,seat_six;
	private RightSeatView seat_one,seat_three,seat_five;
	private ImageView public_poker1,public_poker2,public_poker3,public_poker4,public_poker5;
	private Animation public_poker_anim;
//房间信息
	private Room room;
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gamelayout);
		
		init();
		initPokerAnim();
	}
	private void init() {
		// TODO Auto-generated method stub
		reback = (ImageButton)findViewById(R.id.reback);
		follow = (ImageButton)findViewById(R.id.follow);
		add = (ImageButton)findViewById(R.id.add);
		quit = (ImageButton)findViewById(R.id.quit);
		tips = (ImageButton)findViewById(R.id.tips);
		current_rank = (TextView)findViewById(R.id.player_current_rank);
		seat_one = (RightSeatView)findViewById(R.id.seat_one);
		seat_one.setPokerStyle(1);
		seat_two = (LeftSeatView)findViewById(R.id.seat_two);
		seat_three = (RightSeatView)findViewById(R.id.seat_three);
		seat_three.setPokerStyle(1);
		seat_four = (LeftSeatView)findViewById(R.id.seat_four);
		seat_five = (RightSeatView)findViewById(R.id.seat_five);
		seat_five.setPokerStyle(0);
		seat_six = (LeftSeatView)findViewById(R.id.seat_six);
		public_poker1 = (ImageView)findViewById(R.id.poker1);
		public_poker2 = (ImageView)findViewById(R.id.poker2);
		public_poker3 = (ImageView)findViewById(R.id.poker3);
		public_poker4 = (ImageView)findViewById(R.id.poker4);
		public_poker5 = (ImageView)findViewById(R.id.poker5);
		reback.setOnClickListener(this);
		follow.setOnClickListener(this);
		add.setOnClickListener(this);
		quit.setOnClickListener(this);
		tips.setOnClickListener(this);
		setPublicPokerVisibility(View.INVISIBLE);
	}	
	
	public void initPokerAnim(){
		public_poker_anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.poker_scale);
	}
	
	public void setPublicPokerVisibility(int i){
		public_poker1.setVisibility(i);
		public_poker2.setVisibility(i);
		public_poker3.setVisibility(i);
		public_poker4.setVisibility(i);
		public_poker5.setVisibility(i);
	}
//	发公共牌
	public void showPublicPoker(){
		if(public_poker1.getVisibility()!=View.VISIBLE){
			public_poker1.setVisibility(View.VISIBLE);
			public_poker2.setVisibility(View.VISIBLE);
			public_poker3.setVisibility(View.VISIBLE);
			public_poker1.setAnimation(public_poker_anim);
			public_poker2.setAnimation(public_poker_anim);
			public_poker3.setAnimation(public_poker_anim);
			return;
		}else if(public_poker4.getVisibility()!=View.VISIBLE){
			public_poker4.setVisibility(View.VISIBLE);
			public_poker4.startAnimation(public_poker_anim);
			return;
		}else if(public_poker5.getVisibility()!=View.VISIBLE){
			public_poker5.setVisibility(View.VISIBLE);
			public_poker5.startAnimation(public_poker_anim);
			return;
		}else{
			return;
		}
	}
//	发底牌
	public void bottomDeal(){
		seat_one.ownPokerAnim();
		seat_two.ownPokerAnim();
		seat_three.ownPokerAnim();
		seat_four.ownPokerAnim();
		seat_five.ownPokerAnim();
		seat_six.ownPokerAnim();
	}
//	重置状态
	public void resetAllPlayStatus(){
		seat_one.getRight_seat_poker1().setVisibility(View.INVISIBLE);
		seat_one.getRight_seat_poker2().setVisibility(View.INVISIBLE);
		seat_two.getLeft_seat_poker1().setVisibility(View.INVISIBLE);
		seat_two.getLeft_seat_poker2().setVisibility(View.INVISIBLE);
		seat_three.getRight_seat_poker1().setVisibility(View.INVISIBLE);
		seat_three.getRight_seat_poker2().setVisibility(View.INVISIBLE);
		seat_four.getLeft_seat_poker1().setVisibility(View.INVISIBLE);
		seat_four.getLeft_seat_poker2().setVisibility(View.INVISIBLE);
		seat_five.getRight_seat_poker1().setVisibility(View.INVISIBLE);
		seat_five.getRight_seat_poker2().setVisibility(View.INVISIBLE);
		seat_six.getLeft_seat_poker1().setVisibility(View.INVISIBLE);
		seat_six.getLeft_seat_poker2().setVisibility(View.INVISIBLE);
		setPublicPokerVisibility(View.INVISIBLE);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.add:
			bottomDeal();
			break;
		case R.id.reback:
			Intent i = new Intent(GameActivity.this,MainActivity.class);
			startActivity(i);
			finish();
			break;
		case R.id.follow:
			resetAllPlayStatus();
			break;
		case R.id.quit:
			showPublicPoker();
			break;
		case R.id.tips:
			break;
		default:
			break;
		}
	}
}
