package com.mackvel.soma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);



        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                DataPreference=getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
                dataEditor=DataPreference.edit();
                String logged_in=DataPreference.getString("logged in","");
                if (logged_in.equals("student logged in")){
                    startActivity(new Intent(SplashScreen.this,StudentActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                }
                else if (logged_in.equals("teacher logged in")){
                    startActivity(new Intent(SplashScreen.this,TeacherActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                }
                else{
                    startActivity(new Intent(SplashScreen.this,WalkThroughActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                }

            }
        },SPLASH_DISPLAY_LENGTH);
    }
}