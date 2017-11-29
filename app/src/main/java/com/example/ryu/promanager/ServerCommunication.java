package com.example.ryu.promanager;

import android.content.SharedPreferences;
import android.icu.text.SymbolTable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Ryu on 2017-07-25.
 */

public class ServerCommunication {
    private String mURL = "http://165.132.221.64:52273";                            //thingplugURL, https://data.smartcitygoyang.kr:7070/N2M/openAPI/lakepark/monitoring
    private Handler mHandler;

    //Static Values
    final static int SERVER_COMMAND_SUCCESS = 0x01;          //Server send correct message
    final static int SERVER_COMMAND_FAIL = 0x02;             //Server send fail message
    final static int SERVER_CONNECT_FAIL = 0x04;
    public ServerCommunication(Handler handler) {
        mHandler = handler;
    }

    //Login and Sign up
    public void LoginProcess(String id_text, String pw_text){
        HashMap<String, String> mProperties = new HashMap<String, String>();
        this.SetProperty(mProperties, "id", id_text);
        this.SetProperty(mProperties, "pw", pw_text);
        this.SendMSG(mProperties,"login");
    }
    public void SignupProcess(String id, String pw, String name, String email, String phone){
        HashMap<String, String> mProperties = new HashMap<String, String>();
        this.SetProperty(mProperties, "Content-type", "application/json");
        SetProperty(mProperties, "usr_id", id);
        String msg = pw+","+name+","+email+","+phone;
        SendMSG(mProperties, "signup", msg);
    }

    //My Informations
    public void GetMyTodoProcess(String usr_id){
        HashMap<String, String> mProperties = new HashMap<String, String>();
        this.SetProperty(mProperties, "usr_id", usr_id);
        this.SendMSG(mProperties,"get_my_todo");
    }
    public void GetMyProjectProcess(String usr_id){
        HashMap<String, String> mProperties = new HashMap<String, String>();
        this.SetProperty(mProperties, "usr_id", usr_id);
        this.SendMSG(mProperties,"my_project");
    }


    //Member Management in Project Activity
    public void GetProjectMember(String project_id){
        HashMap<String, String> mProperties = new HashMap<String, String>();
        this.SetProperty(mProperties, "project_id", project_id);
        this.SendMSG(mProperties,"get_project_member");
    }
    public void Invite(String usr_id, String project_id){
        //usr_id = 초대받는사람
        HashMap<String, String> mProperties = new HashMap<String, String>();
        this.SetProperty(mProperties, "usr_id", usr_id);
        this.SetProperty(mProperties, "project_id", project_id);
        this.SendMSG(mProperties, "invite");
    }
    public void AcceptInvite(String usr_id, String project_id){
        HashMap<String, String> mProperties = new HashMap<String, String>();
        this.SetProperty(mProperties, "usr_id", usr_id);
        this.SetProperty(mProperties, "project_id", project_id);
        this.SendMSG(mProperties, "accept_invite");
    }
    public void RefuseInvite(String usr_id, String project_id){
        HashMap<String, String> mProperties = new HashMap<String, String>();
        this.SetProperty(mProperties, "usr_id", usr_id);
        this.SetProperty(mProperties, "project_id", project_id);
        this.SendMSG(mProperties, "refuse_invite");
    }

    //Todo Management in Project Activity
    public void GetProjectTodo(String project_id){
        HashMap<String, String> mProperties = new HashMap<String, String>();
        this.SetProperty(mProperties, "project_id", project_id);
        this.SendMSG(mProperties,"get_project_todo");
    }
    public void CreateTodo(String usr_id, String project_id, String start_date, String due_date, String title, String content){
        HashMap<String, String> mProperties = new HashMap<String, String>();
        this.SetProperty(mProperties, "Accept", "application/json");
        this.SetProperty(mProperties, "Content-type", "application/json");
        this.SetProperty(mProperties, "usr_id", usr_id);
        this.SetProperty(mProperties, "project_id", project_id);
        String msg = start_date+","+  start_date+","+ start_date+","+ content;
        this.SendMSG(mProperties, "create_todo", msg);
    }

    //Board Management in Project Activity
    public void GetProjectPost(String project_id){
        HashMap<String, String> mProperties = new HashMap<String, String>();
        this.SetProperty(mProperties, "project_id", project_id);
        this.SendMSG(mProperties, "get_project_post");
    }
    public void PostProcess(String project_id, String usr_id, String date, String title, String content) {
        HashMap<String, String> mProperties = new HashMap<String, String>();
        this.SetProperty(mProperties, "Accept", "application/json");
        this.SetProperty(mProperties, "Content-type", "application/json");
        this.SetProperty(mProperties, "writer", usr_id);
        this.SetProperty(mProperties, "project_id", project_id);
        this.SetProperty(mProperties, "post_date", date);
        String msg = title+"$king$"+content;
        this.SendMSG(mProperties,"post", msg);
    }

    public void SetProperty(HashMap<String, String> mProperties, String key, String value){
        System.out.println("key " + key + " : value " + value);
        mProperties.put(key, value);
    }

    public void SendMSG(HashMap<String, String> mProperties, final String router){
        HttpURLConnection conn = null;

        URL url = null; //요청 URL을 입력
        try {
            url = new URL(mURL + "/" + router);
            System.out.println("URL : " + url.toString());
            conn = (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ConnectOpen(conn, router);
        SetConnectProperty(conn, mProperties);
        SendMessage(conn);
    }
    private void SendMSG(HashMap<String, String> mProperties, final String router, final String msg){
        HttpURLConnection conn = null;
        URL url = null; //요청 URL을 입력
        try {
            url = new URL(mURL + "/" + router);
            System.out.println("URL : " + url.toString());
            conn = (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ConnectOpen(conn, router);
        SetConnectProperty(conn, mProperties);
        SendMessage(conn, msg);
    }
    private void ConnectOpen(HttpURLConnection conn, final String msg){
        try {
            conn.setRequestMethod("POST"); //요청 방식을 설정 (default : GET)
            conn.setDoInput(true); //input을 사용하도록 설정 (default : true)
            conn.setDoOutput(true); //output을 사용하도록 설정 (default : false)
            conn.setConnectTimeout(60); //타임아웃 시간 설정 (default : 무한대기)
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void SetConnectProperty(HttpURLConnection conn, HashMap<String, String> mProperties){
        for(Map.Entry<String, String> entry : mProperties.entrySet()){
            conn.setRequestProperty(entry.getKey(), entry.getValue());
        }
    }
    private void SendMessage(final HttpURLConnection conn){
        final Thread thread = new Thread(){
            @Override
            public void run() {
                StringBuilder sb = new StringBuilder();
                try {
                    conn.connect();
                    if(conn.getResponseCode() != 200) {
                        Message msg = Message.obtain();
                        Bundle bundle = new Bundle();
                        bundle.putInt("result", SERVER_CONNECT_FAIL);
                        msg.setData(bundle);
                        mHandler.sendMessage(msg);
                        return;
                    }
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        if (sb.length() > 0) {
                            sb.append("\n");
                        }
                        sb.append(line + "\n");
                    }
                    System.out.println("sb : " + sb);
                    ReadMessage(sb);
                } catch (IOException e) {
                    Message msg = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putInt("result", SERVER_CONNECT_FAIL);
                    msg.setData(bundle);
                    mHandler.sendMessage(msg);
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
    private void SendMessage(final HttpURLConnection conn, final String post_msg){
        System.out.println("msg : " + post_msg);
        final Thread thread = new Thread(){
            @Override
            public void run() {
                StringBuilder sb = new StringBuilder();
                try {
                    DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                    dos.write(post_msg.getBytes());
                    dos.flush();
                    dos.close();
                    if(conn.getResponseCode() != 200) {
                        Message msg = Message.obtain();
                        Bundle bundle = new Bundle();
                        bundle.putInt("result", SERVER_CONNECT_FAIL);
                        msg.setData(bundle);
                        mHandler.sendMessage(msg);
                        return;
                    }
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        if (sb.length() > 0) {
                            sb.append("\n");
                        }
                        sb.append(line + "\n");
                    }
                    System.out.println("sb : " + sb);
                    ReadMessage(sb);
                } catch (IOException e) {
                    Message msg = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putInt("result", SERVER_CONNECT_FAIL);
                    msg.setData(bundle);
                    mHandler.sendMessage(msg);
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
    private void ReadMessage(StringBuilder sb){
        Message msg = Message.obtain();
        Bundle bundle = new Bundle();
        String result = sb.substring(sb.indexOf("\"result\"") + 10, sb.indexOf("\"result\"") + 14);
        System.out.println("result : " + result);
        String list = null;
        String err = null;
        if(sb.indexOf("list") != -1)
            list = sb.substring(sb.indexOf("[") + 1, sb.indexOf("]"));

        switch (result) {
            case "succ":
                bundle.putInt("result", SERVER_COMMAND_SUCCESS);
                bundle.putString("list", list);
                msg.setData(bundle);
                break;
            case "fail":
                bundle.putInt("result", SERVER_COMMAND_FAIL);
                msg.setData(bundle);
                break;
        }
        mHandler.sendMessage(msg);
    }
}