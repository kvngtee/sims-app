package com.example.ecampus.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.ecampus.R;
import com.example.ecampus.models.News;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class AllNewsAdapter extends FirestoreRecyclerAdapter<News, ViewHolder> {
    private Context context;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Boolean showIf;


    public AllNewsAdapter(FirestoreRecyclerOptions<News> options, Context context, SwipeRefreshLayout swipeRefreshLayout, Boolean showIf) {
        super(options);
        this.context = context;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.showIf = showIf;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_row, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull News model) {
        if (showIf) {
            holder.bind(context, model);
        }
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onError(@NonNull FirebaseFirestoreException e) {
        super.onError(e);
        Log.e("Friestore RCV", e.getMessage());
    }
}
