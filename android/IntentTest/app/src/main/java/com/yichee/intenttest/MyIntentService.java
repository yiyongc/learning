package com.yichee.intenttest;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

public class MyIntentService extends IntentService {

    //private static final String TAG = "com.yichee.intenttest";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //Log.i(TAG, "The service has now started.");
        Toast.makeText(this, "The service has now started.", Toast.LENGTH_LONG).show();
    }
}
