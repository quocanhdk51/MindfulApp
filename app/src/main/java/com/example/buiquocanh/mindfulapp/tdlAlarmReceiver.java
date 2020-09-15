package com.example.buiquocanh.mindfulapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

public class tdlAlarmReceiver extends BroadcastReceiver {
    private DataBaseHelper dataBaseHelper;
    private String taskName;
    @Override
    public void onReceive(Context context, Intent intent) {
        dataBaseHelper = new DataBaseHelper(context);
        refreshTaskStatus();
    }

    private void refreshTaskStatus() {
        Cursor data = dataBaseHelper.getData();
        while (data.moveToNext()) {
            taskName = data.getString(2);
            dataBaseHelper.updateTaskStatus("0", taskName);
        }
    }
}
