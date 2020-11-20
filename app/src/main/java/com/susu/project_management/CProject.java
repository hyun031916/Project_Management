package com.susu.project_management;

public class CProject {
    String email;
    String title;
    String date;
    String description;
    String user;

    public CProject(String email, String title, String date, String description, String user) {
        this.email = email;
        this.title = title;
        this.date = date;
        this.description = description;
        this.user = user;
    }

    public CProject() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
