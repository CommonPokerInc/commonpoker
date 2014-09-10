package com.poker.common.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.poker.common.R;

public class PersonView extends FrameLayout{

	private TextView title;
	
	private ImageButton personImg;
	
	private TextView personMoney;
	
	public PersonView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		 View v = LayoutInflater.from(context).inflate(R.layout.person_view, this);
		 title = (TextView) v.findViewById(R.id.person_title);
		 personMoney = (TextView)v.findViewById(R.id.person_current_money);
		 personImg = (ImageButton) v.findViewById(R.id.person_image);
	}
	
	public void setPersonImgClickListen(OnClickListener l){
		personImg.setOnClickListener(l);
	}

	public TextView getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title.setText(title);
	}

	public ImageButton getPersonImg() {
		return personImg;
	}

	public void setPersonImg(ImageButton personImg) {
		this.personImg = personImg;
	}

	public TextView getPersonMoney() {
		return personMoney;
	}

	public void setPersonMoney(String personMoney) {
		this.personMoney.setText(personMoney);
	}
	

}
