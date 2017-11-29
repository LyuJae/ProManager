package com.example.ryu.promanager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by YM on 2017-11-28.
 */
public class board extends Activity {

    ListView listView_board;
    board_adapter boardadapter;
    ArrayList<board_item> list_itemArrayBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("onCreate in borad");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_layout);

        Init();
        Button add = (Button) findViewById(R.id.board_btnAdd);
        Button del = (Button) findViewById(R.id.board_btnDel);

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent_board_add = new Intent(getApplicationContext(), board_add.class);
                startActivity(intent_board_add);
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardadapter.notifyDataSetChanged();
            }
        });
        listView_board = (ListView) findViewById(R.id.post_list);
    }
    void UpdateView(String list) {
        StringTokenizer token = new StringTokenizer(list, "{");
        ArrayList<board_item> temp =  new ArrayList<board_item>();
        boardadapter = new board_adapter(board.this, temp);
        listView_board.setAdapter(boardadapter);

        list_itemArrayBoard = new ArrayList<board_item>();
        while (token.hasMoreTokens()) {
            String curr = token.nextToken();
            String post_id = curr.substring(curr.indexOf("post_id\":") + 9, curr.indexOf(",\"writer"));
            String usr_id = curr.substring(curr.indexOf("writer\":\"") + 9, curr.indexOf("\",\"title"));
            String title = curr.substring(curr.indexOf("title\":\"") + 8, curr.indexOf("\",\"date\":"));
            String date = curr.substring(curr.indexOf("date\":\"") + 7, curr.indexOf("\",\"content\":"));
            String content = curr.substring(curr.indexOf("content\":\"") + 10, curr.indexOf("\"}"));
            System.out.println("post_id : " + post_id);
            System.out.println("usr_id : " + usr_id);
            System.out.println("title : " + title);
            System.out.println("date : " + date);
            System.out.println("content : " + content);

            list_itemArrayBoard.add(new board_item(usr_id, title, content, post_id, date));
            boardadapter = new board_adapter(board.this, list_itemArrayBoard);

        }
        listView_board.setAdapter(boardadapter);
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
                    UpdateView(list);

                    break;
                case ServerCommunication.SERVER_COMMAND_FAIL:
                    Toast.makeText(getApplicationContext(), "Connect Fail", Toast.LENGTH_SHORT).show();
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
        list_itemArrayBoard = new ArrayList<board_item>();
    }


    @Override
    protected void onResume() {
        System.out.println("onResume in board");
        super.onResume();
        mServer.GetProjectPost(this.project_id);
    }


}
