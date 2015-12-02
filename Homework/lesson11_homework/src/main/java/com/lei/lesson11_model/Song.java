package com.lei.lesson11_model;

import java.io.Serializable;

public class Song implements Serializable {
    private String sname;
    private String url;
    private int duration;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getUrl() {
        return url;
    }

    public String getSname() {
        return sname;
    }

    public int getDuration() {
        return duration;
    }

    public Song() {
    }

    public Song(int duration, String sname, String url) {
        this.duration = duration;
        this.sname = sname;
        this.url = url;
    }
}
