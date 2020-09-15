package com.example.buiquocanh.mindfulapp;

import android.database.Cursor;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Timer extends AppCompatActivity {
    private Chronometer chronometer;
    private boolean running;
    private long pauseOffset;
    private MindfulDatabaseHelper dataBaseHelper;
    private long dayTime, weekTime, monthTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        Toolbar timerActivityToolBar = findViewById(R.id.myToolBar);
        setSupportActionBar(timerActivityToolBar);
        TextView title = timerActivityToolBar.findViewById(R.id.toolbarTitle);
        title.setText("MindFul Breathing Timer");
        title.setTextSize(15);
        title.setTextColor(Color.WHITE);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        running = false;
        chronometer = findViewById(R.id.timer);
        dataBaseHelper = new MindfulDatabaseHelper(this);

    }

    public void startTimer(View view) {
        if (!running) {
            pauseOffset = 0;
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
            getCurrentTime();
        }
    }

    public void stopTimer(View view) {
        if (running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
            long timeElasped = (SystemClock.elapsedRealtime() - chronometer.getBase())/1000;
            handleTime(timeElasped);
            addItem(dayTime, weekTime, monthTime, getCurrentTime());
            toastMessage("You have mindfully breathed for " + timeElasped);
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void getData() {
        Cursor data = dataBaseHelper.getMindfulBreathingTimeData();
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
        boolean successAddingData = dataBaseHelper.addDataForMindfulBreathingTime(day,week,month,time);
        if (successAddingData)
            Log.d("success", "success adding data");
        else
            Log.d("fail","fail adding Data");
    }

    private void handleTime(long timeElasped) {
        if (dataBaseHelper.isEmpty()) {
            dayTime = 0;
            weekTime = 0;
            monthTime = 0;
        }
        else
            getData();
        dayTime += timeElasped;
        weekTime += timeElasped;
        monthTime += timeElasped;
    }
}
