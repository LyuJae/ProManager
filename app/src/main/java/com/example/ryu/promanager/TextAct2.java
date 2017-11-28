package com.example.ryu.promanager;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class TextAct2 extends AppCompatActivity  {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text2);
        Button BtnBack;

        BtnBack = (Button) findViewById(R.id.btnback);

        BtnBack.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                finish();
            }
        });
    }
}
