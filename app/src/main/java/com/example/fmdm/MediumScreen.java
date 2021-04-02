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
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class MediumScreen extends AppCompatActivity {

    String URL = "https://thesis-api-usls.herokuapp.com/medium", resultURL = "https://thesis-api-usls.herokuapp.com/get-result";
    StringBuilder messages;


    Context context;
    Image[] mediumImageArray;
    String[][] mediumArray = new String[30][2];



    ArrayList<Character> input = new ArrayList<Character>();
    ArrayList<Float> values = new ArrayList<Float>();
    float pinky,ring,middle,index,thumb,contact1,contact2,contact3,contact4, contact5, contact6;


    ImageView imageView_Medium;
    TextView textView_Medium,textView_forNum,tv_scoreMedium;
    EditText editText_Medium;
    String correctAnswer_Medium, username,medium_String,idnumber,firstName,lastName,temp = "",difficulty = "medium",grading = "1";
    int easy_score2, score_Medium;
    Set<Integer> set = new HashSet<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medium_screen);

        imageView_Medium = (ImageView)findViewById(R.id.imageview_Medium);
        textView_Medium = (TextView)findViewById(R.id.textview_Medium);
        textView_forNum = (TextView)findViewById(R.id.tv_forNumber);
        tv_scoreMedium = (TextView)findViewById(R.id.score_MediumTV);
        editText_Medium = (EditText)findViewById(R.id.edittext_Medium);

        idnumber = getIntent().getStringExtra("studentID");
        firstName = getIntent().getStringExtra("firstName");
        lastName = getIntent().getStringExtra("lastName");

        idnumber = getIntent().getStringExtra("studentID");
        easy_score2 = getIntent().getIntExtra("easy_score1",0);


        messages = new StringBuilder();
        LocalBroadcastManager.getInstance(this).registerReceiver(mReciever, new IntentFilter("incomingMessage"));
        mediumRequest();

        tv_scoreMedium.setText("Score: " + String.valueOf(score_Medium));



    }

////    head
Handler handler = new Handler(new Handler.Callback() {
    @Override
    public boolean handleMessage(@NonNull Message msg) {
        Bundle bundle = msg.getData();
        String text = bundle.getString("key");
        if(text!= temp){
            if (editText_Medium.getText().length() == 0){
                editText_Medium.setText(text);
            }else{
                editText_Medium.setText(editText_Medium.getText().toString() + text);
            }

            temp = text;
        }


        return false;
    }
});

    BroadcastReceiver mReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String text = intent.getStringExtra("theMessage");
            System.out.println("The OUTPUT: " + text);

            float getFloat = Float.parseFloat(text);
            values.add(getFloat);
            input.clear();
            if (values.size() == 5) {
                thumb = values.get(0);
                index = values.get(1);
                middle = values.get(2);
                ring = values.get(3);
                pinky = values.get(4);
//                contact1 = values.get(5);
//                contact2 = values.get(6);
//                contact3 = values.get(7);
//                contact4 = values.get(8);
//                contact5 = values.get(9);
//                contact6 = values.get(10);
                values.clear();

                System.out.println("thumb: " + thumb);
                System.out.println("index: " + index);
                System.out.println("middle: " + middle);
                System.out.println("ring: " + ring);
                System.out.println("pinky: " + pinky);
//                System.out.println("contact1: " + contact1);
//                System.out.println("contact2: " + contact2);
//                System.out.println("contact3: " + contact3);
//                System.out.println("contact4: " + contact4);
//                System.out.println("contact5: " + contact5);
//                System.out.println("contact6: " + contact6);
                //a
                if((thumb>=2.86 && thumb<=2.98) && (index>=2.74 && index<=2.95) && (middle>=2.82 && middle<=2.95) && (ring>=2.80 && ring<=3.10) && (pinky>=3.00  && pinky<=3.40) && (contact1 == 1.00)){
                    Message message = Message.obtain();

                    Bundle bundle = new Bundle();
                    bundle.putString("key", "a");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //b
                if((thumb>=3.25 && thumb<=3.60) && (index>=3.48 && index<=3.60) && (middle>=3.40 && middle<=3.65) && (ring>=3.45 && ring<=3.65) && (pinky>=3.15 && pinky<=3.40)){
                    Message message = Message.obtain();
//
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "b");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //c
                if((thumb>=3.20 && thumb<=3.50) && (index>=2.95 && index<=3.10) && (middle>=3.10 && middle<=3.25) && (ring>=3.25 && ring<=3.45) && (pinky>=3.40 && pinky<=3.60)&&(contact2==2.00)){
                    Message message = Message.obtain();
//
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "c");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //d
                if((thumb>=3.30 && thumb<=3.45) && (index>=3.20 && index<=3.36) && (middle>=3.20 && middle<=3.43) && (ring>=3.40 && ring<=3.65) && (pinky>=3.38 && pinky<=3.55) && (contact3==3.00)){
                    Message message = Message.obtain();

                    Bundle bundle = new Bundle();
                    bundle.putString("key", "d");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //e
                if((thumb>=3.00 && thumb<=3.15) && (index>=2.90 && index<=3.12) && (middle>=2.60 && middle<=2.75) && (ring>=2.95 && ring<=3.15) && (pinky>=2.60 && pinky<=2.90)){
                    Message message = Message.obtain();
//
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "e");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //f
                if((thumb>=3.47 && thumb<=3.57) && (index>=3.50 && index<=3.60) && (middle>=3.49 && middle<=3.59) && (ring>=2.96 && ring<=3.16) && (pinky>=3.32 && pinky<=3.50)){
                    Message message = Message.obtain();
//
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "f");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //g
                if((thumb>=3.00 && thumb<=3.20) && (index>=2.82 && index<=3.05) && (middle>=2.82 && middle<=3.15) && (ring>=3.60 && ring<=3.80) && (pinky>=3.20 && pinky<=3.51)&&(contact3==3.00)){
                    Message message = Message.obtain();

                    Bundle bundle = new Bundle();
                    bundle.putString("key", "g");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //h
                if((thumb>=3.00 && thumb<=3.15) && (index>=3.00 && index<=3.20) && (middle>=3.46 && middle<=3.60) && (ring>=3.52 && ring<=3.63) && (pinky>=3.28 && pinky<=3.45)){
                    Message message = Message.obtain();
//
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "h");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //i
                if((thumb>=3.20 && thumb<=3.65) && (index>=2.95 && index<=3.15) && (middle>=2.95 && middle<=3.10) && (ring>=2.90 && ring<=3.20) && (pinky>=3.18 && pinky<=3.40)){
                    Message message = Message.obtain();

                    Bundle bundle = new Bundle();
                    bundle.putString("key", "i");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //j
                if((thumb>=3.40 && thumb<=3.60) && (index>=2.90 && index<=3.10) && (middle>=2.85 && middle<=3.05) && (ring>=2.90 && ring<=3.10) && (pinky>=3.15 && pinky<=3.30)&&(contact6==6.00)){
                    Message message = Message.obtain();

                    Bundle bundle = new Bundle();
                    bundle.putString("key", "j");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //k
                if((thumb>=2.90 && thumb<=3.10) && (index>=3.02 && index<=3.20) && (middle>=3.46 && middle<=3.60) && (ring>=3.55 && ring<=3.68) && (pinky>=3.41 && pinky<=3.56)){
                    Message message = Message.obtain();
//
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "k");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //l
                if((thumb>=2.98 && thumb<=3.12) && (index>=2.90 && index<=3.10) && (middle>=2.80 && middle<=3.05) && (ring>=3.50 && ring<=3.75) && (pinky>=3.35 && pinky<=3.60)){
                    Message message = Message.obtain();
//
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "l");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //m
                if((thumb>=2.90 && thumb<=3.15) && (index>=2.90 && index<=3.15) && (middle>=2.90 && middle<=3.15) && (ring>=3.00 && ring<=3.25) && (pinky>=2.90 && pinky<=3.15)){
                    Message message = Message.obtain();

                    Bundle bundle = new Bundle();
                    bundle.putString("key", "m");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //n
                if((thumb>=2.90 && thumb<=3.15) && (index>=2.95 && index<=3.15) && (middle>=2.90 && middle<=3.15) && (ring>=2.90 && ring<=3.15) && (pinky>=3.00 && pinky<=3.25)&&(contact3==3.00)){
                    Message message = Message.obtain();
//
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "n");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //o
                if((thumb>=3.00 && thumb<=3.30) && (index>=3.10 && index<=3.25) && (middle>=3.07 && middle<=3.21) && (ring>=3.10 && ring<=3.30) && (pinky>=3.28 && pinky<=3.42)&&(contact3 ==3.00)){
                    Message message = Message.obtain();
//
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "o");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //p
                if((thumb>=2.90 && thumb<=3.05) && (index>=2.89 && index<=3.05) && (middle>=3.31 && middle<=3.50) && (ring>=3.54 && ring<=3.70) && (pinky>=3.29 && pinky<=3.51)) {
                    Message message = Message.obtain();
//
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "p");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //q
                if((thumb>=3.40 && thumb<=3.65) && (index>=3.45 && index<=3.65) && (middle>=2.78 && middle<=2.95) && (ring>=3.50 && ring<=3.70) && (pinky>=3.25 && pinky<=3.45)) {
                    Message message = Message.obtain();
//
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "q");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //r
                if((thumb>=2.80 && thumb<=3.15) && (index>=2.60 && index<=2.99) && (middle>=3.10 && middle<=3.25) && (ring>=3.45 && ring<=3.70) && (pinky>=3.20 && pinky<=3.45)&&(contact4==4.00)) {
                    Message message = Message.obtain();
//
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "r");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //s
                if((thumb>=2.84 && thumb<=2.99) && (index>=2.76 && index<=2.91) && (middle>=2.75 && middle<=2.90) && (ring>=2.76 && ring<=2.91) && (pinky>=3.12 && pinky<=3.27)&&(contact4==4.00)) {
                    Message message = Message.obtain();
//
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "s");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //t
                if((thumb>=2.90 && thumb<=3.10) && (index>=2.75 && index<=2.95) && (middle>=2.78 && middle<=2.95) && (ring>=3.15 && ring<=3.30) && (pinky>=3.05 && pinky<=3.23)) {
                    Message message = Message.obtain();
//
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "t");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //u
                if((thumb>=3.00 && thumb<=3.32) && (index>=2.75 && index<=2.95) && (middle>=3.50 && middle<=3.65) && (ring>=3.58 && ring<=3.73) && (pinky>=3.30 && pinky<=3.48) && (contact2 == 2.0)) {
                    Message message = Message.obtain();
//
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "u");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //v
                if((thumb>=3.10 && thumb<=3.30) && (index>=2.80 && index<=3.10) && (middle>=3.50 && middle<=3.65) && (ring>=3.58 && ring<=3.73) && (pinky>=3.33 && pinky<=3.48)) {
                    Message message = Message.obtain();

                    Bundle bundle = new Bundle();
                    bundle.putString("key", "v");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //w
                if((thumb>=2.98 && thumb<=3.20) && (index>=3.40 && index<=3.65) && (middle>=2.42 && middle<=3.67) && (ring>=3.50 && ring<=3.75) && (pinky>=3.20 && pinky<=3.50)) {
                    Message message = Message.obtain();
//
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "w");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }

                //x
                if((thumb>=2.80 && thumb<=3.13) && (index>=2.75 && index<=3.15) && (middle>=2.70 && middle<=3.05) && (ring>=3.10 && ring<=3.30) && (pinky>=2.75 && pinky<=3.05)) {
                    Message message = Message.obtain();

                    Bundle bundle = new Bundle();
                    bundle.putString("key", "x");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //y
                if((thumb>=3.47 && thumb<=3.62) && (index>=3.00 && index<=3.13) && (middle>=2.90 && middle<=3.10) && (ring>=2.95 && ring<=3.13) && (pinky>=3.54 && pinky<=3.65)) {
                    Message message = Message.obtain();

                    Bundle bundle = new Bundle();
                    bundle.putString("key", "y");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //z
                if((thumb>=3.24 && thumb<=3.40) && (index>=3.08 && index<=3.40) && (middle>=3.05 && middle<=3.23) && (ring>=3.50 && ring<=3.73) && (pinky>=3.10 && pinky<=3.25)) {
                    Message message = Message.obtain();

                    Bundle bundle = new Bundle();
                    bundle.putString("key", "z");
                    message.setData(bundle);

//                                    new Timer().schedule(new TimerTask() {
//                                        @Override
//                                        public void run() {
//                                        }
//                                    },delay);


                    handler.sendMessage(message);
                }
            }
        }
    };



    public void setImage_Medium(){
        context = getApplicationContext();
        Random random = new Random();




        while (true){
            int num = random.nextInt(6);
            if(set.contains(num) == false){
                set.add(num);
                Log.e("Size of Set: ", String.valueOf(set.size()));

                Glide
                        .with(context)
                        .load(mediumArray[num][0])
                        .into(imageView_Medium);
                correctAnswer_Medium = String.valueOf(mediumArray[num][1]);
                textView_Medium.setText(correctAnswer_Medium);
                break;
            }
        }
    }

    public void mediumRequest(){
        final ProgressDialog loading = new ProgressDialog(MediumScreen.this);
        loading.setMessage("Please Wait...");
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        StringRequest stringRequest = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                mediumImageArray = gson.fromJson(response,Image[].class);
//                userArray = gson.fromJson(response,User[].class);
                Log.d("RESPONSE::::", response);
                loading.dismiss();

                for(int r=0; r < mediumImageArray.length; r++){
                    Image easy = mediumImageArray[r];


                    for(int c=0; c < 2; c++){

                        if(c == 0){
                            mediumArray[r][c] = easy.getUrl().toString();
                        }else{
                            mediumArray[r][c] = easy.getName().toString().toLowerCase();
                        }
                    }
                }
                setImage_Medium();

                for(int i = 0; i < mediumArray.length; i++){
                    for(int j = 0; j < mediumArray[i].length; j++){

                        Log.d("MY ARRAY : " , String.valueOf(i) + " " + mediumArray[i][j]);
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

    public void submitAnswer_Medium(View view){
        medium_String = editText_Medium.getText().toString().toLowerCase();

        if(textView_Medium.getText().toString().equals(medium_String) && !textView_Medium.getText().toString().equals("")) {
            score_Medium++;
            tv_scoreMedium.setText("Score: " + String.valueOf(score_Medium));
            editText_Medium.setText("");
            Log.e("MediumScreen.class", String.valueOf(set.size()) + " " + String.valueOf(mediumImageArray.length));

            if (set.size() <= mediumImageArray.length) {
                if (set.size() < mediumImageArray.length) {
                    correct();
                    if (score_Medium == 3) {
                        hard_unlock();
                    } else if (set.size() == mediumImageArray.length && score_Medium >= 3) {
                        goto_hard();
                    } else if (set.size() == mediumImageArray.length && score_Medium < 3) {
                        tryMedium_Again();
                    } else {
                        setImage_Medium();
                    }
                } else {
                    if (score_Medium == 3) {
                        hard_unlock();
                    } else if (set.size() == mediumImageArray.length && score_Medium >= 3) {
                        goto_hard();
                    } else if (set.size() == mediumImageArray.length && score_Medium < 3) {
                        tryMedium_Again();
                    } else {
                        setImage_Medium();
                    }
                }
            }
        }else {
            editText_Medium.setText("");
            if (set.size() <= mediumImageArray.length){
                if (set.size() < mediumImageArray.length) {
                    incorrect();
                    if (score_Medium == 3) {
                        hard_unlock();
                    } else if (set.size() == mediumImageArray.length && score_Medium >= 3) {
                        goto_hard();
                    } else if (set.size() == mediumImageArray.length && score_Medium < 3) {
                        tryMedium_Again();
                    } else {
                        setImage_Medium();
                    }
                } else {
                    if (score_Medium == 1) {
                        hard_unlock();
                    } else if (set.size() == mediumImageArray.length && score_Medium >= 3) {
                        goto_hard();
                    } else if (set.size() == mediumImageArray.length && score_Medium < 3) {
                        tryMedium_Again();
                    } else {
                        setImage_Medium();
                    }
                }
            }

        }


    }

    public void hard_unlock(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Hard Level Unlock");
        alert.setMessage("Would you like to skip Medium Level?");
        alert.setCancelable(false);
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                post_Data();


                Intent intent = new Intent(MediumScreen.this,LevelsScreen.class);
//                intent.putExtra("easy_score2",easy_score2);
//                intent.putExtra("medium_score", score_Medium);
//                intent.putExtra("studentID",idnumber);
                startActivity(intent);
                finish();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                setImage_Medium();
            }
        });
        alert.create().show();
    }

    public void goto_hard(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Question List is empty");
        alert.setMessage("Your score is " + score_Medium + " you will now proceed to the next level");
        alert.setCancelable(false);
        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                post_Data();


                Intent intent = new Intent(MediumScreen.this,LevelsScreen.class);
//                intent.putExtra("easy_score2",easy_score2);
//                intent.putExtra("medium_score", score_Medium);
//                intent.putExtra("studentID",idnumber);
                startActivity(intent);
                finish();
            }
        });
        alert.create().show();
    }

    public  void  tryMedium_Again(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Try Medium Again");
        alert.setMessage("Your score is " + score_Medium + " and you did not reach the amount of score " +
                "to proceed to the next level");
        alert.setCancelable(false);
        alert.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(MediumScreen.this,MediumScreen.class);
                intent.putExtra("studentID",idnumber);
                intent.putExtra("username",username);
                startActivity(intent);
                finish();
            }
        });
        alert.create().show();
    }

    public  void mediumDeleteButton(View view){
        medium_String = editText_Medium.getText().toString();

        if(medium_String.length() > 1){
            medium_String = medium_String.substring(0,medium_String.length() - 1);
            editText_Medium.setText(medium_String);
            editText_Medium.setSelection(editText_Medium.getText().length());

        }else if(medium_String.length() <= 1){
            editText_Medium.setText("");


        }

    }

    public void post_Data(){
        RequestQueue requestQueue = Volley.newRequestQueue(MediumScreen.this);

        final ProgressDialog loading = new ProgressDialog(MediumScreen.this);
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
                Toast.makeText(MediumScreen.this,"Error: "  + error.getMessage(),Toast.LENGTH_LONG).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                loading.dismiss();
                Log.d("idnumber : " , idnumber);
                Log.d("firstname: " , firstName);
                Log.d("lastname : " , lastName);
                Log.d("difficulty : " , difficulty);
                Log.d("grading : " , grading);
                Log.d("score : " , String.valueOf(score_Medium));
                Map<String, String> params = new HashMap<>();
                params.put("student_id",idnumber);
                params.put("difficulty",difficulty);
                params.put("result",String.valueOf(score_Medium));
                params.put("grading",grading);

                return params;
            }
        };
        requestQueue.add(stringReq);

    }

    public void correct(){
        textView_Medium.setText("");
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
        textView_Medium.setText("");
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
}
