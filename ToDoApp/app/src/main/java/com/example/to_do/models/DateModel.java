package com.example.to_do.models;

public class DateModel {
    private String month, day, year, numberDay;

    public DateModel(String day, String month, String numberDay, String year) {
        this.month = month;
        this.day = day;
        if (numberDay.charAt(0) == '0') this.numberDay = numberDay.substring(1);
        else this.numberDay = numberDay;
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public String getNumberDay() {
        return numberDay;
    }

    public String getYear() {
        return year;
    }

    public String getNumberMonth() {
        switch (month) {
            case "JAN":
                return "1";
            case "FEB":
                return "2";
            case "MAR":
                return "3";
            case "APR":
                return "4";
            case "MAY":
                return "5";
            case "JUN":
                return "6";
            case "JUL":
                return "7";
            case "AUG":
                return "8";
            case "SEP":
                return "9";
            case "OCT":
                return "10";
            case "NOV":
                return "11";
            case "DEC":
                return "12";
        }
        return "";
    }

}
