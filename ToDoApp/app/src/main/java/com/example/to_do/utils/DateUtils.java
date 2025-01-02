package com.example.to_do.utils;

import android.icu.text.SimpleDateFormat;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    private static Calendar calendar;
    private static final SimpleDateFormat format24 = new SimpleDateFormat("HH:mm");
    private static final SimpleDateFormat format12 = new SimpleDateFormat("hh:mm a");

    public static ArrayList<Date> getAllDatesFromNowToNextFiveMonths() {
        ArrayList<Date> dates = new ArrayList<>();
        calendar = Calendar.getInstance(); // Get the current date

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 6); // Add 6 months to the current date

        while (calendar.before(endDate)) {
            dates.add(calendar.getTime()); // Add the current date to the list
            calendar.add(Calendar.DATE, 1); // Increment by one day
        }

        return dates;
    }

    public static String convertStringToTime12(String time) {
        try {
            return format12.format(format24.parse(time));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String add30M(String time) {
        try {
            calendar.setTime(format12.parse(convertStringToTime12(time)));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        calendar.add(Calendar.MINUTE, 30);
        return format12.format(calendar.getTime());
    }
}
