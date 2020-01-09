package com.example.ecampus.models;

public class Chat {
    private String mName;
    private String mMessage;
    private String mUid;
    private long messageTime;

    public Chat() {

    }  // Needed for Firebase

    public Chat(String mName, String mMessage, String mUid, long messageTime) {
        this.mName = mName;
        this.mMessage = mMessage;
        this.mUid = mUid;
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

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
