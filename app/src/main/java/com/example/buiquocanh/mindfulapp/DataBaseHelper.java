package com.example.buiquocanh.mindfulapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//This is the database helper for todo list
public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "ACTIVITY";
    private static final String COL1 = "STATUS";
    private static final String COL2 = "TASK";

    public DataBaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
        //addDataForMindfulBreathingTime("0", "0", "0");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, STATUS TEXT, TASK TEXT UNIQUE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String status, String task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, status);
        contentValues.put(COL2, task);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;

    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateTaskStatus(String status, String taskName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL1 + " = '" + status + "' WHERE " + COL2 + " = '" + taskName + "'";
        db.execSQL(query);
    }

    public void deleteData(String taskName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL2 + " = '" + taskName + "'";
        db.execSQL(query);
    }

    public void editTask(String newTaskName, String oldTaskName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 + " = '" + newTaskName + "' WHERE " + COL2 + " = '" + oldTaskName + "'";
        db.execSQL(query);
    }

}
