package com.poker.common.entity;

import android.R.bool;

/*
 * author zkzhou
 * description 扑克牌类
 * time 2014-7-29
 *
 */
public class Poker {


    //扑克牌花色
//	0：方块
//	1：梅花
//	2：红桃
//	3：黑桃
	private int color;
	//大小
	private int size;
	//编号
	private int number;
	
	public Poker(){
		
	}
//	public Poker(String color,int size) {
//		// TODO Auto-generated constructor stub
//		super();
//		initNum(color,size);
//	}
	
//	根据number来初始化扑克牌
//	0-12方块
//	13-25梅花 
//	26-38红桃 
//	39-51黑桃
	public Poker(int number){
		this.number = number;
		this.color = number / 13;
		this.size = number % 13;
	}
	
	public Poker(Poker p){
		this.number = p.getNumber();
		this.color = p.getColor();
		this.size = p.getSize();
	}
	
	public void setPoker(Poker p){
		this.number = p.getNumber();
		this.color = p.getColor();
		this.size = p.getSize();
	}

//	private void initNum(String color, int size) {
		// TODO Auto-generated method stub
//		int c=0;
//		if("方块".equals(color)){
//			c=1;
//		}else if("梅花".equals(color)){
//			c=2;
//		}else if("红桃".equals(color)){
//			c=3;
//		}else{
//			c=4;
//		}
//		switch(Integer.valueOf(c)){
//		//方块
//		case 1:
//			number = (size%14)*4-3;
//			break;
//	    //梅花
//		case 2:
//			number = (size%14)*4-2;
//			break;
//		//红桃
//		case 3:
//			number = (size%14)*4-1;
//			break;
//		//黑桃
//		case 4:
//			number = (size%14)*4;
//			break;
//		default:
//			break;
//		}
//	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	@Override
    public boolean equals(Object o) {
	    // TODO Auto-generated method stub
	    Poker s = (Poker)o;
	    if(s.getNumber() == this.getNumber()){
	        return true;
	    }else{
	        return false;
	    }
//	    return super.equals(o);
	}


}
