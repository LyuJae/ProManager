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

        EditText editname, editprogress, editproject;
        Button Btninc, Btndec, Btnsave;
        final RatingBar rating;
        editname = (EditText) findViewById(R.id.name);
        editproject = (EditText) findViewById(R.id.project);
        editprogress = (EditText) findViewById(R.id.progress);
        Btninc = (Button) findViewById(R.id.btnInc);
        Btndec = (Button) findViewById(R.id.btnDec);
        rating = (RatingBar) findViewById(R.id.ratingBar);
        Btnsave = (Button) findViewById(R.id.btnSave);


        Btninc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rating.setRating(rating.getRating()+rating.getStepSize());
            }
        });

        Btndec.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rating.setRating(rating.getRating()-rating.getStepSize());
            }
        });


            Intent intent = new Intent(addAct.this, MainActivity.class);

            intent.putExtra("contact_name", editname.getText().toString());
            intent.putExtra("contact_project", editproject.getText().toString());
            intent.putExtra("contact_progress", editprogress.getText().toString());

        Btnsave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
        }
    });



    }
}
