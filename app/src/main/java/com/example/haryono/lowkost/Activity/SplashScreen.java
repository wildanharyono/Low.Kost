package com.example.haryono.lowkost.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.haryono.lowkost.R;

public class SplashScreen extends AppCompatActivity {

    public static int SPLASH_TIME_OUT=4000; //Inisiasi splash

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //untuk membuat splash screen fullscreen tanpa tool bar.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        Toast.makeText(this,"KELOMPOK 8",Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() { //inisiasi handler
            @Override
            public void run() { //inisiasi method run
                Intent homeIntent = new Intent(SplashScreen.this, MainActivity.class); //inisiasi intent untuk pindah ke activity lain
                startActivity(homeIntent);  //memanggil method start
                finish(); //memanggil method finish
            }
        },SPLASH_TIME_OUT); //tag penutup untuk splash
    }
}
