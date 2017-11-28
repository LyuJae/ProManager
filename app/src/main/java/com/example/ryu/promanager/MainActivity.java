package com.example.ryu.promanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstadd);

       Button Btnsave;

        Btnsave = (Button) findViewById(R.id.btnSave);

        Btnsave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, RealActivity.class);

        EditText firstName = (EditText) findViewById(R.id.firstname);
        intent.putExtra("contact_name", firstName.getText().toString());
        EditText Major = (EditText) findViewById(R.id.major);
        intent.putExtra("contact_major", Major.getText().toString());
        EditText Stu_Number = (EditText) findViewById(R.id.Stu_number);
        intent.putExtra("contact_number", Stu_Number.getText().toString());
        EditText firstProject = (EditText) findViewById(R.id.firstproject);
        intent.putExtra("contact_project", firstProject.getText().toString());
        EditText firstProgress = (EditText) findViewById(R.id.firstprogress);
        intent.putExtra("contact_progress", firstProgress.getText().toString());

                startActivity(intent);
            }
        });



    }
}
