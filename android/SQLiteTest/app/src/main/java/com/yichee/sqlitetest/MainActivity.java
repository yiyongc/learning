package com.yichee.sqlitetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText myTextInput;
    TextView dataTextView;
    ProductDBHandler productDBHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myTextInput = (EditText) findViewById(R.id.myTextInput);
        dataTextView = (TextView) findViewById(R.id.dataTextView);
        productDBHandler = new ProductDBHandler(this, null, null, 1);
        printDatabase();
    }

    private void printDatabase() {
        String dbString = productDBHandler.databaseToString();
        dataTextView.setText(dbString);
        myTextInput.setText("");
    }

    public void addButtonClicked(View view) {
        Product product = new Product(myTextInput.getText().toString());
        productDBHandler.addProduct(product);
        printDatabase();
    }

    public void deleteButtonClicked(View view) {
        String input = myTextInput.getText().toString();
        productDBHandler.deleteProduct(input);
        printDatabase();
    }


}
