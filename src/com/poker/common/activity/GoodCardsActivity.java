package com.poker.common.activity;

import com.poker.common.R;
import com.poker.common.custom.EachGoodCardsView;
import com.poker.common.util.ScreenShot;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class GoodCardsActivity extends AbsBaseActivity implements OnClickListener{

    private EachGoodCardsView eachGoodCardsView;
    private ImageView poker1,poker2,poker3,poker4,poker5;
    private ImageView snapshot,close;
    private RelativeLayout snapshotLayout,closeLayout;
    private boolean isScreenShot = false;
    
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 取消标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 全屏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_cards);
        initView();
        initData();
    }


    @SuppressLint("NewApi")
    private void initData() {
        // TODO Auto-generated method stub
        poker4.setAlpha(0.5f);
        poker5.setAlpha(0.5f);
    }


    private void initView() {
        // TODO Auto-generated method stub
        eachGoodCardsView = (EachGoodCardsView)findViewById(R.id.good_cards_view);
        poker1 = (ImageView)findViewById(R.id.good_cards_poker1_img);
        poker2 = (ImageView)findViewById(R.id.good_cards_poker2_img);
        poker3 = (ImageView)findViewById(R.id.good_cards_poker3_img);
        poker4 = (ImageView)findViewById(R.id.good_cards_poker4_img);
        poker5 = (ImageView)findViewById(R.id.good_cards_poker5_img);
        snapshot = (ImageView)findViewById(R.id.good_cards_snapshot_img);
        close = (ImageView)findViewById(R.id.good_cards_close_img);
        snapshotLayout = (RelativeLayout)findViewById(R.id.good_cards_snapshot_layout);
        closeLayout = (RelativeLayout)findViewById(R.id.good_cards_close_layout);
        
        snapshot.setOnClickListener(this);
        close.setOnClickListener(this);
    }


    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch  (arg0.getId()) {
            case R.id.good_cards_snapshot_img:
                snapshotLayout.setVisibility(View.INVISIBLE);
                closeLayout.setVisibility(View.INVISIBLE);
                isScreenShot = ScreenShot.shoot(GoodCardsActivity.this);
                if (isScreenShot && !snapshotLayout.isShown() && !closeLayout.isShown()) {
                    Toast.makeText(getApplicationContext(), "截图保存成功！",
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "截图保存不成功！",
                            Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.good_cards_close_img:
                finish();
                break;
                
            default:
                break;
        }
    }
}
