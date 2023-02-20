package com.yichee.myapplication;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BottomPictureFragment extends Fragment {

    private static TextView topText;
    private static TextView bottomText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_picture_fragment, container, false);

        topText = (TextView) view.findViewById(R.id.topTextView);
        bottomText = (TextView) view.findViewById(R.id.bottomTextView);

        return view;
    }

    public void setMemeText(String top, String bottom) {
        topText.setText(top);
        bottomText.setText(bottom);
    }
}
