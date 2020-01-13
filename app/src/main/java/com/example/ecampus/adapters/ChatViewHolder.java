package com.example.ecampus.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecampus.R;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.mikhaellopez.circularimageview.CircularImageView;

class ChatViewHolder extends RecyclerView.ViewHolder {
    CircularImageView userPic;
    TextView userName, msgText;
    RelativeTimeTextView msgTime;

    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);
        userName = itemView.findViewById(R.id.user_name);
        userPic = itemView.findViewById(R.id.userpic);
        msgText = itemView.findViewById(R.id.message_text);
        msgTime = itemView.findViewById(R.id.message_time);
    }
}
