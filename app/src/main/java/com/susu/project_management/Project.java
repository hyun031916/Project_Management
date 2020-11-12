package com.susu.project_management;

import java.text.DateFormat;

public class Project {
    String title;
    DateFormat date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DateFormat getDate() {
        return date;
    }

    public void setDate(DateFormat date) {
        this.date = date;
    }
}
