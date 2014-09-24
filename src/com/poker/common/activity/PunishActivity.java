
package com.poker.common.activity;

import com.poker.common.R;
import com.poker.common.R.layout;
import com.poker.common.R.menu;
import com.poker.common.entity.ClientPlayer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PunishActivity extends AbsBaseActivity implements OnClickListener {

    private Button zhenxinhua,damaoxian;
    private LinearLayout punishmentLayout;
    private boolean hasBegin = false;
    private List<ClientPlayer> punishmentPlayers = new ArrayList<ClientPlayer>();
    private Intent intent;
    private LayoutParams llayout;
    private RelativeLayout[]playerLayouts;
    private RelativeLayout playerLayout1,playerLayout2,playerLayout3,playerLayout4,playerLayout5;
    private ImageView player1,player2,player3,player4,player5;
    private TextView name1,name2,name3,name4,name5;
    private Timer timer = new Timer();
    private int index =-1;
    private Handler handler = new Handler(){
        @SuppressLint("NewApi")
        @Override  
        public void handleMessage(Message msg)  
        {  
            switch (msg.what) {
                case 0:
                	playerLayouts[4].setAlpha(1f);
                	playerLayouts[0].setAlpha(0.5f);
                    break;
                default:
                	playerLayouts[msg.what - 1].setAlpha(1f);
                	playerLayouts[msg.what].setAlpha(0.5f);
                    break;
            }
            
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punish);
        initData();
        initView();
    }

    private void initData() {
        // TODO Auto-generated method stub
//        intent = this.getIntent();
//        punishmentPlayers = intent.getParcelableArrayListExtra("punishmentPlayers");
    }

    private void initView() {
        // TODO Auto-generated method stub
        zhenxinhua = (Button)findViewById(R.id.punishment_zhenxinhua_btn);
        damaoxian = (Button)findViewById(R.id.punishment_damaoxian_btn);
        
        playerLayout1 = (RelativeLayout)findViewById(R.id.punishment_player_layout1);
        playerLayout2 = (RelativeLayout)findViewById(R.id.punishment_player_layout2);
        playerLayout3 = (RelativeLayout)findViewById(R.id.punishment_player_layout3);
        playerLayout4 = (RelativeLayout)findViewById(R.id.punishment_player_layout4);
        playerLayout5 = (RelativeLayout)findViewById(R.id.punishment_player_layout5);
        
        player1 = (ImageView)findViewById(R.id.punishment_player_img1);
        player2 = (ImageView)findViewById(R.id.punishment_player_img2);
        player3 = (ImageView)findViewById(R.id.punishment_player_img3);
        player4 = (ImageView)findViewById(R.id.punishment_player_img4);
        player5 = (ImageView)findViewById(R.id.punishment_player_img5);
        
        name1 = (TextView)findViewById(R.id.punishment_player_name1);
        name2 = (TextView)findViewById(R.id.punishment_player_name2);
        name3 = (TextView)findViewById(R.id.punishment_player_name3);
        name4 = (TextView)findViewById(R.id.punishment_player_name4);
        name5 = (TextView)findViewById(R.id.punishment_player_name5);
        
        playerLayouts = new RelativeLayout[]{playerLayout1,playerLayout2,playerLayout3,playerLayout4,playerLayout5};
        
//        llayout = new LayoutParams(3*60,LayoutParams.WRAP_CONTENT);
//        punishmentLayout.setLayoutParams(llayout);
        for(int i=0;i<5;i++){
        	playerLayouts[i].setVisibility(View.VISIBLE);
        }
        
        zhenxinhua.setOnClickListener(this);
        damaoxian.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.punishment_damaoxian_btn:
                hasBegin = !hasBegin;
                damaoxian.setClickable(false);
                if(hasBegin){
//                    txt.setText("�Y��");
                    timer.scheduleAtFixedRate(new TimerTask()  
                    {  
                        @Override  
                        public void run()  
                        {  
                            // TODO Auto-generated method stub  
                            index++;
                            if(index >= 5){
                                index = 0;
                            }
                            Message mesasge = new Message();  
                            mesasge.what = index;  
                            handler.sendMessage(mesasge);  
                        }  
                    }, 0, 100);  
                }else{
//                    txt.setText("��ʼ");
                    timer.cancel();
                    zhenxinhua.setClickable(false);
                }
                break;
                
            case R.id.punishment_zhenxinhua_btn:
                finish();
                break;

            default:
                break;
        }
    }

}
