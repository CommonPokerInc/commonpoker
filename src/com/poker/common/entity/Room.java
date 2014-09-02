package com.poker.common.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

/*
 * author zkzhou
 * description 房间类
 * time 2014-7-29
 *
 */
public class Room implements Parcelable{
	
	//房间类型：有限局
	public static final int TYPE_LIMIT = 1;
	//房间类型，淘汰局
	public static final int TYPE_RANK = 2;
	
	private int type;
	//房间名字
	private String name;
	//密码
	private String password;
	//房间人数
	private int count;
	//局数
	private int innings;
	//最小注额
	private int minStake;
	//基本筹码
	private int basicChips;
	//位置编号
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

	public int getMinStake() {
		return minStake;
	}

	public void setMinStake(int minStake) {
		this.minStake = minStake;
	}

	public int getBasicChips() {
		return basicChips;
	}

	public void setBasicChips(int basicChips) {
		this.basicChips = basicChips;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(new Gson().toJson(this));
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public final static Creator<Room>CREATOR = new Creator<Room>(){

        @Override
        public Room createFromParcel(Parcel parcel) {
            // TODO Auto-generated method stub
            return new Gson().fromJson(parcel.readString(), Room.class);
        }

        @Override
        public Room[] newArray(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }
        
    };
}
