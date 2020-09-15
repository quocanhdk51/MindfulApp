package com.example.buiquocanh.mindfulapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//This is the database helper to create database, insert data and fetch data for mindful breathing timer
public class MindfulDatabaseHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "TEST2";
    private static final String TIMER_COL1 = "DAILY", TIMER_COL2 = "WEEKLY", TIMER_COL3 = "MONTHLY", TIMER_COL0 = "ID", TIMER_COL4 = "TIME";

    public MindfulDatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, DAILY BIGINT, WEEKLY BIGINT, MONTHLY BIGINT, TIME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addDataForMindfulBreathingTime(long daily, long weekly, long monthly, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TIMER_COL1, daily);
        contentValues.put(TIMER_COL2, weekly);
        contentValues.put(TIMER_COL3, monthly);
        contentValues.put(TIMER_COL4, time);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getMindfulBreathingTimeData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public boolean isEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM " + TABLE_NAME;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int itemNumber = mcursor.getInt(0);
        if (itemNumber > 0)
            return false;
        else
            return true;
    }

}
