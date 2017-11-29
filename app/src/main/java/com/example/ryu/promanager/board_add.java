package com.example.ryu.promanager;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by YM on 2017-11-29.
 */
public class board_add extends Activity {


    Handler mainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.getData().getInt("result")) {
                case ServerCommunication.SERVER_COMMAND_SUCCESS:
                    Toast.makeText(getApplicationContext(), "글을 작성하였습니다.", Toast.LENGTH_SHORT).show();
                    Log.d(this.getClass().getName(), "list : " + msg.getData().getString("list"));
                    String list = msg.getData().getString("list");

                    finish();
                    break;
                case ServerCommunication.SERVER_COMMAND_FAIL:
                    Toast.makeText(getApplicationContext(), "작성에 실패하였습니다. 잠시 후 다시 시도해 주세요", Toast.LENGTH_SHORT).show();
                    break;
                case ServerCommunication.SERVER_CONNECT_FAIL:
                    Toast.makeText(getApplicationContext(), "서버에 접속하지 못하였습니다.", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    EditText addtitle;
    EditText addcontent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_add_layout);

        Init();


        ArrayList<board_item> additem;
        additem = new ArrayList<board_item>();

        addtitle = (EditText) findViewById(R.id.add_title);
        addcontent = (EditText) findViewById(R.id.add_content);

        final String title = addtitle.getText().toString();
        final String content = addcontent.getText().toString();


        Button addconfirm = (Button) findViewById(R.id.add_confirm);
        addconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat NOW = new SimpleDateFormat("yyyyMMddHHmm");
                final String strNOW = NOW.format(date);
                System.out.println("strNow : " + strNOW);
                mServer.PostProcess(project_id, usr_id,  strNOW, addtitle.getText().toString(), addcontent.getText().toString());
            }
        });
    }

    String usr_id = null;
    String project_id = null;
    ServerCommunication mServer = null;
    void Init() {
        mServer = new ServerCommunication(mainHandler);
        SharedPreferences pref = getSharedPreferences("info", MODE_PRIVATE);
        this.project_id = pref.getString("project_id", "");
        this.usr_id = pref.getString("usr_id", "");
        System.out.println("project_id : " + project_id);
        System.out.println("usr_id : " + usr_id);
    }
}
