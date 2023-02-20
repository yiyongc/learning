package com.yichee.threadtest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Handler afterThreadHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            TextView myTextView = (TextView) findViewById(R.id.threadTextView);
            myTextView.setText("It works!");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickThreadButton(View view) {
        //Place in thread so that app doesnt crash while waiting
//        long futureTime = System.currentTimeMillis() + 10000;
//        while(System.currentTimeMillis() < futureTime) {
//            synchronized (this) {
//                try {
//                    wait(futureTime - System.currentTimeMillis());
//                } catch (Exception e) {}
//            }
//        }
        Runnable r = new Runnable() {
            @Override
            public void run() {
                long futureTime = System.currentTimeMillis() + 10000;
                while(System.currentTimeMillis() < futureTime) {
                    synchronized (this) {
                        try {
                            wait(futureTime - System.currentTimeMillis());
                        } catch (Exception e) {}
                    }
                }
                afterThreadHandler.sendEmptyMessage(0);
            }
        };

        Thread myThread = new Thread(r);
        myThread.start();
    }
}
