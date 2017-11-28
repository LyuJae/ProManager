package com.example.ryu.promanager;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class TextAct4 extends AppCompatActivity  {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnback;

        btnback = (Button) findViewById(R.id.btnBack);

        btnback.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                finish();
            }
        });

    }
}
