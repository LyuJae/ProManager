package com.example.ryu.promanager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by YM on 2017-11-28.
 */
public class teammate extends Activity {

    ListView listView;
    teammate_adapter teammateadapter;
    ArrayList<teammate_item> list_itemArrayTeammate;


    void UpdateView(String list) {
        StringTokenizer token = new StringTokenizer(list, "{");
        ArrayList<teammate_item> temp =  new ArrayList<teammate_item>();
        teammateadapter = new teammate_adapter(teammate.this, temp);
        listView.setAdapter(teammateadapter);

        list_itemArrayTeammate = new ArrayList<teammate_item>();
        while (token.hasMoreTokens()) {
            String curr = token.nextToken();
            String usr_name = curr.substring(curr.indexOf("usr_name\":\"") + 11, curr.indexOf("\",\"email\":"));
            String email = curr.substring(curr.indexOf("email\":\"") + 8, curr.indexOf("\",\"phone\":\""));
            String phone = curr.substring(curr.indexOf("phone\":\"") + 8, curr.indexOf("\"}"));
            System.out.println("usr_name : " + usr_name);
            System.out.println("email : " + email);
            System.out.println("phone : " + phone);

            list_itemArrayTeammate.add(new teammate_item(R.drawable.ic_launcher, usr_name, phone, email));
            teammateadapter = new teammate_adapter(teammate.this, list_itemArrayTeammate);
        }
        listView.setAdapter(teammateadapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_activity);

        Init();

        listView = (ListView) findViewById(R.id.my_listview);

        Button addBtn = (Button)findViewById(R.id.add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InviteDialog.class);
                startActivity(intent);
            }
        });

    }

    Handler mainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.getData().getInt("result")) {
                case ServerCommunication.SERVER_COMMAND_SUCCESS:
                    //Toast.makeText(getApplicationContext(), "Connect Succ", Toast.LENGTH_SHORT).show();
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
        list_itemArrayTeammate = new ArrayList<teammate_item>();
    }


    @Override
    protected void onResume() {
        System.out.println("onResume in teammate");
        super.onResume();
        mServer.GetProjectMember(this.project_id);
    }

}
