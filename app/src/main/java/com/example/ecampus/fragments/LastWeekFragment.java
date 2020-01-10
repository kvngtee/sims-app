package com.example.ecampus.fragments;


import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.ecampus.R;
import com.example.ecampus.adapters.AllNewsAdapter;
import com.example.ecampus.adapters.ViewHolder;
import com.example.ecampus.models.News;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class LastWeekFragment extends Fragment {

    private View view;

    @BindView(R.id.myrecyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    private String thisYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    private String thisMonth = DateFormat.format("MM", new Date()).toString();
    private CollectionReference News =
            FirebaseFirestore.getInstance().
                    collection("news")
                    .document(thisYear).collection(thisMonth);
    private Query query = News.orderBy("date", Query.Direction.DESCENDING);
    private FirestoreRecyclerAdapter firestoreRecyclerAdapter;


    public LastWeekFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_newsfeed, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setAdapter();

        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            setAdapter();
            swipeRefreshLayout.setRefreshing(false);
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

        return new AllNewsAdapter(options, getActivity(), swipeRefreshLayout, true);
    }
}


