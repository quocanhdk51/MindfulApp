package com.example.buiquocanh.mindfulapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private DataBaseHelper dataBaseHelper;
    private AlarmManager dailyMindfulTimerAlarmManager, weeklyMindfulTimerAlarmManager, monthlyMindfulTimerAlarmManager, tdlAlarmManager;
    private PendingIntent dailyMindfulAlarmIntent, weeklyMindfulAlarmIntent, monthlyMindfulAlarmIntent, tdlAlarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBaseHelper = new DataBaseHelper(this);
        dataBaseHelper.addData("0", "15 Minutes Mindful Breathing");
        dataBaseHelper.addData("0", "15 Minutes Bedtime Retrospective");
        //initiateDailyMindfulAlarm();
        //initiateWeeklyMindfulAlarm();
        //initiateMonthlyMindfulAlarm();
        //initiateTDLAlarm();
    }

    public void mindBreathingCounterClicked(View view) {
        Intent mindfulBreathingCounterActivity = new Intent(MainActivity.this, MindfulBreathingCounter.class);

        startActivity(mindfulBreathingCounterActivity);
    }

    public void toDoListClicked(View view) {
        Intent toDoListActivity = new Intent(MainActivity.this, ToDoList.class);

        startActivity(toDoListActivity);

    }


    //The four method below is to handle the alarm manager and call the receiver class to trigger some event at certain time
    //For now, for some unknown reason, these four function are not working
    private void initiateDailyMindfulAlarm() {
        dailyMindfulTimerAlarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, DailyMindfulTimerAlarmReceiver.class);
        dailyMindfulAlarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        //calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 3);
        calendar.set(Calendar.MINUTE,59);


        dailyMindfulTimerAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, dailyMindfulAlarmIntent);
    }

    private void initiateWeeklyMindfulAlarm() {
        weeklyMindfulTimerAlarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, WeeklyMindfulTimerAlarmReceiver.class);
        weeklyMindfulAlarmIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        Calendar calendar = Calendar.getInstance();
        //calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_WEEK, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 3);
        calendar.set(Calendar.MINUTE, 59);



        weeklyMindfulTimerAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, weeklyMindfulAlarmIntent);
    }

    private void initiateMonthlyMindfulAlarm() {
        monthlyMindfulTimerAlarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, MonthlyMindfulTimerAlarmReceiver.class);
        monthlyMindfulAlarmIntent = PendingIntent.getBroadcast(this, 2, intent, 0);

        Calendar calendar = Calendar.getInstance();
        //calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 3);
        calendar.set(Calendar.MINUTE, 59);



        monthlyMindfulTimerAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, monthlyMindfulAlarmIntent);
    }

    private void initiateTDLAlarm() {
        tdlAlarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, tdlAlarmReceiver.class);
        tdlAlarmIntent = PendingIntent.getBroadcast(this, 3, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 3);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);



        tdlAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, tdlAlarmIntent);
    }
}
