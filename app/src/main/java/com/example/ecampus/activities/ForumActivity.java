package com.example.ecampus.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecampus.R;
import com.example.ecampus.adapters.AllNewsAdapter;
import com.example.ecampus.adapters.ViewHolder;
import com.example.ecampus.models.Chat;
import com.example.ecampus.models.News;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class ForumActivity extends AppCompatActivity {

    @BindView(R.id.input)
    EditText inputMsg;

    @BindView(R.id.sendMsg)
    FloatingActionButton sendMsg;

    @BindView(R.id.messageRec)
    RecyclerView recyclerView;

    private CardView btnBack;

    private CollectionReference Forum =
            FirebaseFirestore.getInstance().
                    collection("Forum");
    private Query query = Forum.orderBy("date", Query.Direction.DESCENDING);

    FirestoreRecyclerAdapter firestoreRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setContentView(R.layout.activity_forum);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        setAdapter();

        sendMsg.setEnabled(false);
        btnBack = findViewById(R.id.cardview);


        btnBack.setOnClickListener(v -> {
                btnBack.setCardBackgroundColor(Color.parseColor("#6C63Ff"));
                Intent intent = new Intent(ForumActivity.this, HomescreenActivity.class);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                startActivity(intent);
        });


// User is typing ...
        inputMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    //set user is typing feature
                    // enable the sendMsg btn
                    sendMsg.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        sendMsg.setOnClickListener(v -> {
            SharedPreferences sharedPrefs = getSharedPreferences("APP_PREFS", MODE_PRIVATE);
            String firstName = sharedPrefs.getString("firstName", "");
            String lastName = sharedPrefs.getString("lastName", "");
            String fullName = firstName + " " + lastName;
            String userID = sharedPrefs.getString("userID", "");
            Timestamp msgTime = Timestamp.now();
            Chat chat = new Chat(fullName, inputMsg.getText().toString().trim(), userID, msgTime);


            Forum.add(chat).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toasty.success(getApplicationContext(), "Msg Sent").show();
                }else {
                    Log.i("ERROR", task.getException().getMessage());
                }
            });
        });

    }

        @Override
        public void onStart() {
            super.onStart();
            firestoreRecyclerAdapter.startListening();
        }

        @Override
        public void onStop() {
            super.onStop();
            firestoreRecyclerAdapter.stopListening();
        }

        private void setAdapter() {
            firestoreRecyclerAdapter = NewAdapter();
            recyclerView.setAdapter(firestoreRecyclerAdapter);
        }

        @NonNull
        private FirestoreRecyclerAdapter<News, ViewHolder> NewAdapter() {

            FirestoreRecyclerOptions<News> options =
                    new FirestoreRecyclerOptions.Builder<News>()
                            .setQuery(query, News.class)
                            .setLifecycleOwner(this).build();

            return new AllNewsAdapter(options, getApplicationContext());
        }
    }


