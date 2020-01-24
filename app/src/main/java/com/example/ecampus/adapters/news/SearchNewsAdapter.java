package com.example.ecampus.adapters.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecampus.R;
import com.example.ecampus.models.News;

import java.util.List;

public class SearchNewsAdapter extends RecyclerView.Adapter<NewsFragViewHolder> {
    Context context;
    List<News> newsList;

    public SearchNewsAdapter(Context context, List<News> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsFragViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_row, parent, false);
        return new NewsFragViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsFragViewHolder holder, int position) {
        holder.bind(context, newsList.get(position));
    }


    @Override
    public int getItemCount() {
        return newsList.size();
    }

}


