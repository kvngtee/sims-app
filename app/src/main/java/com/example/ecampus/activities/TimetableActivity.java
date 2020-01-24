package com.example.ecampus.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.ecampus.R;

public class TimetableActivity extends AppCompatActivity {

    private CardView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        btnBack = findViewById(R.id.cardview);

        btnBack.setOnClickListener(view -> {
            btnBack.setCardBackgroundColor(Color.parseColor("#6C63Ff"));
            Intent intent = new Intent(TimetableActivity.this, HomescreenActivity.class);
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            startActivity(intent);
        });

    }
}
