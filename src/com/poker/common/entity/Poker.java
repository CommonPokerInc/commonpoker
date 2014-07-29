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
	private String color;
	//大小
	private int size;
	//编号
	private int number;
	
	public Poker(String color,int size) {
		// TODO Auto-generated constructor stub
		super();
		initNum(color,size);
	}

	private void initNum(String color, int size) {
		// TODO Auto-generated method stub
		int c=0;
		if("方块".equals(color)){
			c=1;
		}else if("梅花".equals(color)){
			c=2;
		}else if("红桃".equals(color)){
			c=3;
		}else{
			c=4;
		}
		switch(Integer.valueOf(c)){
		//方块
		case 1:
			number = (size%14)*4-3;
			break;
	    //梅花
		case 2:
			number = (size%14)*4-2;
			break;
		//红桃
		case 3:
			number = (size%14)*4-1;
			break;
		//黑桃
		case 4:
			number = (size%14)*4;
			break;
		default:
			break;
		}
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
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

}
