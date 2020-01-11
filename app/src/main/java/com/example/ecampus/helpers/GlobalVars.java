package com.example.ecampus.helpers;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.ecampus.R;
import com.example.ecampus.adapters.ViewHolder;
import com.example.ecampus.models.News;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import java.util.Calendar;
import java.util.Date;

import es.dmoral.toasty.Toasty;

public class GlobalVars {

    private String thisYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    private String thisMonth = DateFormat.format("MM", new Date()).toString();
    private CollectionReference News =
            FirebaseFirestore.getInstance().
                    collection("news")
                    .document(thisYear).collection(thisMonth);
    private Query query = News.orderBy("date", Query.Direction.DESCENDING);

    @NonNull
    public FirestoreRecyclerAdapter<News, ViewHolder> NewAdapter(Fragment fragment, String bindFor, SwipeRefreshLayout swipeRefreshLayout) {
        FirestoreRecyclerOptions<News> options =
                new FirestoreRecyclerOptions.Builder<News>()
                        .setQuery(query, News.class)
                        .setLifecycleOwner(fragment).build();

        return new FirestoreRecyclerAdapter<News, ViewHolder>(options) {

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new ViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.news_row, parent, false));
            }

            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull News model) {
                long today = new Date().getTime();
                long newsDate = model.getDate().getTime(); //doc.getDocument().getDate("date").getTime();
                long diff = today - newsDate;
                int daysDiff = (int) (diff / (1000 * 60 * 60 * 24));
               holder.bind(fragment.getActivity(), model);

                switch (bindFor) {
                    case "LATEST":
                        if (daysDiff <= 0) {
                            Log.i("TODAY", "It's today");
                            Log.i("TODAY", model.getTitle());
                            Log.i("DIFF",String.valueOf(diff));
                            Log.i("DAYS DIFF", String.valueOf(daysDiff));
                            holder.itemView.setVisibility(View.VISIBLE);
                         }else {
                            holder.itemView.setVisibility(View.GONE);
                        }
                        break;
                    case "YESTERDAY":
                        if (daysDiff == 1) {
                            Log.i("YESTERDAY", "It's was yesterday");
                            Log.i("YESTERDAY", model.getTitle());
                            Log.i("DIFF",String.valueOf(diff));
                            Log.i("DAYS DIFF", String.valueOf(daysDiff));
                            holder.itemView.setVisibility(View.VISIBLE);
                          }else {
                            holder.itemView.setVisibility(View.GONE);
                        }
                        break;
                    case "LAST_WEEK":
                        if (daysDiff > 1 && daysDiff <= 6) {
                            Log.i("Last Week", "This was Last Week");
                            Log.i("Last Week", model.getTitle());
                            Log.i("DIFF",String.valueOf(diff));
                            Log.i("DAYS DIFF", String.valueOf(daysDiff));
                            holder.itemView.setVisibility(View.VISIBLE);
                        }else {
                            holder.itemView.setVisibility(View.GONE);
                        }
                        break;
                    case "OLDER":
                        if (daysDiff > 6) {
                            Log.i("OLDER", "This is too Old");
                            Log.i("OLDER", model.getTitle());
                            Log.i("DIFF",String.valueOf(diff));
                            Log.i("DAYS DIFF", String.valueOf(daysDiff));
                            holder.itemView.setVisibility(View.VISIBLE);
                        }else {
                            holder.itemView.setVisibility(View.GONE);
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
                Toasty.error(fragment.getActivity(), "Something went wrong").show();
                Log.e("Friestore RCV", e.getMessage());
            }
        };
    }

}
