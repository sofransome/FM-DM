package com.example.fmdm;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
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

public class EasyScreen extends AppCompatActivity {

    String URL = "https://thesis-api-usls.herokuapp.com/easy", resultURL = "https://thesis-api-usls.herokuapp.com/get-result";
    StringBuilder messages;

    Context context;
    Image[] easyImageArray;
    String[][] easyArray = new String[50][2];



    ArrayList<Character> input = new ArrayList<Character>();
    ArrayList<Float> values = new ArrayList<Float>();
    float pinky,ring,middle,index,thumb,contact1,contact2,contact3,contact4, contact5, contact6;


    ImageView imageView_Easy;
    TextView textView_Easy,textView_forNum,tv_scoreEasy;
    EditText editText_Easy;
    String correctAnswer_Easy,easy_String,idnumber,firstName,lastName,temp = "",difficulty = "easy",grading = "1";
    int score_Easy;
    Set<Integer> set = new HashSet<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_screen);

        idnumber = getIntent().getStringExtra("studentID");
        firstName = getIntent().getStringExtra("firstName");
        lastName = getIntent().getStringExtra("lastName");



        imageView_Easy = (ImageView)findViewById(R.id.imageview_Easy);
        textView_Easy = (TextView)findViewById(R.id.textview_Easy);
        textView_forNum = (TextView)findViewById(R.id.tv_forNumber);
        tv_scoreEasy = (TextView)findViewById(R.id.score_EasyTV);
        editText_Easy = (EditText)findViewById(R.id.edittext_Easy);


        messages = new StringBuilder();
        LocalBroadcastManager.getInstance(this).registerReceiver(mReciever, new IntentFilter("incomingMessage"));
        easyRequest();

        tv_scoreEasy.setText("Score: " + String.valueOf(score_Easy));

    }

    //Head
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            String text = bundle.getString("key");
            if(text!= temp){
                if (editText_Easy.getText().length() == 0){
                    editText_Easy.setText(text);
                }else{
                    editText_Easy.setText(editText_Easy.getText().toString() + text);
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

    //tail



    public void setImage_Easy(){
        context = getApplicationContext();
        Random random = new Random();




        while (true){
            int num = random.nextInt(50);
            if(set.contains(num) == false){
                set.add(num);
                Log.e("Size of Set: ", String.valueOf(set.size()));

                Glide
                        .with(context)
                        .load(easyArray[num][0])
                        .into(imageView_Easy);
                correctAnswer_Easy = String.valueOf(easyArray[num][1]);
                textView_Easy.setText(correctAnswer_Easy);
                break;
            }
        }
    }

    public void easyRequest(){
        final ProgressDialog loading = new ProgressDialog(EasyScreen.this);
        loading.setMessage("Please Wait...");
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        StringRequest stringRequest = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                easyImageArray = gson.fromJson(response,Image[].class);

                Log.d("RESPONSE::::", response);
                loading.dismiss();

                for(int r=0; r < easyImageArray.length; r++){
                    Image easy = easyImageArray[r];


                    for(int c=0; c < 2; c++){

                        if(c == 0){
                            easyArray[r][c] = easy.getUrl().toString();
                        }else{
                            easyArray[r][c] = easy.getName().toString().toLowerCase();
                        }
                    }
                }
                setImage_Easy();

                for(int i = 0; i < easyArray.length; i++){
                    for(int j = 0; j < easyArray[i].length; j++){

                        Log.d("MY ARRAY : " , String.valueOf(i) + " " + easyArray[i][j]);
                    }
                    System.out.println();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Log.e("EasyScreen.class", "onErrorResponse: " + error.getMessage());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void submitAnswer_Easy(View view){
        easy_String = editText_Easy.getText().toString().toLowerCase();

        if(textView_Easy.getText().toString().equals(easy_String) && !textView_Easy.getText().toString().equals("")) {
            score_Easy++;
            tv_scoreEasy.setText("Score: " + String.valueOf(score_Easy));
            editText_Easy.setText("");
            Log.e("MainActivity.class", String.valueOf(set.size()) + " " + String.valueOf(easyImageArray.length));

            if (set.size() <= easyImageArray.length) {
                if(set.size() < easyImageArray.length){
                    correct();
                    if (score_Easy == 3) {
                        medium_unlock();
                    } else if (set.size() == easyImageArray.length && score_Easy >= 3) {
                        goto_medium();
                    } else if(set.size() == easyImageArray.length && score_Easy < 3) {
                        tryEasy_Again();
                    }else{
                        setImage_Easy();
                    }
                }else{
                    if (score_Easy == 3) {
                        medium_unlock();
                    } else if (set.size() == easyImageArray.length && score_Easy >= 3) {
                        goto_medium();
                    } else if(set.size() == easyImageArray.length && score_Easy < 3) {
                        tryEasy_Again();
                    }else{
                        setImage_Easy();
                    }
                }
            }
        }else {
            Log.e("MainActivity.class", String.valueOf(set.size()) + " " + String.valueOf(easyImageArray.length));
            editText_Easy.setText("");
            if (set.size() <= easyImageArray.length){
                if(set.size() < easyImageArray.length){
                    incorrect();
                    if (score_Easy == 3) {
                        medium_unlock();
                    } else if (set.size() == easyImageArray.length && score_Easy >= 3) {
                        goto_medium();
                    } else if(set.size() == easyImageArray.length && score_Easy < 3) {
                        tryEasy_Again();
                    }else{
                        setImage_Easy();
                    }
                }else{
                    if (score_Easy == 3) {
                        medium_unlock();
                    } else if (set.size() == easyImageArray.length && score_Easy >= 3) {
                        goto_medium();
                    } else if(set.size() == easyImageArray.length && score_Easy < 3) {
                        tryEasy_Again();
                    }else{
                        setImage_Easy();
                    }
                }
            }

        }


    }

    public void medium_unlock(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Medium Level Unlock");
        alert.setMessage("Would you like to skip Easy Level?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                post_Data();

                Intent intent = new Intent(EasyScreen.this,LevelsScreen.class);
                intent.putExtra("easy_score",score_Easy);
                intent.putExtra("studentID",idnumber);
                startActivity(intent);
                finish();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                setImage_Easy();
            }
        });
        alert.create().show();
    }

    public void goto_medium(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Question List is empty");
        alert.setMessage("You now unlock the Medium Level");
        alert.setCancelable(false);
        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                post_Data();


                Intent intent = new Intent(EasyScreen.this,LevelsScreen.class);
                intent.putExtra("easy_score",score_Easy);
                intent.putExtra("studentID",idnumber);
                startActivity(intent);
                finish();
            }
        });
        alert.create().show();
    }

    public  void  tryEasy_Again(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Try Easy Again");
        alert.setMessage("Your score is " + score_Easy + " and you did not reach the amount of score " +
                "to proceed to the next level");
        alert.setCancelable(false);
        alert.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(EasyScreen.this, EasyScreen.class);
                intent.putExtra("studentID",idnumber);
                startActivity(intent);
                finish();
            }
        });
        alert.create().show();
    }

    public  void easyDeleteButton(View view){
        easy_String = editText_Easy.getText().toString();

        if(easy_String.length() > 1){
            easy_String = easy_String.substring(0,easy_String.length() - 1);
            editText_Easy.setText(easy_String);
            editText_Easy.setSelection(editText_Easy.getText().length());

        }else if(easy_String.length() <= 1){
            editText_Easy.setText("");


        }

    }

    public void post_Data(){
        RequestQueue requestQueue = Volley.newRequestQueue(EasyScreen.this);

        final ProgressDialog loading = new ProgressDialog(EasyScreen.this);
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
                Toast.makeText(EasyScreen.this,"Error: "  + error.getMessage(),Toast.LENGTH_LONG).show();

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
                Log.d("score : " , String.valueOf(score_Easy));
                Map<String, String> params = new HashMap<>();
                params.put("student_id",idnumber);
                params.put("difficulty",difficulty);
                params.put("result",String.valueOf(score_Easy));
                params.put("grading",grading);

                return params;
            }
        };
        requestQueue.add(stringReq);

    }

    public void correct(){
        textView_Easy.setText("");
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
        textView_Easy.setText("");
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
