package com.example.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper{

    public DbHelper(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Constants.DROP_TABLE);
        onCreate(db);
    }

    public long insertTask(TaskModel taskModel){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.COLUMN_TASK,taskModel.getTaskName());
        values.put(Constants.COLUMN_STATUS,taskModel.getStatus());

        long id = database.insert(Constants.TABLE_NAME,null,values);
        database.close();
        return id;
    }

    public ArrayList<TaskModel> getData(){
        ArrayList<TaskModel> taskModels = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(Constants.SELECT_TABLE,null);
        if (cursor.moveToFirst()){
            do {
                TaskModel taskModel = new TaskModel();
                taskModel.setTaskId(cursor.getInt(cursor.getColumnIndexOrThrow(Constants.COLUMN_ID)));
                taskModel.setTaskName(cursor.getString(cursor.getColumnIndexOrThrow(Constants.COLUMN_TASK)));
                taskModel.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow(Constants.COLUMN_STATUS)));
                taskModels.add(taskModel);
            }while (cursor.moveToNext());
        }
        return taskModels;
    }

    public void updateTask(TaskModel taskModel){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.COLUMN_STATUS,taskModel.getStatus());
        database.update(Constants.TABLE_NAME,contentValues,
                Constants.COLUMN_ID +"=?",
                new String[]{String.valueOf(taskModel.getTaskId())});
    }

    public void deleteTask(long id){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(Constants.TABLE_NAME,
                Constants.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)});
    }


}
