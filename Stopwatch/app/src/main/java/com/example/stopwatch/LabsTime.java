package com.example.stopwatch;

public class LabsTime {
    public static int id = 0;
    private String diffTime, time;

    public LabsTime(String diffTime, String time) {
        this.id++;
        this.diffTime = diffTime;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiffTime() {
        return diffTime;
    }

    public void setDiffTime(String diffTime) {
        this.diffTime = diffTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
