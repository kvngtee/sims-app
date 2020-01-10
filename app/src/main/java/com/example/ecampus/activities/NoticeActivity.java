package com.example.ecampus.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecampus.R;
import com.example.ecampus.adapters.NoticeAdapter;
import com.example.ecampus.models.Notice;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class NoticeActivity extends AppCompatActivity {

    private CardView cardview;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference noticeRef = db.collection("notice");
    private NoticeAdapter adapter;
    private ShimmerFrameLayout ShimmerViewContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setUpRecyclerView();

        ShimmerViewContainer = findViewById(R.id.shimmer_view_container);


        cardview = findViewById(R.id.cardview);

        cardview.setOnClickListener(v -> {
            cardview.setCardBackgroundColor(Color.parseColor("#6C63Ff"));
            Intent intent = new Intent(NoticeActivity.this, HomescreenActivity.class);
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            startActivity(intent);
        });
    }


    private void setUpRecyclerView() {
        Query query = noticeRef.orderBy("date", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Notice> options = new FirestoreRecyclerOptions.Builder<Notice>()
                .setQuery(query, Notice.class)
                .build();

        adapter = new NoticeAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.myrecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (adapter != null) {
            adapter.stopListening();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        ShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }

}
