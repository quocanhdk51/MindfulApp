package com.example.buiquocanh.mindfulapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MonthlyMindfulTimerAlarmReceiver extends BroadcastReceiver {
    private MindfulDatabaseHelper mindfulDatabaseHelper;
    private long dayTime, weekTime, monthTime;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w("I am the receiver", "Alarm callback trigger");
        mindfulDatabaseHelper = new MindfulDatabaseHelper(context);

        handleTime();
        addItem(dayTime, weekTime, 0, getCurrentTime());
    }

    private void getData() {
        Cursor data = mindfulDatabaseHelper.getMindfulBreathingTimeData();
        data.moveToLast();
        dayTime = data.getLong(1);
        weekTime = data.getLong(2);
        monthTime = data.getLong(3);
    }

    private String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy:MM:dd-HH:mm:ss");
        return mdformat.format(calendar.getTime());
    }

    private void addItem(long day, long week, long month, String time) {
        boolean successAddingData = mindfulDatabaseHelper.addDataForMindfulBreathingTime(day,week,month,time);
        if (successAddingData)
            System.out.println("success");
        else
            System.err.println("fail adding Data");
    }

    private void handleTime() {
        if (mindfulDatabaseHelper.isEmpty()) {
            dayTime = 0;
            weekTime = 0;
            monthTime = 0;
        }
        else
            getData();
    }
}
