package com.poker.common.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.poker.common.R;
import com.poker.common.entity.Poker;
import com.poker.common.util.FileUtil;
import com.poker.common.util.Util;

/**
 *
 * ��˵��
 *
 * @author RinfonChen:
 * @Day 2014��8��20�� 
 * @Time ����5:46:12
 * @Declaration :�����㷨
 *
 */
public class TestActivity extends Activity{

    Button ok ;
    EditText a,b,c,d,e,f,g;
    TextView t;
    ArrayList<Poker> pokers = new ArrayList<Poker>();
    FileUtil file;
    ArrayList<Poker> pokersBack;
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
        file = new FileUtil(getApplicationContext());
        ok.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                pokers = Util.getPokers(1);
                pokersBack = new ArrayList<Poker>();
                String str = "";
                StringBuffer sb = new StringBuffer();
                for(int i = 0;i<pokers.size();i++){
                    str += pokers.get(i).getNumber()+" ";
                }
                int result = Util.getPokerType(pokers,pokersBack);
                t.setText(result+"");
                str += "---->"+result+": ��ʤ����: ";
                for(int i = 0;i<pokersBack.size();i++){
                    str += pokersBack.get(i).getNumber()+" ";
                }
//                String str = p1.getNumber()+" "+p2.getNumber()+" "+p3.getNumber()+" "
//                        +p4.getNumber()+" "+p5.getNumber()+" "+p6.getNumber()+" "+p7.getNumber()+"--->"
//                        +Util.getPokerType(pokers);
                sb.append(str);
                sb.append("\r\n");
                try {
                    file.writeDateFile("result.txt", sb.toString());
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
    }
    
}
