package com.example.ecampus.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.ecampus.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;


public class HomescreenActivity extends AppCompatActivity {

    private int backButtonCount = 0;
    GridLayout mainGrid;
    Toolbar toolbar;
    CircularImageView profile;
private TextView    userName, todayDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        SharedPreferences sharedPrefs = getSharedPreferences("APP_PREFS", MODE_PRIVATE);

        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMMM, yyyy");
        mainGrid = findViewById(R.id.mainGrid);

        profile = findViewById(R.id.userpic);

        userName = findViewById(R.id.username);
        todayDate = findViewById(R.id.date);

        userName.setText(sharedPrefs.getString("firstName", "No name"));
        todayDate.setText(sdf.format(new Date()));

        Picasso.get().load(sharedPrefs.getString("image", "")).placeholder(R.drawable.user).into(profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomescreenActivity.this, ProfileActivity.class);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                startActivity(intent);
            }
        });

        //Set Event
        setSingleEvent(mainGrid);

    }


    @SuppressLint("ClickableViewAccessibility")
    public void setSingleEvent(GridLayout maingrid) {
        for (int i = 0; i < maingrid.getChildCount(); i++) {
            final CardView cardView = (CardView) maingrid.getChildAt(i);
            final int finalI = i;

            cardView.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View view, MotionEvent event) {
                    if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                        //Change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#FF6F00"));
                        if (finalI == 0) {
                            Intent intent = new Intent(HomescreenActivity.this, NewsfeedActivity.class);
                            intent.putExtra("info", "This is activity from card item index  " + finalI);
                            startActivity(intent);

                        } else if (finalI == 1) {
                            Intent intent = new Intent(HomescreenActivity.this, ForumActivity.class);
                            intent.putExtra("info", "This is activity from card item index  " + finalI);
                            startActivity(intent);
                        } else if (finalI == 2) {
                            Intent intent = new Intent(HomescreenActivity.this, NoticeActivity.class);
                            intent.putExtra("info", "This is activity from card item index  " + finalI);
                            startActivity(intent);
                        } else if (finalI == 3) {
                            Intent intent = new Intent(HomescreenActivity.this, TimetableActivity.class);
                            intent.putExtra("info", "This is activity from card item index  " + finalI);
                            startActivity(intent);
                        } else if (finalI == 4) {
                            Intent intent = new Intent(HomescreenActivity.this, StaffrecordActivity.class);
                            intent.putExtra("info", "This is activity from card item index  " + finalI);
                            startActivity(intent);
                        } else if (finalI == 5) {
                            Intent intent = new Intent(HomescreenActivity.this, CalculatorActivity.class);
                            intent.putExtra("info", "This is activity from card item index  " + finalI);
                            startActivity(intent);
                        } else if (finalI == 6) {
                            Intent intent = new Intent(HomescreenActivity.this, WebsiteActivity.class);
                            intent.putExtra("info", "This is activity from card item index  " + finalI);
                            startActivity(intent);
                        } else if (finalI == 7) {
                            Intent intent = new Intent(HomescreenActivity.this, ProfileActivity.class);
                            intent.putExtra("info", "This is activity from card item index  " + finalI);
                            startActivity(intent);
                        } else if (finalI == 8) {
                            SharedPreferences sharedPref = getSharedPreferences("APP_PREFS", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putBoolean("isLoggedIn", false);
                            editor.apply();
                            Log.i("LOGIN", "Keep me logged In IS: false ");
                            Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(HomescreenActivity.this, LoginActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        }


                    } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                        /* Reset Color */
                        cardView.setCardBackgroundColor(Color.parseColor("#6C63FF"));
                    }

                    return true;
                }

            });

        }
    }
}
