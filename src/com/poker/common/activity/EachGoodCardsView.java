package com.poker.common.activity;

import com.poker.common.R;

import org.w3c.dom.Text;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class EachGoodCardsView extends FrameLayout{
    
    private Context context;
    private TextView title,money;
    private ImageView poker1,poker2;

    public EachGoodCardsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        this.context = context;
        View view = LayoutInflater.from(this.context).inflate(R.layout.good_cards_each, this);
        initView(view);
    }

    private void initView(View view) {
        // TODO Auto-generated method stub
        title = (TextView)view.findViewById(R.id.good_cards_each_title_txt);
        money = (TextView)view.findViewById(R.id.good_cards_each_money_txt);
        poker1 = (ImageView)view.findViewById(R.id.good_cards_each_poker1_img);
        poker2 = (ImageView)view.findViewById(R.id.good_cards_each_poker2_img);
    }
    
    public TextView getTitle(){
        return title;
    }
    
    public void setTitle(String titleStr){
        if(!"".equals(titleStr)){
            title.setText(titleStr);
        }
    }
    
    public TextView getMoney(){
        return money;
    }
    
    public void setMoney(String moneyStr){
        if (!"".equals(moneyStr)) {
            money.setText(moneyStr);
        }
    }
    
    public void setPoker1(int i){
        poker1.setImageResource(i);
    }
    
    public void setPoker2(int i){
        poker2.setImageResource(i);
    }
}
