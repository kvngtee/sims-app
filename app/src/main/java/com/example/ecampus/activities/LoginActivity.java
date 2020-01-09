package com.example.ecampus.activities;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.ecampus.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.Map;

import es.dmoral.toasty.Toasty;


public class LoginActivity extends AppCompatActivity {

    private EditText StudentID, Password;
    private TextView Fname, info, welcome, forgotpass;
    private Button button;
    private CheckBox rememberMe;
    private CardView cardview;
    private CircularImageView userpic;
    private ProgressDialog mProgressDialog;
    private int backButtonCount = 0;
    boolean doubleBackToExitPressedOnce = false;
    private FirebaseFirestore db;
    SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        sharedPrefs = getSharedPreferences("APP_PREFS", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPrefs.edit();

        StudentID = findViewById(R.id.studentid);
        Password = findViewById(R.id.password);
        button = findViewById(R.id.loginbtn);
        Fname = findViewById(R.id.Fname);
        welcome = findViewById(R.id.welcome);
        info = findViewById(R.id.info);
        userpic = findViewById(R.id.userpic);
        cardview = findViewById(R.id.cardview);
        rememberMe = findViewById(R.id.rememberMe);


        Fname.setVisibility(View.GONE);
        StudentID.getText().toString().trim();

        StudentID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (StudentID.length() == 17) {
                    //check if user id is in fire store and display data
                    //Query Firestore and retrieve the document
                    Source source = Source.SERVER;
                    db.collection("students")
                            .whereEqualTo("studentID", StudentID.getText().toString())
                            .get(source)
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        QuerySnapshot snapshot = task.getResult();
                                        if (snapshot.getDocuments().size() == 1) {

                                            Fname.setVisibility(View.VISIBLE);

                                            welcome.setText("Welcome back,");
                                            info.setText("Please enter your password \nto continue.");
                                            Fname.setText(snapshot.getDocuments().get(0).get("firstName").toString() + ".");
                                            Picasso.get().load(snapshot.getDocuments().get(0).getString("image")).placeholder(R.drawable.user).into(userpic);

                                            Map<String, Object> data = snapshot.getDocuments().get(0).getData();
                                            editor.putString("firstName", data.get("firstName").toString());
                                            editor.putString("lastName", data.get("lastName").toString());
                                            editor.putString("born", data.get("born").toString());
                                            editor.putString("department", data.get("department").toString());
                                            editor.putString("email", data.get("email").toString());
                                            editor.putString("image", data.get("image").toString());
                                            editor.putString("level", data.get("level").toString());
                                            editor.putString("nationality", data.get("nationality").toString());
                                            editor.putString("phone", data.get("phone").toString());
                                            editor.putString("studentID", data.get("studentID").toString());
                                            editor.putString("password", data.get("password").toString());
                                            editor.putString("userID", snapshot.getDocuments().get(0).getId());
                                            editor.apply();

                                        } else {
                                            Fname.setVisibility(View.GONE);
                                            info.setText("Please enter your student ID \nand password to continue.");
                                            userpic.setImageResource(R.drawable.user);
                                            Log.d("GET_DATA", "No such document");
                                            Toasty.error(getApplicationContext(), "Invalid Student ID...please try again").show();
                                        }
                                    } else {
                                        Toasty.error(getApplicationContext(), "Couldn't connect to the internet").show();
                                        Log.d("GET_DATA", "get failed with ", task.getException());
                                    }
                                }
                            });


                } else if (StudentID.length() < 17) {

                    Fname.setVisibility(View.GONE);
                    info.setText("Please enter your student ID \nand password to continue.");
                    userpic.setImageResource(R.drawable.user);
                    welcome.setText("Welcome");

                }

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StudentID.getText().toString().length() < 17) {
                    Toasty.error(getApplicationContext(), "Student ID is invalid/incomplete").show();
                } else {
                    if (sharedPrefs.getString("password", "1234").equalsIgnoreCase(Password.getText().toString())) {
                        Log.i("OH HAPPY DAY", "pASSWORD MATCH");
                        button.setBackgroundResource(R.drawable.clicked);
                        Intent intent = new Intent(LoginActivity.this, HomescreenActivity.class);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        startActivity(intent);
                        Toasty.success(LoginActivity.this, "Welcome!", Toast.LENGTH_SHORT, true).show();
                    } else {
                        Toasty.error(getApplicationContext(), "Password is incorrect").show();
                    }

                }
            }
        });

        //checking if the rememberMe if checked
        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();
                    Log.i("LOGIN", "Keep me logged In IS: TRUE ");

                } else {
                    editor.putBoolean("isLoggedIn", false);
                    editor.apply();
                    Log.i("LOGIN", "Keep me logged In IS: false ");
                }
            }
        });

        forgotpass = findViewById(R.id.forgot_pass);
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, ForgotpasswordActivity.class);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                startActivity(intent);

            }
        });


        cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardview.setCardBackgroundColor(Color.parseColor("#6C63Ff"));
                Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Login CheckPoint", "LoginActivity resumed");
        //check Internet Connection
        // new CheckInternetConnection(this).checkConnection();

    }

    @Override
    protected void onStop() {
        super.onStop();
        //mProgressDialog.dismiss();
        Log.e("Login CheckPoint", "LoginActivity stopped");

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please press BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


}
