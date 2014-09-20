package com.poker.common.util;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.app.Activity;
import android.app.Service;
import android.os.Vibrator;
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
    
}
