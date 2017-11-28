package com.example.ryu.promanager;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.style.BackgroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {
    final int DYNAMIC_VIEW_ID = 0x8000;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabHost tabHost1 = (TabHost) findViewById(R.id.tabHost1) ;
        tabHost1.setup() ;
        // 첫 번째 Tab. (탭 표시 텍스트:"TAB 1"), (페이지 뷰:"content1")
         TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1") ;
         ts1.setContent(R.id.content1) ;
         ts1.setIndicator("프로젝트") ;
         tabHost1.addTab(ts1) ;
         // 두 번째 Tab. (탭 표시 텍스트:"TAB 2"), (페이지 뷰:"content2")
         TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2") ;
         ts2.setContent(R.id.content2) ;
         ts2.setIndicator("일정") ;
         tabHost1.addTab(ts2) ;

        TextView text1, text2, text3, newTextView;
        LinearLayout Linearlayout;

        text1 = (TextView) findViewById(R.id.Text1);
        text3 = (TextView) findViewById(R.id.Text3);
        text2 = (TextView) findViewById(R.id.Text2);

        Linearlayout = (LinearLayout) findViewById(R.id.linearlayout);
        newTextView = new TextView(getApplicationContext());
        newTextView.setLayoutParams(new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        Linearlayout.setOrientation(LinearLayout.VERTICAL);
        newTextView.setGravity(Gravity.CENTER);
        String ntext="프로젝트4";
        newTextView.setText(ntext);
        newTextView.setTextColor(Color.parseColor("#000000"));
        newTextView.setClickable(true);
        Linearlayout.addView(newTextView);



        newTextView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent2 = new Intent(getApplicationContext(), TextAct4.class);
               startActivity(intent2);
            }
        });

        text1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TextAct.class);
                startActivity(intent);

            }
        });
        text2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TextAct2.class);
                startActivity(intent);

            }
        });
        text3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TextAct3.class);
                startActivity(intent);

            }
        });

    }
}
