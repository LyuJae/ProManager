package com.example.ryu.promanager;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.StringTokenizer;


public class MainActivity extends AppCompatActivity implements TabHost.OnTabChangeListener {
    final int DYNAMIC_VIEW_ID = 0x8000;

    String usr_id = null;
    String isLogin = "false";
    ServerCommunication mServer = null;

    String curr_tab = "project";

    TabHost.TabSpec project_tab = null;
    TabHost.TabSpec date_tab = null;

    void UpdateView(String list) {
        StringTokenizer token = new StringTokenizer(list, "{");
        //일정탭일 때 파싱 로직
        if (curr_tab.compareTo("date") == 0) {
            date_ll.removeAllViews();
            while (token.hasMoreTokens()) {
                String curr = token.nextToken();
                String project_name = curr.substring(curr.indexOf("project_name") + 15, curr.indexOf("\",\"start_date"));
                String start_date = curr.substring(curr.indexOf("start_date\":\"") + 13, curr.indexOf("\",\"due_date"));
                String due_date = curr.substring(curr.indexOf("due_date\":\"") + 11, curr.indexOf("\",\"title"));
                String title = curr.substring(curr.indexOf("title\":\"") + 8, curr.indexOf("\",\"content\":"));
                String content = curr.substring(curr.indexOf("content\":\"") + 10, curr.indexOf("\"}"));
                System.out.println("project_name : " + project_name);
                System.out.println("start_date : " + start_date);
                System.out.println("due_date : " + due_date);
                System.out.println("title : " + title);
                System.out.println("content : " + content);

                TextView tv = new TextView(getApplicationContext());
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                param.setMargins(0, 5, 0, 5);
                tv.setLayoutParams(param);
                String t = "[project_name : " + project_name + "\r\n"
                        + "start_date : " + start_date + "\r\n"
                        + "due_date : " + due_date + "\r\n"
                        + "title : " + title + "\r\n"
                        + "content : " + content + "]";
                tv.setText(t);
                tv.setTextColor(Color.parseColor("#000000"));
                date_ll.addView(tv);
            }
            //==
        } else {
            project_ll.removeAllViews();
            while (token.hasMoreTokens()) {
                //프로젝트 탭일 때 파싱 로직
                String curr = token.nextToken();
                System.out.println("curr : " + curr);
                final String project_id = curr.substring(curr.indexOf("project_id\":") + 12, curr.indexOf(",\"project_name\""));
                String project_name = curr.substring(curr.indexOf("project_name\":") + 15, curr.indexOf("\",\"status\":\""));
                String status = curr.substring(curr.indexOf("status\":\"") + 9, curr.indexOf("\"}"));
                System.out.println("project_id : " + project_id);
                System.out.println("project_name : " + project_name);
                System.out.println("status : " + status);
                //===
                LinearLayout temp_ll = new LinearLayout(getApplicationContext());
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 50);
                param.setMargins(5, 5, 5, 5);
                //temp_ll.setPadding(5, 5, 5, 5);
                temp_ll.setLayoutParams(param);
                temp_ll.setOrientation(LinearLayout.VERTICAL);
                temp_ll.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.p_color));

                TextView tv = new TextView(getApplicationContext());
                param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                tv.setLayoutParams(param);
                String t = project_name ;
                tv.setText(t);
                tv.setTextColor(Color.parseColor("#000000"));
                tv.setTextSize(30);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences pref = null;
                        SharedPreferences.Editor edit = null;
                        pref = getSharedPreferences("info", MODE_PRIVATE);
                        edit = pref.edit();
                        edit.putString("project_id", project_id);
                        edit.commit();


                        Intent intent = new Intent(getApplicationContext(), ProjectActivity.class);
                        startActivity(intent);
                    }
                });
                temp_ll.addView(tv);
                /*
                TextView tv = new TextView(getApplicationContext());
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                param.setMargins(0, 5, 0, 5);
                tv.setLayoutParams(param);
                String t = "[project_id : " + project_id + "\r\n"
                        + "project_name : " + project_name + "\r\n"
                        + "status : " + status + "]";
                tv.setText(t);
                tv.setTextColor(Color.parseColor("#000000"));
                */
                project_ll.addView(temp_ll);
            }
        }
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
            }
        }
    };

    @Override
    protected void onPostResume() {
        super.onPostResume();

        System.out.println("onResume Call");
        super.onResume();
        SharedPreferences pref = getSharedPreferences("info", MODE_PRIVATE);
        this.usr_id = pref.getString("usr_id", "");

        this.isLogin = pref.getString("islogin", "false");
        System.out.println("usr_id : " + this.usr_id);
        System.out.println("isLogin : " + this.isLogin);
        if (this.isLogin.compareTo("true") == 0) {
            if(curr_tab.compareTo("project") == 0){
                mServer.GetMyProjectProcess(this.usr_id);
            }
            else{
                mServer.GetMyTodoProcess(this.usr_id);
            }
        }
    }

    LinearLayout project_ll = null;
    LinearLayout date_ll = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        TabHost tabHost1 = (TabHost) findViewById(R.id.tabHost1);
        tabHost1.setup();
        // 첫 번째 Tab. (탭 표시 텍스트:"TAB 1"), (페이지 뷰:"content1")
        project_tab = tabHost1.newTabSpec("project");
        project_tab.setContent(R.id.projecttab);
        project_tab.setIndicator("프로젝트");
        tabHost1.addTab(project_tab);
        // 두 번째 Tab. (탭 표시 텍스트:"TAB 2"), (페이지 뷰:"content2")
        date_tab = tabHost1.newTabSpec("date");
        date_tab.setContent(R.id.datetab);
        date_tab.setIndicator("일정");
        tabHost1.addTab(date_tab);

        tabHost1.setOnTabChangedListener(this);



        project_ll = (LinearLayout) findViewById(R.id.project_content);
        date_ll = (LinearLayout) findViewById(R.id.date_content);

        Init();


    }

    void Init() {
        SharedPreferences pref = null;
        SharedPreferences.Editor edit = null;
        pref = getSharedPreferences("info", MODE_PRIVATE);
        edit = pref.edit();
        edit.putString("usr_id", "");
        edit.putString("islogin", "false");
        edit.putString("project_id", "");
        edit.commit();

        mServer = new ServerCommunication(mainHandler);
    }

    @Override
    public void onTabChanged(String tabId) {
        System.out.println("changed id : " + tabId);
        curr_tab = tabId;
        if (this.isLogin.compareTo("true") == 0) {
            if(curr_tab.compareTo("project") == 0){
                mServer.GetMyProjectProcess(this.usr_id);
            }
            else{
                mServer.GetMyTodoProcess(this.usr_id);
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemLogin:
                if(item.getTitle().toString().compareTo("Login") ==0){
                    item.setTitle("Logout");
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
                else{
                    //로그아웃
                    item.setTitle("Login");
                }
                return true;
        }
        return false;
    }

}
