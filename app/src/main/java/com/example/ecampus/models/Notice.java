package com.example.ecampus.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Notice {

    private String category;
    private String noticetext;
   // private Date date;

    public Notice(){

    }

    public Notice(String category, String noticetext) {
        this.category = category;
        this.noticetext = noticetext;
       // this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public String getNoticetext() {
        return noticetext;
    }

   // public Date getDate() {
       /// return date;
   // }

}
  //  SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
  // sfd.format(new Date(timestamp))