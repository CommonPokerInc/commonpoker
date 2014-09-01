package com.poker.common.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.poker.common.R;

public class RightSeatView extends FrameLayout {

	private ImageView right_up,right_counter_img,right_seat_poker1,right_seat_poker2;
	private TextView right_counter_text,card_type;
	private Context mContext;
	private ObjectAnimator pokerAnim1;
	private PersonView personView;
	public RightSeatView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;
		View view = LayoutInflater.from(context).inflate(R.layout.right_seat_view, this);
		
		initView(view);
	}
	@SuppressLint("NewApi")
	private void initView(View view) {
		// TODO Auto-generated method stub
		right_up = (ImageView)view.findViewById(R.id.right_up);
		right_counter_img = (ImageView)view.findViewById(R.id.right_counter_img);
		right_counter_text = (TextView)view.findViewById(R.id.right_counter_text);
		personView = (PersonView)view.findViewById(R.id.right_seat_personView);
		card_type = (TextView)view.findViewById(R.id.card_type);
		right_seat_poker1 = (ImageView)view.findViewById(R.id.right_seat_poker1);
		right_seat_poker2 = (ImageView)view.findViewById(R.id.right_seat_poker2);
		right_seat_poker1.setVisibility(View.INVISIBLE);
		right_seat_poker2.setVisibility(View.INVISIBLE);
		right_counter_img.setVisibility(View.INVISIBLE);
		right_counter_text.setVisibility(View.INVISIBLE);
		right_up.setVisibility(View.INVISIBLE);
		initAnim();
	}
	
	@SuppressLint("NewApi")
	public void initAnim(){
		pokerAnim1 = ObjectAnimator.ofFloat(right_seat_poker1,"rotation", 0, -15);
		pokerAnim1.setDuration(150);
		pokerAnim1.addListener(new AnimatorListenerAdapter(){
		    public void onAnimationEnd(Animator animation){
		        Log.i("pokerAnim1","end");
		    }
		});
	}
	
	public PersonView getSeatView(){
		return personView;
	}
	
	@SuppressLint("NewApi")
	public void ownPokerAnim(){
//		不能用xml动画，某些机型会卡
		if(right_seat_poker1.getVisibility() != View.VISIBLE){
			right_seat_poker1.setVisibility(View.VISIBLE);
			right_seat_poker2.setVisibility(View.VISIBLE);
		}
		pokerAnim1.start();
	}
	
	public PersonView getPersonView() {
        return personView;
    }
    public void setPersonView(PersonView personView) {
        this.personView = personView;
    }
	
	public void setPersonViewTitle(String str){
        this.getPersonView().setTitle(str);
    }
    
    public void setPersonViewPersonMoney(String money){
        this.getPersonView().setPersonMoney(money);
    }
	
	public void setPokerStyle(int style){
		RelativeLayout.LayoutParams mParams;
		RelativeLayout.LayoutParams mParams2;
		mParams = (RelativeLayout.LayoutParams)right_seat_poker1.getLayoutParams();
		mParams2 = (RelativeLayout.LayoutParams)right_seat_poker2.getLayoutParams();
		if(style == 0){
			mParams.setMargins(-20, 0, 0, 0);
//			mParams2.setMargins(100, 0, 0, 0);
			mParams.width = mParams2.width = 100;
			mParams.height = mParams2.height = 100;
			right_seat_poker1.setImageResource(R.drawable.diamond10);
			right_seat_poker2.setImageResource(R.drawable.diamond10);
		}
		right_seat_poker1.setLayoutParams(mParams);
		right_seat_poker2.setLayoutParams(mParams2);
	}
	
	public void setBigBlindvisibility(int visibility){
        this.right_up.setVisibility(visibility);
	}
	   
	public ImageView getRight_counter_img() {
		return right_counter_img;
	}
	public void setRight_counter_img(ImageView right_counter_img) {
		this.right_counter_img = right_counter_img;
	}
	public ImageView getRight_seat_poker1() {
		return right_seat_poker1;
	}
	public void setRight_seat_poker1(ImageView right_seat_poker1) {
		this.right_seat_poker1 = right_seat_poker1;
	}
	public ImageView getRight_seat_poker2() {
		return right_seat_poker2;
	}
	public void setRight_seat_poker2(ImageView right_seat_poker2) {
		this.right_seat_poker2 = right_seat_poker2;
	}
	public TextView getRight_counter_text() {
		return right_counter_text;
	}
	public void setRight_counter_text(TextView right_counter_text) {
		this.right_counter_text = right_counter_text;
	}
	
}
