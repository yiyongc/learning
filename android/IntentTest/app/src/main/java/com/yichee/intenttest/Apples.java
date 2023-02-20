package com.yichee.intenttest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Apples extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apples);

        Bundle mainData = getIntent().getExtras();
        if (mainData == null)
            return;
        else {
            TextView myText = (TextView) findViewById(R.id.applesText);
            String appendMessage = mainData.getString("message");
            myText.append(" - " + appendMessage);
        }
    }

    public void backToMain(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
