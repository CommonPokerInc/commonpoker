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
//	0������
//	1��÷��
//	2������
//	3������
	private int color;
	//��С
	private int size;
	//���
	private int number;
	
	public Poker(){
		
	}
//	public Poker(String color,int size) {
//		// TODO Auto-generated constructor stub
//		super();
//		initNum(color,size);
//	}
	
//	����number����ʼ���˿���
//	0-12����
//	13-25÷�� 
//	26-38���� 
//	39-51����
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
//		if("����".equals(color)){
//			c=1;
//		}else if("÷��".equals(color)){
//			c=2;
//		}else if("����".equals(color)){
//			c=3;
//		}else{
//			c=4;
//		}
//		switch(Integer.valueOf(c)){
//		//����
//		case 1:
//			number = (size%14)*4-3;
//			break;
//	    //÷��
//		case 2:
//			number = (size%14)*4-2;
//			break;
//		//����
//		case 3:
//			number = (size%14)*4-1;
//			break;
//		//����
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
