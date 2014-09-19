package com.poker.common.custom;

import com.poker.common.R;
import com.poker.common.entity.ClientPlayer;
import com.poker.common.util.UserUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MeView extends FrameLayout{

    private View view;
    private ImageView me_image_img;
    private TextView me_name_txt,player_action_txt,me_money_txt,bet_txt;
    private RelativeLayout bet_layout;
    
	public MeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		view = LayoutInflater.from(context).inflate(R.layout.me_view, this);
		initView();
	}
	
	public void initView(){
	    me_image_img = (ImageView)view.findViewById(R.id.me_image_img);
	    me_name_txt = (TextView)view.findViewById(R.id.me_name_txt);
	    player_action_txt = (TextView)view.findViewById(R.id.player_action_txt);
	    me_money_txt = (TextView)view.findViewById(R.id.me_money_txt);
	    bet_layout = (RelativeLayout)view.findViewById(R.id.bet_layout);
	    bet_txt = (TextView)view.findViewById(R.id.bet_txt);
	}
	
	public void setPersonImg(int imgID){
	    me_image_img.setImageResource(imgID);
	}
	
	public void setPersonName(String name){
	    me_name_txt.setText(name);
	}
	
	public void setActionText(String actionText){
	    player_action_txt.setText(actionText);
	}
	
	public void setBaseMoneyText(String str){
	    me_money_txt.setText(str);
	}
	
	public void setBetMoneyText(String str){
	    bet_txt.setText(str);
	}
	
	public void setBetVisiable(int v){
	    bet_layout.setVisibility(v);
	}
	
	public void setPerson(ClientPlayer player){
	    setPersonImg(UserUtil.head_img[player.getInfo().getAvatar()]);
	    setPersonName(player.getInfo().getName());
	    setBaseMoneyText(String.valueOf(player.getInfo().getBaseMoney()));
	}
	
	public void setActionViewVisiable(int v){
	    player_action_txt.setVisibility(v);
	}

}
