
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PunishActivity extends Activity implements OnClickListener {

    private ImageView punishmentBegin,close;
    private LinearLayout punishmentLayout;
    private TextView punishmentTxt,txt;
    private boolean hasBegin = false;
    private List<ClientPlayer> punishmentPlayers = new ArrayList<ClientPlayer>();
    private Intent intent;
    private LayoutParams llayout;
    private ImageView[]players;
    private ImageView player1,player2,player3,player4,player5;
    private Timer timer = new Timer();
    private int index =-1;
    private Handler handler = new Handler(){
        @SuppressLint("NewApi")
        @Override  
        public void handleMessage(Message msg)  
        {  
            switch (msg.what) {
                case 0:
                    players[4].setAlpha(1f);
                    players[0].setAlpha(0.5f);
                    break;
                default:
                    players[msg.what - 1].setAlpha(1f);
                    players[msg.what].setAlpha(0.5f);
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
        punishmentBegin = (ImageView)findViewById(R.id.punishment_img);
        close = (ImageView)findViewById(R.id.punishment_close_img);
        punishmentLayout = (LinearLayout)findViewById(R.id.punishment_layout);
        punishmentTxt = (TextView)findViewById(R.id.punishment_txt);
        txt = (TextView)findViewById(R.id.punishment_begin_txt);
        
        player1 = (ImageView)findViewById(R.id.punishment_player_img1);
        player2 = (ImageView)findViewById(R.id.punishment_player_img2);
        player3 = (ImageView)findViewById(R.id.punishment_player_img3);
        player4 = (ImageView)findViewById(R.id.punishment_player_img4);
        player5 = (ImageView)findViewById(R.id.punishment_player_img5);
        
        players = new ImageView[]{player1,player2,player3,player4,player5};
        
//        llayout = new LayoutParams(3*60,LayoutParams.WRAP_CONTENT);
//        punishmentLayout.setLayoutParams(llayout);
        for(int i=0;i<5;i++){
            players[i].setVisibility(View.VISIBLE);
        }
        
        punishmentBegin.setOnClickListener(this);
        close.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.punishment_img:
                hasBegin = !hasBegin;
                if(hasBegin){
                    txt.setText("½YÊø");
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
                    txt.setText("¿ªÊ¼");
                    timer.cancel();
                    punishmentBegin.setClickable(false);
                }
                break;
                
            case R.id.punishment_close_img:
                finish();
                break;

            default:
                break;
        }
    }

}
