package com.example.alarm.manager.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateOperations {

    public static String convertDate(String date){
        DateFormat simple = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault());
        Date result = new Date(date);

        return simple.format(result);
    }

    public static String getTime(String date){
        DateFormat simple = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date result = new Date(date);

        return simple.format(result);
    }
}
