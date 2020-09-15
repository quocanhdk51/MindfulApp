package com.example.buiquocanh.mindfulapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MindfulBreathingCounter extends AppCompatActivity {
    private MindfulDatabaseHelper databaseHelper;
    private long dayTime, weekTime, monthTime;
    TextView day, week, month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mindful_breathing_counter);


        Toolbar mindfulBreathingActivityToolBar = findViewById(R.id.myToolBar);
        setSupportActionBar(mindfulBreathingActivityToolBar);
        TextView title = mindfulBreathingActivityToolBar.findViewById(R.id.toolbarTitle);
        title.setText("MindFul Breathing");
        title.setTextSize(15);
        title.setTextColor(Color.WHITE);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        day = findViewById(R.id.timeDayTextView);
        week = findViewById(R.id.timeWeekTextView);
        month = findViewById(R.id.timeMonthTextView);

        databaseHelper = new MindfulDatabaseHelper(this);
        refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mbcMenuInflater = getMenuInflater();
        mbcMenuInflater.inflate(R.menu.mbc_item_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mindfulBreathingHistory:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void mindBreathingCounterClicked(View view) {
        Intent openCounter = new Intent(MindfulBreathingCounter.this, Timer.class);

        startActivity(openCounter);
    }

    private void getData() {
        Cursor data = databaseHelper.getMindfulBreathingTimeData();
        data.moveToLast();
        dayTime = data.getLong(1);
        weekTime = data.getLong(2);
        monthTime = data.getLong(3);
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private String convertSecondsToActualTime(long time) {
        long hour, min, sec;
        hour = time/3600;
        min = (time/60)%60;
        sec = time%60;
        return Long.toString(hour)+":"+Long.toString(min)+":"+Long.toString(sec);
    }

    private void disPlayTime() {
        month.setText(convertSecondsToActualTime(monthTime));
        week.setText(convertSecondsToActualTime(weekTime));
        day.setText(convertSecondsToActualTime(dayTime));
    }

    //getting data and display on screen
    private void refresh() {
        if (databaseHelper.isEmpty()) {
            dayTime = 0;
            weekTime = 0;
            monthTime = 0;
        }
        else
            getData();

        disPlayTime();
    }

}
