package com.example.ecampus.activities;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.ecampus.R;

public class SplashActivity extends Activity {

    private static int SPLASH_TIME_OUT = 2800;

    //to get user session data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        YoYo.with(Techniques.Bounce)
                .duration(4000)
                .playOn(findViewById(R.id.logo));


        final SharedPreferences sharedPrefs = getSharedPreferences("APP_PREFS", MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                if (sharedPrefs.getBoolean("isLoggedIn", false)){
                    startActivity(new Intent(SplashActivity.this, HomescreenActivity.class));
                } else if (sharedPrefs.getBoolean("isFirstLaunch", true)){
                    startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
                } else{
                    if (sharedPrefs.getBoolean("isLoggedIn", false)){
                        startActivity(new Intent(SplashActivity.this, HomescreenActivity.class));
                    }else{
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }
                }
                finish();
            }
        }, SPLASH_TIME_OUT);

    }


    @Override
    protected void onResume() {
        super.onResume();
        }
    }
