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
 * Created by YM on 2017-11-29.
 */
public class schedule extends Activity {

    ListView listView_schedule;
    schedule_adapter scheduleadapter;
    ArrayList<schedule_item> list_itemArraySchedule;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_layout);

        Init();

        listView_schedule = (ListView) findViewById(R.id.schedule_listview);
        list_itemArraySchedule = new ArrayList<schedule_item>();

        list_itemArraySchedule.add(new schedule_item("김철수", "7단원 코딩", "2017-11-29일까지"));

        Button sch = (Button) findViewById(R.id.schedule_btn);

        sch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        scheduleadapter = new schedule_adapter(schedule.this, list_itemArraySchedule);
        listView_schedule.setAdapter(scheduleadapter);
    }

    void UpdateView(String list) {
        StringTokenizer token = new StringTokenizer(list, "{");
        ArrayList<schedule_item> temp =  new ArrayList<schedule_item>();
        scheduleadapter = new schedule_adapter(schedule.this, temp);
        listView_schedule.setAdapter(scheduleadapter);

        list_itemArraySchedule = new ArrayList<schedule_item>();
        while (token.hasMoreTokens()) {
            String curr = token.nextToken();
            String usr_name = curr.substring(curr.indexOf("usr_name\":\"") + 11, curr.indexOf("\",\"start_date"));
            String start_date = curr.substring(curr.indexOf("start_date\":\"") + 13, curr.indexOf("\",\"due_date\""));
            String due_date = curr.substring(curr.indexOf("due_date\":\"") + 11, curr.indexOf("\",\"title\":\""));
            String title = curr.substring(curr.indexOf("title\":\"") + 8, curr.indexOf("\",\"content\":\""));
            String content = curr.substring(curr.indexOf("content\":\"") + 10, curr.indexOf("\"}"));
            System.out.println("usr_name : " + usr_name);
            System.out.println("start_date : " + start_date);
            System.out.println("due_date : " + due_date);
            System.out.println("tile : " + title);
            System.out.println("content : " + content);

            list_itemArraySchedule.add(new schedule_item(title, content, due_date));
            scheduleadapter = new schedule_adapter(schedule.this, list_itemArraySchedule);

        }
        listView_schedule.setAdapter(scheduleadapter);
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
        list_itemArraySchedule = new ArrayList<schedule_item>();
    }


    @Override
    protected void onResume() {
        System.out.println("onResume in schedule");
        super.onResume();
        mServer.GetProjectTodo(this.project_id);
    }

}