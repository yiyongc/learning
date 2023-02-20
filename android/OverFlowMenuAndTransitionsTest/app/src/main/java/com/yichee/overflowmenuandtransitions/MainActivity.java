package com.yichee.overflowmenuandtransitions;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        Button myButton = (Button) findViewById(R.id.myButton);
        myButton.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        moveButton();
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        RelativeLayout mainView = (RelativeLayout) findViewById(R.id.main_view);

        switch(item.getItemId()) {
            case R.id.menu_red:
                if (item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);
                mainView.setBackgroundColor(Color.RED);
                return true;
            case R.id.menu_green:
                if (item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);
                mainView.setBackgroundColor(Color.GREEN);
                return true;
            case R.id.menu_blue:
                if (item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);
                mainView.setBackgroundColor(Color.BLUE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void moveButton() {
        View myButton = findViewById(R.id.myButton);

        TransitionManager.beginDelayedTransition((ViewGroup) findViewById(R.id.main_view));

        RelativeLayout.LayoutParams buttonPos = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonPos.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        buttonPos.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        myButton.setLayoutParams(buttonPos);

        ViewGroup.LayoutParams buttonSize = myButton.getLayoutParams();
        buttonSize.width = 500;
        buttonSize.height = 300;
        myButton.setLayoutParams(buttonSize);
    }
}
