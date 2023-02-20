package com.yichee.intenttest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Start intent service
        Intent intent = new Intent(this, MyIntentService.class);
        startService(intent);

        //Start service
        Intent intent2 = new Intent(this, MyService.class);
        startService(intent2);
    }

    public void toApples(View view) {
        Intent i = new Intent(this, Apples.class);

        final EditText mainInput = (EditText) findViewById(R.id.mainInput);
        String userMessage = mainInput.getText().toString();
        i.putExtra("message", userMessage);

        startActivity(i);

    }

    public void toBacon(View view) {
        Intent i = new Intent(this, Bacon.class);

        final EditText mainInput = (EditText) findViewById(R.id.mainInput);
        String userMessage = mainInput.getText().toString();
        i.putExtra("message", userMessage);

        startActivity(i);
    }
}
