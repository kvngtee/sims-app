package com.example.ecampus.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.ecampus.R;
import com.example.ecampus.adapters.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class NewsfeedActivity extends AppCompatActivity {

    CircularImageView profile;


    TabLayout tabLayout;

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        SharedPreferences sharedPrefs = getSharedPreferences("APP_PREFS", MODE_PRIVATE);

        profile = findViewById(R.id.userpic);
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.myViewPager);
        viewPager.setAdapter(new ViewPagerAdapter(getApplicationContext(), getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        Picasso.get().load(sharedPrefs.getString("image", "")).placeholder(R.drawable.user).into(profile);
        profile.setOnClickListener(v -> {
            Intent intent = new Intent(NewsfeedActivity.this, ProfileActivity.class);
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            startActivity(intent);
        });
    }
}
