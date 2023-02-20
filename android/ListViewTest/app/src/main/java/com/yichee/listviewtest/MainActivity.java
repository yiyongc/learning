package com.yichee.listviewtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Add to listview
        String[] games = {"Final Fantasy XV", "Tales of Zestiria", "Tales of Berseria", "Nioh", "Persona 5", "Digimon Story Cyber Sleuth"};
        Integer[] gameImages = { R.mipmap.final_fantasy_xv, R.mipmap.tales_of_zestiria, R.mipmap.tales_of_berseria, R.mipmap.nioh, R.mipmap.persona_5, R.mipmap.digimon};
        List<RowItem> gameItems = new ArrayList<>();

        for (int i = 0; i < games.length; i++) {
            gameItems.add(new RowItem(games[i], gameImages[i]));
        }
        //Converts an array into list items
        //ListAdapter gamesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, games);
        CustomRowAdapter gamesAdapter = new CustomRowAdapter(this, gameItems);

        ListView myListView = (ListView) findViewById(R.id.myListView);
        myListView.setAdapter(gamesAdapter);


        //Add click functionality to list items
        myListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        RowItem game = (RowItem) parent.getItemAtPosition(position);
                        Toast.makeText(MainActivity.this, game.getName(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

}
