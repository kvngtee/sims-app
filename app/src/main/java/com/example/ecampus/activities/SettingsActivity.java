package com.example.ecampus.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.ecampus.R;

public class SettingsActivity extends AppCompatActivity {

    ToggleButton notificationtogglebtn, darkmodetogglebtn;
    private CardView cardview;
    private LinearLayout agreement, appfeedback, bugreport, rate, developer;
    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        myDialog = new Dialog(this);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        cardview = findViewById(R.id.cardview);
        notificationtogglebtn = findViewById(R.id.notificationtoggleButton);
        darkmodetogglebtn = findViewById(R.id.darkmodetoggleButton);
        agreement = findViewById(R.id.agreements);
        appfeedback = findViewById(R.id.app_feedback);
        bugreport = findViewById(R.id.bug);
        rate = findViewById(R.id.rate);
        developer = findViewById(R.id.developer);


        cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardview.setCardBackgroundColor(Color.parseColor("#6C63Ff"));
                Intent intent = new Intent(SettingsActivity.this, ProfileActivity.class);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                startActivity(intent);
            }
        });


        notificationtogglebtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {

            }
        });

        darkmodetogglebtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {

            }
        });

        agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              ShowPopup(v);


            }
        });

        appfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailintent = new Intent(Intent.ACTION_SEND);
                emailintent.setType("message/rfc822");
                emailintent.putExtra(Intent.EXTRA_EMAIL, new String[]{"asieduprince123@gmail.com"});
                emailintent.putExtra(Intent.EXTRA_SUBJECT, "FEEDBACK");
                emailintent.putExtra(Intent.EXTRA_TEXT, "");
                try {
                    startActivity(Intent.createChooser(emailintent, "Send Email..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(SettingsActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        bugreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailintent = new Intent(Intent.ACTION_SEND);
                emailintent.setType("message/rfc822");
                emailintent.putExtra(Intent.EXTRA_EMAIL, new String[]{"asieduprince123@gmail.com"});
                emailintent.putExtra(Intent.EXTRA_SUBJECT, "BUG REPORT");
                emailintent.putExtra(Intent.EXTRA_TEXT, "");
                try {
                    startActivity(Intent.createChooser(emailintent, "Send Email..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(SettingsActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

            }
        });


        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowRateDialog(v);

            }
        });

        developer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, DeveloperActivity.class);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                startActivity(intent);


            }
        });

    }

    public void ShowPopup(View view) {
        final Button btnAccept;
        ImageView close;

        myDialog.setContentView(R.layout.agreementpopup);
        close = myDialog.findViewById(R.id.close);
        btnAccept = myDialog.findViewById(R.id.acceptbtn);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }



    public void ShowRateDialog(View view){
        final Button btnlater;
        final Button btnRate;

        myDialog.setContentView(R.layout.ratepopup);

        btnlater = myDialog.findViewById(R.id.maybelater);
        btnRate = myDialog.findViewById(R.id.ratenow);

        btnlater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();

    }

}
