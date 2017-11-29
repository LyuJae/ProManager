package com.example.ryu.promanager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ProjectActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Init();

        Button board = (Button) findViewById(R.id.board);
        Button schedule = (Button) findViewById(R.id.schedule);
        Button teammate = (Button) findViewById(R.id.teammate);

        board.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent_board = new Intent(getApplicationContext(), board.class);
                startActivity(intent_board);
            }
        });

        teammate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent_teammate = new Intent(getApplicationContext(), teammate.class);
                startActivity(intent_teammate);
            }
        });
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_schedule = new Intent(getApplicationContext(), schedule.class);
                startActivity(intent_schedule);
            }
        });

    }

    Handler mainHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.getData().getInt("result")){
                case ServerCommunication.SERVER_COMMAND_SUCCESS:
                    Toast.makeText(getApplicationContext(), "Connect Succ", Toast.LENGTH_SHORT).show();
                    System.out.println("list : " + msg.getData().getString("list"));
                    String list = msg.getData().getString("list");
                    //UpdateView(list);
                    break;
                case ServerCommunication.SERVER_COMMAND_FAIL:
                    Toast.makeText(getApplicationContext(), "Connect Fail", Toast.LENGTH_SHORT).show();
                    break;
                case ServerCommunication.SERVER_CONNECT_FAIL:
                    Toast.makeText(getApplicationContext(), "서버에 접속하지 못하였습니다.", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    String usr_id = null;
    String project_id = null;

    ServerCommunication mServer = null;


    void Init(){
        SharedPreferences pref = getSharedPreferences("info", MODE_PRIVATE);
        this.project_id = pref.getString("project_id", "");
        this.usr_id = pref.getString("usr_id", "");
        System.out.println("project_id : " + project_id);
        System.out.println("usr_id : " + usr_id);
        mServer = new ServerCommunication(mainHandler);
    }
    @Override
    protected void onResume() {
        super.onResume();


    }
}
