package com.poker.common.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.poker.common.R;

/**
 *
 * 类说明
 *
 * @author RinfonChen:
 * @Day 2014年8月4日 
 * @Time 下午7:16:39
 * @Declaration :
 *
 */
public class SendGameActivity extends Activity{

    private ImageButton backBtn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_game_activity);
        
        backBtn = (ImageButton)findViewById(R.id.send_game_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

}
