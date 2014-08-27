
package com.poker.common.util;

import android.content.Context;

/**
 * 
 * author FrankChan
 * description 用户设置
 * time 2014-8-26
 *
 */
public class SettingHelper {
	
	private final static String NICKNAME = "nickname";
	
	private final static String AVATAR_NUMBER = "avatar_number";
	
	private final static String WIN_TIMES = "win_times";
	
	private final static String PLAY_TIMES = "play_times";
	
	private final static String MAX_POKER ="max_poker";
	
	private final static String POWER = "power";
	
	private final static String VOICE ="voice";
	
	private final static String VIBRATION = "VIBRATION";
	
	private PreferenceHelper helper;
	
	public SettingHelper(Context ctx){
		helper = new PreferenceHelper(ctx);
	}
	
	public String getNickname(){
		return helper.getString(NICKNAME);
	}

	public void setNickname(String name){
		helper.setString(NICKNAME, name);
	}
	
	public int getAvatarNumber(){
		return helper.getInteger(AVATAR_NUMBER);
	}
	
	public void setAvatarNumber(int avatar){
		helper.setInteger(AVATAR_NUMBER, avatar);
	}

	public void setWinTimes(int times){
		helper.setInteger(WIN_TIMES, times);
	}
	
	public int getWinTimes(){
		return helper.getInteger(WIN_TIMES);
	}
	
	/**
	 * 胜利场数加一
	 */
	public void addWinTimes(){
		int times = helper.getInteger(WIN_TIMES);
		setWinTimes(++times);
	}
	
	public void setPlayTimes(int times){
		helper.setInteger(PLAY_TIMES, times);
	}
	
	public int getPlayTimes(){
		return helper.getInteger(PLAY_TIMES);
	}
	
	/**
	 * 已玩场数加一
	 */
	public void addPlayTimes(){
		int times = helper.getInteger(PLAY_TIMES);
		setPlayTimes(++times);
	}
	
	
	/** 
	 * 
	 * 获取最大牌型字符串，用逗号隔开
	 * 例如 "5,6,7,8,9"
	 */
	public String getMaxPoker() {
		return helper.getString(MAX_POKER,"您的游戏场数为零");
	}

	/** 
	 * 参数为牌型数字顺序，中间用英文逗号隔开
	 * 例如 "5,6,7,8,9" 和 "6,7,8,9,10"
	 * @param strPokerList
	 */
	public void setMaxPoker(String strPokerList){
		helper.setString(MAX_POKER, strPokerList);
	}
	
	public int getPower() {
		return helper.getInteger(POWER);
	}

	public void setPower(int power){
		helper.setInteger(POWER, power);
	}
	
	/**
	 * 是否设置允许音效，默认为是
	 */
	public boolean getVoiceStatus() {
		return helper.getBoolean(VOICE, true);
	}
	
	public void setVoiceStatus(boolean status){
		helper.setBoolean(VOICE, status);
	}

	/**
	 * 是否设置允许震动，默认为是
	 */
	public boolean getVibrationStatus() {
		return helper.getBoolean(VIBRATION,true);
	}
	
	public void setVibrationStatus(boolean status){
		helper.setBoolean(VIBRATION, status);
	}
}


