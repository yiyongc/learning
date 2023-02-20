package com.yichee.phototakingtest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView myImageView;
    File imageFileLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button photoButton = (Button) findViewById(R.id.photoButton);
        myImageView = (ImageView) findViewById(R.id.myImageView);

        if (!hasCamera()) {
            photoButton.setEnabled(false);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            requestPermission();

        }

    public void launchCamera(View view) {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date());


        File fileDirectory = new File(Environment.getExternalStorageDirectory() + File.separator + (Environment.DIRECTORY_PICTURES));

        String fileName = "QR_" + timeStamp;
        File image = null;
        try {
            image = new File(fileDirectory + File.separator + fileName + ".png");
            Toast.makeText(this, image.getAbsolutePath(), Toast.LENGTH_LONG).show();
            imageFileLocation = image;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (i.resolveActivity(getPackageManager()) != null) {
            i.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this, "com.yichee.phototakingtest.GenericFileProvider", image));
            startActivityForResult(i, REQUEST_IMAGE_CAPTURE);
        }
    }

    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bitmap = null;
            try {
                Uri myUri = FileProvider.getUriForFile(this, "com.yichee.phototakingtest.GenericFileProvider", imageFileLocation);
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), myUri);

            } catch (Exception e) {
                e.printStackTrace();
            }
            myImageView.setImageBitmap(bitmap);
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }
}
