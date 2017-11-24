package com.example.ryu.promanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textName, textProject, textProgress, textMajor, textStu_number
    Button Btnadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        textName = (TextView) findViewById(R.id.textName);
        textMajor = (TextView) findViewById(R.id.major)
        textProject = (TextView) findViewById(R.id.textProject);
        textProgress = (TextView) findViewById(R.id.textProgress);
        Btnadd = (Button) findViewById(R.id.btnadd);

        String name = intent.getStringExtra("contact_name");
        if(name !=null)
            textName.setText(name);
        String Major = intent.getStringExtra("contact_major");
        if(name !=null)
            textName.setText(name);
        String Number = intent.getStringExtra("contact_number");
        if(name !=null)
            textName.setText(name);
        String proj = intent.getStringExtra("contact_project");
        if(proj !=null)
            textProject.setText(proj);
        String prog = intent.getStringExtra("contact_progress");
        if(proj !=null)
            textProgress.setText(prog);

        Btnadd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intents = new Intent(getApplicationContext(),addAct.class);
                startActivity(intents);

            }
        });
    }
}
