package com.example.ryu.promanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
public class addAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

       Button Btnsave = (Button) findViewById(R.id.btnSave);
       Button Btnback = (Button) findViewById(R.id.btnBack);

        Btnsave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(addAct.this, RealActivity.class);

                EditText Name = (EditText) findViewById(R.id.name);
                intent.putExtra("contact_name", Name.getText().toString());
                EditText major = (EditText) findViewById(R.id.Major);
                intent.putExtra("contact_major", major.getText().toString());
                EditText Stu_number = (EditText) findViewById(R.id.Stu_Number);
                intent.putExtra("contact_number", Stu_number.getText().toString());
                EditText project = (EditText) findViewById(R.id.Project);
                intent.putExtra("contact_project", project.getText().toString());
                EditText progress = (EditText) findViewById(R.id.Progress);
                intent.putExtra("contact_progress", progress.getText().toString());


                startActivity(intent);
            }
        });
        Btnback.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
        }
    });



    }
}
