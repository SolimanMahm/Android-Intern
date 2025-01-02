package com.example.to_do.models;

public class TaskModel {
    private String title, note, startTime, endTime, date;
    private int isCompleted, color, id;

    public TaskModel(String title, String note, String startTime, String endTime, String date, int color, int isCompleted) {
        this.title = title;
        this.note = note;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.color = color;
        this.isCompleted = isCompleted;
    }

    public TaskModel(int id, String title, String note, String startTime, String endTime, String date, int color, int isCompleted) {
        this.title = title;
        this.note = note;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.isCompleted = isCompleted;
        this.color = color;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDate() {
        return date;
    }

    public int getColor() {
        return color;
    }

    public int getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(int isCompleted) {
        this.isCompleted = isCompleted;
    }
}
