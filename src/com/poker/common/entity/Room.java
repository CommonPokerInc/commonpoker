package com.poker.common.entity;
/*
 * author zkzhou
 * description ������
 * time 2014-7-29
 *
 */
public class Room {

	//��������
	private String name;
	//����
	private String password;
	//��������
	private int count;
	//����
	private int innings;
	//��Сע��
	private double minStake;
	//��������
	private double basicChips;
	//λ�ñ��
	private int number;
	
	public Room(){
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getInnings() {
		return innings;
	}

	public void setInnings(int innings) {
		this.innings = innings;
	}

	public double getMinStake() {
		return minStake;
	}

	public void setMinStake(double minStake) {
		this.minStake = minStake;
	}

	public double getBasicChips() {
		return basicChips;
	}

	public void setBasicChips(double basicChips) {
		this.basicChips = basicChips;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
}
