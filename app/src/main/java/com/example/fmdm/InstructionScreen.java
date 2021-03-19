package com.example.fmdm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class InstructionScreen extends AppCompatActivity {

    String idnumber;
    StringBuilder messages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction_screen);

//        idnumber = getIntent().getStringExtra("studentID");

        messages = new StringBuilder();
        LocalBroadcastManager.getInstance(this).registerReceiver(mReciever, new IntentFilter("incomingMessage"));


    }
    BroadcastReceiver mReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String text = intent.getStringExtra("theMessage");

            messages.append(text + "\n");

        }
    };

    public void goto_Tutorial(View view){
        Intent intent = new Intent(InstructionScreen.this, TutorialScreen.class);
//        intent.putExtra("studentID",idnumber);
        startActivity(intent);
        finish();

    }
}
