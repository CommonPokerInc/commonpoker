package com.poker.common.layer;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemImage;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CCTexParams;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import android.view.MotionEvent;

import java.util.HashMap;

import javax.microedition.khronos.opengles.GL10;

/**
 *
 * 类说明
 *
 * @author RinfonChen:
 * @Day 2014年8月1日 
 * @Time 下午2:26:43
 * @Declaration :
 *
 */
public class GameLayer extends CCLayer{

    private CCSprite background;
    private CGSize size;
    private CCSprite poker;
    private int i = 0;
    private CCScaleTo actionTo;
    private HashMap<String,CCSprite> pokers;
    private boolean isclick = false;
    public GameLayer(){
        size = CCDirector.sharedDirector().winSize();
        background = CCSprite.sprite("desktop_2.jpg");
        background.setAnchorPoint(CGPoint.zero());
        background.setPosition(CGPoint.ccp(size.width, 0));
        background.setRotation(-90);
        background.setScaleX(size.height/background.getContentSize().width);
        background.setScaleY(size.width/background.getContentSize().height);
        addChild(background,0);
//        mb = CCMoveBy.action((float) 0.25, CGPoint.ccp(size.height/2, size.width/2));
        actionTo = CCScaleTo.action((float)0.25, 0.8f);
        createMenu();
        initPoker();
    }
    
    public void createMenu(){
        CCMenuItemImage item1 = CCMenuItemImage.item("m1.png", "m1.png", this, "ClickCallback");
        CCMenu menu = CCMenu.menu(item1);
        item1.setPosition(CGPoint.make(size.width / 2 - 100, 30));
        addChild(menu, 1);
    }
    
    public void initPoker(){
        pokers = new HashMap<String,CCSprite>();
        for(int i = 0;i<5;i++){
            CCSprite poker = CCSprite.sprite("CLB0"+(i+1)+".png");
            poker.setPosition(CGPoint.ccp((size.width-650) / 2+100*(i+1) , size.height/2+30));
            poker.setVisible(false);
            addChild(poker,2);
            pokers.put(String.valueOf(i), poker);
        }
    }
    
    public void ClickCallback(Object sender){
//        if(poker == null){
//            poker = CCSprite.sprite("CLBAce.png");
//            poker.setPosition(CGPoint.ccp(size.width / 2-200 , size.height/2+30));
//            addChild(poker, 2);
//        }
        if(i<5){
            System.out.println("click"+i);
            if(i<2){
                pokers.get(String.valueOf(i)).setVisible(true);
                pokers.get(String.valueOf(i)).runAction(CCSequence.actions(actionTo, CCCallFunc.action(this, "actionFunCallback")));
            }
            else{
                pokers.get(String.valueOf(i)).setVisible(true);
                pokers.get(String.valueOf(i)).runAction(actionTo);
                i++;
            }
        }
    }
    
    public void actionFunCallback(){
        System.out.println("clickback"+i);
        
        
        if(i<2){
//            pokers.get(String.valueOf(i)).runAction(actionTo);
            i++;
            pokers.get(String.valueOf(i)).setVisible(true);
            pokers.get(i+"").runAction(CCSequence.actions(actionTo, CCCallFunc.action(this, "actionFunCallback")));
        }else{
            i++;
            return;
        }
    }
    
}
