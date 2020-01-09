package com.example.ecampus.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.ecampus.R;
import com.example.ecampus.activities.NewsfeedActivity;
import com.example.ecampus.adapters.NewsAdapter;
import com.example.ecampus.models.News;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OldestFragment extends Fragment {

    private List<News> mList = new ArrayList<>();
    private View view;
    private RecyclerView recyclerView;
    private  SwipeRefreshLayout swipeRefreshLayout;

    public OldestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_newsfeed, container, false);
        mList = ((NewsfeedActivity) getActivity()).getNewsList();

         swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
         recyclerView = view.findViewById(R.id.myrecyclerview);
      refreshRecyclerView();
      swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                   refreshRecyclerView();
                    swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    public void refreshRecyclerView() {
        mList=   ((NewsfeedActivity)getActivity()).getNewsList();
        NewsAdapter newsAdapter = new NewsAdapter( mList);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(newsAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshRecyclerView();
    }
}
