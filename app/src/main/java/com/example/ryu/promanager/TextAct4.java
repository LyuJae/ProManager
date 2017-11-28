package com.example.ryu.promanager;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class TextAct4 extends AppCompatActivity  {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView projname;
        LinearLayout LL = (LinearLayout) findViewById(R.id.linearlayout);

        LL.removeAllViews();
        LL.setOrientation(LinearLayout.VERTICAL);

        projname = new TextView(getApplicationContext());
        projname.setLayoutParams(new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        projname.setText("안녕하세요.\n 프로젝트 4번입니다!\n");
        projname.setTextColor(Color.parseColor("#0E0E00"));
        projname.setTextSize(20);
        projname.setGravity(Gravity.CENTER);
        LL.addView(projname);

    }
}
