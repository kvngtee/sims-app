package com.example.ecampus.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecampus.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity {

    private ImageButton settings;
    CircularImageView UserIamge;
    private TextView FullName, Level,DOB, Department, Nationality, Email, PhoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        settings = findViewById(R.id.settings);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, SettingsActivity.class);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                startActivity(intent);

            }
        });

        UserIamge = findViewById(R.id.userpic);
        FullName = findViewById(R.id.full_name);
        Level = findViewById(R.id.level);
        Department = findViewById(R.id.department);
        DOB = findViewById(R.id.date_of_birth);
        Nationality = findViewById(R.id.nationality);
        Email = findViewById(R.id.email);
        PhoneNo = findViewById(R.id.phone);

        SharedPreferences sharedPrefs = getSharedPreferences("APP_PREFS", MODE_PRIVATE);
        Picasso.get().load(sharedPrefs.getString("image", ""))
                .placeholder(R.drawable.user).into(UserIamge);

        String fullName = sharedPrefs.getString("firstName", "") + " " + sharedPrefs.getString("lastName", "");
        FullName.setText(fullName);
        Level.setText(sharedPrefs.getString("level", ""));
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
        Date dob = new Date(sharedPrefs.getString("born", ""));
        DOB.setText(sdf.format(dob));
        Nationality.setText(sharedPrefs.getString("nationality",""));
        Email.setText(sharedPrefs.getString("email", ""));
        PhoneNo.setText(sharedPrefs.getString("phone", ""));
        Department.setText(sharedPrefs.getString("department", ""));

    }
}
