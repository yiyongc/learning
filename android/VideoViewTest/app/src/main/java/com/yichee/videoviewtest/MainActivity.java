package com.yichee.videoviewtest;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final VideoView myVideoView = (VideoView) findViewById(R.id.myVideoView);
        myVideoView.setVideoPath("http://www.sample-videos.com/video/mp4/720/big_buck_bunny_720p_1mb.mp4");
        //myVideoView.setVideoURI(Uri.parse("http://www.sample-videos.com/video/mp4/720/big_buck_bunny_720p_1mb.mp4"));
        myVideoView.start();
        MediaController myController = new MediaController(this);
        myController.setAnchorView(myVideoView);
        myVideoView.setMediaController(myController);
    }
}
