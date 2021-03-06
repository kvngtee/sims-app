package com.example.ecampus.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.ecampus.R;

public class StaffrecordActivity extends AppCompatActivity {

    private CardView cardview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staffrecord);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        cardview = findViewById(R.id.cardview);


        cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardview.setCardBackgroundColor(Color.parseColor("#6C63Ff"));
                Intent intent = new Intent(StaffrecordActivity.this, HomescreenActivity.class);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                startActivity(intent);
            }
        });

    }
}
