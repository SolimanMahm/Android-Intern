package com.example.to_do.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.to_do.models.TaskModel;

import java.util.ArrayList;

public class MyDatabase extends SQLiteOpenHelper {
    public static final String DB_NAME = "ToDo_db";
    public static final int DB_VERSION = 1;
    public static final String TASK_TB_NAME = "task";
    public static final String TASK_CLN_ID = "id";
    public static final String TASK_CLN_DATE = "date";
    public static final String TASK_CLN_TITLE = "title";
    public static final String TASK_CLN_NOTE = "note";
    public static final String TASK_CLN_START_TIME = "startTime";
    public static final String TASK_CLN_END_TIME = "endTime";
    public static final String TASK_CLN_COLOR = "color";
    public static final String TASK_CLN_IS_COMPLETED = "isCompleted";

    private SQLiteDatabase sqLiteDatabase;
    private ContentValues values;

    public MyDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        values = new ContentValues();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TASK_TB_NAME + " (" + TASK_CLN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TASK_CLN_DATE + " TEXT, " + TASK_CLN_TITLE + " TEXT, " + TASK_CLN_NOTE + " TEXT, " + TASK_CLN_START_TIME + " TEXT, " + TASK_CLN_END_TIME + " TEXT, " + TASK_CLN_COLOR + " TEXT, " + TASK_CLN_IS_COMPLETED + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TASK_TB_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertTask(TaskModel task) {
        sqLiteDatabase = getWritableDatabase();

        values.put(TASK_CLN_DATE, task.getDate());
        values.put(TASK_CLN_TITLE, task.getTitle());
        values.put(TASK_CLN_NOTE, task.getNote());
        values.put(TASK_CLN_START_TIME, task.getStartTime());
        values.put(TASK_CLN_END_TIME, task.getEndTime());
        values.put(TASK_CLN_COLOR, task.getColor());
        values.put(TASK_CLN_IS_COMPLETED, task.getIsCompleted());

        return sqLiteDatabase.insert(TASK_TB_NAME, null, values) != -1;
    }

    public boolean updateTask(int id) {
        sqLiteDatabase = getWritableDatabase();

        values.put(TASK_CLN_IS_COMPLETED, 1);

        return sqLiteDatabase.update(TASK_TB_NAME, values, TASK_CLN_ID + "=?",
                new String[]{String.valueOf(id)}) > 0;
    }

    public boolean deleteTask(int id) {
        sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.delete(TASK_TB_NAME, TASK_CLN_ID + "=?",
                new String[]{String.valueOf(id)}) > 0;
    }

    public ArrayList<TaskModel> getAllTasks(String date) {
        ArrayList<TaskModel> tasks = new ArrayList<>();
        sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TASK_TB_NAME + " WHERE " + TASK_CLN_DATE + "=?",
                new String[]{date});

        while (cursor.moveToNext()) {
            tasks.add(constructTaskFromCursor(cursor));
        }
        cursor.close();
        return tasks;
    }

    public TaskModel searchTask(String date, String time) {
        sqLiteDatabase = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TASK_TB_NAME + " WHERE " + TASK_CLN_DATE + "=? AND "
                            + TASK_CLN_START_TIME + "=? AND " + TASK_CLN_IS_COMPLETED + "=0",
                    new String[]{date, time});
            if (cursor != null && cursor.moveToNext()) {
                return constructTaskFromCursor(cursor);
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return null;
    }

    private TaskModel constructTaskFromCursor(Cursor cursor) {
        return new TaskModel(
                cursor.getInt(cursor.getColumnIndexOrThrow(TASK_CLN_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(TASK_CLN_TITLE)),
                cursor.getString(cursor.getColumnIndexOrThrow(TASK_CLN_NOTE)),
                cursor.getString(cursor.getColumnIndexOrThrow(TASK_CLN_START_TIME)),
                cursor.getString(cursor.getColumnIndexOrThrow(TASK_CLN_END_TIME)),
                cursor.getString(cursor.getColumnIndexOrThrow(TASK_CLN_DATE)),
                cursor.getInt(cursor.getColumnIndexOrThrow(TASK_CLN_COLOR)),
                cursor.getInt(cursor.getColumnIndexOrThrow(TASK_CLN_IS_COMPLETED))
        );
    }
}
