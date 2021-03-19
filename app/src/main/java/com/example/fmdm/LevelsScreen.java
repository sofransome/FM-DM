package com.example.fmdm;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LevelsScreen extends AppCompatActivity {

    int easy_score1, easy_score3,medium_score1;
    String idnumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels_screen);


        idnumber = getIntent().getStringExtra("studentID");

        easy_score1 = getIntent().getIntExtra("easy_score", 0);
        easy_score3 = getIntent().getIntExtra("easy_score2", 0);

        Button buttonEasy = (Button)findViewById(R.id.btnEasy);
        Button buttonMedium = (Button)findViewById(R.id.btnMedium);
        Button buttonHard = (Button)findViewById(R.id.btnHard);


        buttonEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelsScreen.this,EasyScreen.class);
                intent.putExtra("studentID",idnumber);
                startActivity(intent);
                finish();


            }
        });

        buttonMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelsScreen.this,MediumScreen.class);
                intent.putExtra("studentID",idnumber);
                intent.putExtra("easy_score1",easy_score1);
                startActivity(intent);
                finish();
            }
        });

        buttonHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelsScreen.this,HardScreen.class);
                intent.putExtra("studentID",idnumber);
                intent.putExtra("easy_score3",easy_score3);
                intent.putExtra("medium_score1",medium_score1);
                startActivity(intent);
                finish();
            }
        });


//        easy_score1 = getIntent().getIntExtra("easy_score", 0);
//        easy_score3 = getIntent().getIntExtra("easy_score2", 0);
        medium_score1 = getIntent().getIntExtra("medium_score", 0);
        if((easy_score1 < 2) || (easy_score1 == 0)){
            buttonMedium.setEnabled(false);
        }else{
            buttonEasy.setEnabled(false);
            buttonMedium.setEnabled(true);
        }



//        if((easy_score3 < 2) || (easy_score3 == 0)){
//            buttonMedium.setEnabled(false);
//        }else{
//            buttonMedium.setEnabled(true);
//        }
//        int score_medium = getIntent().getIntExtra("medium_score", 0);

        if((medium_score1 < 2) || (medium_score1 == 0)){
            buttonHard.setEnabled(false);
        }else{
            buttonEasy.setEnabled(false);
            buttonMedium.setEnabled(false);
            buttonHard.setEnabled(true);
        }
    }
}
