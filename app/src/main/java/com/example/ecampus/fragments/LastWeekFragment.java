package com.example.ecampus.fragments;


import android.os.Bundle;
import android.util.Log;
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
import com.example.ecampus.activities.NewsfeedActivity;
import com.example.ecampus.adapters.news.NewsFragAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class LastWeekFragment extends Fragment {

    private View view;

    private NewsfeedActivity newsfeedActivity = new NewsfeedActivity();

    private FirestoreRecyclerAdapter firestoreRecyclerAdapter;

    @BindView(R.id.myrecyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

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

    @Override
    public void onResume() {
        super.onResume();
        setAdapter();
    }

    private void setAdapter() {
        Log.i("","Set Adapter is called by Last Week Fragment ");
        firestoreRecyclerAdapter =
                new NewsFragAdapter(newsfeedActivity.getNewsOptions(this),
                        "LAST_WEEK", swipeRefreshLayout);        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(firestoreRecyclerAdapter);
    }

}


