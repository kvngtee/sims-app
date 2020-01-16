
package com.example.ecampus.adapters.news_fragment;

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

import java.util.Date;

import es.dmoral.toasty.Toasty;

public class NewsFragAdapter extends FirestoreRecyclerAdapter<News, NewsFragViewHolder> {

    private Context context;
    private String bindFor;
    private SwipeRefreshLayout swipeRefreshLayout;

    public NewsFragAdapter(
            @NonNull FirestoreRecyclerOptions<News> options, String bindFor,
            SwipeRefreshLayout swipeRefreshLayout) {
        super(options);
        this.bindFor = bindFor;
        this.swipeRefreshLayout = swipeRefreshLayout;
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
    protected void onBindViewHolder(@NonNull NewsFragViewHolder holder, int position, @NonNull News news) {

        // These are the exact time conversion using milliseconds for best time comparison
        //long today = 86400000, yesterday = 172800000, oneWeek = 604800000;

        long currentDate = new Date().getTime();
        long newsDate = news.getDate().getTime();
        long diff = currentDate - newsDate;
        int daysDiff = (int) (diff / (1000 * 60 * 60 * 24));
        holder.bind(context, news);


        switch (bindFor) {
            case "LATEST":
                if (daysDiff <= 0) {
                    holder.showItemView();
                } else {
                    holder.hideItemView();
                }
                break;
            case "YESTERDAY":
                if (daysDiff == 1) {
                    holder.showItemView();
                } else {
                    holder.hideItemView();
                }
                break;
            case "LAST_WEEK":
                if (daysDiff > 1 && daysDiff <= 6) {
                    holder.showItemView();
                } else {
                    holder.hideItemView();
                }
                break;
            case "OLDER":
                if (daysDiff > 6) {
                    holder.showItemView();
                } else {
                    holder.hideItemView();
                }
                break;
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
        Toasty.error(context, "Something went wrong").show();
        Log.e("Friestore RCV", e.getMessage());
    }
}

