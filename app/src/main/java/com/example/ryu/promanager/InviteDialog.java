package com.example.ryu.promanager;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Ryu on 2017-11-29.
 */

public class InviteDialog extends Activity {
    EditText in_id = null;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.invite_layout);

        Init();

        in_id = (EditText)findViewById(R.id.invite_text);
        Button iBtn = (Button)findViewById(R.id.invite_btn);
        iBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mServer.Invite(in_id.getText().toString(), project_id);
            }
        });
    }
    Handler mainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.getData().getInt("result")) {
                case ServerCommunication.SERVER_COMMAND_SUCCESS:
                    Toast.makeText(getApplicationContext(), "Connect Succ", Toast.LENGTH_SHORT).show();
                    Log.d(this.getClass().getName(), "list : " + msg.getData().getString("list"));
                    String list = msg.getData().getString("list");

                    finish();
                    break;
                case ServerCommunication.SERVER_COMMAND_FAIL:
                    Toast.makeText(getApplicationContext(), "초대에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case ServerCommunication.SERVER_CONNECT_FAIL:
                    Toast.makeText(getApplicationContext(), "서버에 접속하지 못하였습니다.", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "Handler got default", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    ServerCommunication mServer = null;
    String usr_id = null;
    String project_id = null;

    void Init() {
        mServer = new ServerCommunication(mainHandler);
        SharedPreferences pref = getSharedPreferences("info", MODE_PRIVATE);
        this.project_id = pref.getString("project_id", "");
        this.usr_id = pref.getString("usr_id", "");
        System.out.println("project_id : " + project_id);
        System.out.println("usr_id : " + usr_id);
    }
}
