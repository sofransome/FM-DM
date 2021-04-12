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
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String URL = "https://thesis-api-usls.herokuapp.com/authentication-api";


    EditText studentidnumber_et,studentpassword_et;
    String idnumber,password,message,firstName,lastName;

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
                JSONObject jObject = null;
                try {
                    jObject = new JSONObject(response);
                    JSONObject user = jObject.getJSONObject("user");
                    message = (String)user.getString("message");
                    idnumber = (String)user.getString("id");
                    firstName = (String)user.getString("first_name");
                    lastName = (String)user.getString("last_name");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(message.equals("Success")){
                    loading.dismiss();
                    showSuccessMessage();

                }else if(message.equals("Invalid Credentials")){
                    loading.dismiss();
                    Toast.makeText(MainActivity.this,"Login Failed! Invalid Credentials.",Toast.LENGTH_LONG).show();
                }else{
                    loading.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MainActivity.this,"Error: "  + error.getMessage(),Toast.LENGTH_LONG).show();

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
        alert.setMessage(firstName + " " + lastName +  " Successfully Signin!");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MainActivity.this, ConnectingDevicesInstruction.class);
                intent.putExtra("studentID",idnumber);
                intent.putExtra("firstName",firstName);
                intent.putExtra("lastName",lastName);

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
