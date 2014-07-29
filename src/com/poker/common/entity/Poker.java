package com.poker.common.entity;

import android.R.bool;

/*
 * author zkzhou
 * description �˿�����
 * time 2014-7-29
 *
 */
public class Poker {

	//�˿��ƻ�ɫ
	private String color;
	//��С
	private int size;
	//���
	private int number;
	
	public Poker(String color,int size) {
		// TODO Auto-generated constructor stub
		super();
		initNum(color,size);
	}

	private void initNum(String color, int size) {
		// TODO Auto-generated method stub
		int c=0;
		if("����".equals(color)){
			c=1;
		}else if("÷��".equals(color)){
			c=2;
		}else if("����".equals(color)){
			c=3;
		}else{
			c=4;
		}
		switch(Integer.valueOf(c)){
		//����
		case 1:
			number = (size%14)*4-3;
			break;
	    //÷��
		case 2:
			number = (size%14)*4-2;
			break;
		//����
		case 3:
			number = (size%14)*4-1;
			break;
		//����
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
