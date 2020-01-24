package com.example.ecampus.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.ecampus.R;
import com.example.ecampus.adapters.news.NewsFragViewPager;
import com.example.ecampus.adapters.news.SearchNewsAdapter;
import com.example.ecampus.helpers.SqliteHelper;
import com.example.ecampus.models.News;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewsfeedActivity extends AppCompatActivity {

    CircularImageView profile;


    TabLayout tabLayout;

    ViewPager viewPager;

    LinearLayout tabsPager;

    SearchNewsAdapter searchNewsAdapter;

    SqliteHelper sqliteHelper;

    List<News> newsList;

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
        viewPager.setAdapter(new NewsFragViewPager(getApplicationContext(), getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        tabsPager = findViewById(R.id.tabsPager);

        Picasso.get().load(sharedPrefs.getString("image", "")).placeholder(R.drawable.user).fit().centerCrop().into(profile);
        profile.setOnClickListener(v -> {
            Intent intent = new Intent(NewsfeedActivity.this, ProfileActivity.class);
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            startActivity(intent);
        });

        sqliteHelper = new SqliteHelper(getApplicationContext());

        newsList = sqliteHelper.allNews();
        searchNewsAdapter = new SearchNewsAdapter(getApplicationContext(), newsList);
        RecyclerView newsRCV = findViewById(R.id.newsRCV);
        newsRCV.setHasFixedSize(true);
        newsRCV.setLayoutManager(new LinearLayoutManager(this));
        newsRCV.setAdapter(searchNewsAdapter);

        SearchView searchView = findViewById(R.id.simpleSearchView);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                newsList = sqliteHelper.searchNews(s);
                newsRCV.setAdapter(new SearchNewsAdapter(getApplicationContext(), newsList));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s.trim().length()>0) {
                    newsRCV.setVisibility(View.VISIBLE);
                    tabsPager.setVisibility(View.GONE);
                } else  {
                    tabsPager.setVisibility(View.VISIBLE);
                    newsRCV.setVisibility(View.GONE);
                }
                newsList = sqliteHelper.searchNews(s);
                newsRCV.setAdapter(new SearchNewsAdapter(getApplicationContext(), newsList));
                return true;
            }
        });
    }

    public FirestoreRecyclerOptions<News> getNewsOptions(LifecycleOwner owner) {
        String thisYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String thisMonth = DateFormat.format("MM", new Date()).toString();

        CollectionReference News =
                FirebaseFirestore.getInstance().
                        collection("news")
                        .document(thisYear).collection(thisMonth);
        Query query = News.orderBy("date", Query.Direction.DESCENDING);
        return new FirestoreRecyclerOptions.Builder<News>()
                .setQuery(query, News.class)
                .setLifecycleOwner(owner).build();
    }
}
