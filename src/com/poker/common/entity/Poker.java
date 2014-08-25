
package com.poker.common.entity;

import com.poker.common.R;

import android.R.bool;

/*
 * author zkzhou
 * description 扑克牌类
 * time 2014-7-29
 *
 */
public class Poker {

    // 扑克牌花色
    // 0：方块
    // 1：梅花
    // 2：红桃
    // 3：黑桃
    private int color;

    // 大小
    private int size;

    // 编号
    private int number;

    // 扑克牌图片ID
    private int PokerImageId;

    private static int[] pokersImg = {
            R.drawable.diamond1, R.drawable.diamond2, R.drawable.diamond3, R.drawable.diamond4,
            R.drawable.diamond5, R.drawable.diamond6, R.drawable.diamond7, R.drawable.diamond8,
            R.drawable.diamond9, R.drawable.diamond10, R.drawable.diamond11, R.drawable.diamond12,
            R.drawable.diamond13, R.drawable.club1, R.drawable.club2, R.drawable.club3,
            R.drawable.club4, R.drawable.club5, R.drawable.club6, R.drawable.club7,
            R.drawable.club8, R.drawable.club9, R.drawable.club10, R.drawable.club11,
            R.drawable.club12, R.drawable.club13, R.drawable.heart1, R.drawable.heart2,
            R.drawable.heart3, R.drawable.heart4, R.drawable.heart5, R.drawable.heart6,
            R.drawable.heart7, R.drawable.heart8, R.drawable.heart9, R.drawable.heart10,
            R.drawable.heart11, R.drawable.heart12, R.drawable.heart13, R.drawable.spade1,
            R.drawable.spade2, R.drawable.spade3, R.drawable.spade4, R.drawable.spade5,
            R.drawable.spade6, R.drawable.spade7, R.drawable.spade8, R.drawable.spade9,
            R.drawable.spade10, R.drawable.spade11, R.drawable.spade12, R.drawable.spade13
    };

    public Poker() {

    }

    // 根据number来初始化扑克牌
    // 0-12方块
    // 13-25梅花
    // 26-38红桃
    // 39-51黑桃
    public Poker(int number) {
        this.number = number;
        this.color = number / 13;
        this.size = number % 13;
        PokerImageId = pokersImg[number];
    }

    public Poker(Poker p) {
        this.number = p.getNumber();
        this.color = p.getColor();
        this.size = p.getSize();
        PokerImageId = pokersImg[this.number];
    }

    public void setPoker(Poker p) {
        this.number = p.getNumber();
        this.color = p.getColor();
        this.size = p.getSize();
        PokerImageId = pokersImg[this.number];
    }

    // private void initNum(String color, int size) {
    // TODO Auto-generated method stub
    // int c=0;
    // if("方块".equals(color)){
    // c=1;
    // }else if("梅花".equals(color)){
    // c=2;
    // }else if("红桃".equals(color)){
    // c=3;
    // }else{
    // c=4;
    // }
    // switch(Integer.valueOf(c)){
    // //方块
    // case 1:
    // number = (size%14)*4-3;
    // break;
    // //梅花
    // case 2:
    // number = (size%14)*4-2;
    // break;
    // //红桃
    // case 3:
    // number = (size%14)*4-1;
    // break;
    // //黑桃
    // case 4:
    // number = (size%14)*4;
    // break;
    // default:
    // break;
    // }
    // }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    
    public int getPokerImageId() {
        return PokerImageId;
    }

    public void setPokerImageId(int pokerImageId) {
        PokerImageId = pokerImageId;
    }

    @Override
    public boolean equals(Object o) {
        // TODO Auto-generated method stub
        Poker s = (Poker) o;
        if (s.getNumber() == this.getNumber()) {
            return true;
        } else {
            return false;
        }
        // return super.equals(o);
    }

}
