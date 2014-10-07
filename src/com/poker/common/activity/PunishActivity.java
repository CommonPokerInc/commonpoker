
package com.poker.common.activity;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.poker.common.R;
import com.poker.common.entity.ClientPlayer;
import com.poker.common.util.SystemUtil;
import com.poker.common.util.UserUtil;

public class PunishActivity extends AbsBaseActivity implements OnClickListener {

    private Button zhenxinhua,damaoxian;
    private LinearLayout punishmentLayout;
    private boolean hasBegin = false;
    private ArrayList<ClientPlayer> punishmentPlayers = new ArrayList<ClientPlayer>();
    private Intent intent;
    private LayoutParams llayout;
    private RelativeLayout[]playerLayouts;
    private RelativeLayout playerLayout1,playerLayout2,playerLayout3,playerLayout4,playerLayout5;
//    private ImageView player1,player2,player3,player4,player5;
    private ImageView playerImageList[];
//    private TextView name1,name2,name3,name4,name5;
    private TextView punishText;
    private TextView[] nameList;
    private Timer timer = new Timer();
    private int index =-1;
    private int endIndex = 0,endCur = 0;
    private Handler handler = new Handler(){
        @SuppressLint("NewApi")
        @Override  
        public void handleMessage(Message msg)  
        {  
            switch (msg.what) {
                case 0:
                	playerLayouts[0].setAlpha(0.5f);
                	playerLayouts[punishmentPlayers.size() - 1].setAlpha(1f);
                    break;
                default:
                	playerLayouts[msg.what - 1].setAlpha(1f);
                	playerLayouts[msg.what].setAlpha(0.5f);
                    break;
            }
        	if(endIndex == endCur){
        		timer.cancel();
        		punishText.setText(SystemUtil.getPunishString(1));
            	punishText.setVisibility(View.VISIBLE);
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
        intent = this.getIntent();
		Bundle buldle = intent.getExtras(); 
        punishmentPlayers = (ArrayList<ClientPlayer>)buldle.getSerializable("arrayList");
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
        playerImageList = new ImageView[6];
        playerImageList[0] = (ImageView)findViewById(R.id.punishment_player_img1);
        playerImageList[1] = (ImageView)findViewById(R.id.punishment_player_img2);
        playerImageList[2] = (ImageView)findViewById(R.id.punishment_player_img3);
        playerImageList[3] = (ImageView)findViewById(R.id.punishment_player_img4);
        playerImageList[4] = (ImageView)findViewById(R.id.punishment_player_img5);
//        player1 = (ImageView)findViewById(punishmentPlayers.get(0).);
//        player2 = (ImageView)findViewById(R.id.punishment_player_img2);
//        player3 = (ImageView)findViewById(R.id.punishment_player_img3);
//        player4 = (ImageView)findViewById(R.id.punishment_player_img4);
//        player5 = (ImageView)findViewById(R.id.punishment_player_img5);
        nameList = new TextView[6];
        nameList[0] = (TextView)findViewById(R.id.punishment_player_name1);
        nameList[1] = (TextView)findViewById(R.id.punishment_player_name2);
        nameList[2] = (TextView)findViewById(R.id.punishment_player_name3);
        nameList[3] = (TextView)findViewById(R.id.punishment_player_name4);
        nameList[4] = (TextView)findViewById(R.id.punishment_player_name5);
//        name1 = (TextView)findViewById(R.id.punishment_player_name1);
//        name2 = (TextView)findViewById(R.id.punishment_player_name2);
//        name3 = (TextView)findViewById(R.id.punishment_player_name3);
//        name4 = (TextView)findViewById(R.id.punishment_player_name4);
//        name5 = (TextView)findViewById(R.id.punishment_player_name5);
//        
        playerLayouts = new RelativeLayout[]{playerLayout1,playerLayout2,playerLayout3,playerLayout4,playerLayout5};
        punishText = (TextView)findViewById(R.id.punishbar);
        punishText.setVisibility(View.INVISIBLE);
//        llayout = new LayoutParams(3*60,LayoutParams.WRAP_CONTENT);
//        punishmentLayout.setLayoutParams(llayout);
        for(int i=0;i<punishmentPlayers.size();i++){
        	playerImageList[i].setImageResource(UserUtil.head_img[punishmentPlayers.get(i).getInfo().getAvatar()]);
        	nameList[i].setText(punishmentPlayers.get(i).getInfo().getName());
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
                	endIndex  = new Random().nextInt(20);
                    timer.scheduleAtFixedRate(new TimerTask()  
                    {  
                        @Override  
                        public void run()  
                        {  
                            // TODO Auto-generated method stub  
                            index++;
                            endCur++;
                            if(index >= punishmentPlayers.size()){
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
            	punishText.setText(SystemUtil.getPunishString(0));
            	punishText.setVisibility(View.VISIBLE);
//                finish();
                break;

            default:
                break;
        }
    }

}
