package com.agungpermanaputra.bir;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    final int splash=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent introSlider = new Intent(MainActivity.this, introSlider.class );
                startActivity(introSlider);
                finish();
            }
        },splash);
    }
}