package com.example.ryu.promanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class RealActivity extends AppCompatActivity {

    Button Btnadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();

        Btnadd = (Button) findViewById(R.id.btnadd);

        TextView textname = (TextView) findViewById(R.id.textName);
        String name = intent.getStringExtra("contact_name");
        if(name !=null)
            textname.setText("이름: " +name);
        TextView textMajor = (TextView) findViewById(R.id.textmajor);
        String Major = intent.getStringExtra("contact_major");
        if(name !=null)
            textMajor.setText("학과: " + Major);
        TextView textStu_number = (TextView) findViewById(R.id.textstu_number);
        String Number = intent.getStringExtra("contact_number");
        if(name !=null)
            textStu_number.setText("학번: " +Number);
        TextView textProject = (TextView) findViewById(R.id.textproject);
        String proj = intent.getStringExtra("contact_project");
        if(proj !=null)
            textProject.setText("프로젝트 명: " +proj);
        TextView textProgress = (TextView) findViewById(R.id.textprogress);
        String prog = intent.getStringExtra("contact_progress");
        if(proj !=null)
            textProgress.setText("프로젝트 진행상황:" + prog);

        Btnadd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intents = new Intent(getApplicationContext(),addAct.class);
                startActivity(intents);

            }
        });
    }
}
