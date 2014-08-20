package com.poker.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.poker.common.entity.Poker;

public class Util {
	
//	public static ArrayList<Poker> currentPokers = new ArrayList<Poker>();
    
//    同花顺
    public static final int FLUSH = 9;
    
//  金刚
    public static final int KING_KONG = 8;
  
//  葫芦
    public static final int GOURD = 7;

//  同花
    public static final int ROYAL_FLUSH = 6;

//  顺子
    public static final int STRAIGHT = 5;

//  三条
    public static final int THREE = 4;

//  两对
    public static final int TWO_PAIR = 3;

//  一对
    public static final int PAIR = 2;

//  高牌
    public static final int HIGH_CARD = 1;
	
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
		ArrayList<Poker> pokersBack = new ArrayList<Poker>();
		ArrayList<Poker> pokersBackUp = new ArrayList<Poker>();
		if(chekcoutPokerSucceedingNumbers(pokers,pokersBack)){
		    if(checkoutFivePokerColorSame(pokersBack,pokersBackUp)){
		        return FLUSH;
		    }else{
		        return STRAIGHT;
		    }
		}else if(checkoutFivePokerColorSame(pokers,pokersBack)){
		    return ROYAL_FLUSH;
		}else if(chekcoutFourPokerNumber(pokers,pokersBack)){
		    return KING_KONG;
		}else if(checkoutThreePokerNumber(pokers,pokersBack)){
		    if(checkoutTwoPokerNumber(pokersBack,pokersBackUp)){
		        return GOURD;
		    }else{
		        return THREE;
		    }
		}else if(checkoutTwoPokerNumber(pokers,pokersBack)){
		    if(checkoutTwoPokerNumber(pokersBack,pokersBackUp)){
		        return TWO_PAIR;
		    }else{
		        return PAIR;
		    }
		}else{
		    return HIGH_CARD;
		}
	}
	
//	判断是否存在5张花色相同
	public static boolean checkoutFivePokerColorSame(ArrayList<Poker> pokers,ArrayList<Poker> pokersBack){
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
		    pokersBack.add(box);
			temp--;
			for(int n = currentIndex-1;n>=0&&temp>=0;n--){
				if(pokers.get(n).getColor() == box.getColor()){
				    pokersBack.add(pokers.get(n));
					temp--;
				}
			}
			return true;
		}
		return false;
	}
	
//	判断是否存在5张牌数字连续
	public static boolean chekcoutPokerSucceedingNumbers(ArrayList<Poker> pokers,ArrayList<Poker> pokersBack){
//		包括1 13 12 11 10 9情况
		int temp = 0;
    		for(int i = pokers.size()-1;i>=5;i--){
        			for(int j = i;j>=1;j--){
        				if(pokers.get(j).getSize() == pokers.get(j-1).getSize()+1||
        				        (pokers.get(j).getSize() == 1&&pokers.get(j).getSize() == pokers.get(j-1).getSize()-12)){
        					temp++;
        					pokersBack.add(pokers.get(j));
        					if(temp == 4){
        					    pokersBack.add(pokers.get(j-1));
        						return true;
        					}
        				}else if(pokers.get(j).getSize() != pokers.get(j-1).getSize()){
        					break;
        				}
        			}
        			if(temp == 4){
        				return true;
        			}else{
        			    temp = 0;
        			    pokersBack.clear();
        			}
        		}
//    		1 2 3 4 5的情况
    		if(pokers.get(pokers.size()-1).getSize() == 1&&pokers.get(0).getSize() == 2){
    		    pokersBack.add(pokers.get(pokers.size()-1));
    		    temp++;
    		    for(int i = 0;i<pokers.size()-1;i++){
    		        if(pokers.get(i).getSize() == pokers.get(i+1).getSize()-1){
                        temp++;
                        pokersBack.add(pokers.get(i));
                        if(temp == 4){
                            pokersBack.add(pokers.get(i+1));
                            return true;
                        }
                    }else if(pokers.get(i).getSize() != pokers.get(i+1).getSize()){
                        break;
                    }
                }
                if(temp == 4){
                    return true;
                }else{
                    temp = 0;
                    pokersBack.clear();
                }
    		}
		return false;
	}
	
//	判断是否存在4张牌数字相同
	public static boolean chekcoutFourPokerNumber(ArrayList<Poker> pokers,ArrayList<Poker> pokersBack){
//		int temp = 0;
//		Poker box = new Poker();
//		int currentIndex = -1;
//		for(int i = pokers.size() - 1;i>=4;i--){
//			box = pokers.get(i);
//			currentIndex = i;
//			for(int j = i-1;j>=0;j--){
//				 if(pokers.get(i).getSize() == pokers.get(j).getSize()){
//					 temp++;
//				 }
//			}
//			if(temp == 4){
//				break;
//			}else{
//				temp = 0;
//			}
//		}
//		if(temp == 4){
//			currentPokers.add(box);
//			temp--;
//			for(int n = currentIndex-1;n>=0&&temp>=0;n--){
//				if(pokers.get(n).getSize() == box.getSize()){
//					currentPokers.add(pokers.get(n));
//					temp--;
//				}
//			}
//			return true;
//		}
//		return false;
	    
	    return checkNumberSame(pokers,4,pokersBack);
	}
	
//	判断是否存在3张牌数字相同
	public static boolean checkoutThreePokerNumber(ArrayList<Poker> pokers,ArrayList<Poker> pokersBack){
//		int temp = 0;
//		Poker box = new Poker();
//		int currentIndex = -1;
//		for(int i = pokers.size() - 1;i>=3;i--){
//			box = pokers.get(i);
//			currentIndex = i;
//			for(int j = i-1;j>=0;j--){
//				 if(pokers.get(i).getSize() == pokers.get(j).getSize()){
//					 temp++;
//				 }
//			}
//			if(temp == 3){
//				break;
//			}else{
//				temp = 0;
//			}
//		}
//		if(temp == 3){
//			currentPokers.add(box);
//			temp--;
//			for(int n = currentIndex-1;n>=0&&temp>=0;n--){
//				if(pokers.get(n).getSize() == box.getSize()){
//					currentPokers.add(pokers.get(n));
//					temp--;
//				}
//			}
//			return true;
//		}
//		return false;
	    return checkNumberSame(pokers,3,pokersBack);
	}
	
//	判断是否存在2张牌数字相同
	public static boolean checkoutTwoPokerNumber(ArrayList<Poker> pokers,ArrayList<Poker> pokersBack){
//		int temp = 0;
//		Poker box = new Poker();
//		int currentIndex = -1;
//		for(int i = pokers.size() - 1;i>=2;i--){
//			box = pokers.get(i);
//			currentIndex = i;
//			for(int j = i-1;j>=0;j--){
//				 if(pokers.get(i).getSize() == pokers.get(j).getSize()){
//					 temp++;
//				 }
//			}
//			if(temp == 2){
//				break;
//			}else{
//				temp = 0;
//			}
//		}
//		if(temp == 2){
//			currentPokers.add(box);
//			temp--;
//			for(int n = currentIndex-1;n>=0&&temp>=0;n--){
//				if(pokers.get(n).getSize() == box.getSize()){
//					currentPokers.add(pokers.get(n));
//					temp--;
//				}
//			}
//			return true;
//		}
//		return false;
	    return checkNumberSame(pokers,2,pokersBack);
	}
	
	public static ArrayList<Poker> insertSort(ArrayList<Poker> pokers){//插入排序算法
//	             把所有1都变成14,进行排序
	    for(int i = pokers.size()-1;i>=0;i--){
	        if(pokers.get(i).getSize() == 1){
	            pokers.get(i).setSize(14);
	        }
	    }
	    
        for(int i = pokers.size()-1;i>=0;i--){
                for(int j=i;j>0;j--){
                    if (pokers.get(j).getSize()<pokers.get(j-1).getSize()){
                                Poker temp = new Poker(pokers.get(j-1));
                                pokers.get(j-1).setPoker(pokers.get(j));
                                pokers.get(j).setPoker(temp);
                    }else break;
                }
        }
        
//      恢复所有的14
        for(int i = pokers.size()-1;i>=0;i--){
            if(pokers.get(i).getSize() == 14){
                pokers.get(i).setSize(1);
            }
        }
        return pokers;
	}
	
	public static boolean checkNumberSame(ArrayList<Poker> pokers,int size,ArrayList<Poker> pokersBack){
	    int temp = 0;
        Poker box = new Poker();
        int currentIndex = -1;
        for(int i = pokers.size() - 1;i>=size;i--){
            box = pokers.get(i);
            currentIndex = i;
            for(int j = i-1;j>=0;j--){
                 if(pokers.get(i).getSize() == pokers.get(j).getSize()){
                     temp++;
                 }
            }
            if(temp == size){
                break;
            }else{
                temp = 0;
            }
        }
        if(temp == size){
            pokersBack.add(box);
            temp--;
            for(int n = currentIndex-1;n>=0&&temp>=0;n--){
                if(pokers.get(n).getSize() == box.getSize()){
                    pokersBack.add(pokers.get(n));
                    temp--;
                }
            }
            return true;
        }
        return false;
	}
}
