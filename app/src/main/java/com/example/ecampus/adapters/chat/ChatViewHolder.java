package com.example.ecampus.adapters.chat;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecampus.R;
import com.example.ecampus.models.Chat;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

class ChatViewHolder extends RecyclerView.ViewHolder {

    CircularImageView inUserPic, outUserPic;
    TextView inUserName, outUserName, inMsgText, outMsgText;
    RelativeTimeTextView inMsgTime, outMsgTime;
    LinearLayout incomingChat, outGoingChat;

    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);
        inUserName = itemView.findViewById(R.id.in_userName);
        inUserPic = itemView.findViewById(R.id.in_userPic);
        inMsgText = itemView.findViewById(R.id.in_message_text);
        inMsgTime = itemView.findViewById(R.id.in_message_time);
        outUserName = itemView.findViewById(R.id.out_user_name);
        outUserPic = itemView.findViewById(R.id.out_userpic);
        outMsgText = itemView.findViewById(R.id.out_message_text);
        outMsgTime = itemView.findViewById(R.id.out_message_time);
        incomingChat = itemView.findViewById(R.id.incomingChat);
        outGoingChat = itemView.findViewById(R.id.outgoingChat);

    }

    public void bind(Chat chat, String currentUserID) {
        inMsgText.setText(chat.getmMessage());
        outMsgText.setText(chat.getmMessage());
        inMsgTime.setReferenceTime(chat.getMessageTime().toDate().getTime());
        outMsgTime.setReferenceTime(chat.getMessageTime().toDate().getTime());
        inUserName.setText(chat.getmName());
        outUserName.setText(chat.getmName());
        Picasso.get().load(chat.getmUserPic()).fit().centerCrop().into(inUserPic);
        Picasso.get().load(chat.getmUserPic()).fit().centerCrop().into(outUserPic);

        if (currentUserID.equalsIgnoreCase(chat.getmUid())) {
            incomingChat.setVisibility(View.GONE);
            outGoingChat.setVisibility(View.VISIBLE);
        } else {
            incomingChat.setVisibility(View.VISIBLE);
            outGoingChat.setVisibility(View.GONE);

        }
    }
}
