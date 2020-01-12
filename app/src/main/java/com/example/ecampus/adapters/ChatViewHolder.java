package com.example.ecampus.adapters;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ecampus.R;
import com.example.ecampus.models.Chat;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatViewHolder extends RecyclerView.ViewHolder {

    Context context;

    @BindView(R.id.incoming)
    RelativeLayout incomingChat;

    @BindView(R.id.outgoing)
    RelativeLayout outgoingChat;


    @BindView(R.id.userpic)
    CircularImageView userPic;

    @BindView(R.id.user_name)
    TextView userName;

    @BindView(R.id.message_text)
    TextView msgbody;

    @BindView(R.id.message_time)
    RelativeTimeTextView msgTime;


    public ChatViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);


    }

    public void bind(Context ctx, Chat chat, String userID) {
        context = ctx;
        userName.setText(chat.getmName());
        msgbody.setText(chat.getmMessage());
        msgTime.setReferenceTime(chat.getMessageTime().toDate().getTime());
        Picasso.get().load(chat.getmUserPic()).into(userPic);

        if (userID.equalsIgnoreCase(chat.getmUid())) {
            incomingChat.setVisibility(View.INVISIBLE);
            outgoingChat.setVisibility(View.VISIBLE);
        } else {
            incomingChat.setVisibility(View.VISIBLE);
            outgoingChat.setVisibility(View.INVISIBLE);
        }


    }
}