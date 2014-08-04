package com.poker.common.activity;

import com.poker.common.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;

/**
 *
 * 类说明
 *
 * @author RinfonChen:
 * @Day 2014年8月4日 
 * @Time 下午2:44:40
 * @Declaration :
 *
 */
public class MainActivity extends Activity implements OnClickListener{

    private ImageButton sendGameBtn;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mainactivity);
        init();
    }
    
    public void init(){
        sendGameBtn = (ImageButton)findViewById(R.id.send_game_btn);
        sendGameBtn.setOnClickListener(this);
    }

    
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if(v.getId() == R.id.send_game_btn){
            Intent it = new Intent(MainActivity.this,SendGameActivity.class);
            startActivity(it);
            finish();
        }
        
    }
    
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    
    
    

}
