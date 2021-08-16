package com.example.vsga_mp_catatan_harian;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "republic";
    private static final int DB_VER = 1;
    private static final String DB_TABLE = "Task";
    private static final String DB_COLUM = "TaskName";

    public DbHelper (Context context) {
        super(context, DB_NAME, null, DB_VER);

    }

    @Override
    public void onCreate (SQLiteDatabase dbsqlite) {
        String query = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AITOINCREMENT, %s TEXT NOT NULL", DB_TABLE,DB_COLUM);
        dbsqlite.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase dbsqlite, int oldVersion, int newVersion) {
        String query = String.format("DELETE TABLE IF EXISTS %s", DB_TABLE);
        dbsqlite.execSQL(query);
        onCreate(dbsqlite);
    }

    public void insertNewTask(String task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COLUM, task);

        db.insertWithOnConflict(DB_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();

    }

    public void deleteTask(String task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE, DB_COLUM + " = ?", new String[] {task});
        db.close();

    }

    public ArrayList<String>getTaskList() {
        ArrayList<String>taskList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[]{DB_COLUM}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(DB_COLUM);
            taskList.add(cursor.getString(index));
        }
        cursor.close();
        db.close();
        return taskList;
    }
}
