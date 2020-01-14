package com.example.ecampus.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecampus.R;
import com.example.ecampus.models.Chat;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class ChatAdapter extends FirestoreRecyclerAdapter<Chat, ChatViewHolder> {
    private String currentUserID;
    public ChatAdapter(@NonNull FirestoreRecyclerOptions<Chat> options, String currentUserID) {
        super(options);
        this.currentUserID = currentUserID;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatViewHolder holder, int position, @NonNull Chat chat) {
        Log.i("CHAT", chat.getmMessage());
 holder.bind(chat, currentUserID);
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_row,
                parent, false);
    return  new ChatViewHolder(v);
    }

}
