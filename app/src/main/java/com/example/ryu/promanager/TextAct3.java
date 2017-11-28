package com.example.ryu.promanager;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TextAct3 extends AppCompatActivity  {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text3);
        Button btnBack;

        btnBack = (Button) findViewById(R.id.BtnBack);

        btnBack.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                finish();
            }
        });
    }
}
