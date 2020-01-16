
package com.example.ecampus.adapters.news;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.ecampus.R;
import com.example.ecampus.adapters.news_fragment.NewsFragViewHolder;
import com.example.ecampus.models.News;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestoreException;

import es.dmoral.toasty.Toasty;

public class NewsSearchAdapter extends FirestoreRecyclerAdapter<News, NewsFragViewHolder> {

    private Context context;

    public NewsSearchAdapter(
            @NonNull FirestoreRecyclerOptions<News> options) {
        super(options);
    }

    @NonNull
    @Override
    public NewsFragViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NewsFragViewHolder view = new NewsFragViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_row, parent, false));
        context = parent.getContext();
        return view;
    }

    @Override
    protected void onBindViewHolder(@NonNull NewsFragViewHolder holder, int position, @NonNull News model) {
        holder.bind(context, model);
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
    }

    @Override
    public void onError(@NonNull FirebaseFirestoreException e) {
        super.onError(e);
        Toasty.error(context, "Something went wrong").show();
        Log.e("Friestore RCV", e.getMessage());
    }
}

