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
    String[][] hardArray = new String[30][2];

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
    int score_Easy, score_Medium, score_Hard;
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

        updateCountDownText();
        generate_Questions();

}

    //Head

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            String text = bundle.getString("key");
            if(text!= temp){
                if (editText_Hard.getText().length() == 0){
                    editText_Hard.setText(text);
                }else{
                    editText_Hard.setText(editText_Hard.getText().toString() + text);
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



    public void generate_Questions(){
        context = getApplicationContext();
        Random random = new Random();

        while (true){
            int num = random.nextInt(6);
            if(set.contains(num) == false){
                set.add(num);
                Log.e("Size of Set: ", String.valueOf(set.size()));

                tv_question.setText(hardArray[num][0]);
                correctAnswer_Hard = String.valueOf(hardArray[num][1]);

                break;
            }
        }

//        while (true){
//            int num = random.nextInt(51);
//            if (set.contains(num) == false){
//                set.add(num);
//                switch (num){
//                    case 0:
//                        tv_question.setText("The organ inside your head that controls how you think, feel, and move.");
//                        correctAnswer_Hard = "brain";
//                        break;
//                    case 1:
//                        tv_question.setText("Protective coverings on the ends of the fingers.");
//                        correctAnswer_Hard = "nails";
//                        break;
//                    case 2:
//                        tv_question.setText("A muscular organ that pumps blood throughtout the body.");
//                        correctAnswer_Hard = "heart";
//                        break;
//                    case 3:
//                        tv_question.setText("The top part of the front of the body, between the neck and stomach.");
//                        correctAnswer_Hard = "chest";
//                        break;
//                    case 4:
//                        tv_question.setText("The upper-most part of human body.");
//                        correctAnswer_Hard = "head";
//
//                    case 5:
//                        tv_question.setText("Our solar System's star that is made of hydrogen and helium gases, and supplies the heat and light to our planet.");
//                        correctAnswer_Hard = "sun";
//                        break;
//                    case 6:
//                        tv_question.setText("The largest planet in the Solar System.");
//                        correctAnswer_Hard = "jupiter";
//                        break;
//                    case 7:
//                        tv_question.setText("The planet where we live.");
//                        correctAnswer_Hard = "earth";
//                        break;
//                    case 8:
//                        tv_question.setText("The planet that is closest to the Sun.");
//                        correctAnswer_Hard = "mercury";
//                        break;
//                    case 9:
//                        tv_question.setText("A mineral composed primarily of sodium chloride.");
//                        correctAnswer_Hard = "salt";
//                        break;
//                    case 10:
//                        tv_question.setText("A piece of absorbent fabric or paper used for drying or wiping a body or a surface.");
//                        correctAnswer_Hard = "towel";
//                        break;
//                    case 11:
//                        tv_question.setText("An animal doctor is called a.");
//                        correctAnswer_Hard = "veterinarian";
//                        break;
//                    case 12:
//                        tv_question.setText("A sweet food made from a mixture of flour, shortening, eggs, sugar, and other ingredients, baked and often decorated.");
//                        correctAnswer_Hard = "cake";
//                        break;
//                    case 13:
//                        tv_question.setText("A man's best friend.");
//                        correctAnswer_Hard = "dog";
//                        break;
//                    case 14:
//                        tv_question.setText("The one who deliver classroom instruction that helps students learn.");
//                        correctAnswer_Hard = "teacher";
//                        break;
//                    case 15:
//                        tv_question.setText("Their job is to find what's making you ill and then, treat you so that you can get better.");
//                        correctAnswer_Hard = "doctor";
//                        break;
//                    case 16:
//                        tv_question.setText("Use in writing or drawing, consisting of a thin graphite enclosed in a long thin piece of wood.");
//                        correctAnswer_Hard = "pencil";
//                        break;
//                    case 17:
//                        tv_question.setText("Use to remove pencil markings.");
//                        correctAnswer_Hard = "eraser";
//                        break;
//                    case 18:
//                        tv_question.setText("A place where a person lives.");
//                        correctAnswer_Hard = "home";
//                        break;
//                    case 19:
//                        tv_question.setText("A place where sick or injured people are given care or treatment.");
//                        correctAnswer_Hard = "hospital";
//                        break;
//                    case 20:
//                        tv_question.setText("A seat for one person that has a back and usually four legs.");
//                        correctAnswer_Hard = "chair";
//                        break;
//                    case 21:
//                        tv_question.setText("An area covered with sand or small rocks that is next to an ocean.");
//                        correctAnswer_Hard = "beach";
//                        break;
//                    case 22:
//                        tv_question.setText("A bird that is raised by people for its eggs and meat.");
//                        correctAnswer_Hard = "chicken";
//                        break;
//                    case 23:
//                        tv_question.setText("The planet that is sixth in order from the sun and that is surrounded by large rings.");
//                        correctAnswer_Hard = "saturn";
//                        break;
//                    case 24:
//                        tv_question.setText("The body part at the end of your arm that includes your fingers.");
//                        correctAnswer_Hard = "hand";
//                        break;
//                    case 25:
//                        tv_question.setText("A thin threadlike growth from the skin of a person or animal.");
//                        correctAnswer_Hard = "hair";
//                        break;
//                    case 26:
//                        tv_question.setText("The part of the face or head through which a person or animal smells and breathes.");
//                        correctAnswer_Hard = "nose";
//                        break;
//                    case 27:
//                        tv_question.setText("A part of the house or a room in which food is cooked.");
//                        correctAnswer_Hard = "kitchen";
//                        break;
//                    case 28:
//                        tv_question.setText("A muscular organ in the mouth of most vertebrates that manipulates food for mastication and is used in the act of swallowing.");
//                        correctAnswer_Hard = "tongue";
//                        break;
//                    case 29:
//                        tv_question.setText("A large natural flow of water that crosses an area of land and goes into an ocean or lake.");
//                        correctAnswer_Hard = "river";
//                        break;
//                    case 30:
//                        tv_question.setText("a cold-blooded animal that lives in water, breathes with gills,and usually has fins and scales.");
//                        correctAnswer_Hard = "fish";
//                        break;
//                    case 31:
//                        tv_question.setText("The clear liquid that has no color,taste, or smell, and that is used for drinking.");
//                        correctAnswer_Hard = "water";
//                        break;
//                    case 32:
//                        tv_question.setText("A chemical that is found in the air,that has no color,taste, or smell, and that is necessary for life.");
//                        correctAnswer_Hard = "oxygen";
//                        break;
//                    case 33:
//                        tv_question.setText("The opening through which food passes into the body.");
//                        correctAnswer_Hard = "mouth";
//                        break;
//                    case 34:
//                        tv_question.setText("A piece of clothing for the upper body that has sleeves and usually a collar and buttons down the front.");
//                        correctAnswer_Hard = "shirt";
//                        break;
//                    case 35:
//                        tv_question.setText("The upper interior surface of a room or other similar compartment.");
//                        correctAnswer_Hard = "ceiling";
//                        break;
//                    case 36:
//                        tv_question.setText("A shaped covering for the head worn for warmth, as a fashion item, or as part of a uniform.");
//                        correctAnswer_Hard = "hat";
//                        break;
//                    case 37:
//                        tv_question.setText("An area of land that rises very high above the land around it and that is higher than a hill.");
//                        correctAnswer_Hard = "mountain";
//                        break;
//                    case 38:
//                        tv_question.setText("A hard flat surface for vehicles, people, and animals to travel on.");
//                        correctAnswer_Hard = "road";
//                        break;
//                    case 39:
//                        tv_question.setText("Usually used for washing, bathing, and other types of housekeeping.");
//                        correctAnswer_Hard = "soap";
//                        break;
//                    case 40:
//                        tv_question.setText("A chart that shows the days, weeks, and months of a year.");
//                        correctAnswer_Hard = "calendar";
//                        break;
//                    case 41:
//                        tv_question.setText("A flat dish, typically circular, from which food is eaten or served.");
//                        correctAnswer_Hard = "plate";
//                        break;
//                    case 42:
//                        tv_question.setText("A set of steps leading from one floor of a building to another, typically inside the building.");
//                        correctAnswer_Hard = "stairs";
//                        break;
//                    case 43:
//                        tv_question.setText("A place where products are bought and sold.");
//                        correctAnswer_Hard = "market";
//                        break;
//                    case 44:
//                        tv_question.setText("A thick growth of trees and bushes that covers a large area.");
//                        correctAnswer_Hard = "forest";
//                        break;
//                    case 45:
//                        tv_question.setText("The flashes of light that are produced in the sky during a storm.");
//                        correctAnswer_Hard = "lightning";
//                        break;
//                    case 46:
//                        tv_question.setText("A long curved fruit with a thick peel that is yellow when it is ripe.");
//                        correctAnswer_Hard = "banana";
//                        break;
//                    case 47:
//                        tv_question.setText("A bake food made from a mixture of water and flour.");
//                        correctAnswer_Hard = "bread";
//                        break;
//                    case 48:
//                        tv_question.setText("A nutrient-rich liquid food produced by the mammary glands of mammals..");
//                        correctAnswer_Hard = "milk";
//                        break;
//                    case 49:
//                        tv_question.setText("Something used as a way to pay for goods and services and to pay peaople for their work.");
//                        correctAnswer_Hard = "money";
//                        break;
//                    case 50:
//                        tv_question.setText("Things that are no longer useful or wanted and that have been thrown out.");
//                        correctAnswer_Hard = "garbage";
//                        break;
//                }
//                break;
//            }
//        }


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


                    for(int c=0; c < 2; c++){

                        if(c == 0){
                            hardArray[r][c] = question.getQuestion().toString();
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
        hard_String = editText_Hard.getText().toString();

        if(correctAnswer_Hard.equals(hard_String)){
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

            }
        });
        alert.create().show();
    }
}
