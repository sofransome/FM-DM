package com.example.fmdm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserScore extends AppCompatActivity {

    String idnumber;


    TextView tv1, tv2, tv3;
    int score_Easy,score_Medium,score_Hard,userID;
    String username,firstname,lastname,currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_score);





        tv1 = (TextView)findViewById(R.id.ez);
        tv2 = (TextView)findViewById(R.id.med);
        tv3 = (TextView)findViewById(R.id.hard);


        idnumber = getIntent().getStringExtra("studentID");
        score_Easy = getIntent().getIntExtra("score_Easy",0);
        score_Medium = getIntent().getIntExtra("score_Medium",0);
        score_Hard = getIntent().getIntExtra("score_Hard",0);








        tv1.setText("Easy - " + String.valueOf(score_Easy) + "/50" );
        tv2.setText("Medium - " + String.valueOf(score_Medium) + "/50" );
        tv3.setText("Hard - " + String.valueOf(score_Hard) + "/50"  );
    }

    public void goto_SigninAgain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



}
