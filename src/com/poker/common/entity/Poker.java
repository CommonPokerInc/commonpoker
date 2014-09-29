
package com.poker.common.entity;

import com.poker.common.R;
import com.poker.common.util.PokerUtil;

import java.io.Serializable;

/*
 * author zkzhou
 * description �˿�����
 * time 2014-7-29
 *
 */
public class Poker implements Serializable{

    // �˿��ƻ�ɫ
    // 0������
    // 1��÷��
    // 2������
    // 3������
    private int color;

    // ��С
    private int size;

    // ���
    private int number;

    // �˿���ͼƬID
    private int PokerImageId;

    public Poker() {

    }

    // ���number����ʼ���˿���
    // 0-12����
    // 13-25÷��
    // 26-38����
    // 39-51����
    public Poker(int number) {
        this.number = number;
        this.color = number / 13;
        this.size = number % 13;
        PokerImageId = PokerUtil.pokersImg[number];
    }

    public Poker(Poker p) {
        this.number = p.getNumber();
        this.color = p.getColor();
        this.size = p.getSize();
        PokerImageId = PokerUtil.pokersImg[this.number];
    }

    public void setPoker(Poker p) {
        this.number = p.getNumber();
        this.color = p.getColor();
        this.size = p.getSize();
        PokerImageId = PokerUtil.pokersImg[this.number];
    }

    // private void initNum(String color, int size) {
    // TODO Auto-generated method stub
    // int c=0;
    // if("����".equals(color)){
    // c=1;
    // }else if("÷��".equals(color)){
    // c=2;
    // }else if("����".equals(color)){
    // c=3;
    // }else{
    // c=4;
    // }
    // switch(Integer.valueOf(c)){
    // //����
    // case 1:
    // number = (size%14)*4-3;
    // break;
    // //÷��
    // case 2:
    // number = (size%14)*4-2;
    // break;
    // //����
    // case 3:
    // number = (size%14)*4-1;
    // break;
    // //����
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
