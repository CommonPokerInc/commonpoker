package com.poker.common.activity;

import com.poker.common.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

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
                Intent it = new Intent(SendGameActivity.this,MainActivity.class);
                startActivity(it);
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
