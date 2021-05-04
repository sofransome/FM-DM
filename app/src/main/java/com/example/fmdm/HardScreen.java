package com.example.fmdm;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class HardScreen extends AppCompatActivity {


    String URL = "https://thesis-api-usls.herokuapp.com/hard", resultURL = "https://thesis-api-usls.herokuapp.com/get-result";
    StringBuilder messages;


    Context context;
    Question[] hardQuestionArray;
    String[][] hardArray = new String[51][2];

    long START_TIME_IN_MILLIS = 30000;


    ArrayList<Character> input = new ArrayList<Character>();
    ArrayList<Float> values = new ArrayList<Float>();
    float pinky,ring,middle,index,thumb,contact1,contact2,contact3,contact4, contact5, contact6;


    TextView tv_question,tv_countdownTimer,tv_scoreHard;
    EditText editText_Hard;
    CountDownTimer countDownTimer;
    boolean TimerRunning;
    long TimeLeftInMillis = START_TIME_IN_MILLIS;
    String correctAnswer_Hard,hard_String,idnumber,firstName,lastName,temp = "",difficulty = "hard",grading = "1";
    int score_Easy, score_Medium, score_Hard, hardCounter;
    Set<Integer> set = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard_screen);


        tv_countdownTimer = (TextView)findViewById(R.id.tv_forCountdownTimer);
        tv_question = (TextView)findViewById(R.id.tv_forQuestion);
        tv_scoreHard = (TextView)findViewById(R.id.score_HardTV);
        editText_Hard = (EditText)findViewById(R.id.edittext_Hard);

        idnumber = getIntent().getStringExtra("studentID");
        score_Easy = getIntent().getIntExtra("easy_score3",0);
        score_Medium= getIntent().getIntExtra("medium_score1",0);


        start();
        messages = new StringBuilder();
        LocalBroadcastManager.getInstance(this).registerReceiver(mReciever, new IntentFilter("incomingMessage"));
        hardRequest();

        updateCountDownText();


}

    //Head




    BroadcastReceiver mReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String text = intent.getStringExtra("theMessage");
            if (editText_Hard.getText().length() == 0){
                editText_Hard.setText(text);
                editText_Hard.setSelection(editText_Hard.getText().length());
            }else{
                editText_Hard.setText(editText_Hard.getText().toString() + text);
                editText_Hard.setSelection(editText_Hard.getText().length());
            }
        }
    };



    //tail



    public void generate_Questions(){
        context = getApplicationContext();
        Random random = new Random();

        while (true){
            int num = random.nextInt(50);
            if(set.contains(num) == false){
                set.add(num);
                Log.e("Size of Set: ", String.valueOf(set.size()));

                tv_question.setText(hardArray[num][0]);
                correctAnswer_Hard = String.valueOf(hardArray[num][1]);

                break;
            }
        }
    }

    public void hardRequest(){
        final ProgressDialog loading = new ProgressDialog(HardScreen.this);
        loading.setMessage("Please Wait...");
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        StringRequest stringRequest = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                hardQuestionArray = gson.fromJson(response,Question[].class);
                Log.d("RESPONSE::::", response);
                loading.dismiss();

                for(int r=0; r < hardQuestionArray.length; r++){
                    Question question = hardQuestionArray[r];
                    hardCounter++;


                    for(int c=0; c < 2; c++){

                        if(c == 0){
                            hardArray[r][c] = question.getName().toString();
                        }else{
                            hardArray[r][c] = question.getAnswer().toString().toLowerCase();
                        }
                    }
                }
                generate_Questions();

                for(int i = 0; i < hardArray.length; i++){
                    for(int j = 0; j < hardArray[i].length; j++){

                        Log.d("MY ARRAY : " , String.valueOf(i) + " " + hardArray[i][j]);
                    }
                    System.out.println();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Log.e("MainActivity.class", "onErrorResponse: " + error.getMessage());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public  void submitAnswer_Hard(View view){
        hard_String = editText_Hard.getText().toString().toLowerCase();

        if(correctAnswer_Hard.toLowerCase().equals(hard_String)){
            score_Hard++;
            tv_scoreHard.setText("Score: " + String.valueOf(score_Hard));
            correct();
            editText_Hard.setText("");
            generate_Questions();
        }else{
            incorrect();
            editText_Hard.setText("");
            generate_Questions();
        }

    }

    public void startTimer(){
        countDownTimer = new CountDownTimer(TimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                TimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                tv_countdownTimer.setText("00:00");
                TimerRunning = false;
                if(!TimerRunning){
                    timeout();

                }


            }
        }.start();
        TimerRunning = true;
        updateCountDownText();


    }
    public void updateCountDownText(){
        int minutes = (int) ((TimeLeftInMillis/ 1000) / 60);
        int seconds = (int) ((TimeLeftInMillis/ 1000) % 60);

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        tv_countdownTimer.setText(timeLeftFormatted);
    }

    public  void hardDeleteButton(View view){
        hard_String = editText_Hard.getText().toString();

        if(hard_String.length() > 1){
            hard_String = hard_String.substring(0,hard_String.length() - 1);
            editText_Hard.setText(hard_String);
            editText_Hard.setSelection(editText_Hard.getText().length());

        }else if(hard_String.length() <= 1){
            editText_Hard.setText("");


        }

    }

    public void post_Data(){
        RequestQueue requestQueue = Volley.newRequestQueue(HardScreen.this);

        final ProgressDialog loading = new ProgressDialog(HardScreen.this);
        loading.setMessage("Please Wait...");
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        StringRequest stringReq =  new StringRequest(Request.Method.POST, resultURL, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d("Response: ", response);

//                Toast.makeText(EasyScreen.this,"Response : "  + response,Toast.LENGTH_LONG).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(HardScreen.this,"Error: "  + error.getMessage(),Toast.LENGTH_LONG).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                loading.dismiss();
                Log.d("idnumber : " , idnumber);
//                Log.d("firstname: " , firstName);
//                Log.d("lastname : " , lastName);
                Log.d("difficulty : " , difficulty);
//                Log.d("grading : " , grading);
                Log.d("score : " , String.valueOf(score_Medium));
                Map<String, String> params = new HashMap<>();
                params.put("student_id",idnumber);
                params.put("difficulty",difficulty);
                params.put("result",String.valueOf(score_Hard));
                params.put("total",String.valueOf(hardCounter));
//                params.put("grading",grading);

                return params;
            }
        };
        requestQueue.add(stringReq);
    }

    public void correct(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Correct Answer");
        alert.setMessage("Wow! You got it right!");
        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.create().show();
    }

    public void incorrect(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Wrong Answer");
        alert.setMessage("Keep on Trying");
        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.create().show();
    }

    public void start(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Instruction");
        alert.setMessage("You are now in the Hard Level answer the following questions before the time runs out!");
        alert.setCancelable(false);
        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startTimer();


            }
        });
        alert.create().show();
    }

    public void timeout(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Time Runs out!");
        alert.setMessage("You will now be redirected to your scores");
        alert.setCancelable(false);
        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                post_Data();
                Intent intent = new Intent(HardScreen.this, UserScore.class);
                intent.putExtra("score_Easy",score_Easy);
                intent.putExtra("score_Medium",score_Medium);
                intent.putExtra("score_Hard",score_Hard);
                intent.putExtra("userID",idnumber);
                startActivity(intent);
                finish();

            }
        });
        alert.create().show();
    }
}
