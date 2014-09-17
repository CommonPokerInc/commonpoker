package com.poker.common.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.poker.common.R;
import com.poker.common.custom.VerticalSeekBar;
import com.poker.common.entity.ToastView;

public class zzkTestActivity extends Activity implements OnTouchListener,OnGestureListener{

	
	private Button testToastButton;
	private LayoutInflater inflater;
	private View othersView,myView;
	private ToastView othersToast,myToast;
	private int width,height;
	private GestureDetector mGestureDetector; 
	private int verticalMinDistance = 5,horizontalMinDistance = 5;
    private int minVelocity         = 0;
    private RelativeLayout addBetLayout;
    private LinearLayout toastLayout;
    //玩家toast控件的元素
    private ImageView player,allInImg;
    private TextView name,othersAction,myAction,betTxt;
    private int max = 0;//玩家投注的最大值
    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        // TODO Auto-generated method stub
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.zzktest);
	        getScreenSize();
	        
	        testToastButton = (Button)findViewById(R.id.toast);
	        toastLayout = (LinearLayout)findViewById(R.id.toast_layout);
	        
	        //增加赌注的滑动条初始化控件
	        addBetLayout = (RelativeLayout)findViewById(R.id.addbet_layout);
	        addBetLayout.setVisibility(View.INVISIBLE);
	        betTxt = (TextView)findViewById(R.id.bet_txt);
	        allInImg = (ImageView)findViewById(R.id.allin);
	        
	        mGestureDetector = new GestureDetector((OnGestureListener)this);
	        testToastButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showOthersToast();
				}
			});
	        toastLayout.setOnTouchListener(this); 
	        toastLayout.setLongClickable(true);
	        
	 }
	 
	private void getScreenSize(){
		 WindowManager wm = this.getWindowManager();
	     this.width = wm.getDefaultDisplay().getWidth();
	     this.height = wm.getDefaultDisplay().getHeight();
	     Log.v("zkzhou","屏幕高度："+""+height+";"+"屏幕宽度："+""+width);
	     
	}
	
	protected void showOthersToast() {
		// TODO Auto-generated method stub
	    if(inflater == null){
	        inflater = LayoutInflater.from(this);
	    }
	    if(othersView == null){
	        othersView = inflater.inflate(R.layout.player_action_view, null);
	        player = (ImageView)othersView.findViewById(R.id.player_img);
	        name = (TextView)othersView.findViewById(R.id.player_name_txt);
	        othersAction = (TextView)othersView.findViewById(R.id.player_action_txt);
	    }
		if(othersToast == null){
		    othersToast = new ToastView(this,othersView,this.height/4,Toast.LENGTH_LONG);
		}
		othersToast.show();
	}
	
    private void showMyToast() {
        // TODO Auto-generated method stub
        if(inflater == null){
            inflater = LayoutInflater.from(this);
        }
        if(myView == null){
            myView = inflater.inflate(R.layout.me_action_view, null);
            myAction = (TextView)myView.findViewById(R.id.me_action_txt);
        }
        if(myToast == null){
            myToast = new ToastView(this,myView,this.height/4,Toast.LENGTH_LONG);
        }
        myToast.show();
    }

    @Override
    public boolean onDown(MotionEvent arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {
        // TODO Auto-generated method stub
        if(arg0.getX() - arg1.getX() > verticalMinDistance &&  Math.abs(arg2) > minVelocity){
            // TODO Auto-generated method stub
//            showMyToast();
        }else if(arg1.getX() - arg0.getX() > verticalMinDistance &&  Math.abs(arg2) > minVelocity){
//            showMyToast();
        }
        if(arg0.getY() - arg1.getY() > horizontalMinDistance && Math.abs(arg3) > minVelocity){
//        	showSeekBar(arg0.getY() - arg1.getY());
        }else if(arg1.getY() - arg0.getY() > horizontalMinDistance && Math.abs(arg3) > minVelocity){
        	
        }
        return false;
    }
    
	@Override
    public void onLongPress(MotionEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onShowPress(MotionEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean onSingleTapUp(MotionEvent arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onTouch(View arg0, MotionEvent arg1) {
        // TODO Auto-generated method stub
        return mGestureDetector.onTouchEvent(arg1);
    }

}
