package com.poker.common.util;

import com.poker.common.entity.Poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

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
	public static ArrayList<Poker> getPokers(int personsCount){
	    ArrayList<Poker> pokers = new ArrayList(); 
		int pokerLenght = personsCount*2+5;
		Random rd = new Random();
		int num ;
		for(int i = pokerLenght - 1;i>=0;i--){
			num = rd.nextInt(51);
			Poker p = new Poker(num);
			if(!pokers.contains(p)){
				pokers.add(p);
			}else{
				i++;
			}
		}
		return pokers;
	}

//	根据玩家的7张牌得到最大的牌型，数字越大，牌型越大。
	public static int getPokerType(ArrayList<Poker> pokers,ArrayList<Poker> pokersBack){
		insertSort(pokers);
//		pokersBack = new ArrayList<Poker>();
		ArrayList<Poker> pokersBackUp = new ArrayList<Poker>();
		if(checkoutFivePokerColorSame(pokers,pokersBack)){
		    if(chekcoutPokerSucceedingNumbers(pokersBack,pokersBackUp)){
		        pokersBack = pokersBackUp;
		        return FLUSH;
		    }else{
		        return ROYAL_FLUSH;
		    }
		}else if(chekcoutPokerSucceedingNumbers(pokers,pokersBack)){
		    return STRAIGHT;
		}else if(chekcoutFourPokerNumber(pokers,pokersBack)){
		    return KING_KONG;
		}else if(checkoutThreePokerNumber(pokers,pokersBack)){
		    pokers.removeAll(pokersBack);
		    if(checkoutTwoPokerNumber(pokers,pokersBackUp)){
		        pokersBack.addAll(pokersBackUp);
		        return GOURD;
		    }else{
		        return THREE;
		    }
		}else if(checkoutTwoPokerNumber(pokers,pokersBack)){
		    pokers.removeAll(pokersBack);
		    if(checkoutTwoPokerNumber(pokers,pokersBackUp)){
		        pokersBack.addAll(pokersBackUp);
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
		for(int i = pokers.size() - 1;i>=4;i--){
			box = pokers.get(i);
			currentIndex = i;
			temp++;
			for(int j = i-1;j>=0;j--){
				 if(pokers.get(i).getColor() == pokers.get(j).getColor()){
					 temp++;
				 }
			}
			if(temp<5){
			    temp = 0;
			}else{
			    break;
			}
		}
		if(temp >= 5){
			for(int n = 0;n<currentIndex&&temp>=0;n++){
                if(pokers.get(n).getColor() == box.getColor()){
                    Poker pLast = new Poker(pokers.get(n));
                    pokersBack.add(pLast);
                    temp--;
                }
			}
            Poker p = new Poker(box);
            pokersBack.add(p);
			return true;
		}
		return false;
	}
	
//	判断是否存在5张牌数字连续
	public static boolean chekcoutPokerSucceedingNumbers(ArrayList<Poker> pokers,ArrayList<Poker> pokersBack){
//		包括1 13 12 11 10 9情况
		int temp = 0;
    		for(int i = pokers.size()-1;i>=4;i--){
        			for(int j = i;j>=1;j--){
        				if(pokers.get(j).getSize() == pokers.get(j-1).getSize()+1||
        				        (pokers.get(j).getSize() == 1&&pokers.get(j).getSize() == pokers.get(j-1).getSize()-12)){
        					temp++;
        					Poker p = new Poker(pokers.get(j));
        					pokersBack.add(p);
        					if(temp == 4){
                                Poker pLast = new Poker(pokers.get(j-1));
                                pokersBack.add(pLast);
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
    		if(pokers.get(pokers.size()-1).getSize() == 0&&pokers.get(0).getSize() == 1){
    		    pokersBack.add(pokers.get(pokers.size()-1));
    		    temp++;
    		    for(int i = 0;i<pokers.size()-1;i++){
    		        if(pokers.get(i).getSize() == pokers.get(i+1).getSize()-1){
                        temp++;
                        Poker p = new Poker(pokers.get(i));
                        pokersBack.add(p);
                        if(temp == 4){
                            Poker pLast = new Poker(pokers.get(i+1));
                            pokersBack.add(pLast);
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
	    
	    return checkNumberSame(pokers,4,pokersBack);
	}
	
//	判断是否存在3张牌数字相同
	public static boolean checkoutThreePokerNumber(ArrayList<Poker> pokers,ArrayList<Poker> pokersBack){

	    return checkNumberSame(pokers,3,pokersBack);
	}
	
//	判断是否存在2张牌数字相同
	public static boolean checkoutTwoPokerNumber(ArrayList<Poker> pokers,ArrayList<Poker> pokersBack){

	    return checkNumberSame(pokers,2,pokersBack);
	}
	
	public static ArrayList<Poker> insertSort(ArrayList<Poker> pokers){//插入排序算法
//	             把所有1都变成14,进行排序
	    for(int i = pokers.size()-1;i>=0;i--){
	        if(pokers.get(i).getSize() == 0){
	            pokers.get(i).setSize(14);
	        }
	    }
	    
        for(int i = 1;i<pokers.size();i++){
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
                pokers.get(i).setSize(0);
            }
        }
        return pokers;
	}
	
	public static boolean checkNumberSame(ArrayList<Poker> pokers,int size,ArrayList<Poker> pokersBack){
	    int temp = 0;
        Poker box = new Poker();
        int currentIndex = -1;
        for(int i = pokers.size() - 1;i>=(size-1);i--){
            box = pokers.get(i);
            currentIndex = i;
            for(int j = i-1;j>=0;j--){
                 if(pokers.get(i).getSize() == pokers.get(j).getSize()){
                     temp++;
                     if(temp == size - 1){
                         break;
                     }
                 }
            }
            if(temp == size - 1){
                break;
            }else{
                temp = 0;
            }
        }
        if(temp == size - 1){
            Poker p = new Poker(box);
            pokersBack.add(p);
            temp--;
            for(int n = currentIndex-1;n>=0&&temp>=0;n--){
                if(pokers.get(n).getSize() == box.getSize()){
                    Poker pLast = new Poker(pokers.get(n));
                    pokersBack.add(pLast);
                    temp--;
                }
            }
            return true;
        }
        return false;
	}

//	计算筹码
	public static boolean CountChips(ArrayList chips,ArrayList callBack){
	    if(!chips.isEmpty()){
	        Collections.sort(chips);
	        if(!callBack.isEmpty()){
                callBack.clear();
	        }
	        
	        for(int i = 0;i<chips.size();i++){
	            if(Integer.parseInt(chips.get(i).toString())!=0){
    	            callBack.add(Integer.parseInt(chips.get(i).toString())*(chips.size()-i));
    	            for(int j = i;j<chips.size();j++){
    	                chips.set(j, Integer.parseInt(chips.get(j).toString()) - 
    	                        Integer.parseInt(chips.get(i).toString())); 
    	            }
	            }
	            
	        }
	    }
        return false;
	    
	}
}
