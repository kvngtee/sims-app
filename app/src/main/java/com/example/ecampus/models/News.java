package com.example.ecampus.models;

import java.util.Date;

public class News {

    private String id;
    private String image;
    private String title;
    private String desc;
    private Date date;

    public News(String id, String image, String title, String desc, Date date) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.desc = desc;
        this.date = date;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public News(){

    }
}
