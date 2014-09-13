package com.poker.common.entity;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

public class ToastView extends Toast{
    
    private int duration;
    private View view;
    private int y;
    public ToastView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
    
    public ToastView(Context context,View view,int y,int duration){
        super(context);
        this.view = view;
        this.y = y;
        this.duration = duration;
        this.setDuration(duration);
        this.setGravity(y);
        this.setView(view);
    }
    
    public void setDuration(int duration){
        super.setDuration(duration);
    }
    
    public int getDuration(){
        return duration;
    }
    
    public void setGravity(int y){
        super.setGravity(Gravity.BOTTOM, 0,y);
    }
    
    public int getY(){
        return y;
    }
    
    public void show(){
        super.show();
    }
    
    public View getView(){
        return view;
    }

    public void setView(View view){
        super.setView(view);
    }
}
