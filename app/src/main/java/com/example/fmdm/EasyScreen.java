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
    int score_Easy,easyCounter;
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


    BroadcastReceiver mReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String text = intent.getStringExtra("theMessage");
            if (editText_Easy.getText().length() == 0){
                editText_Easy.setText(text);
                editText_Easy.setSelection(editText_Easy.getText().length());
            }else{
                editText_Easy.setText(editText_Easy.getText().toString() + text);
                editText_Easy.setSelection(editText_Easy.getText().length());
            }

        }
    };

//    tail



    public void setImage_Easy(){
        context = getApplicationContext();
        Random random = new Random();




        while (true){
            int num = random.nextInt(easyCounter);
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
                    easyCounter++;
                    Log.d("EZCOUNTER::::", String.valueOf(easyCounter));


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
                dialog.cancel();

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
//                Log.d("firstname: " , firstName);
//                Log.d("lastname : " , lastName);
                Log.d("difficulty : " , difficulty);
                Log.d("easyCounter : " , String.valueOf(easyCounter));
                Log.d("score : " , String.valueOf(score_Easy));
                Map<String, String> params = new HashMap<>();
                params.put("student_id",idnumber);
                params.put("difficulty",difficulty);
                params.put("result",String.valueOf(score_Easy));
                params.put("total",String.valueOf(easyCounter));
//                params.put("grading",grading);

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
