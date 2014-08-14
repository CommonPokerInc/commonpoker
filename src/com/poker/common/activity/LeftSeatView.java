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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.poker.common.R;

@SuppressLint("NewApi")
public class LeftSeatView extends FrameLayout {

	private ImageView left_up,left_counter_img,left_seat_poker1,left_seat_poker2;
	private TextView left_counter_text,card_type;
	private PersonView personView;
	private Context mContext;
	private ObjectAnimator pokerAnim1;
	public LeftSeatView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;
		View view = LayoutInflater.from(context).inflate(R.layout.left_seat_view, this);
		initView(view);
	}
	@SuppressLint("NewApi")
	private void initView(View view) {
		// TODO Auto-generated method stub
		left_up = (ImageView)view.findViewById(R.id.left_up);
		left_counter_img = (ImageView)view.findViewById(R.id.left_counter_img);
		left_counter_text = (TextView)view.findViewById(R.id.left_couter_text);
		card_type = (TextView)view.findViewById(R.id.card_type);
		personView = (PersonView)view.findViewById(R.id.left_seat_personView);
		left_seat_poker1 = (ImageView)view.findViewById(R.id.left_seat_poker1);
		left_seat_poker2 = (ImageView)view.findViewById(R.id.left_seat_poker2);
		left_seat_poker1.setRotation(-15);
		left_seat_poker1.setVisibility(View.INVISIBLE);
		left_seat_poker2.setVisibility(View.INVISIBLE);
		left_counter_img.setVisibility(View.INVISIBLE);
		left_counter_text.setVisibility(View.INVISIBLE);
		left_up.setVisibility(View.INVISIBLE);
		initAnim();
	}
	public ImageView getLeft_up() {
		return left_up;
	}
	public void setLeft_up(ImageView left_up) {
		this.left_up = left_up;
	}

	public ImageView getLeft_counter_img() {
		return left_counter_img;
	}
	public void setLeft_counter_img(ImageView left_counter_img) {
		this.left_counter_img = left_counter_img;
	}
	public ImageView getLeft_seat_poker1() {
		return left_seat_poker1;
	}
	public void setLeft_seat_poker1(ImageView left_seat_poker1) {
		this.left_seat_poker1 = left_seat_poker1;
	}
	public ImageView getLeft_seat_poker2() {
		return left_seat_poker2;
	}
	public void setLeft_seat_poker2(ImageView left_seat_poker2) {
		this.left_seat_poker2 = left_seat_poker2;
	}
	public TextView getLeft_counter_text() {
		return left_counter_text;
	}
	public void setLeft_counter_text(TextView left_counter_text) {
		this.left_counter_text = left_counter_text;
	}
	public PersonView getPersonView() {
		return personView;
	}
	public void setPersonView(PersonView personView) {
		this.personView = personView;
	}
	
	public void initAnim(){
		pokerAnim1 = ObjectAnimator.ofFloat(left_seat_poker1,"rotation", 0, -15);
		pokerAnim1.setDuration(150);
		pokerAnim1.addListener(new AnimatorListenerAdapter(){
		    public void onAnimationEnd(Animator animation){
		        Log.i("pokerAnim1","end");
		    }
		});
		
	}
	
	public void ownPokerAnim(){
		if(left_seat_poker1.getVisibility() != View.VISIBLE){
			left_seat_poker1.setVisibility(View.VISIBLE);
			left_seat_poker2.setVisibility(View.VISIBLE);
		}
		pokerAnim1.start();

	}
	
}
