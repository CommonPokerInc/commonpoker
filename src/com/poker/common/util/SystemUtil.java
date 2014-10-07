package com.poker.common.util;

import java.util.HashMap;
import java.util.Random;

import com.poker.common.R;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
/**
 *
 * ��˵��
 *
 * @author RinfonChen:
 * @Day 2014��8��27�� 
 * @Time ����4:51:00
 * @Declaration :
 *
 */
public class SystemUtil {

    
	public static String[] zhenxinhuaTxt = {"你同时有过几个性伴侣？","描述你最印象深刻的春梦","澄清一件别人误会你最深的事"};
	public static String[] damaoxianTxt = {"跳肚皮舞一分钟","找一个异性的陌生人说“新年快乐”","COS一个卡通人物知道有人认出为止"};
	public static SoundPool sp = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
	public static HashMap<Integer,Integer> spMap = new HashMap<Integer, Integer>();
//   ��ȡIMEI
 // Requires READ_PHONE_STATE
    public static String getIMEI(Context mContext){
        TelephonyManager TelephonyMgr = (TelephonyManager)mContext.getSystemService(mContext.TELEPHONY_SERVICE); 
        return TelephonyMgr.getDeviceId(); 
    }
    
    public static String getID(Context mContext){
        return getIMEI(mContext)+System.currentTimeMillis();
    }
    
    public static void Vibrate(final Activity activity, long milliseconds) {
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }
    public static void Vibrate(final Activity activity, long[] pattern,boolean isRepeat) {
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(pattern, isRepeat ? 1 : -1);
    }
    
    public static String getPunishString(int type){
    	Random a = new Random();
    	switch(type){
    	case 0:
    		return zhenxinhuaTxt[a.nextInt(3)];
    	case 1:
    		return damaoxianTxt[a.nextInt(3)];
    	}
    	return "";
    }
    
    public static void initSounds(Context context){
    	spMap.put(1,sp.load(context, R.raw.backgroudmusic,2));
    	spMap.put(2,sp.load(context, R.raw.chip,1));
    	spMap.put(3,sp.load(context, R.raw.kongjian,1));
    	spMap.put(4,sp.load(context, R.raw.turnpoker,1));
    	spMap.put(5,sp.load(context, R.raw.win,1));
    }
    
//    音效播放函数：
    public static void playSounds(int sound, int number,Context context){
        //实例化AudioManager对象，控制声音
        AudioManager am = (AudioManager)context.getSystemService(context.AUDIO_SERVICE);
        //最大音量
        float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //当前音量
        float audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        float volumnRatio = audioCurrentVolumn/audioMaxVolumn;
        //播放
        sp.play(spMap.get(sound),     //声音资源
            volumnRatio,         //左声道
            volumnRatio,         //右声道
            1,             //优先级，0最低
            number,         //循环次数，0是不循环，-1是永远循环
            1);            //回放速度，0.5-2.0之间。1为正常速度
    }
    
    public void stopSoundS(int number){
    	sp.pause(spMap.get(number));
    }

    
}
