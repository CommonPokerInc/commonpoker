package com.poker.common.util;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 *
 * 类说明
 *
 * @author RinfonChen:
 * @Day 2014年8月27日 
 * @Time 下午4:51:00
 * @Declaration :
 *
 */
public class SystemUtil {

    
//   获取IMEI
 // Requires READ_PHONE_STATE
    public static String getIMEI(Context mContext){
        TelephonyManager TelephonyMgr = (TelephonyManager)mContext.getSystemService(mContext.TELEPHONY_SERVICE); 
        return TelephonyMgr.getDeviceId(); 
    }
    
    public static String getID(Context mContext){
        return getIMEI(mContext)+System.currentTimeMillis();
    }
    
}
