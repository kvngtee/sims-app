package com.example.ecampus.models;

import com.google.firebase.Timestamp;

public class Chat {
    private String mName;
    private String mMessage;
    private String mUid;
    private String mUserPic;
    private Timestamp messageTime;

    public Chat() {

    }  // Needed for Firebase

    public Chat(String mName, String mMessage, String mUid, String mUserPic, Timestamp messageTime) {
        this.mName = mName;
        this.mMessage = mMessage;
        this.mUid = mUid;
        this.mUserPic = mUserPic;
        this.messageTime = messageTime;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getmUid() {
        return mUid;
    }

    public void setmUid(String mUid) {
        this.mUid = mUid;
    }

    public String getmUserPic() {
        return mUserPic;
    }

    public void setmUserPic(String mUserPic) {
        this.mUserPic = mUserPic;
    }

    public Timestamp getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Timestamp messageTime) {
        this.messageTime = messageTime;
    }
}
