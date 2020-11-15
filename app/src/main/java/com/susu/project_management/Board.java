package com.susu.project_management;

public class Board {
    String title;
    String date;
    String description;
    String user;
    int type;

    public Board() {
    }

    public Board(String title, String date, String description, String user, int layouttype) {
        this.title = title;
        this.date = date;
        this.description = description;
        this.user = user;
        this.type = layouttype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
