package com.example.ecampus.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.ecampus.R;
import com.example.ecampus.models.Chat;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class AllNewsAdapter extends FirestoreRecyclerAdapter<Chat, ChatViewHolder> {
    private Context context;
    private String currentUserID;

    public AllNewsAdapter(@NonNull FirestoreRecyclerOptions<Chat> options, Context context, String currentUserID) {
        super(options);
        this.context = context;
        this.currentUserID = currentUserID;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_row, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatViewHolder holder, int position, @NonNull Chat model) {
        holder.bind(context, model, currentUserID);
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
    }

    @Override
    public void onError(@NonNull FirebaseFirestoreException e) {
        super.onError(e);
        Log.e("Friestore RCV", e.getMessage());
    }

}
