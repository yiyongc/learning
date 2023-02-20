package com.yichee.listviewtest;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomRowAdapter extends ArrayAdapter<RowItem> {

    public CustomRowAdapter(@NonNull Context context, List<RowItem> games) {
        super(context, R.layout.custom_row, games);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myInflator = LayoutInflater.from(getContext());
        View customView = convertView;
        if(customView == null){
            customView = myInflator.inflate(R.layout.custom_row, parent, false);
        }

        RowItem game = getItem(position);

        TextView rowTextView = (TextView) customView.findViewById(R.id.rowTextView);
        ImageView rowImageView = (ImageView) customView.findViewById(R.id.rowImageView);

        rowTextView.setText(game.getName());
        rowImageView.setImageResource(game.getImage());

        return customView;
    }
}
