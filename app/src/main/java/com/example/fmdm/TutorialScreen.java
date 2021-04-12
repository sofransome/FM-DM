package com.example.fmdm;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

public class TutorialScreen extends AppCompatActivity {





    ArrayList<Character> input = new ArrayList<Character>();
    ArrayList<Float> values = new ArrayList<Float>();
    float pinky,ring,middle,index,thumb,port1,port2,port3,port4, port5, port6, port7;
    long delay = 1000;
    StringBuilder messages;




    public ImageView imgViewQuestion;
    EditText et_answerfield;
    String correct_answer,tutorial_String,idnumber,firstName,lastName,temp = "";
    TextView textView;
    int i=34,score=0;
    Button tutorialBtn;


    int[] image_list={
            R.drawable.nine, R.drawable.eight, R.drawable.seven, R.drawable.six,
            R.drawable.five, R.drawable.four, R.drawable.three, R.drawable.two,
            R.drawable.one,
            R.drawable.z, R.drawable.y, R.drawable.x, R.drawable.w,
            R.drawable.v, R.drawable.u, R.drawable.t, R.drawable.s,
            R.drawable.r, R.drawable.q, R.drawable.p, R.drawable.o,
            R.drawable.n, R.drawable.m, R.drawable.l, R.drawable.k,
            R.drawable.j, R.drawable.i, R.drawable.h, R.drawable.g,
            R.drawable.f, R.drawable.e, R.drawable.d, R.drawable.c,
            R.drawable.b, R.drawable.a

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_screen);



        imgViewQuestion = (ImageView)findViewById(R.id.imageview_Tutorial) ;
        et_answerfield = (EditText)findViewById(R.id.editText_tutorial);
        textView = (TextView)findViewById(R.id.tutorial_textview);

        idnumber = getIntent().getStringExtra("studentID");
        firstName = getIntent().getStringExtra("firstName");
        lastName = getIntent().getStringExtra("lastName");

//        tutorialBtn = (Button)findViewById(R.id.tutorial_submit);
//        tutorialBtn.setEnabled(true);

        messages = new StringBuilder();
        LocalBroadcastManager.getInstance(this).registerReceiver(mReciever, new IntentFilter("incomingMessage"));
        setImage();


    }



    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            String text = bundle.getString("key");
            if(text!= temp){
                if (et_answerfield.getText().length() == 0){
                    et_answerfield.setText(text);

                }else{
                    et_answerfield.setText(et_answerfield.getText().toString() + text);
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

            input.add(text.charAt(0));
            if (input.size() == 4) {
                String convertString = new String(String.valueOf(input).replace(",", "").replace("[", "").replace("]", "").replace(" ", ""));



            float getFloat = Float.parseFloat(convertString);
            values.add(getFloat);
            input.clear();
            if (values.size() == 12) {
                port1 = values.get(0);
                port2 = values.get(1);
                port3 = values.get(2);
                port4 = values.get(3);
                port5 = values.get(4);
                port6 = values.get(5);
                port7 = values.get(6);
                thumb = values.get(7);
                index = values.get(8);
                middle = values.get(9);
                ring = values.get(10);
                pinky = values.get(11);

                values.clear();

                System.out.println("port1: " + port1);
                System.out.println("port2: " + port2);
                System.out.println("port3: " + port3);
                System.out.println("port4: " + port4);
                System.out.println("port5: " + port5);
                System.out.println("port6: " + port6);
                System.out.println("port7: " + port7);
                System.out.println("thumb: " + thumb);
                System.out.println("index: " + index);
                System.out.println("middle: " + middle);
                System.out.println("ring: " + ring);
                System.out.println("pinky: " + pinky);
                //a
                if ((thumb >= 2.86 && thumb <= 2.98) && (index >= 2.74 && index <= 2.95) && (middle >= 2.82 && middle <= 2.95) && (ring >= 2.80 && ring <= 3.10) && (pinky >= 3.00 && pinky <= 3.40) && (port1 == 1.00)) {
                    Message message = Message.obtain();

                    Bundle bundle = new Bundle();
                    bundle.putString("key", "a");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //b
                if ((thumb >= 3.25 && thumb <= 3.60) && (index >= 3.48 && index <= 3.60) && (middle >= 3.40 && middle <= 3.65) && (ring >= 3.45 && ring <= 3.65) && (pinky >= 3.15 && pinky <= 3.40)) {
                    Message message = Message.obtain();
//
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "b");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //c
                if ((thumb >= 3.20 && thumb <= 3.50) && (index >= 2.95 && index <= 3.10) && (middle >= 3.10 && middle <= 3.25) && (ring >= 3.25 && ring <= 3.45) && (pinky >= 3.40 && pinky <= 3.60) && (port2 == 2.00)) {
                    Message message = Message.obtain();
//
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "c");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //d
                if ((thumb >= 3.30 && thumb <= 3.45) && (index >= 3.20 && index <= 3.36) && (middle >= 3.20 && middle <= 3.43) && (ring >= 3.40 && ring <= 3.65) && (pinky >= 3.38 && pinky <= 3.55) && (port3 == 3.00)) {
                    Message message = Message.obtain();

                    Bundle bundle = new Bundle();
                    bundle.putString("key", "d");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //e
                if ((thumb >= 3.00 && thumb <= 3.15) && (index >= 2.90 && index <= 3.12) && (middle >= 2.60 && middle <= 2.75) && (ring >= 2.95 && ring <= 3.15) && (pinky >= 2.60 && pinky <= 2.90)) {
                    Message message = Message.obtain();
//
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "e");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //f
                if ((thumb >= 3.47 && thumb <= 3.57) && (index >= 3.50 && index <= 3.60) && (middle >= 3.49 && middle <= 3.59) && (ring >= 2.96 && ring <= 3.16) && (pinky >= 3.32 && pinky <= 3.50)) {
                    Message message = Message.obtain();
//
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "f");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //g
                if ((thumb >= 3.00 && thumb <= 3.20) && (index >= 2.82 && index <= 3.05) && (middle >= 2.82 && middle <= 3.15) && (ring >= 3.60 && ring <= 3.80) && (pinky >= 3.20 && pinky <= 3.51) && (port3 == 3.00)) {
                    Message message = Message.obtain();

                    Bundle bundle = new Bundle();
                    bundle.putString("key", "g");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //h
                if ((thumb >= 3.00 && thumb <= 3.15) && (index >= 3.00 && index <= 3.20) && (middle >= 3.46 && middle <= 3.60) && (ring >= 3.52 && ring <= 3.63) && (pinky >= 3.28 && pinky <= 3.45)) {
                    Message message = Message.obtain();
//
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "h");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //i
                if ((thumb >= 3.20 && thumb <= 3.65) && (index >= 2.95 && index <= 3.15) && (middle >= 2.95 && middle <= 3.10) && (ring >= 2.90 && ring <= 3.20) && (pinky >= 3.18 && pinky <= 3.40)) {
                    Message message = Message.obtain();

                    Bundle bundle = new Bundle();
                    bundle.putString("key", "i");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //j
                if ((thumb >= 3.40 && thumb <= 3.60) && (index >= 2.90 && index <= 3.10) && (middle >= 2.85 && middle <= 3.05) && (ring >= 2.90 && ring <= 3.10) && (pinky >= 3.15 && pinky <= 3.30) && (port6 == 6.00)) {
                    Message message = Message.obtain();

                    Bundle bundle = new Bundle();
                    bundle.putString("key", "j");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //k
                if ((thumb >= 2.90 && thumb <= 3.10) && (index >= 3.02 && index <= 3.20) && (middle >= 3.46 && middle <= 3.60) && (ring >= 3.55 && ring <= 3.68) && (pinky >= 3.41 && pinky <= 3.56)) {
                    Message message = Message.obtain();
//
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "k");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //l
                if ((thumb >= 2.98 && thumb <= 3.12) && (index >= 2.90 && index <= 3.10) && (middle >= 2.80 && middle <= 3.05) && (ring >= 3.50 && ring <= 3.75) && (pinky >= 3.35 && pinky <= 3.60)) {
                    Message message = Message.obtain();
//
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "l");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //m
                if ((thumb >= 2.90 && thumb <= 3.15) && (index >= 2.90 && index <= 3.15) && (middle >= 2.90 && middle <= 3.15) && (ring >= 3.00 && ring <= 3.25) && (pinky >= 2.90 && pinky <= 3.15)) {
                    Message message = Message.obtain();

                    Bundle bundle = new Bundle();
                    bundle.putString("key", "m");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //n
                if ((thumb >= 2.90 && thumb <= 3.15) && (index >= 2.95 && index <= 3.15) && (middle >= 2.90 && middle <= 3.15) && (ring >= 2.90 && ring <= 3.15) && (pinky >= 3.00 && pinky <= 3.25) && (port3 == 3.00)) {
                    Message message = Message.obtain();
//
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "n");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //o
                if ((thumb >= 3.00 && thumb <= 3.30) && (index >= 3.10 && index <= 3.25) && (middle >= 3.07 && middle <= 3.21) && (ring >= 3.10 && ring <= 3.30) && (pinky >= 3.28 && pinky <= 3.42) && (port3 == 3.00)) {
                    Message message = Message.obtain();
//
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "o");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //p
                if ((thumb >= 2.90 && thumb <= 3.05) && (index >= 2.89 && index <= 3.05) && (middle >= 3.31 && middle <= 3.50) && (ring >= 3.54 && ring <= 3.70) && (pinky >= 3.29 && pinky <= 3.51)) {
                    Message message = Message.obtain();
//
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "p");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //q
                if ((thumb >= 3.40 && thumb <= 3.65) && (index >= 3.45 && index <= 3.65) && (middle >= 2.78 && middle <= 2.95) && (ring >= 3.50 && ring <= 3.70) && (pinky >= 3.25 && pinky <= 3.45)) {
                    Message message = Message.obtain();
//
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "q");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //r
                if ((thumb >= 2.80 && thumb <= 3.15) && (index >= 2.60 && index <= 2.99) && (middle >= 3.10 && middle <= 3.25) && (ring >= 3.45 && ring <= 3.70) && (pinky >= 3.20 && pinky <= 3.45) && (port4 == 4.00)) {
                    Message message = Message.obtain();
//
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "r");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //s
                if ((thumb >= 2.84 && thumb <= 2.99) && (index >= 2.76 && index <= 2.91) && (middle >= 2.75 && middle <= 2.90) && (ring >= 2.76 && ring <= 2.91) && (pinky >= 3.12 && pinky <= 3.27) && (port4 == 4.00)) {
                    Message message = Message.obtain();
//
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "s");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //t
                if ((thumb >= 2.90 && thumb <= 3.10) && (index >= 2.75 && index <= 2.95) && (middle >= 2.78 && middle <= 2.95) && (ring >= 3.15 && ring <= 3.30) && (pinky >= 3.05 && pinky <= 3.23)) {
                    Message message = Message.obtain();
//
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "t");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //u
                if ((thumb >= 3.00 && thumb <= 3.32) && (index >= 2.75 && index <= 2.95) && (middle >= 3.50 && middle <= 3.65) && (ring >= 3.58 && ring <= 3.73) && (pinky >= 3.30 && pinky <= 3.48) && (port2 == 2.0)) {
                    Message message = Message.obtain();
//
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "u");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //v
                if ((thumb >= 3.10 && thumb <= 3.30) && (index >= 2.80 && index <= 3.10) && (middle >= 3.50 && middle <= 3.65) && (ring >= 3.58 && ring <= 3.73) && (pinky >= 3.33 && pinky <= 3.48)) {
                    Message message = Message.obtain();

                    Bundle bundle = new Bundle();
                    bundle.putString("key", "v");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //w
                if ((thumb >= 2.98 && thumb <= 3.20) && (index >= 3.40 && index <= 3.65) && (middle >= 2.42 && middle <= 3.67) && (ring >= 3.50 && ring <= 3.75) && (pinky >= 3.20 && pinky <= 3.50)) {
                    Message message = Message.obtain();
//
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "w");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }

                //x
                if ((thumb >= 2.80 && thumb <= 3.13) && (index >= 2.75 && index <= 3.15) && (middle >= 2.70 && middle <= 3.05) && (ring >= 3.10 && ring <= 3.30) && (pinky >= 2.75 && pinky <= 3.05)) {
                    Message message = Message.obtain();

                    Bundle bundle = new Bundle();
                    bundle.putString("key", "x");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //y
                if ((thumb >= 3.47 && thumb <= 3.62) && (index >= 3.00 && index <= 3.13) && (middle >= 2.90 && middle <= 3.10) && (ring >= 2.95 && ring <= 3.13) && (pinky >= 3.54 && pinky <= 3.65)) {
                    Message message = Message.obtain();

                    Bundle bundle = new Bundle();
                    bundle.putString("key", "y");
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
                //z
                if ((thumb >= 3.24 && thumb <= 3.40) && (index >= 3.08 && index <= 3.40) && (middle >= 3.05 && middle <= 3.23) && (ring >= 3.50 && ring <= 3.73) && (pinky >= 3.10 && pinky <= 3.25)) {
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
        }
    };



    public void submitAnswer(View view){
         tutorial_String = et_answerfield.getText().toString();

        if(textView.getText().toString().equals(tutorial_String)){
            score++;
            correct();
            et_answerfield.setText("");
            //score = 35
            if(score==35){

                Intent intent = new Intent(TutorialScreen.this,LevelsScreen.class);
                intent.putExtra("studentID",idnumber);
                startActivity(intent);
                finish();
            }
            setImage();


        }else{
            incorrect();

        }
    }


    public void setImage(){
        if(i<0){
            i=0;
        }
        int imageSelected = image_list[i];
        imgViewQuestion.setImageResource(imageSelected);
        correct_answer = getResources().getResourceName(imageSelected);
        correct_answer = correct_answer.substring(correct_answer.lastIndexOf("/")+1);

        if(correct_answer.equals("one")){
            correct_answer = "1";
        }else if(correct_answer.equals("two")){
            correct_answer = "2";
        }else if(correct_answer.equals("three")){
            correct_answer = "3";
        }else if(correct_answer.equals("four")){
            correct_answer = "4";
        }else if(correct_answer.equals("five")){
            correct_answer = "5";
        }else if(correct_answer.equals("six")){
            correct_answer = "6";
        }else if(correct_answer.equals("seven")){
            correct_answer = "7";
        }else if(correct_answer.equals("eight")){
            correct_answer = "8";
        }else if(correct_answer.equals("nine")){
            correct_answer = "9";
        }else{
            correct_answer = correct_answer;
        }
        textView.setText(correct_answer);
        i--;

    }

    public void skip(View view){
        setImage();
        if( i < 0 && i >34 ){
            Intent intent = new Intent(TutorialScreen.this,LevelsScreen.class);
            intent.putExtra("studentID",idnumber);
            intent.putExtra("firstName",firstName);
            intent.putExtra("lastName",lastName);
            startActivity(intent);
            finish();
        }
    }

    public  void tutorialDeleteButton(View view){
        tutorial_String = et_answerfield.getText().toString();

        if(tutorial_String.length() > 1){
            tutorial_String = tutorial_String.substring(0,tutorial_String.length() - 1);
            et_answerfield.setText(tutorial_String);
            et_answerfield.setSelection(et_answerfield.getText().length());

        }else if(tutorial_String.length() <= 1){
            et_answerfield.setText("");


        }

    }

    public void goto_Levels(View view){
        Intent intent = new Intent(this, LevelsScreen.class);
        intent.putExtra("studentID",idnumber);
        intent.putExtra("firstName",firstName);
        intent.putExtra("lastName",lastName);
        startActivity(intent);
        finish();

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

}
