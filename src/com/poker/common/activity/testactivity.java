package com.poker.common.activity;

import com.poker.common.R;
import com.poker.common.entity.Poker;
import com.poker.common.util.Util;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 *
 * 类说明
 *
 * @author RinfonChen:
 * @Day 2014年8月20日 
 * @Time 下午5:46:12
 * @Declaration :测试算法
 *
 */
public class testactivity extends Activity{

    Button ok ;
    EditText a,b,c,d,e,f,g;
    TextView t;
    ArrayList<Poker> pokers = new ArrayList<Poker>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testactivity);
        
        ok = (Button)findViewById(R.id.button1);
        a = (EditText)findViewById(R.id.editText1);
        b = (EditText)findViewById(R.id.EditText01);
        c = (EditText)findViewById(R.id.EditText02);
        d = (EditText)findViewById(R.id.EditText03);
        e = (EditText)findViewById(R.id.EditText04);
        f = (EditText)findViewById(R.id.EditText05);
        g = (EditText)findViewById(R.id.EditText06);
        t = (TextView)findViewById(R.id.textView1);
        ok.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                pokers.clear();
                Poker p1 = new Poker(Integer.parseInt(a.getText().toString()));
                Poker p2 = new Poker(Integer.parseInt(b.getText().toString()));
                Poker p3 = new Poker(Integer.parseInt(c.getText().toString()));
                Poker p4 = new Poker(Integer.parseInt(d.getText().toString()));
                Poker p5 = new Poker(Integer.parseInt(e.getText().toString()));
                Poker p6 = new Poker(Integer.parseInt(f.getText().toString()));
                Poker p7 = new Poker(Integer.parseInt(g.getText().toString()));
                pokers.add(p1);
                pokers.add(p2);
                pokers.add(p3);
                pokers.add(p4);
                pokers.add(p5);
                pokers.add(p6);
                pokers.add(p7);
                t.setText(Util.getPokerType(pokers)+"");
            }
        });
    }
    
}
