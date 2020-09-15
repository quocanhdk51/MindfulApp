package com.example.buiquocanh.mindfulapp;

public class Task {
    private boolean status;
    private String activityName;

    public Task(boolean status, String activityName) {
        this.status = status;
        this.activityName = activityName;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}
