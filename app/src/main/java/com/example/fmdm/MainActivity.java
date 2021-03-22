package com.example.fmdm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String URL = "https://thesis-api-usls.herokuapp.com/api/authentication-mobile";

    EditText studentidnumber_et,studentpassword_et;
    String idnumber,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentidnumber_et = (EditText)findViewById(R.id.et_studentidnumber);
        studentpassword_et = (EditText)findViewById(R.id.et_studentpassword);
    }

    public void Signin(View view){
         idnumber = studentidnumber_et.getText().toString();
         password = studentpassword_et.getText().toString();

        if(idnumber.isEmpty()){
            Toast.makeText(getApplicationContext(),"Login Failed: Student ID number field is empty!",Toast.LENGTH_LONG).show();
        }else if(password.isEmpty()){
            Toast.makeText(getApplicationContext(),"Login Failed: Password field is empty!",Toast.LENGTH_LONG).show();
        }else{

        }

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        final ProgressDialog loading = new ProgressDialog(MainActivity.this);
        loading.setMessage("Please Wait...");
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        StringRequest stringReq =  new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("Response: ", response);
//                Toast.makeText(MainActivity.this,"Response : "  + response,Toast.LENGTH_LONG).show();
                if(response.equals("OK") ){

                    showSuccessMessage();

                }else{
                    Toast.makeText(MainActivity.this,"WALA Response : "  + response,Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Error: "  + error.getMessage(),Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email",idnumber);
                params.put("password",password);

                return params;
            }
        };
        requestQueue.add(stringReq);

    }

    public void showSuccessMessage(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Sign in");
        alert.setMessage("Successfully Signin!");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MainActivity.this, ConnectingDevicesInstruction.class);
                intent.putExtra("studentID",idnumber);
                startActivity(intent);
                finish();
            }
        });
        alert.create().show();

    }

    public  void next(View view){
        Intent intent = new Intent(MainActivity.this, ConnectingDevicesInstruction.class);
        intent.putExtra("studentID",idnumber);
        startActivity(intent);
        finish();

    }
}
