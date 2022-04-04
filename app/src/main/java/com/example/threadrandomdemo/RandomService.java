package com.example.threadrandomdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class RandomService extends Service {

    private String TAG = this.getClass().getSimpleName();
    private Thread randomThread;

    public RandomService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, TAG + "has already executed onCreate method",
                Toast.LENGTH_SHORT).show();
        randomThread = new Thread(runnable);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, TAG + "has already executed onStartCommand method",
                Toast.LENGTH_SHORT).show();
        if (!randomThread.isAlive()) {
            randomThread.start();
            Log.d(TAG,"the ThreadId is " + randomThread.getId() + "in onStartCommand");
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, TAG + "has already executed onDestory method",
                Toast.LENGTH_SHORT).show();
        super.onDestroy();
        randomThread.interrupt();
    }


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                while (!randomThread.isInterrupted()) {
                    double doubleRandom = Math.random() * 100.0;
                    MainActivity.UpdateText(doubleRandom);
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}