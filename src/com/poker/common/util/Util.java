package com.poker.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.poker.common.entity.Poker;

public class Util {
//	随机生成根据人数来定的扑克牌
	public static List getPokers(int personsCount){
		List pokers = new ArrayList(); 
		int pokerLenght = personsCount*2+5;
		Random rd = new Random();
		int num ;
		for(int i = pokerLenght - 1;i>=0;i--){
			num = rd.nextInt(51);
			if(!pokers.contains(num)){
				pokers.add(num);
			}else{
				i++;
			}
		}
		return pokers;
	}
//	根据玩家的7张牌得到最大的牌型，数字越大，牌型越大。
	public static int getPokerType(ArrayList<Poker> pokers){
		insertSort(pokers);
		return 0;
	}
	
//	判断是否存在5张花色相同
	public static boolean checkoutFivePokerColorSame(ArrayList<Poker> pokers){
		for(int i = pokers.size();i>=0;i--){
			
		}
		return false;
	}
	
//	判断是否存在5张牌数字连续
	public static boolean chekcoutPokerSucceedingNumbers(ArrayList<Poker> pokers){
		return false;
	}
	
//	判断是否存在4张牌数字相同
	public static boolean chekcoutFourPokerNumber(ArrayList<Poker> pokers){
		return false;
	}
	
//	判断是否存在3张牌数字相同
	public static boolean checkoutThreePokerNumber(ArrayList<Poker> pokers){
		return false;
	}
	
//	判断是否存在2张牌数字相同
	public static boolean checkoutTwoPokerNumber(ArrayList<Poker> pokers){
		return false;
	}
	
	public static ArrayList<Poker> insertSort(ArrayList<Poker> pokers){//插入排序算法
        for(int i=0;i<pokers.size();i++){
                for(int j=i;j>0;j--){
                        if (pokers.get(j).getSize()<pokers.get(j-1).getSize()){
                                Poker temp = new Poker(pokers.get(j-1));
                                pokers.get(j-1).setPoker(pokers.get(j));
                                pokers.get(j).setPoker(temp);
                        }else break;
                }
        }
        return pokers;
}
}
