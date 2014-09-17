package com.poker.common.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
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
    private LinearLayout toastLayout;
    //玩家toast控件的元素
    private ImageView player,bet_fullImg,bet_nullImg;
    private TextView name,othersAction,myAction;
    private int max = 0;//玩家投注的最大值
    private int mMaxMoney,mCurrentMoney = 0;
    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        // TODO Auto-generated method stub
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.zzktest);
	        getScreenSize();
	        
	        testToastButton = (Button)findViewById(R.id.toast);
	        toastLayout = (LinearLayout)findViewById(R.id.toast_layout);
	        
	        //增加赌注的滑动条初始化控件
	        bet_fullImg = (ImageView)findViewById(R.id.bet_full);
	        bet_nullImg = (ImageView)findViewById(R.id.bet_null);
	        mMaxMoney = 2000;
	        
	        mGestureDetector = new GestureDetector(this);
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
    
    
	private void showMoneyBarSlide(float y) {
		// TODO Auto-generated method stub
		Display display = getWindowManager().getDefaultDisplay();
		int windowWidth = display.getWidth();
		int windowHeight = display.getHeight();
		float percent = y/windowHeight;
		int index = (int)(percent * mMaxMoney) + mCurrentMoney;
		if(index > mMaxMoney){
			index = mMaxMoney;
		}else if(index < 0){
			index = 0;
		}
		
		//更新进度条
		ViewGroup.LayoutParams lp = bet_fullImg.getLayoutParams();
		lp.height = bet_nullImg.getLayoutParams().height * index/mMaxMoney;
		bet_fullImg.setLayoutParams(lp);
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
        	showMoneyBarSlide(arg0.getY() - (int)arg1.getRawY());
        }else if(arg1.getY() - arg0.getY() > horizontalMinDistance && Math.abs(arg3) > minVelocity){
        	showMoneyBarSlide(arg0.getY() - (int)arg1.getRawY());
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
    	 if (mGestureDetector.onTouchEvent(arg1))
             return true;
 
         // 处理手势结束
         switch (arg1.getAction() & MotionEvent.ACTION_MASK) {
         case MotionEvent.ACTION_UP:
             endGesture();
             break;
         }
         
        return mGestureDetector.onTouchEvent(arg1);
    }

	private void endGesture() {
		// TODO Auto-generated method stub
		
	}

}
