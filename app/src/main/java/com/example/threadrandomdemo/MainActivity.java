package com.example.threadrandomdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    private final String TAG = this.getClass().getSimpleName();
    private static final int UPDATE_TEXT = 1;

    Button button_startService;
    Button button_stopService;
    private static TextView textView_showRandom;

    private static Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            switch (message.what){
                case UPDATE_TEXT:{
                    textView_showRandom.setText(String.valueOf(message.obj));
                    break;
                }
                default:break;
            }
            return false;
        }
    });
    private static double randomInText;
    public static void UpdateText(double doubleRandom){
        randomInText = doubleRandom;
        Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = UPDATE_TEXT;
                msg.obj = randomInText;
                handler.sendMessage(msg);
            }
        });
        myThread.start();
        Log.d("MainActivity","the ThreadId is " + myThread.getId() + "in Start");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_startService = (Button) findViewById(R.id.startService);
        button_stopService = (Button) findViewById(R.id.stopService);
        textView_showRandom = (TextView) findViewById(R.id.text_view);

        final Intent intent_Service = new Intent(this,RandomService.class);
        button_startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(intent_Service);
            }
        });
        button_stopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(intent_Service);
            }
        });

        Log.d(TAG,"the TaskId is " + getTaskId());
    }
}