package com.poker.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.poker.common.entity.Poker;

public class Util {
	
	public static ArrayList<Poker> currentPokers = new ArrayList<Poker>();
	
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
		int temp = 0;
		Poker box = new Poker();
		int currentIndex = -1;
		for(int i = pokers.size() - 1;i>=5;i--){
			box = pokers.get(i);
			currentIndex = i;
			for(int j = i-1;j>=0;j--){
				 if(pokers.get(i).getColor() == pokers.get(j).getColor()){
					 temp++;
				 }
			}
			if(temp == 5){
				break;
			}else{
				temp = 0;
			}
		}
		if(temp == 5){
			currentPokers.add(box);
			temp--;
			for(int n = currentIndex-1;n>=0&&temp>=0;n--){
				if(pokers.get(n).getColor() == box.getColor()){
					currentPokers.add(pokers.get(n));
					temp--;
				}
			}
			return true;
		}
		return false;
	}
	
//	判断是否存在5张牌数字连续
	public static boolean chekcoutPokerSucceedingNumbers(ArrayList<Poker> pokers){
//		未考虑1 13 12 11 10 9情况
		int temp = 0;
		Poker box ;
		for(int i = pokers.size()-1;i>=5;i--){
			box = pokers.get(i);
//			currentPokers.add(box);
			for(int j = i;j>=1;j--){
				if(pokers.get(j).getSize() == pokers.get(j-1).getSize()+1){
					temp++;
					currentPokers.add(pokers.get(j));
					if(temp == 4){
						currentPokers.add(pokers.get(j-1));
						return true;
					}
				}else if(pokers.get(j).getSize() != pokers.get(j-1).getSize()){
					break;
				}
			}
			if(temp == 4){
				return true;
			}else{
				currentPokers.clear();
			}
		}
		return false;
	}
	
//	判断是否存在4张牌数字相同
	public static boolean chekcoutFourPokerNumber(ArrayList<Poker> pokers){
		int temp = 0;
		Poker box = new Poker();
		int currentIndex = -1;
		for(int i = pokers.size() - 1;i>=4;i--){
			box = pokers.get(i);
			currentIndex = i;
			for(int j = i-1;j>=0;j--){
				 if(pokers.get(i).getSize() == pokers.get(j).getSize()){
					 temp++;
				 }
			}
			if(temp == 4){
				break;
			}else{
				temp = 0;
			}
		}
		if(temp == 4){
			currentPokers.add(box);
			temp--;
			for(int n = currentIndex-1;n>=0&&temp>=0;n--){
				if(pokers.get(n).getSize() == box.getSize()){
					currentPokers.add(pokers.get(n));
					temp--;
				}
			}
			return true;
		}
		return false;
	}
	
//	判断是否存在3张牌数字相同
	public static boolean checkoutThreePokerNumber(ArrayList<Poker> pokers){
		int temp = 0;
		Poker box = new Poker();
		int currentIndex = -1;
		for(int i = pokers.size() - 1;i>=3;i--){
			box = pokers.get(i);
			currentIndex = i;
			for(int j = i-1;j>=0;j--){
				 if(pokers.get(i).getSize() == pokers.get(j).getSize()){
					 temp++;
				 }
			}
			if(temp == 3){
				break;
			}else{
				temp = 0;
			}
		}
		if(temp == 3){
			currentPokers.add(box);
			temp--;
			for(int n = currentIndex-1;n>=0&&temp>=0;n--){
				if(pokers.get(n).getSize() == box.getSize()){
					currentPokers.add(pokers.get(n));
					temp--;
				}
			}
			return true;
		}
		return false;
	}
	
//	判断是否存在2张牌数字相同
	public static boolean checkoutTwoPokerNumber(ArrayList<Poker> pokers){
		int temp = 0;
		Poker box = new Poker();
		int currentIndex = -1;
		for(int i = pokers.size() - 1;i>=2;i--){
			box = pokers.get(i);
			currentIndex = i;
			for(int j = i-1;j>=0;j--){
				 if(pokers.get(i).getSize() == pokers.get(j).getSize()){
					 temp++;
				 }
			}
			if(temp == 2){
				break;
			}else{
				temp = 0;
			}
		}
		if(temp == 2){
			currentPokers.add(box);
			temp--;
			for(int n = currentIndex-1;n>=0&&temp>=0;n--){
				if(pokers.get(n).getSize() == box.getSize()){
					currentPokers.add(pokers.get(n));
					temp--;
				}
			}
			return true;
		}
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
