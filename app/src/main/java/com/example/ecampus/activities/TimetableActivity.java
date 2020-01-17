package com.example.ecampus.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.WindowManager;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecampus.R;
import com.example.ecampus.adapters.news.NewsSearchAdapter;
import com.example.ecampus.models.News;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Calendar;
import java.util.Date;

public class TimetableActivity extends AppCompatActivity {

    private CardView cardview;

    NewsSearchAdapter newsSearchAdapter;

    NewsfeedActivity newsfeedActivity = new NewsfeedActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        cardview = findViewById(R.id.cardview);


        cardview.setOnClickListener(view -> {
            cardview.setCardBackgroundColor(Color.parseColor("#6C63Ff"));
            Intent intent = new Intent(TimetableActivity.this, HomescreenActivity.class);
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            startActivity(intent);
        });

        newsSearchAdapter = new NewsSearchAdapter(newsfeedActivity.getNewsOptions(this));
        RecyclerView newsRCV = findViewById(R.id.newsRCV);
        newsRCV.setHasFixedSize(true);
        newsRCV.setLayoutManager(new LinearLayoutManager(this));
        newsRCV.setAdapter(newsSearchAdapter);

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String thisYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
                String thisMonth = DateFormat.format("MM", new Date()).toString();

                CollectionReference News =
                        FirebaseFirestore.getInstance().
                                collection("news")
                                .document(thisYear).collection(thisMonth);
                Query query = News.orderBy("title").startAt(s.trim()).endAt(s.trim() + "\uf8ff");
                FirestoreRecyclerOptions options = new FirestoreRecyclerOptions.Builder<News>()
                        .setQuery(query, News.class).build();
                newsSearchAdapter.updateOptions(options);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String thisYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
                String thisMonth = DateFormat.format("MM", new Date()).toString();

                CollectionReference News =
                        FirebaseFirestore.getInstance().
                                collection("news")
                                .document(thisYear).collection(thisMonth);
                Query query = News.orderBy("title").startAt(s.trim() ).endAt(s.trim() + "\uf8ff");
                FirestoreRecyclerOptions options = new FirestoreRecyclerOptions.Builder<News>()
                        .setQuery(query, News.class).build();
                newsSearchAdapter.updateOptions(options);
                return true;
            }
        });
    }
}
