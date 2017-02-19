package com.lepko.martin.arquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lepko.martin.arquiz.CameraAR.CameraActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openCamera(View v) {
        Intent intent = new Intent(MainActivity.this, LocationActivity.class);
        //Intent intent = new Intent(MainActivity.this, CameraActivity.class);

        startActivity(intent);
    }
}
