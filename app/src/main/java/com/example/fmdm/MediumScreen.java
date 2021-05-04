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
    int easy_score2, score_Medium, mediumCounter;
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
//        firstName = getIntent().getStringExtra("firstName");
//        lastName = getIntent().getStringExtra("lastName");

        idnumber = getIntent().getStringExtra("studentID");
        easy_score2 = getIntent().getIntExtra("easy_score1",0);


        messages = new StringBuilder();
        LocalBroadcastManager.getInstance(this).registerReceiver(mReciever, new IntentFilter("incomingMessage"));
        mediumRequest();

        tv_scoreMedium.setText("Score: " + String.valueOf(score_Medium));



    }

////    head


    BroadcastReceiver mReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String text = intent.getStringExtra("theMessage");
            if (editText_Medium.getText().length() == 0){
                editText_Medium.setText(text);
                editText_Medium.setSelection(editText_Medium.getText().length());
            }else{
                editText_Medium.setText(editText_Medium.getText().toString() + text);
                editText_Medium.setSelection(editText_Medium.getText().length());
            }

        }

    };



    public void setImage_Medium(){
        context = getApplicationContext();
        Random random = new Random();




        while (true){
            int num = random.nextInt(mediumCounter);
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
                    mediumCounter++;


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
                dialog.cancel();


                Intent intent = new Intent(MediumScreen.this,LevelsScreen.class);
                intent.putExtra("easy_score2",easy_score2);
                intent.putExtra("medium_score", score_Medium);
                intent.putExtra("studentID",idnumber);
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
                intent.putExtra("easy_score2",easy_score2);
                intent.putExtra("medium_score", score_Medium);
                intent.putExtra("studentID",idnumber);
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
//                Log.d("firstname: " , firstName);
//                Log.d("lastname : " , lastName);
                Log.d("difficulty : " , difficulty);
//                Log.d("grading : " , grading);
                Log.d("score : " , String.valueOf(score_Medium));
                Map<String, String> params = new HashMap<>();
                params.put("student_id",idnumber);
                params.put("difficulty",difficulty);
                params.put("result",String.valueOf(score_Medium));
                params.put("total",String.valueOf(mediumCounter));
//                params.put("grading",grading);

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
