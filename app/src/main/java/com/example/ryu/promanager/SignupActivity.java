package com.example.ryu.promanager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Ryu on 2017-11-05.
 */

public class SignupActivity extends AppCompatActivity{

    EditText nameText;
    EditText idText;
    EditText pwText;
    EditText phoneText;
    EditText emailText;
    Button submitBtn;
    Button idcheckBtn;
    ServerCommunication serverCommunication = null;
    Handler mainHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.getData().getInt("result")){
                case ServerCommunication.SERVER_COMMAND_SUCCESS:
                    Toast.makeText(getApplicationContext(), "회원가입 완료!", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case ServerCommunication.SERVER_COMMAND_FAIL:
                    Toast.makeText(getApplicationContext(), "중복된 ID 입니다.", Toast.LENGTH_SHORT).show();
                    break;
                case ServerCommunication.SERVER_CONNECT_FAIL:
                    Toast.makeText(getApplicationContext(), "서버에 접속하지 못하였습니다.", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Sign Up");
        setContentView(R.layout.signup_layout);

        Init();

        nameText = (EditText)findViewById(R.id.name_text);
        idText = (EditText)findViewById(R.id.id_text);
        pwText = (EditText)findViewById(R.id.pw_text);
        phoneText = (EditText)findViewById(R.id.phone_text);
        emailText = (EditText)findViewById(R.id.email_text);
        submitBtn = (Button)findViewById(R.id.submit_btn);
        idcheckBtn = (Button)findViewById(R.id.checkid_btn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverCommunication.SignupProcess (idText.getText().toString(), pwText.getText().toString(),nameText.getText().toString(), emailText.getText().toString(), phoneText.getText().toString());
            }
        });
        idcheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void Init(){
        serverCommunication = new ServerCommunication(mainHandler);
    }

}
