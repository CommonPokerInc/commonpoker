package com.poker.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.poker.common.entity.Poker;

public class Util {
//	������ɸ��������������˿���
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
//	������ҵ�7���Ƶõ��������ͣ�����Խ������Խ��
	public static int getPokerType(ArrayList<Poker> pokers){
		insertSort(pokers);
		return 0;
	}
	
//	�ж��Ƿ����5�Ż�ɫ��ͬ
	public static boolean checkoutFivePokerColorSame(ArrayList<Poker> pokers){
		for(int i = pokers.size();i>=0;i--){
			
		}
		return false;
	}
	
//	�ж��Ƿ����5������������
	public static boolean chekcoutPokerSucceedingNumbers(ArrayList<Poker> pokers){
		return false;
	}
	
//	�ж��Ƿ����4����������ͬ
	public static boolean chekcoutFourPokerNumber(ArrayList<Poker> pokers){
		return false;
	}
	
//	�ж��Ƿ����3����������ͬ
	public static boolean checkoutThreePokerNumber(ArrayList<Poker> pokers){
		return false;
	}
	
//	�ж��Ƿ����2����������ͬ
	public static boolean checkoutTwoPokerNumber(ArrayList<Poker> pokers){
		return false;
	}
	
	public static ArrayList<Poker> insertSort(ArrayList<Poker> pokers){//���������㷨
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
